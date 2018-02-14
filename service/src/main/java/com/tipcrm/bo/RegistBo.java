package com.tipcrm.bo;
public class RegistBo {

    private String email;
    private String password;
    private String username;
    private Boolean isTopManager;
    private Integer departmentId;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getTopManager() {
        return isTopManager;
    }

    public void setTopManager(Boolean topManager) {
        isTopManager = topManager;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }
}
