package com.tipcrm.service.impl;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.tipcrm.bo.MenuBo;
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
    private MenuRepository menuRepository;

    @Autowired
    private PermissionService permissionService;

    @Override
    public List<MenuBo> findMenuByUserId(Integer userId) {
        Set<String> permissions = permissionService.getPermissionValueListByUserId(userId);
        List<Menu> menus = menuRepository.findMenuByPermission(permissions);
        if (CollectionUtils.isEmpty(menus)) {
            return new ArrayList<MenuBo>();
        }
        return convertToMenuBo(menus, null);
    }

    public List<MenuBo> convertToMenuBo(List<Menu> menus, Integer parentId) {
        List<MenuBo> menuBos = new ArrayList<MenuBo>();
        for (Menu menu : menus) {
            Integer currentParentId = null;
            if (menu.getParent() != null) {
                currentParentId = menu.getParent().getId();
            }
            if ((currentParentId == null && parentId == null)
                || (parentId != null && parentId.equals(currentParentId))) {
                menuBos.add(new MenuBo(menu.getDisplayName(), menu.getIcon(), menu.getUrl(), convertToMenuBo(menus, menu.getId())));
            }
        }
        return menuBos;
    }
}
