package com.upedge.ums.modules.affiliate.controller;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.component.annotation.Permission;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.model.user.vo.CommissionRecordVo;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.web.util.RequestUtil;
import com.upedge.common.web.util.UserUtil;
import com.upedge.ums.modules.affiliate.entity.Affiliate;
import com.upedge.ums.modules.affiliate.entity.AffiliateCodeRecord;
import com.upedge.ums.modules.affiliate.request.AffiliateAddRequest;
import com.upedge.ums.modules.affiliate.request.AffiliateListRequest;
import com.upedge.ums.modules.affiliate.request.DisableAffiliateRebateRequest;
import com.upedge.ums.modules.affiliate.response.AffiliateAddResponse;
import com.upedge.ums.modules.affiliate.response.AffiliateCommissionRecordListResponse;
import com.upedge.ums.modules.affiliate.response.AffiliateInfoResponse;
import com.upedge.ums.modules.affiliate.response.AffiliateListResponse;
import com.upedge.ums.modules.affiliate.service.AffiliateCodeRecordService;
import com.upedge.ums.modules.affiliate.service.AffiliateService;
import com.upedge.ums.modules.user.service.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

/**
 * 
 *
 * @author author
 */
@Api(tags = "联盟管理")
@Slf4j
@RestController
@RequestMapping("/affiliate")
public class AffiliateController {



    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private AffiliateService affiliateService;

    @Autowired
    AffiliateCodeRecordService affiliateCodeRecordService;

    @Autowired
    CustomerService customerService;

    @ApiOperation("被推荐人佣金列表")
    @RequestMapping(value="/customer/referee", method=RequestMethod.POST)
    @Permission(permission = "affiliate:affiliate:list")
    public AffiliateListResponse customerRefereeList(@RequestBody @Valid AffiliateListRequest request) {
        if(null == request.getT()){
            request.setT(new Affiliate());
        }

        Session session = UserUtil.getSession(redisTemplate);

        request.getT().setReferrerId(session.getCustomerId());

        List<Affiliate> results = affiliateService.select(request);
        Long total = affiliateService.count(request);
        request.setTotal(total);
        AffiliateListResponse res = new AffiliateListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    @ApiOperation("获取联盟推广地址")
    @GetMapping("/bind/url")
    public BaseResponse getAffiliateBindUrl(){
        Session session = UserUtil.getSession(redisTemplate);

        String token =affiliateService.customerReferrerToken(session.getCustomerId());

        return new BaseResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,token);
    }


    @GetMapping("/totalCommission")
    public BaseResponse affiliateDetail(){
        Session session = UserUtil.getSession(redisTemplate);
        BigDecimal commission = affiliateService.selectTotalByReferrerId(session.getCustomerId());
        if (commission == null){
            commission = BigDecimal.ZERO;
        }
        return BaseResponse.success(commission);
    }

    @ApiOperation("联盟绑定")
    @GetMapping("/bind/{token}")
    public BaseResponse affiliateBind(@PathVariable String token){
        log.warn("联盟注册，推荐人token：" + token);

        AffiliateCodeRecord affiliateCodeRecord = affiliateCodeRecordService.selectByPrimaryKey(token);
        if (null == affiliateCodeRecord){
            return BaseResponse.success();
        }

        HttpServletRequest request = RequestUtil.getRequest();
        String ip = RequestUtil.getIpAddress(request);
        if (ip.equals("unknown")){
            return BaseResponse.success();
        }
        redisTemplate.opsForHash().put(RedisKey.HASH_IP_REFERRER_TOKEN,ip,token);

        return new BaseResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS);

    }


    /**
     * 佣金按月统计
     * @return
     */
    @ApiOperation("佣金按月统计")
    @GetMapping("/commission/month/record")
    public AffiliateCommissionRecordListResponse commissionMonthRecord(){
        Session session = UserUtil.getSession(redisTemplate);
        return affiliateService.commissionMonthRecord(session.getCustomerId());
    }


    /**
     * 推荐人佣金
     * @return
     */
    @ApiOperation("推荐人佣金")
    @GetMapping("/referee/commission")
    public AffiliateListResponse refereeCommissions(){
        Session session = UserUtil.getSession(redisTemplate);
        return affiliateService.refereeCommissionList(session.getCustomerId());
    }

    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    @Permission(permission = "affiliate:affiliate:info:id")
    public AffiliateInfoResponse info(@PathVariable Long id) {
        Affiliate result = affiliateService.selectByPrimaryKey(id);
        AffiliateInfoResponse res = new AffiliateInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    @ApiOperation("联盟列表")
    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "affiliate:affiliate:list")
    public AffiliateListResponse list(@RequestBody @Valid AffiliateListRequest request) {
        List<Affiliate> results = affiliateService.select(request);
        Long total = affiliateService.count(request);
        request.setTotal(total);
        AffiliateListResponse res = new AffiliateListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    @ApiOperation("手动增加联盟")
    @RequestMapping(value="/addAffiliate", method=RequestMethod.POST)
    public AffiliateAddResponse save(@RequestBody @Valid AffiliateAddRequest request) {
        request.setSource(1);
        return affiliateService.addAffiliate(request);
    }

    @ApiOperation("添加佣金记录，feign调用")
    @PostMapping("/addCommissionRecord")
    public BaseResponse addAffiliateCommissionRecord(@RequestBody@Valid CommissionRecordVo commissionRecordVo){
        affiliateService.addAffiliateCommissionRecord(commissionRecordVo);
        return BaseResponse.success();
    }

    @ApiOperation("关闭联盟佣金提成")
    @PostMapping("/disableRebate")
    public BaseResponse disable(@RequestBody@Valid DisableAffiliateRebateRequest request){
        Session session = UserUtil.getSession(redisTemplate);
        return affiliateService.disableAffiliateRebate(request,session);
    }

    @ApiOperation("开启联盟佣金提成")
    @PostMapping("/enableRebate")
    public BaseResponse enable(@RequestBody@Valid DisableAffiliateRebateRequest request){
        Session session = UserUtil.getSession(redisTemplate);
        return affiliateService.enableAffiliateRebate(request,session);
    }




}
