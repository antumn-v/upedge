package com.upedge.common.enums;

/**
 * 統一异常处理枚举
 * errorCode 参照 ResultCode 类
 * 遇到判断需要抛异常只需 throw new CustomerException(CustomerExceptionEnum.LACK_OF_PARAMETER);
 */
public enum  CustomerExceptionEnum {


    /**
     * The time interval should be no more than 15 days
     */
    TIME_INTERVAL_SHOULD_BE_NO_MORE(-1,"The time interval should be no more than 15 days!"),
    /**
     * 查询失败
     */
    QUERY_FAILS(-1,"The query fails"),

    /**
     * 登陆失效
     */
    LOGIN_INFORMATION_INVALID(-1,"LOGIN_INFORMATION_INVALID"),

    /**
     * 邮箱已被注册
     */
    MAILBOX_HAS_BEEN_REGISTERED(-1,"The mailbox has been registered"),

    /**
     * 登陆名已被注册
     */
    LOGIN_NAME_HAS_BEEN_REGISTERED(-1,"The login name has been registered"),

    /**
     * 名称重复
     */
    NAME_OF_THE_REPEATED(-1,"The name of the repeated"),

    /**
     * 参数缺少
     */

    LACK_OF_PARAMETER(-1,"LACK_OF_PARAMETER"),

    /**
     * 为空
     */
    FIND_NULL(-1,"FIND_NULL"),

    /**
     * 数据重复
     */
    DATA_OF_THE_REPEATED(-1,"The data of the repeated"),

    /**
     * 数据异常
     */
    DATA_OF_THE_EXCEPTION(-1,"The data of the exception"),

    /**
     * 验证失败
     */
    VALIDATION_AILED_PLEASE_CHECK(-1,"Validation failed, please check"),


    /**
     * 请先移除授权用户
     */
    PLEASE_REMOVE_THE_AUTHORIZED_USER_FIRST(-1,"Please remove the authorized user first"),

    /**
     * 产品价格调整正在申请中！
     */
    PRODUCT_PRICE_ADJUSTMENT_IS_IN_APPLICATION(-1,"Product price adjustment is in application!"),


    /**
     * 申请状态异常
     */
    APPLICATION_STATUS_ABNORMAL (-1,"Application status abnormal"),

    /**
     * 请先绑定客户经理
     */
    PLEASE_BIND_THE_ACCOUNT_MANAGER_FIRST (-1,"Please bind the account manager first"),

    /**
     * 时间不能为空
     */
    TIME_CANNOT_BE_EMPTY(-1,"Time cannot be empty");

    ;
    ;
    ;


    private Integer errorCode;

    private String errorMessage;

    private CustomerExceptionEnum(Integer errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
