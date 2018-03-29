package com.tipcrm.bo;
import java.util.ArrayList;
import java.util.List;

import com.tipcrm.dao.entity.Role;
import org.springframework.util.CollectionUtils;

public class RoleBasicBo {
    private Integer id;

    private String name;

    private String displayName;

    public static List<RoleBasicBo> toRoleBasicBos(List<Role> roles) {
        List<RoleBasicBo> roleBos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(roles)) {
            for (Role role : roles) {
                RoleBasicBo roleBo = toRoleBasicBo(role);
                if (roleBo != null) {
                    roleBos.add(roleBo);
                }
            }
        }
        return roleBos;
    }

    public static RoleBasicBo toRoleBasicBo(Role role) {
        if (role == null) {
            return null;
        }
        RoleBasicBo roleBo = new RoleBasicBo();
        roleBo.setId(role.getId());
        roleBo.setName(role.getName());
        roleBo.setDisplayName(role.getDisplayName());
        return roleBo;
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

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if ((obj == null) || (obj.getClass() != this.getClass())) {
            return false;
        }
        if (id == ((RoleBasicBo) obj).id) {
            return true;
        } else {
            return false;
        }
    }
}
