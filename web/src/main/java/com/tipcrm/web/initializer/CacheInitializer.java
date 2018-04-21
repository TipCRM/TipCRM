package com.tipcrm.web.initializer;

import com.tipcrm.cache.*;
import com.tipcrm.dao.entity.*;
import com.tipcrm.dao.repository.*;
import com.tipcrm.service.MenuService;
import com.tipcrm.service.PermissionService;
import com.tipcrm.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Component
@Order(value = 1)
public class CacheInitializer implements CommandLineRunner {

    @Autowired
    private ListBoxRepository listBoxRepository;

    @Autowired
    private ConfigurationRepository configurationRepository;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private MenuPermissionRepository menuPermissionRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    private Logger logger = LoggerFactory.getLogger(CacheInitializer.class);

    @Override
    public void run(String... args) {
        logger.info("Initializing cache data...");
        initListBoxCache();
        initConfigurationCache();
        initMenuCache();
        initRoleAndPermissionCache();
    }

    public void initListBoxCache() {
        logger.info("Initializing list box cache data...");
        List<ListBox> listBoxes = listBoxRepository.findAll();
        ListBoxCache.addOrUpdateCache(listBoxes);
    }

    public void initConfigurationCache() {
        logger.info("Initializing configuration cache data...");
        List<Configuration> configurations = configurationRepository.findAll();
        if (!CollectionUtils.isEmpty(configurations)) {
            ConfigurationCache.pushConfigurations(configurations);
        }
    }

    public void initMenuCache() {
        logger.info("Initializing menu cache data...");
        List<Menu> menus = menuRepository.findAllOrderBySequence();
        List<Menu> deactiveMenus = new ArrayList<>();
        for (Menu menu : menus) {
            if (!menu.getActive()) {
                deactiveMenus.add(menu);
            }
        }
        deactiveMenus = menuService.flatMenu(menus, deactiveMenus);
        MenuCache.setMenus(menus);
        MenuCache.setDeactiveMenus(deactiveMenus);
        List<MenuPermission> menuPermissions = menuPermissionRepository.findAll();
        MenuCache.setMenuPermissions(menuPermissions);
    }

    public void initRoleAndPermissionCache() {
        logger.info("Initializing role and permission cache data...");
        List<Role> roles = roleRepository.findAll();
        RoleCache.setAllRole(roles);
        PermissionCache.setAllPermissions(permissionRepository.findAll());

        PermissionCache.setRolePermissions(roleService.getAllRolePermissionMap());
        List<MenuPermission> menuPermissions = menuPermissionRepository.findAll();
        PermissionCache.setMenuPermissions(menuPermissions);

        List<Menu> menus = MenuCache.getDeactiveMenus();
        if (CollectionUtils.isEmpty(menus)) {
            return;
        }
        List<Permission> permissions = new ArrayList<>();
        for (Menu menu : menus) {
            if (menu.getPermission() != null) {
                permissions.add(menu.getPermission());
            }
        }
        List<Permission> allPermissions = permissionService.flatPermission(permissions);
        PermissionCache.setDeactivePermissions(allPermissions);
    }

}