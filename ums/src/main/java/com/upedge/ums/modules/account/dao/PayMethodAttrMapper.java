package com.upedge.ums.modules.account.dao;


import com.upedge.common.base.Page;
import com.upedge.ums.modules.account.entity.PayMethodAttr;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PayMethodAttrMapper {

    /**
     * 获取需要客户输入的参数
     * @param paymethodId
     * @return
     */
    List<PayMethodAttr> selectPayMethodAttrsNeedEntered(Integer paymethodId);

    /**
     * 查询支付方式属性
     * @param page
     * @return
     */
    List<PayMethodAttr> selectPayMethodAttrs(Page<PayMethodAttr> page);

    /**
     * 支付属性数量
     * @param page
     * @return
     */
    Long countPayMethodAttrs(Page<PayMethodAttr> page);

    /**
     * 批量添加支付方式属性
     * @param attrs
     */
    void batchInsert(@Param("attrs") List<PayMethodAttr> attrs);

    void deleteByPayMethodId(Integer payMethodId);

    int deleteByPrimaryKey(Integer id);

    int insert(PayMethodAttr record);

    int insertSelective(PayMethodAttr record);

    PayMethodAttr selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PayMethodAttr record);

    int updateByPrimaryKey(PayMethodAttr record);
}