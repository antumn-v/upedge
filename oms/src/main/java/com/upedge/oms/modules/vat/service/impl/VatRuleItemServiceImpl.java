package com.upedge.oms.modules.vat.service.impl;

import com.upedge.common.base.Page;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.model.user.vo.Session;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
public class VatRuleItemServiceImpl implements VatRuleItemService {

    @Autowired
    private VatRuleItemDao vatRuleItemDao;
    @Autowired
    private VatRuleDao vatRuleDao;
    @Autowired
    private VatRuleLogDao vatRuleLogDao;



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
    public VatRuleItem selectByPrimaryKey(Long id){
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
    public List<VatRuleItem> select(Page<VatRuleItem> record){
        record.initFromNum();
        return vatRuleItemDao.select(record);
    }

    /**
    *
    */
    public long count(Page<VatRuleItem> record){
        return vatRuleItemDao.count(record);
    }

    @Transactional
    @Override
    public VatRuleItemUpdateResponse updateRuleItems(VatRuleItemUpdateRequest request,Session session) {
        List<VatRuleItem> newItems=new ArrayList<>();
        Set<Long> set=new HashSet<>();
        VatRule vatRule=vatRuleDao.selectByPrimaryKey(request.getRuleId());
        List<VatRuleLog> vatRuleLogList=new ArrayList<>();
        for(VatAreaVo vatAreaVo:request.getVatAreaVos()){
            if(set.contains(vatAreaVo.getAreaId())){
              continue;
            }
            VatRuleItem vatRuleItem=new VatRuleItem();
            vatRuleItem.setRuleId(request.getRuleId());
            vatRuleItem.setAreaId(vatAreaVo.getAreaId());
            vatRuleItem.setAreaName(vatAreaVo.getAreaName());
            vatRuleItem.setCreateTime(new Date());
            vatRuleItem.setUpdateTime(new Date());
            set.add(vatAreaVo.getAreaId());
            newItems.add(vatRuleItem);
        }
        List<VatRuleItem> oldItems=vatRuleItemDao.listVatRuleItemByRuleId(request.getRuleId());
        for(VatRuleItem item:oldItems){
            //该地区已移除
            if(!set.contains(item.getAreaId())){
                item.setRuleId(0L);
                item.setUpdateTime(new Date());
                newItems.add(item);
            }
        }
        for(VatRuleItem vatRuleItem:newItems){
            VatRuleLog vatRuleLog=new VatRuleLog();
            vatRuleLog.setRuleId(vatRule.getId());
            vatRuleLog.setAreaId(vatRuleItem.getAreaId());
            vatRuleLog.setAreaName(vatRuleItem.getAreaName());
            vatRuleLog.setMinAmount(vatRule.getMinAmount());
            vatRuleLog.setMaxAmount(vatRule.getMaxAmount());
            vatRuleLog.setMethodType(vatRule.getMethodType());
            vatRuleLog.setRatio(vatRule.getRatio());
            vatRuleLog.setCurrency(vatRule.getCurrency());
            vatRuleLog.setAdminUser(String.valueOf(session.getId()));
            vatRuleLog.setCreateTime(new Date());
            vatRuleLogList.add(vatRuleLog);
        }
        if(vatRuleLogList.size()>0) {
            vatRuleLogDao.insertByBatch(vatRuleLogList);
        }
        vatRuleItemDao.updateRuleItems(newItems);
        return new VatRuleItemUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
    }

    @Transactional
    @Override
    public VatRuleItemRemoveResponse removeItem(VatRuleItemRemoveRequest request, Session session) {
        VatRuleLog vatRuleLog=new VatRuleLog();
        VatRule vatRule=vatRuleDao.selectByPrimaryKey(request.getRuleId());
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
        vatRuleItemDao.removeArea(request.getRuleId(),request.getAreaId());
        return new VatRuleItemRemoveResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
    }

    @Override
    public VatRuleItemListResponse areaSelect(Long ruleId) {
        List<VatAreaVo> areaIdList=vatRuleItemDao.areaIdList(ruleId);
        return new VatRuleItemListResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS,areaIdList,null);
    }
}