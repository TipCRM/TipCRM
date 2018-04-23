package com.tipcrm.bo;

import com.tipcrm.dao.entity.Department;
import com.tipcrm.dao.entity.Level;
import com.tipcrm.dao.entity.User;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class UserBo extends UserBasicBo {

    private String email;

    private String phoneNo;

    private String status;

    private Integer departmentId;

    private String department;

    private String manager;

    private Integer levelId;

    private String level;

    private String motto;

    private Boolean isDepartmentManager;

    public static UserBo convertToUserBo(User user) {
        UserBo userBo = new UserBo();
        userBo.setId(user.getId());
        userBo.setWorkNo(user.getWorkNo());
        userBo.setName(user.getUserName());
        userBo.setEmail(user.getEmail());
        userBo.setPhoneNo(user.getPhoneNo());
        userBo.setMotto(user.getMotto());
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
            userBo.setLevel(level.getName());
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

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getMotto() {
        return motto;
    }

    public void setMotto(String motto) {
        this.motto = motto;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getLevelId() {
        return levelId;
    }

    public void setLevelId(Integer levelId) {
        this.levelId = levelId;
    }

    public Boolean getIsDepartmentManager() {
        return isDepartmentManager;
    }

    public void setIsDepartmentManager(Boolean isDepartmentManager) {
        this.isDepartmentManager = isDepartmentManager;
    }
}
