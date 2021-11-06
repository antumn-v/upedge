package com.upedge.ums.modules.affiliate.service.impl;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.constant.OrderType;
import com.upedge.common.model.user.request.OrderBenefitsRequest;
import com.upedge.common.model.user.vo.OrderBenefitsVo;
import com.upedge.ums.modules.affiliate.dao.AffiliateCommissionRecordDao;
import com.upedge.ums.modules.affiliate.entity.AffiliateCommissionRecord;
import com.upedge.ums.modules.affiliate.service.AffiliateCommissionRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class AffiliateCommissionRecordServiceImpl implements AffiliateCommissionRecordService {

    @Autowired
    private AffiliateCommissionRecordDao affiliateCommissionRecordDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        AffiliateCommissionRecord record = new AffiliateCommissionRecord();
        record.setId(id);
        return affiliateCommissionRecordDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(AffiliateCommissionRecord record) {
        return affiliateCommissionRecordDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(AffiliateCommissionRecord record) {
        return affiliateCommissionRecordDao.insert(record);
    }

    /**
     *
     */
    public AffiliateCommissionRecord selectByPrimaryKey(Long id){
        AffiliateCommissionRecord record = new AffiliateCommissionRecord();
        record.setId(id);
        return affiliateCommissionRecordDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(AffiliateCommissionRecord record) {
        return affiliateCommissionRecordDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(AffiliateCommissionRecord record) {
        return affiliateCommissionRecordDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<AffiliateCommissionRecord> select(Page<AffiliateCommissionRecord> record){
        record.initFromNum();
        return affiliateCommissionRecordDao.select(record);
    }

    /**
    *
    */
    public long count(Page<AffiliateCommissionRecord> record){
        return affiliateCommissionRecordDao.count(record);
    }

    @Override
    public BaseResponse packageMonthOrderBenefitsListAmount(OrderBenefitsRequest orderBenefitsRequest) {
        return BaseResponse.success(getBenefitsList(orderBenefitsRequest, OrderType.NORMAL));
    }

    @Override
    public BaseResponse packageMonthWholeSaleOrderBenefitsListAmount(OrderBenefitsRequest orderBenefitsRequest) {
        return BaseResponse.success(getBenefitsList(orderBenefitsRequest, OrderType.WHOLESALE));
    }

    private List<OrderBenefitsVo> getBenefitsList(OrderBenefitsRequest orderBenefitsRequest, int orderType ){
        return affiliateCommissionRecordDao.getBenefitsList(
                orderBenefitsRequest.getEndDay(),orderBenefitsRequest.getOrderSourceId(),
                orderBenefitsRequest.getStartDay(),orderBenefitsRequest.getUsdRate(),
                orderType
        );
    }
}