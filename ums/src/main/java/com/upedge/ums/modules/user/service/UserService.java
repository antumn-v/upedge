package com.upedge.ums.modules.user.service;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.exception.CustomerException;
import com.upedge.common.model.user.vo.Session;
import com.upedge.ums.modules.user.entity.User;
import com.upedge.common.base.Page;
import com.upedge.ums.modules.user.request.*;
import com.upedge.ums.modules.user.response.CustomerSignUpResponse;
import com.upedge.ums.modules.user.response.UserProfileResponse;
import com.upedge.ums.modules.user.response.UserSignInResponse;

import java.util.List;
import java.util.Map;

/**
 * @author gx
 */
public interface UserService{


    BaseResponse addUser(UserAddRequest request,Session session);

    void userBindAccountOrgApp(Long userId, Long applicationId, Long accountId, Long organizationId, Long roleId);

    Map<String, Object> userSignIn(User user, Long applicationId, Integer source);

    User selectByLoginName(String loginName);

    UserProfileResponse profile();

    BaseResponse userUpdatePassword(UserUpdatePwdRequest request, Session session);

    CustomerSignUpResponse signUp(CustomerSignUpRequest request) throws CustomerException;

    UserSignInResponse signIn(UserSignInRequest request);

    User selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(User record);

    int updateByPrimaryKeySelective(User record);

    int insert(User record);

    int insertSelective(User record);

    List<User> select(Page<User> record);

    long count(Page<User> record);

    BaseResponse userRecoverPassword(UserRecoverPasswordRequest request);

    List<User> selectAllDefaultCustomerInfo();
}

