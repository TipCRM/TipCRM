package com.tipcrm.service.impl;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.tipcrm.bo.MenuBo;
import com.tipcrm.cache.MenuCache;
import com.tipcrm.dao.entity.Menu;
import com.tipcrm.dao.repository.MenuRepository;
import com.tipcrm.service.MenuService;
import com.tipcrm.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
@Transactional
public class MenuServiceImpl implements MenuService {

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private MenuRepository menuRepository;

    public List<Menu> findMenuByPermissionIds(Set<Integer> permissionIds) {
        List<Menu> all = MenuCache.getMenus();
        List<Menu> res = new ArrayList<>();
        for (Menu menu : all) {
            if (menu.getPermission() == null || permissionIds.contains(menu.getPermission().getId())) {
                res.add(menu);
            }
        }
        return res;
    }


    @Override
    public List<MenuBo> getAllMenus() {
        List<Menu> menus = MenuCache.getMenus();
        return convertToMenuBo(menus, null);
    }

    @Override
    public List<MenuBo> findMenuByUserId(Integer userId) {
        Set<Integer> permissions = permissionService.getPermissionIdsByUserId(userId);
        List<Menu> menus = new ArrayList<>();
        if (!CollectionUtils.isEmpty(permissions)) {
            menus = findMenuByPermissionIds(permissions);
        }
        if (CollectionUtils.isEmpty(menus)) {
            return new ArrayList<>();
        }
        return convertToMenuBo(menus, null);
    }

    public List<MenuBo> convertToMenuBo(List<Menu> menus, Integer parentId) {
        List<MenuBo> menuBos = new ArrayList<>();
        for (Menu menu : menus) {
            Integer currentParentId = null;
            if (menu.getParent() != null) {
                currentParentId = menu.getParent().getId();
            }
            if (currentParentId == null && parentId == null
                || parentId != null && parentId.equals(currentParentId)) {
                menuBos.add(
                    new MenuBo(menu.getId(), menu.getName(), menu.getDisplayName(), null, menu.getIcon(), menu.getUrl(), convertToMenuBo(menus, menu.getId()),
                               menu.getActive()));
            }
        }
        return menuBos;
    }

    @Override
    public List<Menu> flatMenu(List<Menu> all, List<Menu> menus) {
        List<Menu> menuList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(all) && !CollectionUtils.isEmpty(menus)) {
            for (Menu menu : menus) {
                menuList.add(menu);
                menuList.addAll(findChildren(all, menu));
            }
        }
        return menuList;
    }

    @Override
    public void deactiveMenu(List<Integer> ids) {
        List<Menu> menus = MenuCache.getMenus();
        List<Menu> deactiveMenus = new ArrayList<>();
        for (Menu menu : menus) {
            if (ids.contains(menu.getId())) {
                deactiveMenus.addAll(deactiveOrActiveMenu(menus, menu, false));
            } else {
                deactiveOrActiveMenu(menus, menu, true);
            }
        }
        MenuCache.setMenus(menus);
        MenuCache.setDeactiveMenus(deactiveMenus);
        menuRepository.save(menus);
    }

    private List<Menu> deactiveOrActiveMenu(List<Menu> menus, Menu parentMenu, Boolean active) {
        List<Menu> res = new ArrayList<>();
        parentMenu.setActive(active);
        res.add(parentMenu);
        for (Menu menu : menus) {
            if (menu.getParent() != null && menu.getParent().getId().equals(parentMenu.getId())) {
                res.addAll(deactiveOrActiveMenu(menus, menu, active));
            }
        }
        return res;
    }

    public List<Menu> findChildren(List<Menu> menus, Menu menu) {
        List<Menu> menusList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(menus)) {
            for (Menu m : menus) {
                if (m.getParent() != null && m.getParent().getId().equals(menu.getId())) {
                    menusList.add(m);
                    menusList.addAll(findChildren(menus, m));
                }
            }
        }
        return menusList;
    }


}
