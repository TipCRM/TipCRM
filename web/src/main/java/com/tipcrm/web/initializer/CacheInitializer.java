package com.tipcrm.web.initializer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import com.tipcrm.bo.MenuCacheBo;
import com.tipcrm.bo.PermissionBo;
import com.tipcrm.bo.PermissionGroupBo;
import com.tipcrm.bo.RoleBasicBo;
import com.tipcrm.cache.ConfigurationCache;
import com.tipcrm.cache.DeactiveCache;
import com.tipcrm.cache.ListBoxCache;
import com.tipcrm.cache.PermissionCache;
import com.tipcrm.cache.RoleCache;
import com.tipcrm.dao.entity.Configuration;
import com.tipcrm.dao.entity.ListBox;
import com.tipcrm.dao.entity.Menu;
import com.tipcrm.dao.entity.Permission;
import com.tipcrm.dao.entity.Role;
import com.tipcrm.dao.repository.ConfigurationRepository;
import com.tipcrm.dao.repository.ListBoxRepository;
import com.tipcrm.dao.repository.MenuRepository;
import com.tipcrm.dao.repository.PermissionRepository;
import com.tipcrm.dao.repository.RoleRepository;
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
    private PermissionRepository permissionRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private MenuService menuService;

    private Logger logger = LoggerFactory.getLogger(CacheInitializer.class);

    @Override
    public void run(String... args) {
        logger.info("Initializing cache data...");
        initListBoxCache();
        initConfigurationCache();
        initRoleAndPermissionCache();
        initDeactiveCache();
    }

    public void initListBoxCache() {
        logger.info("Initializing list box cache data...");
        List<ListBox> listBoxes = listBoxRepository.findAll();
        ListBoxCache.addOrUpdateCache(listBoxes);
    }

    public void initConfigurationCache() {
        logger.info("Initializing configuration cache data...");
        Map<String, String> configurationMap = Maps.newHashMap();
        List<Configuration> configurations = configurationRepository.findAll();
        if (!CollectionUtils.isEmpty(configurations)) {
            for (Configuration configuration : configurations) {
                configurationMap.put(configuration.getKey(), configuration.getValue());
            }
        }
        ConfigurationCache.pushConfigurations(configurationMap);
    }

    public void initRoleAndPermissionCache() {
        logger.info("Initializing role and permission cache data...");
        List<Role> roles = roleRepository.findAll();
        List<RoleBasicBo> roleBos = RoleBasicBo.toRoleBasicBos(roles);
        RoleCache.setAllRole(roleBos);

        PermissionCache.setRolePermissions(roleService.getAllRolePermissionMap());
        List<PermissionGroupBo> groupBos = permissionService.getAllGroup();
        PermissionCache.setAllPermissionGroups(groupBos);
    }

    public void initDeactiveCache() {
        logger.info("Initializing deactive cache data...");
        List<Menu> menus = menuService.findDeactiveFlatMenu();
        if (CollectionUtils.isEmpty(menus)) {
            return;
        }
        List<MenuCacheBo> menuCacheBos = new ArrayList<>();
        List<Permission> permissions = new ArrayList<>();
        for (Menu menu : menus) {
            if (menu.getPermission() != null) {
                MenuCacheBo menuCacheBo = new MenuCacheBo();
                menuCacheBo.setId(menu.getId());
                menuCacheBo.setPermissionId(menu.getPermission().getId());
                menuCacheBos.add(menuCacheBo);
                permissions.add(menu.getPermission());
            }
        }
        List<Permission> allPermissions = permissionService.flatPermission(permissions);
        DeactiveCache.setDeactiveMenus(menuCacheBos);
        DeactiveCache.setDeactivePermissions(PermissionBo.toPermissionBos(allPermissions));
    }
}