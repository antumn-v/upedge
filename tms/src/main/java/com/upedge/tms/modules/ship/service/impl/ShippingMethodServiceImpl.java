package com.upedge.tms.modules.ship.service.impl;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.feign.UmsFeignClient;
import com.upedge.common.model.ship.dto.ShipMethodSelectDto;
import com.upedge.common.model.ship.request.ShipMethodBatchSearchRequest;
import com.upedge.common.model.ship.request.ShipMethodBatchSearchRequest.*;
import com.upedge.common.model.ship.request.ShipMethodPriceRequest;
import com.upedge.common.model.ship.request.ShipMethodSearchRequest;
import com.upedge.common.model.ship.request.ShippingMethodsRequest;
import com.upedge.common.model.ship.response.ShipMethodBatchSearchResponse;
import com.upedge.common.model.ship.response.ShipMethodBatchSearchResponse.*;
import com.upedge.common.model.ship.vo.ShipDetail;
import com.upedge.common.model.ship.vo.ShipMethodNameVo;
import com.upedge.common.model.ship.vo.ShippingMethodVo;
import com.upedge.common.utils.ListUtils;
import com.upedge.tms.modules.area.dao.AreaDao;
import com.upedge.tms.modules.ship.dao.ShippingMethodDao;
import com.upedge.tms.modules.ship.dao.ShippingMethodTemplateDao;
import com.upedge.tms.modules.ship.dao.ShippingUnitDao;
import com.upedge.tms.modules.ship.entity.SaiheTransport;
import com.upedge.tms.modules.ship.entity.ShippingMethod;
import com.upedge.tms.modules.ship.entity.ShippingUnit;
import com.upedge.tms.modules.ship.request.ShippingMethodListRequest;
import com.upedge.tms.modules.ship.response.ShippingMethodDisableResponse;
import com.upedge.tms.modules.ship.response.ShippingMethodEnableResponse;
import com.upedge.tms.modules.ship.response.ShippingMethodListResponse;
import com.upedge.tms.modules.ship.service.ShippingMethodService;
import com.upedge.tms.modules.ship.service.ShippingUnitService;
import com.upedge.tms.mq.TmsProcuderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;


@Service
public class ShippingMethodServiceImpl implements ShippingMethodService {

    @Resource
    UmsFeignClient umsFeignClient;

    @Autowired
    private ShippingMethodDao shippingMethodDao;
    @Autowired
    private ShippingUnitDao shippingUnitDao;

    @Autowired
    ShippingUnitService shippingUnitService;

    @Autowired
    ShippingMethodTemplateDao shippingMethodTemplateDao;

    @Autowired
    AreaDao areaDao;

    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;

    @Autowired
    private TmsProcuderService tmsProcuder;

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
        return shippingMethodDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(ShippingMethod record) {
        com.upedge.common.model.old.tms.ShippingMethod shippingMethod = new com.upedge.common.model.old.tms.ShippingMethod();
        BeanUtils.copyProperties(record,shippingMethod);
        return shippingMethodDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(ShippingMethod record) {
        com.upedge.common.model.old.tms.ShippingMethod shippingMethod = new com.upedge.common.model.old.tms.ShippingMethod();
        BeanUtils.copyProperties(record,shippingMethod);
        return shippingMethodDao.insert(record);
    }

    @Override
    public List<ShipMethodNameVo> selectMixedShipMethodNamesByCountries(String countries) {

        List<String> countryNames = new ArrayList<>();

        if (countries.contains(",")) {
            String[] names = countries.split(",");
            for (int i = 0; i < names.length; i++) {
                countryNames.add(names[i]);
            }
        } else {
            countryNames.add(countries);
        }

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
        BigDecimal volumn = request.getVolumeWeight();
        if (weight.compareTo(volumn) == 1) {
            volumn = weight;
        }
        List<ShipDetail> shipDetails = new ArrayList<>();
        if (ListUtils.isNotEmpty(request.getMethodIds())) {
            shipDetails.addAll(shippingUnitService.selectByMethodIdsAndWeight(request.getMethodIds(), request.getToAreaId(), weight, 0));
            shipDetails.addAll(shippingUnitService.selectByMethodIdsAndWeight(request.getMethodIds(), request.getToAreaId(), volumn, 1));
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
                if (0 == shippingMethod.getWeightType()) {
                    shipDetail = shippingUnitService.selectByCondition(shippingMethod.getId(), request.getToAreaId(), request.getWeight());
                } else if (1 == shippingMethod.getWeightType()) {
                    shipDetail = shippingUnitService.selectByCondition(shippingMethod.getId(), request.getToAreaId(), request.getVolumeWeight());
                }
                if (null != shipDetail) {
                    shipDetail.setWeightType(shippingMethod.getWeightType());
                    shipDetails.add(shipDetail);
                }
            }
            shipDetails.sort(new Comparator<ShipDetail>() {
                @Override
                public int compare(ShipDetail o1, ShipDetail o2) {
                    return o1.getPrice().compareTo(o2.getPrice());
                }
            });
        }
        return shipDetails;
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
        record = selectByPrimaryKey(record.getId());
        com.upedge.common.model.old.tms.ShippingMethod shippingMethod = new com.upedge.common.model.old.tms.ShippingMethod();
        BeanUtils.copyProperties(record,shippingMethod);
        return i;
    }

    /**
     *
     */
    @Transactional
    public int updateByPrimaryKey(ShippingMethod record) {

        com.upedge.common.model.old.tms.ShippingMethod shippingMethod = new com.upedge.common.model.old.tms.ShippingMethod();
        BeanUtils.copyProperties(record,shippingMethod);

        return shippingMethodDao.updateByPrimaryKey(record);
    }

    @Override
    public ShippingMethodListResponse list(ShippingMethodListRequest request) {
        List<ShippingMethod> results = select(request);
        Long total = count(request);
        request.setTotal(total);
        List<ShippingMethodVo> shippingMethodVoList = new ArrayList<>();
        results.forEach(shippingMethod -> {
            ShippingMethodVo shippingMethodVo = new ShippingMethodVo();
            BeanUtils.copyProperties(shippingMethod, shippingMethodVo);
            int unitNum = shippingUnitDao.countUnit(shippingMethod.getId());
            shippingMethodVo.setUnitNum(unitNum);
            int shipNum = shippingMethodTemplateDao.countShipNum(shippingMethod.getId());
            shippingMethodVo.setShipNum(shipNum);
            shippingMethodVoList.add(shippingMethodVo);
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
    @Transactional
    public ShippingMethodEnableResponse enableShippingMethod(Long id) {
        shippingMethodDao.updateShippingMethodState(id, 1);
        ShippingMethod method = selectByPrimaryKey(id);
        com.upedge.common.model.old.tms.ShippingMethod shippingMethod = new com.upedge.common.model.old.tms.ShippingMethod();
        BeanUtils.copyProperties(method,shippingMethod);
        return new ShippingMethodEnableResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS);
    }

    @Override
    @Transactional
    public ShippingMethodDisableResponse disableShippingMethod(Long id) {
        shippingMethodDao.updateShippingMethodState(id, 0);

        ShippingMethod method = selectByPrimaryKey(id);
        com.upedge.common.model.old.tms.ShippingMethod shippingMethod = new com.upedge.common.model.old.tms.ShippingMethod();
        BeanUtils.copyProperties(method,shippingMethod);
        // 调用mq
        ArrayList<Long> list = new ArrayList<>();
        list.add(id);
        tmsProcuder.sendMessage(list);
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
    public void senMq(Long shippingMethodId) {
      List<ShippingUnit> list =  shippingUnitDao.selectListByShippingMethodId(shippingMethodId);
        List<Long> collect = list.stream().map(e -> e.getId()).collect(Collectors.toList());
        tmsProcuder.sendMessage(collect);
    }
}