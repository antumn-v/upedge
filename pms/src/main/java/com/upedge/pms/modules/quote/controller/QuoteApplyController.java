package com.upedge.pms.modules.quote.controller;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.component.annotation.Permission;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.exception.CustomerException;
import com.upedge.common.model.pms.request.OrderQuoteApplyRequest;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.utils.ListUtils;
import com.upedge.common.web.util.UserUtil;
import com.upedge.pms.modules.product.request.ClaimQuoteApplyRequest;
import com.upedge.pms.modules.product.request.QuoteApplyProcessRequest;
import com.upedge.pms.modules.quote.dto.QuoteApplyListDto;
import com.upedge.pms.modules.quote.entity.CustomerProductQuote;
import com.upedge.pms.modules.quote.response.QuoteApplyInfoResponse;
import com.upedge.pms.modules.quote.service.CustomerProductQuoteService;
import com.upedge.pms.modules.quote.service.QuoteApplyService;
import com.upedge.pms.modules.quote.vo.QuoteApplyVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 
 *
 * @author gx
 */
@Api(tags = "产品报价申请")
@RestController
@RequestMapping("/quoteApply")
public class QuoteApplyController {
    @Autowired
    private QuoteApplyService quoteApplyService;

    @Autowired
    CustomerProductQuoteService customerProductQuoteService;

    @Autowired
    RedisTemplate redisTemplate;


    @ApiOperation("认领报价申请")
    @PostMapping("/claim")
    public BaseResponse claimQuoteApply(@RequestBody ClaimQuoteApplyRequest request){
        Session session = UserUtil.getSession(redisTemplate);
        return quoteApplyService.claimQuoteApply(request,session);
    }

    @ApiOperation("处理报价申请")
    @PostMapping("/{quoteApplyId}/process")
    public BaseResponse processQuoteApply(@RequestBody QuoteApplyProcessRequest request,@PathVariable Long quoteApplyId){
        Session session = UserUtil.getSession(redisTemplate);
        try {
            return quoteApplyService.processQuoteApply(request,quoteApplyId,session);
        } catch (CustomerException e) {
            e.printStackTrace();
            return BaseResponse.failed(e.getMessage());
        }
    }

    @ApiOperation("完结报价申请")
    @PostMapping("/{quoteApplyId}/finish")
    public BaseResponse finishQuoteApply(@PathVariable Long quoteApplyId){
        Session session = UserUtil.getSession(redisTemplate);
        return quoteApplyService.finishQuoteApply(quoteApplyId,session);
    }

    @ApiOperation("不用对接")
    @PostMapping("/order")
    public BaseResponse orderQuoteApply(@RequestBody OrderQuoteApplyRequest request){
        return quoteApplyService.orderQuoteApply(request);
    }

    @ApiOperation("报价详情")
    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    @Permission(permission = "quote:quoteapply:info:id")
    public QuoteApplyInfoResponse info(@PathVariable Long id) {
        QuoteApplyVo quoteApplyVo = new QuoteApplyVo();
        Page<QuoteApplyListDto> page = new Page<>();
        QuoteApplyListDto quoteApplyListDto = new QuoteApplyListDto();
        quoteApplyListDto.setId(id);
        page.setT(quoteApplyListDto);
        List<QuoteApplyVo> results = quoteApplyService.quoteApplyList(page);
        if (ListUtils.isNotEmpty(results)){
            quoteApplyVo = results.get(0);
        }
        QuoteApplyInfoResponse res = new QuoteApplyInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,quoteApplyVo,id);
        return res;
    }

    @ApiOperation("报价申请列表")
    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "quote:quoteapply:list")
    public BaseResponse list(@RequestBody @Valid Page<QuoteApplyListDto> request) {
        List<QuoteApplyVo> results = quoteApplyService.quoteApplyList(request);
        Long total = quoteApplyService.quoteApplyCount(request);
        request.setTotal(total);
        BaseResponse res = new BaseResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }


    @ApiOperation("报价进度")
    @PostMapping("/schedule")
    public BaseResponse quoteSchedule(@RequestBody @Valid Page<QuoteApplyListDto> request){
        Session session = UserUtil.getSession(redisTemplate);
        if (null == request.getT()){
            request.setT(new QuoteApplyListDto());
        }
        request.getT().setQuoteState(1);
        request.getT().setHandleUserId(session.getId());
        List<QuoteApplyVo> results = quoteApplyService.quoteApplyList(request);
        Long total = quoteApplyService.quoteApplyCount(request);
        request.setTotal(total);
        BaseResponse res = new BaseResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }


//    @PostMapping("/manage")
//    public BaseResponse quoteApplyManage(){
//
//    }

//    @RequestMapping(value="/add", method=RequestMethod.POST)
//    @Permission(permission = "quote:quoteapply:add")
//    public QuoteApplyAddResponse add(@RequestBody @Valid QuoteApplyAddRequest request) {
//        QuoteApply entity=request.toQuoteApply();
//        quoteApplyService.insertSelective(entity);
//        QuoteApplyAddResponse res = new QuoteApplyAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
//        return res;
//    }
//
//    @RequestMapping(value="/del/{id}", method=RequestMethod.POST)
//    @Permission(permission = "quote:quoteapply:del:id")
//    public QuoteApplyDelResponse del(@PathVariable Long id) {
//        quoteApplyService.deleteByPrimaryKey(id);
//        QuoteApplyDelResponse res = new QuoteApplyDelResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
//        return res;
//    }
//
//    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
//    @Permission(permission = "quote:quoteapply:update")
//    public QuoteApplyUpdateResponse update(@PathVariable Long id,@RequestBody @Valid QuoteApplyUpdateRequest request) {
//        QuoteApply entity=request.toQuoteApply(id);
//        quoteApplyService.updateByPrimaryKeySelective(entity);
//        QuoteApplyUpdateResponse res = new QuoteApplyUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
//        return res;
//    }


}
