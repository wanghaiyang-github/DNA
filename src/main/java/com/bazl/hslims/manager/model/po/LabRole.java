package com.bazl.hslims.manager.model.po;

import java.io.Serializable;
import java.util.List;

public class LabRole implements Serializable {
    private Integer id;

    private String roleName;

    private Integer roleLevel;

    private String checkedForUser;

    private List<LabPermission> permissionList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Integer getRoleLevel() {
        return roleLevel;
    }

    public void setRoleLevel(Integer roleLevel) {
        this.roleLevel = roleLevel;
    }

    public String getCheckedForUser() {
        return checkedForUser;
    }

    public void setCheckedForUser(String checkedForUser) {
        this.checkedForUser = checkedForUser;
    }

    public List<LabPermission> getPermissionList() {
        return permissionList;
    }

    public void setPermissionList(List<LabPermission> permissionList) {
        this.permissionList = permissionList;
    }
}