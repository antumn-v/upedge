package com.upedge.tms.modules.ship.service.impl;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.model.ship.vo.ShipDetail;
import com.upedge.common.utils.IdGenerate;
import com.upedge.common.utils.ListUtils;
import com.upedge.tms.modules.ship.dao.ShippingUnitDao;
import com.upedge.tms.modules.ship.entity.ShippingUnit;
import com.upedge.tms.modules.ship.service.ShippingUnitService;
import com.upedge.tms.mq.TmsProcuderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Service
public class ShippingUnitServiceImpl implements ShippingUnitService {

    @Autowired
    private ShippingUnitDao shippingUnitDao;

    @Autowired
    private TmsProcuderService tmsProcuder;

    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        ShippingUnit record = new ShippingUnit();
        record.setId(id);
        return shippingUnitDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(ShippingUnit record) {
        return shippingUnitDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(ShippingUnit record) {
        return shippingUnitDao.insert(record);
    }

    /**
     *
     */
    public ShippingUnit selectByPrimaryKey(Long id){
        ShippingUnit record = new ShippingUnit();
        record.setId(id);
        return shippingUnitDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    @Override
    public int updateByPrimaryKeySelective(ShippingUnit record) {
        return shippingUnitDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(ShippingUnit record) {
        return shippingUnitDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<ShippingUnit> select(Page<ShippingUnit> record){
        record.initFromNum();
        return shippingUnitDao.select(record);
    }

    /**
    *
    */
    public long count(Page<ShippingUnit> record){
        return shippingUnitDao.count(record);
    }

    @Override
    public ShipDetail selectByCondition(Long methodId, Long toAreaId, BigDecimal weight) {
        ShippingUnit shippingUnit = shippingUnitDao.selectByCondition(methodId, toAreaId, weight);
        if (shippingUnit != null){
            return shipUnitToDetail(shippingUnit,weight);
        }
        return null;
    }

    @Override
    public List<ShipDetail> selectByMethodIdsAndWeight(Set<Long> methodIds, Long toAreaId, BigDecimal weight, Integer weightType) {
        List<ShippingUnit> shippingUnits = shippingUnitDao.selectByMethodIdsAndWeight(methodIds, toAreaId, weight, weightType);
        List<ShipDetail> shipDetails = new ArrayList<>();
        if (ListUtils.isNotEmpty(shippingUnits)){
            for (ShippingUnit shippingUnit : shippingUnits) {
                shipDetails.add(shipUnitToDetail(shippingUnit,weight));
            }
        }
        return shipDetails;
    }

    @Override
    public ShippingUnit getShippingUnitByOption(Long methodId, String fromAreaId, String toAreaId, BigDecimal startWeight, BigDecimal endWeight) {
        return shippingUnitDao.getShippingUnitByOption(methodId,fromAreaId,toAreaId,startWeight,endWeight);
    }

    @Transactional
    @Override
    public Integer insertBatch(List<ShippingUnit> newList) {
        List<com.upedge.common.model.old.tms.ShippingUnit> shippingUnits = new ArrayList<>();
        for (ShippingUnit unit : newList) {
            unit.setId(IdGenerate.nextId());
            com.upedge.common.model.old.tms.ShippingUnit shippingUnit = new com.upedge.common.model.old.tms.ShippingUnit();
            BeanUtils.copyProperties(unit,shippingUnit);
            shippingUnits.add(shippingUnit);
        }
        return shippingUnitDao.insertByBatch(newList);
    }

    @Override
    public BaseResponse useImportAll() {
        shippingUnitDao.useImportAll();
        return new BaseResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
    }

    @Override
    public BaseResponse removeAllUseless() {
        shippingUnitDao.removeAllUseless();
        return new BaseResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS);
    }

    @Override
    public List<ShippingUnit> multiExportShippingUnits() {
        return shippingUnitDao.multiExportShippingUnits();
    }

    @Override
    public void senMq(Long id) {
        ArrayList<Long> list = new ArrayList<>();
        list.add(id);
        tmsProcuder.sendMessage(list);
    }

    public ShipDetail shipUnitToDetail(ShippingUnit shippingUnit, BigDecimal weight) {
        ShipDetail shipDetail = new ShipDetail();
        shipDetail.setShippingUtilId(shippingUnit.getId());
        shipDetail.setMethodId(shippingUnit.getMethodId());
        shipDetail.setMethodName(shippingUnit.getMethodName());
        shipDetail.setDays(shippingUnit.getDeliveryMinDay() + "~" + shippingUnit.getDeliveryMaxDay());
        shipDetail.setWeight(weight);
        BigDecimal price = BigDecimal.ZERO;
        if (weight.compareTo(shippingUnit.getFirstWeight()) < 1){
            price = shippingUnit.getFirstFreight().add(shippingUnit.getFixedFee()).setScale(2,BigDecimal.ROUND_UP);
        }else {
            price = weight.subtract(shippingUnit.getFirstWeight())
                    .divide(shippingUnit.getContinueUnitWeight(),2,BigDecimal.ROUND_UP)
                    .multiply(shippingUnit.getContinueUnitPrice())
                    .add(shippingUnit.getFirstFreight())
                    .add(shippingUnit.getFixedFee());
        }
        shipDetail.setPrice(price);
        return shipDetail;
    }
}