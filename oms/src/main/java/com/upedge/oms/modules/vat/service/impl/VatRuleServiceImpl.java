package com.upedge.oms.modules.vat.service.impl;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.model.user.vo.CustomerIossVo;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.utils.IdGenerate;
import com.upedge.common.utils.ListUtils;
import com.upedge.oms.constant.VatRuleType;
import com.upedge.oms.modules.order.service.OrderService;
import com.upedge.oms.modules.vat.dao.VatRuleDao;
import com.upedge.oms.modules.vat.dao.VatRuleItemDao;
import com.upedge.oms.modules.vat.dao.VatRuleLogDao;
import com.upedge.oms.modules.vat.entity.CustomerVatRule;
import com.upedge.oms.modules.vat.entity.VatRule;
import com.upedge.oms.modules.vat.entity.VatRuleItem;
import com.upedge.oms.modules.vat.entity.VatRuleLog;
import com.upedge.oms.modules.vat.request.VatRuleAddRequest;
import com.upedge.oms.modules.vat.request.VatRuleAssignCustomerRequest;
import com.upedge.oms.modules.vat.request.VatRuleListRequest;
import com.upedge.oms.modules.vat.request.VatRuleUpdateRequest;
import com.upedge.oms.modules.vat.response.VatRuleAddResponse;
import com.upedge.oms.modules.vat.response.VatRuleListResponse;
import com.upedge.oms.modules.vat.response.VatRuleUpdateResponse;
import com.upedge.oms.modules.vat.service.CustomerVatRuleService;
import com.upedge.oms.modules.vat.service.VatRuleService;
import com.upedge.oms.modules.vat.vo.VatRuleVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class VatRuleServiceImpl implements VatRuleService {

    @Autowired
    private VatRuleDao vatRuleDao;
    @Autowired
    private VatRuleItemDao vatRuleItemDao;
    @Autowired
    private VatRuleLogDao vatRuleLogDao;

    @Autowired
    OrderService orderService;

    @Autowired
    CustomerVatRuleService customerVatRuleService;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;


    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        VatRule record = new VatRule();
        record.setId(id);
        return vatRuleDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(VatRule record) {
        return vatRuleDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(VatRule record) {
        return vatRuleDao.insert(record);
    }

    @Override
    public List<VatRule> selectAllAreaVatRule() {
        return vatRuleDao.selectAllAreaVatRule();
    }

    @Override
    public BaseResponse assignCustomer(VatRuleAssignCustomerRequest request, Session session) {
        Long vatRuleId = request.getRuleId();
        List<Long> customerIds = request.getCustomerIds();
        customerIds = customerIds.stream().distinct().collect(Collectors.toList());
        VatRule vatRule = selectByPrimaryKey(vatRuleId);
        if (null == vatRule || vatRule.getVatType() != 1) {
            return BaseResponse.failed("vat规则不存在或不是私有规则");
        }
        //vat配置的国家
        List<VatRuleItem> vatRuleItems = vatRuleItemDao.listVatRuleItemByRuleId(vatRuleId);
        //vat之前分配的用户
//        List<Long> customerIdList = customerVatRuleService.selectCustomerIdsByRuleId(vatRuleId);
//        //vat规则里移除的用户重新匹配运输方式
//        if (ListUtils.isNotEmpty(customerIdList)) {
//            customerIdList.removeAll(customerIds);
//            for (VatRuleItem vatRuleItem : vatRuleItems) {
//                for (Long aLong : customerIdList) {
//                    String key = RedisKey.STRING_AREA_VAT_RULE + aLong + ":" + vatRuleItem.getAreaId();
//                    redisTemplate.delete(key);
//                    orderService.initOrderVatAmountByAreaId(vatRuleItem.getAreaId(), aLong);
//                }
//            }
//        }
        //删除之前vat规则绑定的用户
        customerVatRuleService.deleteByRuleId(vatRuleId);

        //绑定vat规则与用户
        if (ListUtils.isNotEmpty(customerIds)) {
            List<CustomerVatRule> customerVatRules = new ArrayList<>();
            for (VatRuleItem vatRuleItem : vatRuleItems) {
                for (Long customerId : customerIds) {
                    CustomerVatRule customerVatRule = new CustomerVatRule();
                    customerVatRule.setCustomerId(customerId);
                    customerVatRule.setVatRuleId(vatRuleId);
                    customerVatRules.add(customerVatRule);
                    String key = RedisKey.STRING_AREA_VAT_RULE + customerId + ":" + vatRuleItem.getAreaId();
                    redisTemplate.opsForValue().set(key, vatRule);
                }
                orderService.initOrderVatAmountByAreaId(vatRuleItem.getAreaId(), null);
            }
            customerVatRuleService.insertByBatch(customerVatRules);
        }
        return BaseResponse.success();
    }

    @Override
    public BigDecimal getOrderVatAmount(BigDecimal productAmount, BigDecimal shipPrice, Long areaId, Long customerId) {
        CustomerIossVo customerIossVo = (CustomerIossVo) redisTemplate.opsForValue().get(RedisKey.STRING_CUSTOMER_IOSS + customerId);
        if (null != customerIossVo
                && StringUtils.isNotBlank(customerIossVo.getIossNum())) {
            return BigDecimal.ZERO;
        }

        String key = RedisKey.STRING_AREA_VAT_RULE + customerId + ":" + areaId;
        VatRule rule = (VatRule) redisTemplate.opsForValue().get(key);
        if (null == rule) {
            key = RedisKey.STRING_AREA_VAT_RULE + areaId;
            rule = (VatRule) redisTemplate.opsForValue().get(key);
            if (null == rule) {
                return BigDecimal.ZERO;
            }
        }

//            rule = vatRuleDao.selectVatRuleByAreaId(,areaId);
//            if(null == rule) {
//                return BigDecimal.ZERO;
//            }else {
//                redisTemplate.opsForValue().set(key,rule);
//            }

        BigDecimal vatAmount = BigDecimal.ZERO;
        switch (rule.getMethodType()) {
            case VatRuleType
                    .ORDER_AMOUNT:
                vatAmount = (productAmount.add(shipPrice)).multiply(rule.getRatio());
                break;
            case VatRuleType.PRODUCT_AMOUNT:
                vatAmount = productAmount.multiply(rule.getRatio());
                break;
            default:
                vatAmount = BigDecimal.ZERO;
                break;
        }
        if (vatAmount.compareTo(rule.getMinAmount()) == -1) {
            vatAmount = rule.getMinAmount();
        } else if (vatAmount.compareTo(rule.getMaxAmount()) == 1) {
            vatAmount = rule.getMaxAmount();
        }

        return vatAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    /**
     *
     */
    public VatRule selectByPrimaryKey(Long id) {
        return vatRuleDao.selectByPrimaryKey(id);
    }

    /**
     *
     */
    @Transactional
    public int updateByPrimaryKeySelective(VatRule record) {
        return vatRuleDao.updateByPrimaryKeySelective(record);
    }

    /**
     *
     */
    @Transactional
    public int updateByPrimaryKey(VatRule record) {
        return vatRuleDao.updateByPrimaryKey(record);
    }

    /**
     *
     */
    public List<VatRule> select(Page<VatRule> record) {
        record.initFromNum();
        return vatRuleDao.select(record);
    }

    /**
     *
     */
    public long count(Page<VatRule> record) {
        return vatRuleDao.count(record);
    }

    /**
     * VAT规则列表
     *
     * @param request
     * @return
     */
    @Override
    public VatRuleListResponse adminList(VatRuleListRequest request) {
        List<VatRule> results = this.select(request);
        Long total = this.count(request);
        List<VatRuleVo> vatRuleVoList = new ArrayList<>();
        results.forEach(vatRule -> {
            VatRuleVo vatRuleVo = new VatRuleVo();
            BigDecimal ratio = vatRule.getRatio().multiply(new BigDecimal(100));
            vatRule.setRatio(ratio);
            BeanUtils.copyProperties(vatRule, vatRuleVo);
            //地区数
            int areaNum = vatRuleItemDao.countAreaNumByRuleId(vatRule.getId());
            vatRuleVo.setAreaNum(areaNum);
            vatRuleVoList.add(vatRuleVo);
        });
        request.setTotal(total);
        return new VatRuleListResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS, vatRuleVoList, request);
    }

    @Override
    public VatRuleAddResponse addVatRule(VatRuleAddRequest request, Session session) {
        if (request.getRatio() == null
                || request.getRatio().compareTo(BigDecimal.ZERO) <= 0
                || request.getRatio().compareTo(new BigDecimal(100)) > 0) {
            return new VatRuleAddResponse(ResultCode.FAIL_CODE, "VAT比例异常！");
        }
        if (request.getMaxAmount() == null ||
                request.getMaxAmount().compareTo(BigDecimal.ZERO) <= 0) {
            return new VatRuleAddResponse(ResultCode.FAIL_CODE, "价格异常！");
        }
        if (request.getMinAmount() == null ||
                request.getMinAmount().compareTo(BigDecimal.ZERO) <= 0) {
            return new VatRuleAddResponse(ResultCode.FAIL_CODE, "价格异常！");
        }
        if (request.getMethodType() == null) {
            return new VatRuleAddResponse(ResultCode.FAIL_CODE, "申报方式异常！");
        }
        //VAT申报比例
        BigDecimal ratio = request.getRatio().multiply(new BigDecimal(0.01));
        request.setRatio(ratio);
        VatRule entity = request.toVatRule(session.getId());
        entity.setId(IdGenerate.nextId());
        vatRuleDao.insert(entity);
        return new VatRuleAddResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS, entity, request);
    }

    public void recordLog(VatRule vatRule, String userCode) {
        List<VatRuleItem> itemList = vatRuleItemDao.listVatRuleItemByRuleId(vatRule.getId());
        if (itemList != null && itemList.size() > 0) {
            List<VatRuleLog> vatRuleLogList = new ArrayList<>();
            for (VatRuleItem vatRuleItem : itemList) {
                VatRuleLog vatRuleLog = new VatRuleLog();
                vatRuleLog.setRuleId(vatRule.getId());
                vatRuleLog.setAreaId(vatRuleItem.getAreaId());
                vatRuleLog.setAreaName(vatRuleItem.getAreaName());
                vatRuleLog.setMinAmount(vatRule.getMinAmount());
                vatRuleLog.setMaxAmount(vatRule.getMaxAmount());
                vatRuleLog.setMethodType(vatRule.getMethodType());
                vatRuleLog.setRatio(vatRule.getRatio());
                vatRuleLog.setCurrency(vatRule.getCurrency());
                vatRuleLog.setAdminUser(userCode);
                vatRuleLog.setCreateTime(new Date());
                vatRuleLogList.add(vatRuleLog);
            }
            vatRuleLogDao.insertByBatch(vatRuleLogList);
        }
    }

    @Transactional
    @Override
    public VatRuleUpdateResponse update(Long id, VatRuleUpdateRequest request, Session session) {
        if (request.getRatio() == null
                || request.getRatio().compareTo(BigDecimal.ZERO) <= 0
                || request.getRatio().compareTo(new BigDecimal(100)) > 0) {
            return new VatRuleUpdateResponse(ResultCode.FAIL_CODE, "VAT比例异常！");
        }
        if (request.getMaxAmount() == null ||
                request.getMaxAmount().compareTo(BigDecimal.ZERO) <= 0
                || request.getMinAmount() == null ||
                request.getMinAmount().compareTo(BigDecimal.ZERO) <= 0) {
            return new VatRuleUpdateResponse(ResultCode.FAIL_CODE, "价格异常！");
        }
        if (request.getMethodType() == null) {
            return new VatRuleUpdateResponse(ResultCode.FAIL_CODE, "申报方式异常！");
        }
        //VAT申报比例
        BigDecimal ratio = request.getRatio().multiply(new BigDecimal(0.01));
        request.setRatio(ratio);
        VatRule entity = request.toVatRule(id, session.getId());
        this.updateByPrimaryKeySelective(entity);
        this.recordLog(entity, String.valueOf(session.getId()));
        List<Long> customerIds = new ArrayList<>();

        if (request.getVatType() == 1) {
            customerIds = customerVatRuleService.selectCustomerIdsByRuleId(id);
        }
        List<VatRuleItem> vatRuleItems = vatRuleItemDao.selectByRuleId(id);
        for (VatRuleItem vatRuleItem : vatRuleItems) {
            if (ListUtils.isEmpty(customerIds)) {
                String key = RedisKey.STRING_AREA_VAT_RULE + vatRuleItem.getAreaId();
                redisTemplate.opsForValue().set(key, entity);
            } else {
                for (Long customerId : customerIds) {
                    String key = RedisKey.STRING_AREA_VAT_RULE + customerId + ":" + vatRuleItem.getAreaId();
                    redisTemplate.opsForValue().set(key, entity);
                }
            }
            orderService.initOrderVatAmountByAreaId(vatRuleItem.getAreaId(), null);
        }

        return new VatRuleUpdateResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS);
    }
}