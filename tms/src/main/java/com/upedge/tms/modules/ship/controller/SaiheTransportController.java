package com.upedge.tms.modules.ship.controller;

import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.tms.modules.ship.entity.SaiheTransport;
import com.upedge.tms.modules.ship.request.SaiheTransportListRequest;
import com.upedge.tms.modules.ship.request.SaiheTransportUpdateRequest;
import com.upedge.tms.modules.ship.response.SaiheTransportInfoResponse;
import com.upedge.tms.modules.ship.response.SaiheTransportListResponse;
import com.upedge.tms.modules.ship.response.SaiheTransportUpdateResponse;
import com.upedge.tms.modules.ship.service.SaiheTransportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 
 *
 * @author author
 */
@RestController
@RequestMapping("/saiheTransport")
public class SaiheTransportController {
    @Autowired
    private SaiheTransportService saiheTransportService;

    /**
     * 赛盒运输方式下拉列表
     * @return
     */
    @RequestMapping(value="/all", method=RequestMethod.POST)
    public SaiheTransportListResponse allSaiheTransport() {
        return saiheTransportService.allSaiheTransport();
    }

    /**
     * 赛盒运输方式列表
     * @param request
     * @return
     */
    @RequestMapping(value="/list", method=RequestMethod.POST)
    public SaiheTransportListResponse list(@RequestBody @Valid SaiheTransportListRequest request) {
        List<SaiheTransport> results = saiheTransportService.select(request);
        Long total = saiheTransportService.count(request);
        request.setTotal(total);
        SaiheTransportListResponse res = new SaiheTransportListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    /**
     * 单个赛盒运输方式信息
     * @param id
     * @return
     */
    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    public SaiheTransportInfoResponse info(@PathVariable Integer id) {
        SaiheTransport result = saiheTransportService.selectByPrimaryKey(id);
        SaiheTransportInfoResponse res = new SaiheTransportInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    /**
     * 更新赛盒运输方式
     * @param id
     * @param request
     * @return
     */
    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    public SaiheTransportUpdateResponse update(@PathVariable Integer id, @RequestBody @Valid SaiheTransportUpdateRequest request) {
        SaiheTransport entity=request.toSaiheTransport(id);
        saiheTransportService.updateByPrimaryKeySelective(entity);
        SaiheTransportUpdateResponse res = new SaiheTransportUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    /**
     * 同步赛盒运输方式
     * @return
     */
    @RequestMapping(value="/refreshSaihe", method=RequestMethod.POST)
    public SaiheTransportUpdateResponse refreshSaihe() {
        return saiheTransportService.refreshSaihe();
    }


}
