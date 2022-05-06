package com.upedge.ums.modules.affiliate.controller;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.model.user.vo.CustomerAffiliateVo;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.web.util.UserUtil;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
//@RestController
//@RequestMapping("/adminAffiliate")
public class AdminAffiliateController {
    @Autowired
    RedisTemplate<String, Object> redisTemplate;
    @Autowired
    AdminAffiliateService adminAffiliateService;

    /**
     * 联盟名单列表
     * @param request
     * @return
     */
    @RequestMapping(value="/affiliateList", method= RequestMethod.POST)
    public AffiliateListResponse affiliateList(@RequestBody @Valid AffiliateListRequest request) {
        Session session = UserUtil.getSession(redisTemplate);
        return adminAffiliateService.affiliateList(request);
    }

    /**
     * 佣金流水列表
     * @param request
     * @return
     */
    @RequestMapping(value="/commissionRecordList", method=RequestMethod.POST)
    public AffiliateCommissionRecordListResponse commissionRecordList(@RequestBody @Valid AffiliateCommissionRecordListRequest request) {
        return adminAffiliateService.commissionRecordList(request);
    }

    /**
     * 佣金提现
     * @param request
     * @return
     */
    @RequestMapping(value="/commissionWithdrawalList", method=RequestMethod.POST)
    public AffiliateCommissionWithdrawalListResponse commissionWithdrawalList(@RequestBody @Valid AffiliateCommissionWithdrawalListRequest request) {
        if(request.getT()==null){
            AffiliateCommissionWithdrawal withdrawal=new AffiliateCommissionWithdrawal();
            request.setT(withdrawal);
        }
        AffiliateCommissionWithdrawal w=request.getT();
        //申请中=0；
        w.setState(0);
        request.setFields("`id`,`customer_id`,`amount`,`path`,`remark`,`state`,`create_time`,`update_time`,`receive_account`");
        request.setOrderBy("update_time desc");
        return adminAffiliateService.commissionWithdrawalList(request);
    }

    /**
     * 历史提现
     */
    @RequestMapping(value="/historyWithdrawalList", method=RequestMethod.POST)
    public AffiliateCommissionWithdrawalListResponse historyWithdrawalList(@RequestBody @Valid AffiliateCommissionWithdrawalListRequest request) {
        if(request.getT()==null){
            AffiliateCommissionWithdrawal withdrawal=new AffiliateCommissionWithdrawal();
            request.setT(withdrawal);
        }
        AffiliateCommissionWithdrawal w=request.getT();
        //申请通过=1，拒绝申请=2
        w.setGteState(1);
        request.setFields("`id`,`customer_id`,`withdrawal_account_id`,`amount`,`path`,`remark`,`state`,`create_time`,`update_time`,`reason`,`manager_code`,`receive_account`,`payment_account`");
        request.setOrderBy("`update_time` desc");
        return adminAffiliateService.commissionWithdrawalList(request);
    }

    /**
     * admin新增联盟
     * @param request
     * @return
     */
    @RequestMapping(value="/addAffiliate", method=RequestMethod.POST)
    public AffiliateAddResponse save(@RequestBody @Valid AffiliateAddRequest request) {
        return adminAffiliateService.addAffiliate(request);
    }

    /**
     * customerAffiliateInfo
     */
    @PostMapping("/adminAffiliate/customerAffiliateInfo/{customerId}")
    public BaseResponse customerAffiliateInfo(@PathVariable Long customerId){
        CustomerAffiliateVo customerAffiliateVo = adminAffiliateService.customerAffiliateInfo(customerId);
        return BaseResponse.success(customerAffiliateVo);
    }

}
