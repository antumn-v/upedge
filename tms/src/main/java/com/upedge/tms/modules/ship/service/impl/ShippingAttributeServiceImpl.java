package com.upedge.tms.modules.ship.service.impl;

import com.upedge.common.base.Page;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.tms.modules.ship.dao.ShippingAttributeDao;
import com.upedge.tms.modules.ship.entity.ShippingAttribute;
import com.upedge.tms.modules.ship.response.ShippingAttributeListResponse;
import com.upedge.tms.modules.ship.service.ShippingAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class ShippingAttributeServiceImpl implements ShippingAttributeService {

    @Autowired
    private ShippingAttributeDao shippingAttributeDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        ShippingAttribute record = new ShippingAttribute();
        record.setId(id);
        return shippingAttributeDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(ShippingAttribute record) {
        return shippingAttributeDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(ShippingAttribute record) {
        return shippingAttributeDao.insert(record);
    }

    /**
     *
     */
    public ShippingAttribute selectByPrimaryKey(Long id){
        ShippingAttribute record = new ShippingAttribute();
        record.setId(id);
        return shippingAttributeDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(ShippingAttribute record) {
        return shippingAttributeDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(ShippingAttribute record) {
        return shippingAttributeDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<ShippingAttribute> select(Page<ShippingAttribute> record){
        record.initFromNum();
        return shippingAttributeDao.select(record);
    }

    /**
    *
    */
    public long count(Page<ShippingAttribute> record){
        return shippingAttributeDao.count(record);
    }

    @Override
    public ShippingAttributeListResponse allShippingAttribute() {
        List<ShippingAttribute> shippingAttributeList=shippingAttributeDao.allShippingAttribute();
        return new ShippingAttributeListResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS,shippingAttributeList);
    }

}