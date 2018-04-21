package com.tipcrm.bo;

import com.tipcrm.dao.entity.Permission;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PermissionBo implements Serializable {
    private Integer id;
    private String name;
    private String value;
    private String displayName;
    private Boolean checked = false;

    public static List<PermissionBo> toPermissionBos(List<Permission> permissions) {
        List<PermissionBo> bos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(permissions)) {
            for (Permission permission : permissions) {
                PermissionBo permissionBo = toPermissionBo(permission);
                if (permissionBo != null) {
                    bos.add(permissionBo);
                }
            }
        }
        return bos;
    }

    public static PermissionBo toPermissionBo(Permission permission) {
        if (permission == null) {
            return null;
        }
        PermissionBo bo = new PermissionBo();
        bo.setId(permission.getId());
        bo.setName(permission.getName());
        bo.setValue(permission.getValue());
        bo.setDisplayName(permission.getDisplayName());
        return bo;
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

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        if (id.equals(((PermissionBo) obj).getId())) {
            return true;
        } else {
            return false;
        }
    }
}
