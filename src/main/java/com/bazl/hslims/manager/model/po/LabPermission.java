package com.bazl.hslims.manager.model.po;

import java.io.Serializable;

public class LabPermission implements Serializable, Comparable {
    private Integer id;

    private String permissionName;

    private String permissionLink;

    private String checkedForRole;

    private String rootFlag;

    private Integer parentId;

    private String iconStyle;

    private String activeFlag;

    public String getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(String activeFlag) {
        this.activeFlag = activeFlag;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIconStyle() {
        return iconStyle;
    }

    public void setIconStyle(String iconStyle) {
        this.iconStyle = iconStyle;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public String getPermissionLink() {
        return permissionLink;
    }

    public void setPermissionLink(String permissionLink) {
        this.permissionLink = permissionLink;
    }

    public String getCheckedForRole() {
        return checkedForRole;
    }

    public void setCheckedForRole(String checkedForRole) {
        this.checkedForRole = checkedForRole;
    }

    public String getRootFlag() {
        return rootFlag;
    }

    public void setRootFlag(String rootFlag) {
        this.rootFlag = rootFlag;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    @Override
    public int compareTo(Object o) {
        if(o instanceof LabPermission){
            LabPermission lp = (LabPermission) o;
            return id - lp.getId();
        }

        return 0;
    }
}