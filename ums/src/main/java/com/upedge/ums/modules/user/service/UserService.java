package com.upedge.ums.modules.user.service;

import com.upedge.common.exception.CustomerException;
import com.upedge.ums.modules.user.entity.User;
import com.upedge.common.base.Page;
import com.upedge.ums.modules.user.request.CustomerSignUpRequest;
import com.upedge.ums.modules.user.request.UserSignInRequest;
import com.upedge.ums.modules.user.response.CustomerSignUpResponse;
import com.upedge.ums.modules.user.response.UserProfileResponse;
import com.upedge.ums.modules.user.response.UserSignInResponse;

import java.util.List;

/**
 * @author gx
 */
public interface UserService{

    UserProfileResponse profile();


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
}

