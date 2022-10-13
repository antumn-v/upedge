package com.upedge.pms.modules.image.controller;

import java.util.Arrays;
import java.util.Map;

import com.upedge.common.constant.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.common.component.annotation.Permission;
import com.upedge.pms.modules.image.entity.ImageUploadRecord;
import com.upedge.pms.modules.image.service.ImageUploadRecordService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.upedge.common.constant.Constant;
import com.upedge.pms.modules.image.request.ImageUploadRecordAddRequest;
import com.upedge.pms.modules.image.request.ImageUploadRecordListRequest;
import com.upedge.pms.modules.image.request.ImageUploadRecordUpdateRequest;

import com.upedge.pms.modules.image.response.ImageUploadRecordAddResponse;
import com.upedge.pms.modules.image.response.ImageUploadRecordDelResponse;
import com.upedge.pms.modules.image.response.ImageUploadRecordInfoResponse;
import com.upedge.pms.modules.image.response.ImageUploadRecordListResponse;
import com.upedge.pms.modules.image.response.ImageUploadRecordUpdateResponse;
import javax.validation.Valid;

/**
 * 
 *
 * @author gx
 */
@RestController
@RequestMapping("/imageUploadRecord")
public class ImageUploadRecordController {
    @Autowired
    private ImageUploadRecordService imageUploadRecordService;


    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    @Permission(permission = "image:imageuploadrecord:info:id")
    public ImageUploadRecordInfoResponse info(@PathVariable Integer id) {
        ImageUploadRecord result = imageUploadRecordService.selectByPrimaryKey(id);
        ImageUploadRecordInfoResponse res = new ImageUploadRecordInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "image:imageuploadrecord:list")
    public ImageUploadRecordListResponse list(@RequestBody @Valid ImageUploadRecordListRequest request) {
        List<ImageUploadRecord> results = imageUploadRecordService.select(request);
        Long total = imageUploadRecordService.count(request);
        request.setTotal(total);
        ImageUploadRecordListResponse res = new ImageUploadRecordListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    @Permission(permission = "image:imageuploadrecord:add")
    public ImageUploadRecordAddResponse add(@RequestBody @Valid ImageUploadRecordAddRequest request) {
        ImageUploadRecord entity=request.toImageUploadRecord();
        imageUploadRecordService.insertSelective(entity);
        ImageUploadRecordAddResponse res = new ImageUploadRecordAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
        return res;
    }

    @RequestMapping(value="/del/{id}", method=RequestMethod.POST)
    @Permission(permission = "image:imageuploadrecord:del:id")
    public ImageUploadRecordDelResponse del(@PathVariable Integer id) {
        imageUploadRecordService.deleteByPrimaryKey(id);
        ImageUploadRecordDelResponse res = new ImageUploadRecordDelResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    @Permission(permission = "image:imageuploadrecord:update")
    public ImageUploadRecordUpdateResponse update(@PathVariable Integer id,@RequestBody @Valid ImageUploadRecordUpdateRequest request) {
        ImageUploadRecord entity=request.toImageUploadRecord(id);
        imageUploadRecordService.updateByPrimaryKeySelective(entity);
        ImageUploadRecordUpdateResponse res = new ImageUploadRecordUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }


}
