package com.upedge.thirdparty.ali1688.entity.supplier;

/**
 * Created by jiaqi on 2020/6/9.
 */
public class AlibabaAccountAgentCrossBasicResult {

    /**
     * 会员信息
     */
    SimpleAccountInfo result;

    String errorCode;

    String errorMessage;

    Boolean success;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public SimpleAccountInfo getResult() {
        return result;
    }

    public void setResult(SimpleAccountInfo result) {
        this.result = result;
    }
}
