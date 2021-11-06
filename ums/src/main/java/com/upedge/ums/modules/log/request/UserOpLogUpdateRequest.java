package com.upedge.ums.modules.log.request;

import com.upedge.ums.modules.log.entity.UserOpLog;
import lombok.Data;

import java.util.Date;

/**
 * @author author
 */
@Data
public class UserOpLogUpdateRequest{

    /**
     * 操作类型 1:查询 2:修改
     */
    private Integer opType;
    /**
     * 
     */
    private String opName;
    /**
     * 
     */
    private Long customerId;
    /**
     * 
     */
    private Long userId;
    /**
     * 
     */
    private String loginName;
    /**
     * 
     */
    private String url;
    /**
     * 
     */
    private String opReq;
    /**
     * 1:成功 0:异常
     */
    private Integer result;
    /**
     * 
     */
    private String ip;
    /**
     * 创建时间
     */
    private Date createTime;

    public UserOpLog toUserOpLog(Long id){
        UserOpLog userOpLog=new UserOpLog();
        userOpLog.setId(id);
        userOpLog.setOpType(opType);
        userOpLog.setOpName(opName);
        userOpLog.setCustomerId(customerId);
        userOpLog.setUserId(userId);
        userOpLog.setLoginName(loginName);
        userOpLog.setUrl(url);
        userOpLog.setOpReq(opReq);
        userOpLog.setResult(result);
        userOpLog.setIp(ip);
        userOpLog.setCreateTime(createTime);
        return userOpLog;
    }

}
