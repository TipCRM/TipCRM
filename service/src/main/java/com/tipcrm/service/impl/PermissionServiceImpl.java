package com.tipcrm.service.impl;

import com.google.common.collect.Sets;
import com.tipcrm.bo.MenuPermissionBo;
import com.tipcrm.bo.PermissionBo;
import com.tipcrm.cache.MenuCache;
import com.tipcrm.cache.PermissionCache;
import com.tipcrm.dao.entity.*;
import com.tipcrm.dao.repository.RolePermissionRepository;
import com.tipcrm.exception.BizException;
import com.tipcrm.service.PermissionService;
import com.tipcrm.service.RoleService;
import com.tipcrm.service.WebContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private RoleService roleService;

    @Autowired
    private RolePermissionRepository rolePermissionRepository;

    @Autowired
    private WebContext webContext;

    @Override
    public Set<String> getPermissionValuesByUserId(Integer userId) {
        Set<PermissionBo> permissionBos = getPermissionsByUserId(userId);
        Set<String> permissions = new HashSet<>();
        if (!CollectionUtils.isEmpty(permissionBos)) {
            permissions = permissionBos.stream().map(PermissionBo::getValue).collect(Collectors.toSet());
        }
        return permissions;
    }

    @Override
    public Set<String> getPermissionNamesByUserId(Integer userId) {
        Set<PermissionBo> permissionBos = getPermissionsByUserId(userId);
        Set<String> permissions = new HashSet<>();
        if (!CollectionUtils.isEmpty(permissionBos)) {
            permissions = permissionBos.stream().map(PermissionBo::getName).collect(Collectors.toSet());
        }
        return permissions;
    }

    public Set<PermissionBo> getPermissionsByUserId(Integer userId) {
        List<MenuPermissionBo> groups = getMenuPermissionsByUserId(userId);
        Set<PermissionBo> permissions = new HashSet<>();
        if (!CollectionUtils.isEmpty(groups)) {
            for (MenuPermissionBo group : groups) {
                permissions.addAll(group.getPermissions().stream().filter(p -> p.getChecked()).collect(Collectors.toList()));
            }
        }
        List<Menu> deactive = MenuCache.getDeactiveMenus();
        if (!CollectionUtils.isEmpty(deactive)) {
            for (Menu menu : deactive) {
                if (menu.getPermission() != null) {
                    List<Permission> deactivePermission = flatPermission(menu.getPermission());
                    if (!CollectionUtils.isEmpty(deactivePermission)) {
                        permissions.removeAll(deactivePermission.stream().collect(Collectors.toList()));
                    }
                }
            }
        }
        return permissions;
    }

    @Override
    public Set<Integer> getPermissionIdsByUserId(Integer userId) {
        List<MenuPermissionBo> groups = getMenuPermissionsByUserId(userId);
        Set<Integer> permissions = new HashSet<>();
        if (!CollectionUtils.isEmpty(groups)) {
            for (MenuPermissionBo group : groups) {
                permissions.addAll(group.getPermissions().stream().filter(p -> p.getChecked()).map(p -> p.getId()).collect(Collectors.toList()));
            }
        }
        return permissions;
    }

    @Override
    public List<MenuPermissionBo> getMenuPermissionsByUserId(Integer userId) {
        List<MenuPermission> groups = PermissionCache.getMenuPermissions();
        List<MenuPermissionBo> groupBos = MenuPermissionBo.toMenuPermissionBos(groups);
        List<PermissionBo> allPermission = flatMenuPermission(groupBos);
        List<Role> myRoles = roleService.getRolesByUserId(userId);
        if (!CollectionUtils.isEmpty(myRoles)) {
            Set<Permission> myPermissions = PermissionCache.getPermissions(myRoles.stream().map(role -> role.getId()).collect(Collectors.toList()));
            for (Permission myPermission : myPermissions) {
                for (PermissionBo permission : allPermission) {
                    if (myPermission.getId().equals(permission.getId())) {
                        permission.setChecked(true);
                    }
                }
            }
        }
        return groupMenuPermissionBo(groupBos);
    }

    @Override
    public List<MenuPermissionBo> getPermissionsByRoleId(Integer roleId) {
        List<MenuPermission> groups = PermissionCache.getMenuPermissions();
        List<MenuPermissionBo> groupBos = MenuPermissionBo.toMenuPermissionBos(groups);
        List<PermissionBo> allPermission = flatMenuPermission(groupBos);
        Set<Permission> myPermissions = PermissionCache.getPermissions(roleId);
        if (myPermissions == null) {
            throw new BizException("角色不存在");
        }
        if (!CollectionUtils.isEmpty(myPermissions)) {
            for (Permission myPermission : myPermissions) {
                for (PermissionBo permission : allPermission) {
                    if (myPermission.getId().equals(permission.getId())) {
                        permission.setChecked(true);
                    }
                }
            }
        }
        return groupMenuPermissionBo(groupBos);
    }

    public List<MenuPermissionBo> groupMenuPermissionBo(List<MenuPermissionBo> menuPermissions) {
        List<MenuPermissionBo> res = new ArrayList<>();
        if (!CollectionUtils.isEmpty(menuPermissions)) {
            for (MenuPermissionBo menuPermission : menuPermissions) {
                Boolean isFound = false;
                for (MenuPermissionBo mp : res) {
                    if (mp.getMenuId().equals(menuPermission.getMenuId())) {
                        mp.getPermissions().addAll(menuPermission.getPermissions());
                        isFound = true;
                        break;
                    }
                }
                if (!isFound) {
                    res.add(menuPermission);
                }
            }
        }
        return res;
    }

    @Override
    public void updateRolePermissions(Integer roleId, Set<Integer> permissionIds) {
        List<MenuPermissionBo> menuPermissionBos = getPermissionsByRoleId(roleId);
        List<PermissionBo> permissionBos = flatMenuPermission(menuPermissionBos);
        Set<Integer> existIds = new HashSet<>();
        if (!CollectionUtils.isEmpty(permissionBos)) {
            existIds = permissionBos.stream().filter(permissionBo -> permissionBo.getChecked()).map(permissionBo -> permissionBo.getId()).collect(
                    Collectors.toSet());
        }
        Set<Integer> needRemove = new HashSet<>();
        Set<Integer> needAdd = new HashSet<>();
        for (Integer exist : existIds) {
            if (!permissionIds.contains(exist)) {
                needRemove.add(exist);
            }
        }
        for (Integer permission : permissionIds) {
            if (!existIds.contains(permission)) {
                needAdd.add(permission);
            }
        }
        if (!CollectionUtils.isEmpty(needRemove)) {
            rolePermissionRepository.deleteByRoleIdAndPermissionId(roleId, needRemove);
            PermissionCache.popPermissions(roleId, needRemove);
        }
        if (!CollectionUtils.isEmpty(needAdd)) {
            Role role = roleService.findById(roleId);

            List<Permission> permissions = findByIdIn(needAdd);
            User user = webContext.getCurrentUser();
            Date date = new Date();
            List<RolePermission> rolePermissionList = new ArrayList<>();
            for (Permission permission : permissions) {
                RolePermission rolePermission = new RolePermission();
                rolePermission.setRole(role);
                rolePermission.setPermission(permission);
                rolePermission.setDeletable(true);
                rolePermission.setEntryUser(user);
                rolePermission.setEntryTime(date);
                rolePermissionList.add(rolePermission);
            }
            rolePermissionRepository.save(rolePermissionList);
            PermissionCache.pushPermissions(roleId, Sets.newHashSet(permissions));
        }
    }

    @Override
    public List<Permission> findByIdIn(Set<Integer> ids) {
        List<Permission> res = new ArrayList<>();
        for (Permission p : PermissionCache.getAllPermissions()) {
            if (ids.contains(p.getId())) {
                res.add(p);
            }
        }
        return res;
    }

    @Override
    public List<Permission> flatPermission(List<Permission> permissions) {
        List<Permission> allPermission = PermissionCache.getAllPermissions();
        List<Permission> flat = new ArrayList<>();
        for (Permission permission : permissions) {
            flat.add(permission);
            flat.addAll(getChildren(allPermission, permission));
        }
        return flat;
    }

    public List<Permission> flatPermission(Permission permission) {
        List<Permission> allPermission = PermissionCache.getAllPermissions();
        List<Permission> flat = new ArrayList<>();
        flat.add(permission);
        flat.addAll(getChildren(allPermission, permission));
        return flat;
    }

    private List<PermissionBo> flatMenuPermission(List<MenuPermissionBo> menuPermissionBos) {
        List<PermissionBo> permissionBos = new ArrayList<>();
        for (MenuPermissionBo menuPermissionBo : menuPermissionBos) {
            permissionBos.addAll(menuPermissionBo.getPermissions());
        }
        return permissionBos;
    }

    public List<Permission> getChildren(List<Permission> permissions, Permission permission) {
        List<Permission> permissionList = new ArrayList<>();
        for (Permission per : permissions) {
            if (per.getDependence() != null && per.getDependence().getId().equals(permission.getId())) {
                permissionList.add(per);
                permissionList.addAll(getChildren(permissions, per));
            }
        }
        return permissionList;
    }

    @Override
    public List<String> getMyPermission(Integer menuId) {
        List<String> menuPermissions = MenuCache.getMenuPermissions(menuId).stream().map(menuPermission -> menuPermission.getPermission().getName())
                .collect(Collectors.toList());
        Set<String> myPermission = getPermissionNamesByUserId(webContext.getCurrentUserId());

        menuPermissions.retainAll(myPermission);
        return menuPermissions;
    }

    @Override
    public List<Permission> getPermissionById(List<Integer> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return new ArrayList<>();
        }
        return PermissionCache.getAllPermissions().stream().filter(permission -> ids.contains(permission.getId())).collect(Collectors.toList());
    }
}
