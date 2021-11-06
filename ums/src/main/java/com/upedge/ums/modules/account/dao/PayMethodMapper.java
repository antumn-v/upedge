package com.upedge.ums.modules.account.dao;


import com.upedge.common.base.Page;
import com.upedge.ums.modules.account.entity.PayMethod;

import java.util.List;

/**
 * @author 海桐
 */
public interface PayMethodMapper {

    /**
     * 根据银行（第三方）名称查询
     * @param routeName
     * @return
     */
    PayMethod selectByRouteName(String routeName);

    /**
     * 查询支付方式
     * @param page
     * @return
     */
    List<PayMethod> listPayMethod(Page<PayMethod> page);

    /**
     * 支付方式数量
     * @param page
     * @return
     */
    Long countPayMethod(Page<PayMethod> page);

    /**
     * 禁用支付方式
     * @param id
     * @return
     */
    int disablePayMethodById(Integer id);

    /**
     * 恢复支付方式
     * @param id
     * @return
     */
    int enablePayMethodById(Integer id);

    int deleteByPrimaryKey(Integer id);

    int insert(PayMethod record);

    int insertSelective(PayMethod record);

    PayMethod selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PayMethod record);

    int updateByPrimaryKey(PayMethod record);


}