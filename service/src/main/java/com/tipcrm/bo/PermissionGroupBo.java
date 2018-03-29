package com.tipcrm.bo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.tipcrm.dao.entity.PermissionGroup;
import org.springframework.util.CollectionUtils;

public class PermissionGroupBo implements Serializable {

    private Integer id;
    private String name;
    private List<PermissionBo> permissions = new ArrayList<>();

    public static List<PermissionGroupBo> toPermissionGroupBos(List<PermissionGroup> groups) {
        List<PermissionGroupBo> groupBos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(groups)) {
            for (PermissionGroup group : groups) {
                PermissionGroupBo groupBo = toPermissionGroupBo(group);
                if (groupBo != null) {
                    groupBos.add(groupBo);
                }
            }
        }
        return groupBos;
    }

    public static PermissionGroupBo toPermissionGroupBo(PermissionGroup group) {
        if (group == null) {
            return null;
        }
        PermissionGroupBo groupBo = new PermissionGroupBo();
        groupBo.setId(group.getId());
        groupBo.setName(group.getName());
        groupBo.setPermissions(PermissionBo.toPermissionBos(group.getPermissions()));
        return groupBo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PermissionBo> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<PermissionBo> permissions) {
        this.permissions = permissions;
    }
}
