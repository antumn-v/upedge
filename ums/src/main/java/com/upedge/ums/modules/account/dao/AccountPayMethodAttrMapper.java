package com.upedge.ums.modules.account.dao;


import com.upedge.ums.enums.PayoneerAttrEnum;
import com.upedge.ums.modules.account.entity.AccountPayMethodAttr;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 海桐
 */
public interface AccountPayMethodAttrMapper {


    List<AccountPayMethodAttr> selectByAccountPaymethodId(Integer accountPaymethodId);

    String selectAccountPaymethodAttrValueByKey(@Param("accountPaymethodId") Integer accountPaymethodId,
                                                @Param("key") PayoneerAttrEnum key);

    /**
     * 批量插入帐户支付方式属性
     * @param attrs
     * @return
     */
    int batchInsert(@Param("attrs") List<AccountPayMethodAttr> attrs);

    int deleteByAccountPaymethodId(Integer accountPaymethodId);

    int deleteByPrimaryKey(Integer id);

    int insert(AccountPayMethodAttr record);

    int insertSelective(AccountPayMethodAttr record);

    AccountPayMethodAttr selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AccountPayMethodAttr record);

    int updateByPrimaryKey(AccountPayMethodAttr record);
}