package com.upedge.ums.modules.manager.request;

import com.upedge.common.utils.IdGenerate;
import com.upedge.common.web.util.UserUtil;
import com.upedge.ums.modules.manager.entity.ManagerInfo;
import com.upedge.ums.modules.user.entity.User;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
/**
 * @author gx
 */
@Data
public class ManagerInfoAddRequest{

    @NotNull
    private String loginName;
    @NotNull
    private String loginPass;

    private String avatar;
    /**
     *
     */
    private String mobile;
    /**
     *
     */
    private String email;
    /**
     * 国家
     */
    private String country;
    /**
     *
     */
    private String whatsapp;
    /**
     *
     */
    private String wechat;
    /**
     *
     */
    private String fbInfo;
    /**
     *
     */
    private String skype;
    /**
     * 1=男，0=女
     */
    private Integer sex;
    /**
    * 客户经理英文名
    */
    private String managerName;
    /**
    * 邀请注册码
    */
    @NotNull
    private String inviteCode;

    @NotNull
    private BigDecimal perCommission;

    public ManagerInfo toManagerInfo(){
        ManagerInfo managerInfo=new ManagerInfo();
        managerInfo.setId(IdGenerate.nextId());
        managerInfo.setManagerType(1);
        managerInfo.setManagerState(1);
        managerInfo.setManagerName(managerName);
        managerInfo.setCreateTime(new Date());
        managerInfo.setInviteCode(inviteCode);
        managerInfo.setPerCommission(perCommission);
        return managerInfo;
    }

    public User toUser(Long id){
        Date date = new Date();
        User user = new User();
        user.setId(id);
        UserUtil.encryptPassword(loginPass,loginName);
        user.setAvatar(avatar);
        user.setLoginName(loginName);
        user.setFbInfo(fbInfo);
        user.setUsername(managerName);
        user.setEmail(email);
        user.setMobile(mobile);
        user.setSex(sex);
        user.setSkype(skype);
        user.setStatus(1);
        user.setWechat(wechat);
        user.setWhatsapp(whatsapp);
        user.setCreateTime(date);
        user.setUpdateTime(date);
        user.setLoginCount(0);
        user.setCountry(country);
        return user;
    }

}
