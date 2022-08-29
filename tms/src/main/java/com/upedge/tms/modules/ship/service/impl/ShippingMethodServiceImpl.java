package com.upedge.tms.modules.ship.service.impl;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.exception.CustomerException;
import com.upedge.common.feign.OmsFeignClient;
import com.upedge.common.model.ship.dto.ShipMethodSelectDto;
import com.upedge.common.model.ship.request.ShipMethodBatchSearchRequest;
import com.upedge.common.model.ship.request.ShipMethodBatchSearchRequest.BatchShipMethodSelectDto;
import com.upedge.common.model.ship.request.ShipMethodPriceRequest;
import com.upedge.common.model.ship.request.ShipMethodSearchRequest;
import com.upedge.common.model.ship.request.ShippingMethodsRequest;
import com.upedge.common.model.ship.response.ShipMethodBatchSearchResponse;
import com.upedge.common.model.ship.response.ShipMethodBatchSearchResponse.BatchShipMethodSelectVo;
import com.upedge.common.model.ship.vo.*;
import com.upedge.common.model.tms.ShippingTemplateVo;
import com.upedge.common.model.tms.WarehouseVo;
import com.upedge.common.utils.IdGenerate;
import com.upedge.common.utils.ListUtils;
import com.upedge.thirdparty.shipcompany.cne.api.CneApi;
import com.upedge.thirdparty.shipcompany.cne.dto.CneShipMethodDto;
import com.upedge.thirdparty.shipcompany.fpx.api.FpxCommonApi;
import com.upedge.thirdparty.shipcompany.fpx.vo.FpxMethodVo;
import com.upedge.thirdparty.shipcompany.yunexpress.api.YunexpressApi;
import com.upedge.thirdparty.shipcompany.yunexpress.vo.YunExpressShipMethodVo;
import com.upedge.tms.modules.area.dao.AreaDao;
import com.upedge.tms.modules.ship.dao.ShippingMethodDao;
import com.upedge.tms.modules.ship.dao.ShippingMethodTemplateDao;
import com.upedge.tms.modules.ship.dao.ShippingUnitDao;
import com.upedge.tms.modules.ship.entity.SaiheTransport;
import com.upedge.tms.modules.ship.entity.ShippingMethod;
import com.upedge.tms.modules.ship.entity.ShippingMethodTemplate;
import com.upedge.tms.modules.ship.entity.ShippingUnit;
import com.upedge.tms.modules.ship.request.ShipCompanyMethodCodeRequest;
import com.upedge.tms.modules.ship.request.ShippingMethodAddRequest;
import com.upedge.tms.modules.ship.request.ShippingMethodListRequest;
import com.upedge.tms.modules.ship.request.ShippingMethodUpdateRequest;
import com.upedge.tms.modules.ship.response.ShippingMethodDisableResponse;
import com.upedge.tms.modules.ship.response.ShippingMethodEnableResponse;
import com.upedge.tms.modules.ship.response.ShippingMethodListResponse;
import com.upedge.tms.modules.ship.service.ShippingMethodService;
import com.upedge.tms.modules.ship.service.ShippingMethodTemplateService;
import com.upedge.tms.modules.ship.service.ShippingUnitService;
import com.upedge.tms.modules.ship.vo.MethodIdTemplateNameVo;
import com.upedge.tms.modules.ship.vo.ShipMethodCodeVo;
import com.upedge.tms.mq.TmsProducerService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;


@Service
public class ShippingMethodServiceImpl implements ShippingMethodService {

    @Autowired
    private ShippingMethodDao shippingMethodDao;

    @Autowired
    private ShippingUnitDao shippingUnitDao;

    @Autowired
    ShippingUnitService shippingUnitService;

    @Autowired
    ShippingMethodTemplateDao shippingMethodTemplateDao;

    @Autowired
    ShippingMethodTemplateService shippingMethodTemplateService;

    @Autowired
    AreaDao areaDao;

    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;

    @Autowired
    private TmsProducerService tmsProducerService;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    OmsFeignClient omsFeignClient;

    @Override
    public List<ShippingMethod> allShippingMethod() {
        return shippingMethodDao.allShippingMethod();
    }

    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        ShippingMethod record = new ShippingMethod();
        record.setId(id);
        redisTemplate.opsForHash().delete(RedisKey.SHIPPING_METHOD, String.valueOf(id));
        return shippingMethodDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(ShippingMethod record) {
        int i = shippingMethodDao.insert(record);
        updateRedisShipMethod(record.getId());
        return i;
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(ShippingMethod record) {
        return shippingMethodDao.insert(record);
    }

    @Override
    public List<ShipMethodNameVo> selectMixedShipMethodNamesByCountries(List<String> countryNames) {

        List<Long> areaIds = areaDao.selectIdByCountries(countryNames);

        List<ShipMethodNameVo> shipMethodNameVos = new ArrayList<>();

        for (int i = 0; i < areaIds.size(); i++) {
            Long areaId = areaIds.get(i);
            List<ShipMethodNameVo> methodNameVos = shippingUnitDao.selectShipNameByToAreaId(areaId);
            if (ListUtils.isEmpty(methodNameVos)) {
                shipMethodNameVos = new ArrayList<>();
                break;
            }
            if (0 == i) {
                shipMethodNameVos = methodNameVos;
            } else {
                shipMethodNameVos.retainAll(methodNameVos);
            }
        }
        return shipMethodNameVos;
    }

    @Override
    public List<ShipDetail> searchShipMethods(ShipMethodSearchRequest request) {
        BigDecimal weight = request.getWeight();
//        BigDecimal volumn = request.getVolumeWeight();
//        if (weight.compareTo(volumn) == 1) {
//            volumn = weight;
//        }
        List<ShipDetail> shipDetails = new ArrayList<>();
        if (ListUtils.isNotEmpty(request.getMethodIds())) {
            shipDetails.addAll(shippingUnitService.selectByMethodIdsAndWeight(request.getMethodIds(), request.getToAreaId(), weight, 0));
        } else {
            List<Long> templateIds = request.getTemplateIds();
            List<ShippingMethod> methods = new ArrayList<>();
            for (int i = 0; i < templateIds.size(); i++) {
                Long templateId = templateIds.get(i);
                if (0 == i) {
                    methods = shippingMethodTemplateDao.selectMethodIdsByTemplate(templateId);
                } else {
                    methods.retainAll(shippingMethodTemplateDao.selectMethodIdsByTemplate(templateId));
                }
            }
            if (null == methods || 0 == methods.size()) {
                return new ArrayList<>();
            }
            for (ShippingMethod shippingMethod : methods) {
                ShipDetail shipDetail = null;
                shipDetail = shippingUnitService.selectByCondition(shippingMethod.getId(), request.getToAreaId(), request.getWeight());
                shipDetail.setWarehouseCode(shippingMethod.getWarehouseCode());
                if (null != shipDetail) {
                    shipDetail.setWeightType(0);
                    shipDetails.add(shipDetail);
                }
            }
        }
        shipDetails.sort(new Comparator<ShipDetail>() {
            @Override
            public int compare(ShipDetail o1, ShipDetail o2) {
                return o1.getPrice().compareTo(o2.getPrice());
            }
        });
        return shipDetails;
    }

    @Override
    public List<ShipMethodCodeVo> getShipCompanyMethodCode(ShipCompanyMethodCodeRequest request) {
        String shipCompany = request.getShipCompany();
        if (StringUtils.isBlank(shipCompany)){
            return new ArrayList<>();
        }
        List<ShipMethodCodeVo> shipMethodCodeVos = new ArrayList<>();
        switch (shipCompany){
            case "4PX":
//                FpxWarehouseMethodListRequest fpxWarehouseMethodListRequest = new FpxWarehouseMethodListRequest();
//                fpxWarehouseMethodListRequest.setSourcePositionCode(warehouseCode);
//                fpxWarehouseMethodListRequest.setCategoryCode("end");
//                fpxWarehouseMethodListRequest.setServiceCode("F");
                List<FpxMethodVo> fpxMethodVos = FpxCommonApi.getTransportMethods(1);
                fpxMethodVos.forEach(fpxMethodVo -> {
                    ShipMethodCodeVo shipMethodCodeVo = new ShipMethodCodeVo();
                    shipMethodCodeVo.setMethodCode(fpxMethodVo.getLogisticsProductCode());
                    shipMethodCodeVo.setShipCompany(shipCompany);
                    shipMethodCodeVo.setCName(fpxMethodVo.getLogisticsProductNameCn());
                    shipMethodCodeVo.setEName(fpxMethodVo.getLogisticsProductNameEn());
                    shipMethodCodeVos.add(shipMethodCodeVo);
                });
                break;
            case "YunExpress":
                List<YunExpressShipMethodVo> yunExpressShipMethodVos = YunexpressApi.getShipMethods("CN");
                for (YunExpressShipMethodVo yunExpressShipMethodVo : yunExpressShipMethodVos) {
                    ShipMethodCodeVo shipMethodCodeVo = new ShipMethodCodeVo();
                    shipMethodCodeVo.setMethodCode(yunExpressShipMethodVo.getCode());
                    shipMethodCodeVo.setShipCompany(shipCompany);
                    shipMethodCodeVo.setCName(yunExpressShipMethodVo.getCName());
                    shipMethodCodeVo.setEName(yunExpressShipMethodVo.getCName());
                    shipMethodCodeVos.add(shipMethodCodeVo);
                }
                break;
            case "CNE":
                List<CneShipMethodDto> cneShipMethodDtos = CneApi.getCneShipMethods();
                for (CneShipMethodDto cneShipMethodDto : cneShipMethodDtos) {
                    ShipMethodCodeVo shipMethodCodeVo = new ShipMethodCodeVo();
                    shipMethodCodeVo.setMethodCode(cneShipMethodDto.getOName());
                    shipMethodCodeVo.setShipCompany(shipCompany);
                    shipMethodCodeVo.setCName(cneShipMethodDto.getCName());
                    shipMethodCodeVo.setEName(cneShipMethodDto.getCName());
                    shipMethodCodeVos.add(shipMethodCodeVo);
                }
            default:
                break;
        }

        return shipMethodCodeVos;
    }

    @Override
    public BaseResponse addShipMethod(ShippingMethodAddRequest request) throws CustomerException {
        WarehouseVo warehouseVo = (WarehouseVo) redisTemplate.opsForValue().get(RedisKey.STRING_WAREHOUSE+request.getWarehouseCode());
        if (null == warehouseVo){
            return BaseResponse.failed("仓库不存在");
        }
        ShippingMethod shippingMethod = shippingMethodDao.getShippingMethodByName(request.getName());
        if (null != shippingMethod) {
            return BaseResponse.failed("运输方式名称不能重复");
        }
        Long methodId = IdGenerate.nextId();
        shippingMethod = request.toShippingMethod();
        shippingMethod.setId(methodId);
        insert(shippingMethod);

        List<ShippingMethodTemplate> shippingMethodTemplates = new ArrayList<>();
        List<Long> templateIds = request.getTemplateIds();
        for (Long templateId : templateIds) {
            ShippingTemplateRedis shippingTemplateRedis = (ShippingTemplateRedis) redisTemplate.opsForHash().get(RedisKey.SHIPPING_TEMPLATE, String.valueOf(templateId));
            if (null == shippingTemplateRedis) {
                continue;
            }
            ShippingMethodTemplate shippingMethodTemplate = new ShippingMethodTemplate();
            shippingMethodTemplate.setShippingId(templateId);
            shippingMethodTemplate.setMethodId(methodId);
            shippingMethodTemplates.add(shippingMethodTemplate);
        }
        if (ListUtils.isEmpty(shippingMethodTemplates)) {
            throw new CustomerException("运输模板信息错误");
        }
        shippingMethodTemplateDao.insertByBatch(shippingMethodTemplates);
        //更新缓存数据
        updateRedisShipMethod(methodId);
        shippingMethodTemplateService.redisInit();

        if (StringUtils.isNotBlank(shippingMethod.getWarehouseCode())){
            List<Long> methodIds = (List<Long>) redisTemplate.opsForHash().get(RedisKey.HASH_WAREHOUSE_METHOD,shippingMethod.getWarehouseCode());
            if (ListUtils.isEmpty(methodIds)){
                methodIds = new ArrayList<>();
            }
            methodIds.add(methodId);
            redisTemplate.opsForHash().put(RedisKey.HASH_WAREHOUSE_METHOD,shippingMethod.getWarehouseCode(),methodIds);
        }
        return BaseResponse.success();
    }

    @Override
    public BaseResponse updateShipMethod(ShippingMethodUpdateRequest request, Long methodId) throws CustomerException {
        WarehouseVo warehouseVo = (WarehouseVo) redisTemplate.opsForValue().get(RedisKey.STRING_WAREHOUSE+request.getWarehouseCode());
        if (null == warehouseVo){
            return BaseResponse.failed("仓库不存在");
        }
        ShippingMethod shippingMethod = shippingMethodDao.selectByPrimaryKey(methodId);

        if (null == shippingMethod) {
            return BaseResponse.failed("运输方式不存在");
        }
        List<Long> methodIds = (List<Long>) redisTemplate.opsForHash().get(RedisKey.HASH_WAREHOUSE_METHOD,shippingMethod.getWarehouseCode());
        if (ListUtils.isNotEmpty(methodIds)){
            methodIds.remove(methodId);
            if (ListUtils.isNotEmpty(methodIds)){
                redisTemplate.opsForHash().put(RedisKey.HASH_WAREHOUSE_METHOD,shippingMethod.getWarehouseCode(),methodIds);
            }else {
                redisTemplate.opsForHash().delete(RedisKey.HASH_WAREHOUSE_METHOD,shippingMethod.getWarehouseCode());
            }
        }
        boolean b = shippingMethod.getWarehouseCode().equals(request.getWarehouseCode());

        shippingMethod = request.toShippingMethod(methodId);
        shippingMethod.setId(methodId);
        updateByPrimaryKeySelective(shippingMethod);
        //修改运输模板关联表
        updateMethodTemplate(request.getTemplateIds(), methodId);
//        Integer weightType = shippingMethod.getWeightType();
//        if (request.getWeightType() != null
//                && weightType != request.getWeightType()) {
//            boolean sendMq = sendMq(shippingMethod.getId());
//            if (!sendMq) {
//                throw new CustomerException("mq异常，请重新提交或联系IT");
//            }
//        }
        shippingMethodTemplateService.redisInit();
        if (StringUtils.isNotBlank(shippingMethod.getWarehouseCode())){
            methodIds = (List<Long>) redisTemplate.opsForHash().get(RedisKey.HASH_WAREHOUSE_METHOD,shippingMethod.getWarehouseCode());
            if (ListUtils.isEmpty(methodIds)){
                methodIds = new ArrayList<>();
            }
            methodIds.add(methodId);
            redisTemplate.opsForHash().put(RedisKey.HASH_WAREHOUSE_METHOD,shippingMethod.getWarehouseCode(),methodIds);
        }
        if (!b){
            omsFeignClient.updateWarehouseByMethod(methodId);
        }
        return BaseResponse.success();
    }

    void updateMethodTemplate(List<Long> templateIds,Long methodId) throws CustomerException {
        List<ShippingMethodTemplate> shippingMethodTemplates = new ArrayList<>();
        for (Long templateId : templateIds) {
            ShippingTemplateRedis shippingTemplateRedis = (ShippingTemplateRedis) redisTemplate.opsForHash().get(RedisKey.SHIPPING_TEMPLATE, String.valueOf(templateId));
            if (null == shippingTemplateRedis) {
                continue;
            }
            ShippingMethodTemplate shippingMethodTemplate = new ShippingMethodTemplate();
            shippingMethodTemplate.setShippingId(templateId);
            shippingMethodTemplate.setMethodId(methodId);
            shippingMethodTemplates.add(shippingMethodTemplate);
        }
        if (ListUtils.isEmpty(shippingMethodTemplates)) {
            throw new CustomerException("运输模板信息错误");
        }
        shippingMethodTemplateDao.deleteByShipMethodId(methodId);
        shippingMethodTemplateDao.insertByBatch(shippingMethodTemplates);
    }

    @Override
    public ShipMethodBatchSearchResponse batchSearchShipMethods(ShipMethodBatchSearchRequest request) {
        List<BatchShipMethodSelectVo> methodSelectVos = new ArrayList<>();
        List<BatchShipMethodSelectDto> selectDtos = request.getBatchShipMethodSelectDtos();
        for (BatchShipMethodSelectDto selectDto : selectDtos) {
            Future<BatchShipMethodSelectVo> future = threadPoolExecutor.submit(new Callable<BatchShipMethodSelectVo>() {
                @Override
                public BatchShipMethodSelectVo call() throws Exception {
                    ShipMethodSelectDto shipMethodSelectDto = selectDto.getShipMethodSelectDto();
                    ShipMethodSearchRequest searchRequest = new ShipMethodSearchRequest();
                    BeanUtils.copyProperties(shipMethodSelectDto, searchRequest);
                    List<ShipDetail> shipDetails = searchShipMethods(searchRequest);
                    BatchShipMethodSelectVo shipMethodSelectVo = new BatchShipMethodSelectVo();
                    shipMethodSelectVo.setRequestId(selectDto.getRequestId());
                    shipMethodSelectVo.setShips(shipDetails);
                    return shipMethodSelectVo;
                }
            });
            try {
                methodSelectVos.add(future.get());
            } catch (Exception e) {
                e.printStackTrace();
                return new ShipMethodBatchSearchResponse(ResultCode.FAIL_CODE, null);
            }
        }
        ShipMethodBatchSearchResponse response = new ShipMethodBatchSearchResponse();
        response.setBatchShipMethodSelectVos(methodSelectVos);
        return response;
    }

    @Override
    public ShipDetail searchShipPriceByMethodId(ShipMethodPriceRequest request) {
        List<Long> templateIds = request.getTemplateIds();
        List<ShippingMethod> methods = new ArrayList<>();

        for (int i = 0; i < templateIds.size(); i++) {
            Long templateId = templateIds.get(i);
            if (0 == i) {
                methods = shippingMethodTemplateDao.selectMethodIdsByTemplate(templateId);
            } else {
                methods.retainAll(shippingMethodTemplateDao.selectMethodIdsByTemplate(templateId));
            }
        }

        if (null == methods || 0 == methods.size()) {
            return null;
        }
        for (ShippingMethod shippingMethod : methods) {
            if (shippingMethod.getId().equals(request.getShipMethodId())) {
                ShipDetail shipDetail = null;
                if (0 == shippingMethod.getWeightType()) {
                    shipDetail = shippingUnitService.selectByCondition(shippingMethod.getId(), request.getToAreaId(), request.getWeight());
                } else if (1 == shippingMethod.getWeightType()) {
                    shipDetail = shippingUnitService.selectByCondition(shippingMethod.getId(), request.getToAreaId(), request.getVolumeWeight());
                }
                if (null != shipDetail) {
                    shipDetail.setWeightType(shippingMethod.getWeightType());
                    return shipDetail;
                }
            }
        }
        return null;
    }

    /**
     *
     */
    public ShippingMethod selectByPrimaryKey(Long id) {
        return shippingMethodDao.selectByPrimaryKey(id);
    }

    /**
     *
     */
    @Transactional
    public int updateByPrimaryKeySelective(ShippingMethod record) {
        int i = shippingMethodDao.updateByPrimaryKeySelective(record);
        updateRedisShipMethod(record.getId());
        return i;
    }

    /**
     *
     */
    public int updateByPrimaryKey(ShippingMethod record) {
        int i = shippingMethodDao.updateByPrimaryKey(record);
        updateRedisShipMethod(record.getId());
        return i;
    }

    @Override
    public ShippingMethodListResponse list(ShippingMethodListRequest request) {
        List<ShippingMethod> results = select(request);
        Long total = count(request);
        request.setTotal(total);
        List<MethodIdTemplateNameVo> methodIdTemplateNameVos = shippingMethodTemplateDao.selectMethodTemplateNames();
        Map<Long, List<ShippingTemplateVo>> map = new HashMap<>();
        for (MethodIdTemplateNameVo methodIdTemplateNameVo : methodIdTemplateNameVos) {
            map.put(methodIdTemplateNameVo.getMethodId(), methodIdTemplateNameVo.getShippingTemplateVos());
        }
        List<ShippingMethodVo> shippingMethodVoList = new ArrayList<>();
        results.forEach(shippingMethod -> {
            ShippingMethodVo shippingMethodVo = new ShippingMethodVo();
            BeanUtils.copyProperties(shippingMethod, shippingMethodVo);
            shippingMethodVoList.add(shippingMethodVo);
            if (map.get(shippingMethod.getId()) != null) {
                shippingMethodVo.setShippingTemplateVos(map.get(shippingMethod.getId()));
            }
        });
        ShippingMethodListResponse res = new ShippingMethodListResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS, shippingMethodVoList, request);
        return res;
    }

    /**
     *
     */
    public List<ShippingMethod> select(Page<ShippingMethod> record) {
        record.initFromNum();
        return shippingMethodDao.select(record);
    }

    /**
     *
     */
    public long count(Page<ShippingMethod> record) {
        return shippingMethodDao.count(record);
    }

    @Override
    public ShippingMethodEnableResponse enableShippingMethod(Long id) {
        shippingMethodDao.updateShippingMethodState(id, 1);
        updateRedisShipMethod(id);
        shippingMethodTemplateService.redisInit();
        return new ShippingMethodEnableResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS);
    }

    @Override
    public ShippingMethodDisableResponse disableShippingMethod(Long id) throws CustomerException {
        shippingMethodDao.updateShippingMethodState(id, 0);
        // 调用mq
        boolean b = sendMq(id);
        if (!b) {
            throw new CustomerException("mq异常，请重新提交或联系IT");
        }
        redisTemplate.opsForHash().delete(RedisKey.SHIPPING_METHOD, id.toString());
        shippingMethodTemplateService.redisInit();
        return new ShippingMethodDisableResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS);
    }

    @Override
    public List<String> listUseAllShippingMethodName() {
        return shippingMethodDao.listUseAllShippingMethodName();
    }

    @Override
    public ShippingMethod getShippingMethodByName(String methodName) {
        return shippingMethodDao.getShippingMethodByName(methodName);
    }

    @Override
    public ShippingMethodListResponse listShippingMethod(ShippingMethodsRequest request) {
        List<ShippingMethod> shippingMethods = shippingMethodDao.listShippingMethod(request.getIds());
        return new ShippingMethodListResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS, shippingMethods, null);
    }

    /**
     * 根据赛盒运输id查询admin运输方式
     *
     * @param transportId
     * @return
     */
    @Override
    public BaseResponse getShippingMethodByTransportId(Integer transportId, Long shippingMethodId) {
        ShippingMethod shippingMethod = shippingMethodDao.getShippingMethodByTransportId(transportId);
        if (shippingMethod == null) {
            shippingMethod = shippingMethodDao.selectByPrimaryKey(shippingMethodId);
        }
        ShippingMethodVo shippingMethodVo = null;
        if (shippingMethod != null) {
            shippingMethodVo = new ShippingMethodVo();
            BeanUtils.copyProperties(shippingMethod, shippingMethodVo);
        }
        return new BaseResponse(ResultCode.SUCCESS_CODE, shippingMethodVo);
    }

    /**
     * 修改赛盒运输信息时维护冗余字段
     *
     * @param record
     */
    @Override
    public int updateBySaiheTransport(SaiheTransport record) {

        return shippingMethodDao.updateBySaiheTransport(record.getId(), record.getTransportName());
    }

    @Override
    public boolean sendMq(Long shippingMethodId) {
        List<ShippingUnit> list = shippingUnitDao.selectListByShippingMethodId(shippingMethodId);
        List<Long> collect = list.stream().map(e -> e.getId()).collect(Collectors.toList());
        return tmsProducerService.sendMessage(collect);
    }


    public void updateRedisShipMethod(Long methodId) {
        ShippingMethod shippingMethod = selectByPrimaryKey(methodId);
        ShippingMethodRedis shippingMethodRedis = new ShippingMethodRedis();
        BeanUtils.copyProperties(shippingMethod, shippingMethodRedis);
        redisTemplate.opsForHash().put(RedisKey.SHIPPING_METHOD, String.valueOf(methodId), shippingMethodRedis);
    }
}