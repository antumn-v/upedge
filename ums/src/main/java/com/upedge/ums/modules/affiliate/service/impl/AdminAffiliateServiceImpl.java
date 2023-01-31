package com.upedge.ums.modules.affiliate.service.impl;

import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.model.user.vo.CustomerAffiliateVo;
import com.upedge.common.model.user.vo.UserVo;
import com.upedge.ums.modules.affiliate.entity.Affiliate;
import com.upedge.ums.modules.affiliate.entity.AffiliateCommissionRecord;
import com.upedge.ums.modules.affiliate.entity.AffiliateCommissionWithdrawal;
import com.upedge.ums.modules.affiliate.request.AffiliateAddRequest;
import com.upedge.ums.modules.affiliate.request.AffiliateCommissionRecordListRequest;
import com.upedge.ums.modules.affiliate.request.AffiliateCommissionWithdrawalListRequest;
import com.upedge.ums.modules.affiliate.request.AffiliateListRequest;
import com.upedge.ums.modules.affiliate.response.AffiliateAddResponse;
import com.upedge.ums.modules.affiliate.response.AffiliateCommissionRecordListResponse;
import com.upedge.ums.modules.affiliate.response.AffiliateCommissionWithdrawalListResponse;
import com.upedge.ums.modules.affiliate.response.AffiliateListResponse;
import com.upedge.ums.modules.affiliate.service.AdminAffiliateService;
import com.upedge.ums.modules.affiliate.service.AffiliateCommissionRecordService;
import com.upedge.ums.modules.affiliate.service.AffiliateCommissionWithdrawalService;
import com.upedge.ums.modules.affiliate.service.AffiliateService;
import com.upedge.ums.modules.affiliate.vo.AdminAffiliateVo;
import com.upedge.ums.modules.affiliate.vo.AdminCommissionRecordVo;
import com.upedge.ums.modules.affiliate.vo.AdminWithdrawalVo;
import com.upedge.ums.modules.user.dao.UserInfoDao;
import com.upedge.ums.modules.user.service.CustomerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminAffiliateServiceImpl implements AdminAffiliateService {

    public static String key = "f167105ef452466f80c97c3b355658a4";

    @Autowired
    AffiliateService affiliateService;
    @Autowired
    UserInfoDao userInfoMapper;
    @Autowired
    AffiliateCommissionRecordService affiliateCommissionRecordService;
    @Autowired
    AffiliateCommissionWithdrawalService affiliateCommissionWithdrawalService;
    @Autowired
    CustomerService customerService;

    @Autowired
    RedisTemplate redisTemplate;

    /**
     * 联盟名单列表
     * @param request
     * @return
     */
    @Override
    public AffiliateListResponse affiliateList(AffiliateListRequest request) {
        List<Affiliate> results = affiliateService.select(request);
        Long total = affiliateService.count(request);
        request.setTotal(total);
        List<AdminAffiliateVo> affiliateVoList=new ArrayList<>();
        results.forEach(affiliate -> {
            AdminAffiliateVo adminAffiliateVo=new AdminAffiliateVo();
            BeanUtils.copyProperties(affiliate,adminAffiliateVo);

            UserVo referrer = (UserVo) redisTemplate.opsForHash().get(RedisKey.STRING_CUSTOMER_INFO,adminAffiliateVo.getReferrerId().toString());
            adminAffiliateVo.setReferrerName(referrer.getUsername());
            adminAffiliateVo.setReferrerEmail(referrer.getEmail());
            adminAffiliateVo.setReferrerLoginName(referrer.getEmail());

            UserVo referee = (UserVo) redisTemplate.opsForHash().get(RedisKey.STRING_CUSTOMER_INFO,adminAffiliateVo.getRefereeId().toString());
            adminAffiliateVo.setRefereeName(referee.getUsername());
            adminAffiliateVo.setRefereeEmail(referee.getEmail());
            adminAffiliateVo.setRefereeLoginName(referee.getEmail());
            affiliateVoList.add(adminAffiliateVo);
        });
        return new AffiliateListResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS,affiliateVoList,request);
    }

    /**
     * 佣金流水列表
     * @param request
     * @return
     */
    @Override
    public AffiliateCommissionRecordListResponse commissionRecordList(AffiliateCommissionRecordListRequest request) {
        List<AffiliateCommissionRecord> results = affiliateCommissionRecordService.select(request);
        Long total = affiliateCommissionRecordService.count(request);
        request.setTotal(total);
        List<AdminCommissionRecordVo> commissionRecordVoList=new ArrayList<>();
        results.forEach(affiliateCommissionRecord -> {
            AdminCommissionRecordVo adminCommissionRecordVo=new AdminCommissionRecordVo();
            BeanUtils.copyProperties(affiliateCommissionRecord,adminCommissionRecordVo);
//            UserInfoDto referrer=userInfoMapper.queryCustomerUserInfo(affiliateCommissionRecord.getReferrerId());
//            adminCommissionRecordVo.setReferrerName(referrer.getUsername());
//            adminCommissionRecordVo.setReferrerEmail(referrer.getEmail());
//            adminCommissionRecordVo.setReferrerLoginName(referrer.getLoginName());
//            UserInfoDto referee=userInfoMapper.queryCustomerUserInfo(affiliateCommissionRecord.getRefereeId());
//            adminCommissionRecordVo.setRefereeName(referee.getUsername());
//            adminCommissionRecordVo.setRefereeEmail(referee.getEmail());
//            adminCommissionRecordVo.setRefereeLoginName(referee.getLoginName());
            commissionRecordVoList.add(adminCommissionRecordVo);
        });
        return new AffiliateCommissionRecordListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,commissionRecordVoList,request);
    }

    /**
     * 佣金提现列表
     * @param request
     * @return
     */
    @Override
    public AffiliateCommissionWithdrawalListResponse commissionWithdrawalList(AffiliateCommissionWithdrawalListRequest request) {
        List<AffiliateCommissionWithdrawal> results = affiliateCommissionWithdrawalService.select(request);
        Long total = affiliateCommissionWithdrawalService.count(request);
        request.setTotal(total);
        List<AdminWithdrawalVo> adminWithdrawalVos=new ArrayList<>();
        results.forEach(affiliateCommissionWithdrawal -> {
            AdminWithdrawalVo adminWithdrawalVo=new AdminWithdrawalVo();
            BeanUtils.copyProperties(affiliateCommissionWithdrawal,adminWithdrawalVo);
//            UserInfoDto userInfo=userInfoMapper.queryCustomerUserInfo(affiliateCommissionWithdrawal.getCustomerId());
//            adminWithdrawalVo.setUsername(userInfo.getUsername());
//            adminWithdrawalVo.setLoginName(userInfo.getLoginName());
            adminWithdrawalVos.add(adminWithdrawalVo);
        });
        return new AffiliateCommissionWithdrawalListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,adminWithdrawalVos,request);
    }

    @Override
    public AffiliateAddResponse addAffiliate(AffiliateAddRequest request) {
        if(request.getReferrerId().equals(request.getRefereeId())){
            return new AffiliateAddResponse(ResultCode.FAIL_CODE,"推荐人和被推荐人相同！");
        }
//        Customer referee=customerService.selectCustomerById(request.getRefereeId());
//        if(referee==null){
//            return new AffiliateAddResponse(ResultCode.FAIL_CODE,Constant.MESSAGE_FAIL);
//        }
//        Customer referrer=customerService.selectCustomerById(request.getReferrerId());
//        if(referrer==null){
//            return new AffiliateAddResponse(ResultCode.FAIL_CODE,Constant.MESSAGE_FAIL);
//        }
        //检查被推荐人是否已经加入了联盟
        Affiliate a=affiliateService.queryAffiliateByReferee(request.getRefereeId());
        if(a!=null){
            return new AffiliateAddResponse(ResultCode.SUCCESS_CODE,"被推荐人已经加入了联盟！");
        }
        Affiliate affiliate=request.toAffiliate();
        affiliateService.insert(affiliate);
        return new AffiliateAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
    }

    /**
     *获取推荐人信息
     * @param customerId
     * @return
     */
    @Override
    public CustomerAffiliateVo customerAffiliateInfo(Long customerId) {
        Affiliate affiliate  =   affiliateService.selectAffiliateVoByRefereeId(customerId);

        return null;
//        UserInfoDto userInfoDto = userInfoMapper.queryCustomerUserInfo(affiliate.getReferrerId());
//        CustomerAffiliateVo customerAffiliateVo = new CustomerAffiliateVo();
//        customerAffiliateVo.setAffiliateName(userInfoDto.getUsername());
//        customerAffiliateVo.setAffiliateId(affiliate.getReferrerId());
//
//        customerAffiliateVo.setAffiliateLoginName(userInfoDto.getLoginName());
//        customerAffiliateVo.setAffiliateEmail(userInfoDto.getEmail());
//
//        return customerAffiliateVo;
    }

}
