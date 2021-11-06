package com.upedge.oms.modules.reason.controller;

import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.oms.modules.reason.entity.TrackAgainReason;
import com.upedge.oms.modules.reason.request.TrackAgainReasonAddRequest;
import com.upedge.oms.modules.reason.request.TrackAgainReasonListRequest;
import com.upedge.oms.modules.reason.request.TrackAgainReasonUpdateRequest;
import com.upedge.oms.modules.reason.response.*;
import com.upedge.oms.modules.reason.service.TrackAgainReasonService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.*;
/**
 * 
 *
 * @author author
 */
@RestController
@RequestMapping("/trackAgainReason")
public class TrackAgainReasonController {
    @Autowired
    private TrackAgainReasonService trackAgainReasonService;

    @RequestMapping(value="/admin/all", method=RequestMethod.POST)
    public TrackAgainReasonListResponse all() {
        return trackAgainReasonService.all();
    }

    @RequestMapping(value="/admin/info/{id}", method=RequestMethod.GET)
    public TrackAgainReasonInfoResponse info(@PathVariable Long id) {
        TrackAgainReason result = trackAgainReasonService.selectByPrimaryKey(id);
        TrackAgainReasonInfoResponse res = new TrackAgainReasonInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    @RequestMapping(value="/admin/list", method=RequestMethod.POST)
    public TrackAgainReasonListResponse list(@RequestBody @Valid TrackAgainReasonListRequest request) {
        List<TrackAgainReason> results = trackAgainReasonService.select(request);
        Long total = trackAgainReasonService.count(request);
        request.setTotal(total);
        TrackAgainReasonListResponse res = new TrackAgainReasonListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    @RequestMapping(value="/admin/add", method=RequestMethod.POST)
    public TrackAgainReasonAddResponse add(@RequestBody @Valid TrackAgainReasonAddRequest request) {
        TrackAgainReason entity=request.toTrackAgainReason();
        trackAgainReasonService.insertSelective(entity);
        TrackAgainReasonAddResponse res = new TrackAgainReasonAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
        return res;
    }

    @RequestMapping(value="/admin/update/{id}", method=RequestMethod.POST)
    public TrackAgainReasonUpdateResponse update(@PathVariable Long id, @RequestBody @Valid TrackAgainReasonUpdateRequest request) {
        TrackAgainReason entity=request.toTrackAgainReason(id);
        trackAgainReasonService.updateByPrimaryKeySelective(entity);
        TrackAgainReasonUpdateResponse res = new TrackAgainReasonUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @RequestMapping(value="/admin/enable/{id}", method=RequestMethod.POST)
    public TrackAgainReasonUpdateResponse enable(@PathVariable Long id) {
        TrackAgainReason entity=new TrackAgainReason();
        entity.setId(id);
        entity.setState(1);
        trackAgainReasonService.updateByPrimaryKeySelective(entity);
        TrackAgainReasonUpdateResponse res = new TrackAgainReasonUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @RequestMapping(value="/admin/disable/{id}", method=RequestMethod.POST)
    public TrackAgainReasonUpdateResponse disable(@PathVariable Long id) {
        TrackAgainReason entity=new TrackAgainReason();
        entity.setId(id);
        entity.setState(0);
        trackAgainReasonService.updateByPrimaryKeySelective(entity);
        TrackAgainReasonUpdateResponse res = new TrackAgainReasonUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

}
