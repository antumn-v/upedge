package com.upedge.tms.modules.ship.service.impl;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.exception.CustomerException;
import com.upedge.common.model.ship.vo.ShipDetail;
import com.upedge.common.model.ship.vo.ShippingMethodRedis;
import com.upedge.common.model.tms.ArearedisVo;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.utils.IdGenerate;
import com.upedge.common.utils.ListUtils;
import com.upedge.tms.modules.area.dao.AreaDao;
import com.upedge.tms.modules.area.entity.Area;
import com.upedge.tms.modules.ship.dao.ShippingUnitDao;
import com.upedge.tms.modules.ship.dto.ShipUnitExitImportDto;
import com.upedge.tms.modules.ship.entity.ShippingUnit;
import com.upedge.tms.modules.ship.request.ShipUnitDelRequest;
import com.upedge.tms.modules.ship.request.ShipUnitExcelImportRequest;
import com.upedge.tms.modules.ship.service.ShippingUnitService;
import com.upedge.tms.modules.ship.vo.CountryShipUnitVo;
import com.upedge.tms.modules.ship.vo.ShipMethodCountryVo;
import com.upedge.tms.mq.TmsProducerService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;


@Service
public class ShippingUnitServiceImpl implements ShippingUnitService {

    @Autowired
    private ShippingUnitDao shippingUnitDao;

    @Autowired
    private TmsProducerService tmsProducerService;

    @Autowired
    AreaDao areaDao;

    @Autowired
    RedisTemplate redisTemplate;

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

    @Override
    public BaseResponse excelImportShipUnit(ShipUnitExcelImportRequest request, Session session) {
        List<ShipUnitExitImportDto> shipUnitExitImportDtos = request.getShipUnitExitImportDtos();
        if (ListUtils.isEmpty(shipUnitExitImportDtos)
                || null == request.getMethodId()){
            return BaseResponse.failed("请求数据为空");
        }
        Long methodId = request.getMethodId();
        ShippingMethodRedis shippingMethodRedis = (ShippingMethodRedis) redisTemplate.opsForHash().get(RedisKey.SHIPPING_METHOD,String.valueOf(methodId));
        if (shippingMethodRedis == null) {
            return BaseResponse.failed("运输方式不存在");
        }
        String methodName = shippingMethodRedis.getName();
        List<ShippingUnit> shippingUnits = new ArrayList<>();
        Map<String,String> countryMap = new HashMap<>();
        for (ShipUnitExitImportDto shipUnitExitImportDto : shipUnitExitImportDtos) {

            String areaId = countryMap.get(shipUnitExitImportDto.getCountry());
            if (null == areaId){
                Area area = areaDao.getRegionByName(shipUnitExitImportDto.getCountry());
                if (null == area){
                    continue;
                }
                areaId = area.getId().toString();
                countryMap.put(area.getName(),areaId);
            }
            if (StringUtils.isNotBlank(areaId)){
                ShippingUnit shippingUnit = shipUnitExitImportDto.toShipUnit(areaId);
                shippingUnit.setMethodId(methodId);
                shippingUnit.setMethodName(methodName);
                shippingUnits.add(shippingUnit);
            }
        }
        if (ListUtils.isNotEmpty(shippingUnits)){
            shippingUnitDao.insertByBatch(shippingUnits);
            redisTemplate.delete(RedisKey.STRING_METHOD_COUNTRY_UNIT_LIST);
        }
        return BaseResponse.success();
    }

    @Override
    public List<ShipMethodCountryVo> selectMethodCountryUnitVo(Page<ShippingUnit> record) {
        List<ShipMethodCountryVo> shipMethodCountryVos = shippingUnitDao.selectMethodCountryUnitVo(record);
        if (ListUtils.isNotEmpty(shipMethodCountryVos)){
            for (ShipMethodCountryVo shipMethodCountryVo : shipMethodCountryVos) {
                ShippingMethodRedis shippingMethodRedis = (ShippingMethodRedis) redisTemplate.opsForHash().get(RedisKey.SHIPPING_METHOD,String.valueOf(shipMethodCountryVo.getMethodId()));
                if (null != shippingMethodRedis){
                    shipMethodCountryVo.setMethodName(shippingMethodRedis.getDesc());
                }
                List<CountryShipUnitVo> countryShipUnitVos = shipMethodCountryVo.getCountryShipUnitVos();
                for (CountryShipUnitVo countryShipUnitVo : countryShipUnitVos) {
                    ArearedisVo arearedisVo = (ArearedisVo) redisTemplate.opsForHash().get(RedisKey.AREA,String.valueOf(countryShipUnitVo.getAreaId()));
                    if (null != arearedisVo){
                        countryShipUnitVo.setCountryName(arearedisVo.getName());
                    }
                }
            }
        }
        return shipMethodCountryVos;
    }

    /**
     *
     */
    public ShippingUnit selectByPrimaryKey(Long id){
        ShippingUnit record = new ShippingUnit();
        record.setId(id);
        return shippingUnitDao.selectByPrimaryKey(record);
    }

    @Override
    public int deleteByIds(List<Long> ids) throws CustomerException {
        for (Long id : ids) {
            deleteByPrimaryKey(id);
        }
        redisTemplate.delete(RedisKey.STRING_METHOD_COUNTRY_UNIT_LIST);
        boolean b = tmsProducerService.sendMessage(ids);
        if (!b){
            throw new CustomerException("mq异常，请重新提交或联系IT");
        }
        return 1;
    }

    @Override
    public int delete(ShipUnitDelRequest request) throws CustomerException {

        if (ListUtils.isNotEmpty(request.getUnitIds())){
            return deleteByIds(request.getUnitIds());
        }
        if (null != request.getMethodId()){
            List<ShippingUnit> shippingUnits = shippingUnitDao.selectListByShippingMethodId(request.getMethodId());
            List<Long> unitIds = new ArrayList<>();
            for (ShippingUnit shippingUnit : shippingUnits) {
                unitIds.add(shippingUnit.getId());
            }
            deleteByIds(unitIds);
        }
        return 0;
    }

    /**
    *
    */
    @Transactional
    @Override
    public int updateByPrimaryKeySelective(ShippingUnit record) throws CustomerException {
        int i = shippingUnitDao.updateByPrimaryKeySelective(record);
        if(i == 1){
            redisTemplate.delete(RedisKey.STRING_METHOD_COUNTRY_UNIT_LIST);
            boolean b = sendMq(record.getId());
            if (!b){
                throw new CustomerException("mq发送失败，请重新提交或联系IT");
            }
        }
        return i;
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
        List<ShippingUnit> shippingUnits = new ArrayList<>();
        for (ShippingUnit unit : newList) {
            unit.setId(IdGenerate.nextId());
            ShippingUnit shippingUnit = new ShippingUnit();
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
    public boolean sendMq(Long id) {
        ArrayList<Long> list = new ArrayList<>();
        list.add(id);
       return tmsProducerService.sendMessage(list);
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
            price = shippingUnit.getFirstFreight()
                    .add(shippingUnit.getFixedFee())
                    .divide(new BigDecimal("6.3"),2,BigDecimal.ROUND_UP);
        }else {
            price = weight.subtract(shippingUnit.getFirstWeight())
                    .divide(shippingUnit.getContinueUnitWeight(),2,BigDecimal.ROUND_UP)
                    .multiply(shippingUnit.getContinueUnitPrice())
                    .add(shippingUnit.getFirstFreight())
                    .add(shippingUnit.getFixedFee())
                    .divide(new BigDecimal("6.3"),2,BigDecimal.ROUND_UP);
        }
        shipDetail.setPrice(price);
        return shipDetail;
    }
}