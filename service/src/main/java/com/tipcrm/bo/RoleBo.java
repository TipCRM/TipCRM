package com.tipcrm.bo;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.tipcrm.dao.entity.Role;
import org.springframework.util.CollectionUtils;

public class RoleBo extends RoleBasicBo {
    private String entryUser;

    private Date entryDatetime;

    private String updateUser;

    private Date updateDate;

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
        roleBo.setEntryUser(role.getEntryUser().getUserName());
        roleBo.setEntryDatetime(role.getEntryTime());
        if (role.getUpdateUser() != null) {
            roleBo.setUpdateUser(role.getUpdateUser().getUserName());
            roleBo.setUpdateDate(role.getUpdateTime());
        }
        return roleBo;
    }

    public String getEntryUser() {
        return entryUser;
    }

    public void setEntryUser(String entryUser) {
        this.entryUser = entryUser;
    }

    public Date getEntryDatetime() {
        return entryDatetime == null ? null : (Date) entryDatetime.clone();
    }

    public void setEntryDatetime(Date entryDatetime) {
        this.entryDatetime = entryDatetime == null ? null : (Date) entryDatetime.clone();
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Date getUpdateDate() {
        return updateDate == null ? null : (Date) updateDate.clone();
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate == null ? null : (Date) updateDate.clone();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
