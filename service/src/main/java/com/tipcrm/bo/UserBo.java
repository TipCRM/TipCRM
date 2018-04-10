package com.tipcrm.bo;
import java.util.ArrayList;
import java.util.List;

import com.tipcrm.dao.entity.Department;
import com.tipcrm.dao.entity.Level;
import com.tipcrm.dao.entity.User;
import org.springframework.util.CollectionUtils;

public class UserBo extends UserBasicBo {

    private String email;

    private String status;

    private String department;

    private String manager;

    private String level;

    public static UserBo convertToUserBo(User user) {
        UserBo userBo = new UserBo();
        userBo.setId(user.getId());
        userBo.setName(user.getUserName());
        userBo.setEmail(user.getEmail());
        Department department = user.getDepartment();
        if (department != null) {
            userBo.setDepartment(user.getDepartment().getName());
            User manager = department.getManager();
            if (manager != null) {
                userBo.setManager(manager.getUserName());
            }
        }
        Level level = user.getLevel();
        if (level != null) {
            userBo.setLevel(level.getDisplayName());
        }
        return userBo;
    }

    public static List<UserBo> convertToUserBos(List<User> users) {
        List<UserBo> userBos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(users)) {
            for (User user : users) {
                userBos.add(convertToUserBo(user));
            }
        }
        return userBos;

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
