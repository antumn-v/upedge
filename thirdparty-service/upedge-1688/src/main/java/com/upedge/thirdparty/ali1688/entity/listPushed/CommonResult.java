package com.upedge.thirdparty.ali1688.entity.listPushed;

/**
 * Created by guoxing on 2020/6/15.
 */
public class CommonResult {
    /**
     * 错误码
     *
     */
    String errorCode;

    /**
     * 错误信息
     */
    String errorMsg;

    /**
     *是否成功
     */
    Boolean success;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
