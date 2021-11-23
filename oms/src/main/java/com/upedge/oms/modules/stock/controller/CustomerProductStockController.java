package com.upedge.oms.modules.stock.controller;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.component.annotation.Permission;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.exception.CustomerException;
import com.upedge.common.model.order.vo.CustomerProductStockNumVo;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.web.util.UserUtil;
import com.upedge.oms.modules.stock.entity.CustomerProductStock;
import com.upedge.oms.modules.stock.entity.CustomerStockRecord;
import com.upedge.oms.modules.stock.request.CustomerProductStockListRequest;
import com.upedge.oms.modules.stock.request.CustomerStockRecordListRequest;
import com.upedge.oms.modules.stock.response.CustomerProductStockInfoResponse;
import com.upedge.oms.modules.stock.response.CustomerProductStockListResponse;
import com.upedge.oms.modules.stock.service.CustomerProductStockService;
import com.upedge.oms.modules.stock.service.CustomerStockRecordService;
import com.upedge.oms.modules.stock.vo.CustomerStockRecordDetailVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.*;
/**
 * 
 *
 * @author author
 */
@ApiOperation("客户库存")
@RestController
@RequestMapping("/customer/stock")
public class CustomerProductStockController {
    @Autowired
    private CustomerProductStockService customerProductStockService;

    @Autowired
    CustomerStockRecordService customerStockRecordService;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @ApiOperation("使用记录")
    @RequestMapping(value="/{id}/record", method=RequestMethod.POST)
    @Permission(permission = "stock:customerproductstock:info:id")
    public CustomerProductStockInfoResponse info(@PathVariable Long id, @RequestBody CustomerStockRecordListRequest recordListRequest) {

        CustomerProductStock result = customerProductStockService.selectByPrimaryKey(id);

        if (null == recordListRequest.getT()){
            recordListRequest.setT(new CustomerStockRecord());

        }
        recordListRequest.getT().setCustomerId(result.getCustomerId());
        recordListRequest.getT().setVariantId(result.getVariantId());
        recordListRequest.getT().setProductId(result.getProductId());
        recordListRequest.setOrderBy("create_time desc");
        recordListRequest.setPageSize(20);
        List<CustomerStockRecord> records = customerStockRecordService.select(recordListRequest);
        List<CustomerStockRecordDetailVo> resultList = new ArrayList<>();
        for (CustomerStockRecord record : records) {
            CustomerStockRecordDetailVo customerStockRecordDetailVo = new CustomerStockRecordDetailVo();
            BeanUtils.copyProperties(record,customerStockRecordDetailVo);
            customerStockRecordDetailVo.setProductTitle(result.getProductTitle());
            resultList.add(customerStockRecordDetailVo);
        }
        Long count = customerStockRecordService.count(recordListRequest);

        recordListRequest.setTotal(count);

        CustomerProductStockInfoResponse res = new CustomerProductStockInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,resultList,recordListRequest);
        return res;
    }

    @ApiOperation("库存列表")
    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "stock:customerproductstock:list")
    public CustomerProductStockListResponse list(@RequestBody @Valid CustomerProductStockListRequest request) {
        Session session = UserUtil.getSession(redisTemplate);

        if(null == request.getT()){
            request.setT(new CustomerProductStock());
        }
        request.getT().setCustomerId(session.getCustomerId());
        List<CustomerProductStock> results = customerProductStockService.select(request);
        Long total = customerProductStockService.count(request);
        request.setTotal(total);
        CustomerProductStockListResponse res = new CustomerProductStockListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }


    /**
     * 根据variant_sku分组查询商品数量
     */
    @RequestMapping(value="/list", method=RequestMethod.GET)
    public BaseResponse customerStockNum() {
        List<CustomerProductStockNumVo> result = customerProductStockService.getCustomerStockNum();
        return BaseResponse.success(result);
    }

    /**
     * 备库管理/客户商品库存/同步库存
     * @param id
     * @return
     */
//    @ApiOperation("同步赛盒库存")
//    @PostMapping(value = "/refreshSaiheInventory/{id}")
    public BaseResponse refreshSaiheInventory(@PathVariable Long id) throws CustomerException {
        return   customerProductStockService.refreshSaiheInventory(id);
    }


}
