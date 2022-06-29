package com.upedge.oms.modules.vat.service.impl;

import com.upedge.common.base.Page;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.utils.ListUtils;
import com.upedge.oms.modules.order.service.OrderService;
import com.upedge.oms.modules.vat.dao.VatRuleDao;
import com.upedge.oms.modules.vat.dao.VatRuleItemDao;
import com.upedge.oms.modules.vat.dao.VatRuleLogDao;
import com.upedge.oms.modules.vat.entity.VatRule;
import com.upedge.oms.modules.vat.entity.VatRuleItem;
import com.upedge.oms.modules.vat.entity.VatRuleLog;
import com.upedge.oms.modules.vat.request.VatRuleItemRemoveRequest;
import com.upedge.oms.modules.vat.request.VatRuleItemUpdateRequest;
import com.upedge.oms.modules.vat.response.VatRuleItemListResponse;
import com.upedge.oms.modules.vat.response.VatRuleItemRemoveResponse;
import com.upedge.oms.modules.vat.response.VatRuleItemUpdateResponse;
import com.upedge.oms.modules.vat.service.CustomerVatRuleService;
import com.upedge.oms.modules.vat.service.VatRuleItemService;
import com.upedge.oms.modules.vat.vo.VatAreaVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;


@Service
public class VatRuleItemServiceImpl implements VatRuleItemService {

    @Autowired
    private VatRuleItemDao vatRuleItemDao;
    @Autowired
    private VatRuleDao vatRuleDao;
    @Autowired
    private VatRuleLogDao vatRuleLogDao;

    @Autowired
    CustomerVatRuleService customerVatRuleService;

    @Autowired
    OrderService orderService;


    @Autowired
    RedisTemplate redisTemplate;


    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        VatRuleItem record = new VatRuleItem();
        record.setId(id);
        return vatRuleItemDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(VatRuleItem record) {
        return vatRuleItemDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(VatRuleItem record) {
        return vatRuleItemDao.insert(record);
    }

    /**
     *
     */
    public VatRuleItem selectByPrimaryKey(Long id) {
        VatRuleItem record = new VatRuleItem();
        record.setId(id);
        return vatRuleItemDao.selectByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int updateByPrimaryKeySelective(VatRuleItem record) {
        return vatRuleItemDao.updateByPrimaryKeySelective(record);
    }

    /**
     *
     */
    @Transactional
    public int updateByPrimaryKey(VatRuleItem record) {
        return vatRuleItemDao.updateByPrimaryKey(record);
    }

    /**
     *
     */
    public List<VatRuleItem> select(Page<VatRuleItem> record) {
        record.initFromNum();
        return vatRuleItemDao.select(record);
    }

    /**
     *
     */
    public long count(Page<VatRuleItem> record) {
        return vatRuleItemDao.count(record);
    }

    @Override
    public VatRuleItemUpdateResponse updateRuleItems(VatRuleItemUpdateRequest request, Session session) {
        Long ruleId = request.getRuleId();
        List<VatRuleItem> newItems = new ArrayList<>();
        Set<Long> newAreaIds = new HashSet<>();
        VatRule vatRule = vatRuleDao.selectByPrimaryKey(ruleId);
        List<VatRuleLog> vatRuleLogList = new ArrayList<>();
        List<Long> customerIds = new ArrayList<>();
        if (vatRule.getVatType() == 1){
            customerIds = customerVatRuleService.selectCustomerIdsByRuleId(ruleId);
        }

        //删除之前配置的地区
        List<VatRuleItem> oldItems = vatRuleItemDao.listVatRuleItemByRuleId(ruleId);
        vatRuleItemDao.deleteByRuleId(ruleId);
        Set<Long> oldAreaIds = new HashSet<>();
        for (VatRuleItem oldItem : oldItems) {
            oldAreaIds.add(oldItem.getAreaId());
            if (vatRule.getVatType() == 1 && ListUtils.isNotEmpty(customerIds)){
                for (Long customerId : customerIds) {
                    String key = RedisKey.STRING_AREA_VAT_RULE + customerId + ":" + oldItem.getAreaId();
                    redisTemplate.delete(key);
                }
            }else {
                String key = RedisKey.STRING_AREA_VAT_RULE + oldItem.getAreaId();
                redisTemplate.delete(key);
            }

        }
        //判断是否包含已配置的地区，新地区加入redis缓存
        List<Long> areaIds = vatRuleItemDao.selectUniqueAreaIds();
        for (VatAreaVo vatAreaVo : request.getVatAreaVos()) {
            VatRuleItem vatRuleItem = new VatRuleItem();
            if (vatRule.getVatType() == 1){
                vatRuleItem.setIsUnique(false);
                for (Long customerId : customerIds) {
                    String key = RedisKey.STRING_AREA_VAT_RULE + customerId + ":" + vatRuleItem.getAreaId();
                    redisTemplate.opsForValue().set(key, vatRule);
                }
            }else {
                if (areaIds.contains(vatAreaVo.getAreaId())){
                    continue;
                }
                String key = RedisKey.STRING_AREA_VAT_RULE + vatRuleItem.getAreaId();
                redisTemplate.opsForValue().set(key, vatRule);
                vatRuleItem.setIsUnique(true);
            }
            vatRuleItem.setRuleId(request.getRuleId());
            vatRuleItem.setAreaId(vatAreaVo.getAreaId());
            vatRuleItem.setAreaName(vatAreaVo.getAreaName());
            vatRuleItem.setCreateTime(new Date());
            vatRuleItem.setUpdateTime(new Date());
            newAreaIds.add(vatAreaVo.getAreaId());
            newItems.add(vatRuleItem);

        }
        if (ListUtils.isNotEmpty(newItems)) {
            vatRuleItemDao.insertByBatch(newItems);
        }

        //保存之前配置的信息
        for (VatRuleItem item : oldItems) {
            //该地区已移除
            if (oldAreaIds.contains(item.getAreaId())) {
                VatRuleLog vatRuleLog = new VatRuleLog();
                vatRuleLog.setRuleId(vatRule.getId());
                vatRuleLog.setAreaId(item.getAreaId());
                vatRuleLog.setAreaName(item.getAreaName());
                vatRuleLog.setMinAmount(vatRule.getMinAmount());
                vatRuleLog.setMaxAmount(vatRule.getMaxAmount());
                vatRuleLog.setMethodType(vatRule.getMethodType());
                vatRuleLog.setRatio(vatRule.getRatio());
                vatRuleLog.setCurrency(vatRule.getCurrency());
                vatRuleLog.setAdminUser(String.valueOf(session.getId()));
                vatRuleLog.setCreateTime(new Date());
                vatRuleLogList.add(vatRuleLog);
            }
        }
        if (vatRuleLogList.size() > 0) {
            vatRuleLogDao.insertByBatch(vatRuleLogList);
        }
        //新老地区修改订单vat
        newAreaIds.addAll(oldAreaIds);
        for (Long newAreaId : newAreaIds) {
            orderService.initOrderVatAmountByAreaId(newAreaId,null);
        }
        return new VatRuleItemUpdateResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS);
    }

    @Transactional
    @Override
    public VatRuleItemRemoveResponse removeItem(VatRuleItemRemoveRequest request, Session session) {
        VatRuleLog vatRuleLog = new VatRuleLog();
        VatRule vatRule = vatRuleDao.selectByPrimaryKey(request.getRuleId());
        vatRuleLog.setRuleId(0L);
        vatRuleLog.setAreaId(request.getAreaId());
        vatRuleLog.setAreaName(request.getAreaName());
        vatRuleLog.setMinAmount(vatRule.getMinAmount());
        vatRuleLog.setMaxAmount(vatRule.getMaxAmount());
        vatRuleLog.setMethodType(vatRule.getMethodType());
        vatRuleLog.setRatio(vatRule.getRatio());
        vatRuleLog.setCurrency(vatRule.getCurrency());
        vatRuleLog.setAdminUser(String.valueOf(session.getId()));
        vatRuleLog.setCreateTime(new Date());
        vatRuleLogDao.insert(vatRuleLog);
        vatRuleItemDao.removeArea(request.getRuleId(), request.getAreaId());
        String key = RedisKey.STRING_AREA_VAT_RULE + request.getAreaId();
        redisTemplate.delete(key);
        List<Long> areaIds = new ArrayList<>();
        areaIds.add(request.getAreaId());
        orderService.updateOrderVatAmountByAreaId(areaIds, BigDecimal.ZERO);
        return new VatRuleItemRemoveResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS);
    }

    @Override
    public VatRuleItemListResponse areaSelect(Long ruleId) {
        List<VatAreaVo> areaIdList = vatRuleItemDao.areaIdList(ruleId);
        return new VatRuleItemListResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS, areaIdList, null);
    }
}