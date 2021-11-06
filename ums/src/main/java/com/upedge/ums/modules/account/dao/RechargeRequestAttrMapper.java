package com.upedge.ums.modules.account.dao;

import com.upedge.ums.modules.account.entity.RechargeRequestAttr;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 海桐
 */
public interface RechargeRequestAttrMapper {

    RechargeRequestAttr selectByValueAndName(@Param("name") String name,
                                             @Param("value") String value);

    /**
     * 批量插入支付请求其他属性
     * @param attrs
     */
    void batchInsert(@Param("attrs") List<RechargeRequestAttr> attrs);

    int deleteByPrimaryKey(Integer id);

    int insert(RechargeRequestAttr record);

    int insertSelective(RechargeRequestAttr record);

    RechargeRequestAttr selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RechargeRequestAttr record);

    int updateByPrimaryKey(RechargeRequestAttr record);

    int deleteByRechargeId(Long id);

    List<RechargeRequestAttr> listAttrByRechargeRequestId(Long rechargeRequestId);

    void updateRequestAttr(@Param("requestId") Long requestId,
                           @Param("attrName") String attrName,
                           @Param("attrValue") String attrValue);

}