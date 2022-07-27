package com.upedge.pms.modules.purchase.service.impl;

import com.alibaba.logistics.param.AlibabaLogisticsOpenPlatformLogisticsStep;
import com.alibaba.logistics.param.AlibabaLogisticsOpenPlatformLogisticsTrace;
import com.upedge.common.base.Page;
import com.upedge.common.utils.DateUtils;
import com.upedge.common.utils.ListUtils;
import com.upedge.pms.modules.purchase.dao.PurchaseOrderTrackingDao;
import com.upedge.pms.modules.purchase.entity.PurchaseOrderTracking;
import com.upedge.pms.modules.purchase.service.PurchaseOrderTrackingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;


@Service
public class PurchaseOrderTrackingServiceImpl implements PurchaseOrderTrackingService {

    @Autowired
    private PurchaseOrderTrackingDao purchaseOrderTrackingDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        PurchaseOrderTracking record = new PurchaseOrderTracking();
        record.setId(id);
        return purchaseOrderTrackingDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(PurchaseOrderTracking record) {
        return purchaseOrderTrackingDao.insert(record);
    }

    @Override
    public int insertByBatch(List<PurchaseOrderTracking> records) {
        if (ListUtils.isNotEmpty(records)){
            return purchaseOrderTrackingDao.insertByBatch(records);
        }
        return 0;
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(PurchaseOrderTracking record) {
        return purchaseOrderTrackingDao.insert(record);
    }

    @Override
    public List<PurchaseOrderTracking> selectByOrderId(Long orderId) {
        return purchaseOrderTrackingDao.selectByOrderId(orderId);
    }

    @Override
    public void updateOrderTrackingLatestUpdateInfo(Long orderId, List<AlibabaLogisticsOpenPlatformLogisticsTrace> alibabaLogisticsOpenPlatformLogisticsTraces) {
        List<PurchaseOrderTracking> purchaseOrderTrackings = selectByOrderId(orderId);
        a:
        for (AlibabaLogisticsOpenPlatformLogisticsTrace alibabaLogisticsOpenPlatformLogisticsTrace : alibabaLogisticsOpenPlatformLogisticsTraces) {
            String trackingCode = alibabaLogisticsOpenPlatformLogisticsTrace.getLogisticsId();
            if (alibabaLogisticsOpenPlatformLogisticsTrace.getLogisticsSteps() == null
            && alibabaLogisticsOpenPlatformLogisticsTrace.getLogisticsSteps().length == 0){
                continue;
            }
            List<AlibabaLogisticsOpenPlatformLogisticsStep> logisticsSteps = Arrays.asList(alibabaLogisticsOpenPlatformLogisticsTrace.getLogisticsSteps());
            AlibabaLogisticsOpenPlatformLogisticsStep logisticsStep = logisticsSteps.get(0);
            for (PurchaseOrderTracking purchaseOrderTracking : purchaseOrderTrackings) {
                if (trackingCode.equals(purchaseOrderTracking.getTrackingCode())){
                    if (!logisticsStep.getRemark().equals(purchaseOrderTracking.getLatestInfo())){
                        purchaseOrderTracking.setLatestInfo(logisticsStep.getRemark());
                        purchaseOrderTracking.setUpdateTime(DateUtils.parseDate(logisticsStep.getAcceptTime()));
                        updateByPrimaryKeySelective(purchaseOrderTracking);
                        continue a;
                    }
                }
            }
        }
    }

    @Override
    public List<PurchaseOrderTracking> selectByOrderIds(List<Long> orderIds) {
        return purchaseOrderTrackingDao.selectByOrderIds(orderIds);
    }

    /**
     *
     */
    public PurchaseOrderTracking selectByPrimaryKey(Long id){
        PurchaseOrderTracking record = new PurchaseOrderTracking();
        record.setId(id);
        return purchaseOrderTrackingDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(PurchaseOrderTracking record) {
        return purchaseOrderTrackingDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(PurchaseOrderTracking record) {
        return purchaseOrderTrackingDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<PurchaseOrderTracking> select(Page<PurchaseOrderTracking> record){
        record.initFromNum();
        return purchaseOrderTrackingDao.select(record);
    }

    /**
    *
    */
    public long count(Page<PurchaseOrderTracking> record){
        return purchaseOrderTrackingDao.count(record);
    }

}