package com.jacky.labeauty.logic.entity.request;

public class ChangePwdRequest {

    /**
     * origPassword : 123
     * newPassword : 1234
     */

    private String origPassword;
    private String newPassword;

    public String getOrigPassword() {
        return origPassword;
    }

    public void setOrigPassword(String origPassword) {
        this.origPassword = origPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public ChangePwdRequest(String origPassword, String newPassword) {
        this.origPassword = origPassword;
        this.newPassword = newPassword;
    }
}
