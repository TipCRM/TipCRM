package com.tipcrm.bo;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.tipcrm.dao.entity.Role;
import org.springframework.util.CollectionUtils;

public class RoleBo {
    private Integer id;

    private String name;

    private String displayName;

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
        if (id == ((RoleBo) obj).id) {
            return true;
        } else {
            return false;
        }
    }

    public static List<RoleBo> toRoleBos(List<Role> roles) {
        List<RoleBo> roleBos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(roles)) {
            for (Role role : roles) {
                RoleBo roleBo = toRoleBo(role);
                if (roleBo != null) {
                    roleBos.add(roleBo);
                }
            }
        }
        return roleBos;
    }

    public static RoleBo toRoleBo(Role role) {
        if (role == null) {
            return null;
        }
        RoleBo roleBo = new RoleBo();
        roleBo.setId(role.getId());
        roleBo.setName(role.getName());
        roleBo.setDisplayName(role.getDisplayName());
        return roleBo;
    }
}
