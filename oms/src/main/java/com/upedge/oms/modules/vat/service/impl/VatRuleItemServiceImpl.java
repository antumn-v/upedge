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

    @Transactional
    @Override
    public VatRuleItemUpdateResponse updateRuleItems(VatRuleItemUpdateRequest request, Session session) {
        List<VatRuleItem> newItems = new ArrayList<>();
        Set<Long> newAreaIds = new HashSet<>();
        VatRule vatRule = vatRuleDao.selectByPrimaryKey(request.getRuleId());
        List<VatRuleLog> vatRuleLogList = new ArrayList<>();
        List<Long> oldAreaIds = new ArrayList<>();
        List<VatRuleItem> oldItems = vatRuleItemDao.listVatRuleItemByRuleId(request.getRuleId());
        for (VatRuleItem oldItem : oldItems) {
            oldAreaIds.add(oldItem.getAreaId());
        }
        //判断是否包含已配置的地区，新地区加入redis缓存
        for (VatAreaVo vatAreaVo : request.getVatAreaVos()) {
            if (oldAreaIds.contains(vatAreaVo.getAreaId())) {
                oldAreaIds.remove(vatAreaVo.getAreaId());
                continue;
            }
            VatRuleItem vatRuleItem = new VatRuleItem();
            vatRuleItem.setRuleId(request.getRuleId());
            vatRuleItem.setAreaId(vatAreaVo.getAreaId());
            vatRuleItem.setAreaName(vatAreaVo.getAreaName());
            vatRuleItem.setCreateTime(new Date());
            vatRuleItem.setUpdateTime(new Date());
            newAreaIds.add(vatAreaVo.getAreaId());
            newItems.add(vatRuleItem);
            String key = RedisKey.STRING_AREA_VAT_RULE + vatRuleItem.getAreaId();
            redisTemplate.opsForValue().set(key, vatRule);
        }
        if (ListUtils.isNotEmpty(newItems)) {
            vatRuleItemDao.updateRuleItems(newItems);
        }
        //被删除的地区初始化订单vat金额并删除redis缓存
        if (ListUtils.isNotEmpty(oldAreaIds)) {
            orderService.updateOrderVatAmountByAreaId(oldAreaIds, BigDecimal.ZERO);
            for (Long oldAreaId : oldAreaIds) {
                String key = RedisKey.STRING_AREA_VAT_RULE + oldAreaId;
                redisTemplate.delete(key);
            }
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
        //修改订单vat
        for (Long newAreaId : newAreaIds) {
            orderService.initOrderVatAmountByAreaId(newAreaId);
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