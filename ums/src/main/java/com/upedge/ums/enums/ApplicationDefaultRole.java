package com.upedge.ums.enums;

public enum ApplicationDefaultRole {

    UPEDGE_APP_DEFAULT_ROLE(1L,"upedge_app"),

    UPEDGE_ADMIN_DEFAULT_ROLE(2L,"upedge_admin"),


    ;

    Long applicationId;
    String roleCode;


    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    ApplicationDefaultRole(Long applicationId, String roleCode) {
        this.applicationId = applicationId;
        this.roleCode = roleCode;
    }


}
