package com.upedge.ums.modules.account.dao;


import com.upedge.ums.modules.account.entity.Account;
import com.upedge.ums.modules.account.entity.AccountUser;
import com.upedge.ums.modules.user.entity.User;

import java.util.List;

/**
 * @author 海桐
 */
public interface AccountUserMapper {

    /**
     * 查询用户绑定的账户
     * @param userId
     * @return
     */
    Account selectAccountByUser(Long userId);

    /**
     * 查询账户绑定的用户
     * @param accountId
     * @return
     */
    List<User> selectUserByAccount(Long accountId);

    /**
     * 查询与某账户关联的用户ID
     * @param accountId
     * @return
     */
    List<Long> selectUserIdByAccountId(Long accountId);

    /**
     * 解除账户与所有用户的关联
     * @param accountId
     */
    void removeByAccountId(Long accountId);


    /**
     * 解除账户与某个用户的关联
     * @param accountUser
     */
    void unLinkAccountUser(AccountUser accountUser);

    int insert(AccountUser record);

    int insertSelective(AccountUser record);
}