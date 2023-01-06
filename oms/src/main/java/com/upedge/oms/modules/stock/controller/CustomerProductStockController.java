package com.upedge.oms.modules.stock.controller;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.component.annotation.Permission;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.exception.CustomerException;
import com.upedge.common.model.oms.stock.CustomerStockSearchRequest;
import com.upedge.common.model.oms.stock.CustomerStockVo;
import com.upedge.common.model.sms.WholesaleOrderItemDischargeStockVo;
import com.upedge.common.model.tms.WarehouseVo;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.web.util.UserUtil;
import com.upedge.oms.modules.stock.entity.CustomerProductStock;
import com.upedge.oms.modules.stock.entity.CustomerStockRecord;
import com.upedge.oms.modules.stock.request.*;
import com.upedge.oms.modules.stock.response.CustomerProductStockInfoResponse;
import com.upedge.oms.modules.stock.response.CustomerProductStockListResponse;
import com.upedge.oms.modules.stock.service.CustomerProductStockService;
import com.upedge.oms.modules.stock.service.CustomerStockRecordService;
import com.upedge.oms.modules.stock.vo.CustomerStockRecordDetailVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * 
 *
 * @author author
 */
@Api(tags = "客户库存")
@RestController
@RequestMapping("/customer/stock")
public class CustomerProductStockController {
    @Autowired
    private CustomerProductStockService customerProductStockService;

    @Autowired
    CustomerStockRecordService customerStockRecordService;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @ApiOperation("手动修改客户产品库存")
    @PostMapping("/customUpdate")
    public BaseResponse customUpdateCustomerProductStock(@RequestBody@Valid CustomerProductStockCustomUpdateRequest request){
        Session session = UserUtil.getSession(redisTemplate);
        return customerProductStockService.customUpdateCustomerProductStock(request,session);
    }

    @ApiOperation("使用记录")
    @RequestMapping(value="/{id}/record", method=RequestMethod.POST)
    @Permission(permission = "stock:customerproductstock:info:id")
    public CustomerProductStockInfoResponse info(@PathVariable Long id, @RequestBody CustomerStockRecordListRequest recordListRequest) {
        Session session = UserUtil.getSession(redisTemplate);
        CustomerProductStock result = customerProductStockService.selectByPrimaryKey(id);
        if (null == result){
            return new CustomerProductStockInfoResponse(ResultCode.FAIL_CODE,Constant.MESSAGE_FAIL);
        }

        if (null == recordListRequest.getT()){
            recordListRequest.setT(new CustomerStockRecord());
        }
        if (session.getApplicationId() == Constant.APP_APPLICATION_ID){
            recordListRequest.getT().setCustomerShowState(1);
        }
        recordListRequest.getT().setCustomerId(result.getCustomerId());
        recordListRequest.getT().setVariantId(result.getVariantId());
        recordListRequest.getT().setProductId(result.getProductId());
        recordListRequest.setOrderBy("create_time desc");
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

    @ApiOperation("客户库存所在仓库列表")
    @GetMapping("/warehouses/{customerId}")
    public BaseResponse customerStockWarehouses(@PathVariable Long customerId){
        List<WarehouseVo> warehouseVos = customerProductStockService.selectCustomerStockWarehouses(customerId);
        return BaseResponse.success(warehouseVos);
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
        request.getT().setCustomerShowState(1);
        List<CustomerProductStock> results = customerProductStockService.select(request);
        Long total = customerProductStockService.count(request);
        request.setTotal(total);
        CustomerProductStockListResponse res = new CustomerProductStockListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }


//    /**
//     * 根据variant_sku分组查询商品数量
//     */
//    @RequestMapping(value="/list", method=RequestMethod.GET)
//    public BaseResponse customerStockNum() {
//        List<CustomerProductStockNumVo> result = customerProductStockService.getCustomerStockNum();
//        return BaseResponse.success(result);
//    }

    /**
     * 备库管理/客户商品库存/同步库存
     * @param id
     * @return
     */
    @ApiOperation("同步赛盒库存")
    @PostMapping(value = "/refreshSaiheInventory/{id}")
    public BaseResponse refreshSaiheInventory(@PathVariable Long id) throws CustomerException {
        return   customerProductStockService.refreshSaiheInventory(id);
    }

    @ApiOperation("手动添加产品库存")
    @PostMapping("/addManually")
    public BaseResponse manualAddCustomerStock(@RequestBody@Valid ManualAddCustomerStockRequest request){
        Session session = UserUtil.getSession(redisTemplate);
        return customerProductStockService.manualAddCustomerVariantStock(request,session);
    }

    @ApiOperation("可撤销的手动增加库存记录")
    @PostMapping("/revokeList")
    public BaseResponse revokeList(@RequestBody@Valid CustomerProductStockRevokeListRequest request){
        CustomerStockRecord customerStockRecord = new CustomerStockRecord();
        customerStockRecord.setWarehouseCode(request.getWarehouseCode());
        customerStockRecord.setCustomerId(request.getCustomerId());
        customerStockRecord.setVariantId(request.getVariantId());
        customerStockRecord.setType(3);
        customerStockRecord.setRevokeState(0);
        Page<CustomerStockRecord> page = new Page<>();
        page.setT(customerStockRecord);
        page.setPageSize(-1);
        List<CustomerStockRecord> customerStockRecords = customerStockRecordService.select(page);
        return BaseResponse.success(customerStockRecords);
    }

    @ApiOperation("撤销手动增加的库存记录")
    @PostMapping("/revokeManualAdd/{recordId}")
    public BaseResponse revokeManualAddCustomerStock(@PathVariable Long recordId){
        Session session = UserUtil.getSession(redisTemplate);
        return customerProductStockService.revokeManualAddRecord(recordId,session);
    }

    @ApiOperation("更新客户库存展示状态")
    @PostMapping("/updateShowState")
    public BaseResponse updateShowState(@RequestBody@Valid CustomerProductStockUpdateShowStateRequest request){
        CustomerProductStock customerProductStock = customerProductStockService.selectByPrimaryKey(request.getId());
        if (customerProductStock == null){
            return BaseResponse.failed("库存记录不存在");
        }
        if (customerProductStock.getCustomerShowState() == request.getState()){
            return BaseResponse.success();
        }
        customerProductStock = new CustomerProductStock();
        customerProductStock.setUpdateTime(new Date());
        customerProductStock.setId(request.getId());
        customerProductStock.setCustomerShowState(request.getState());
        customerProductStockService.updateByPrimaryKeySelective(customerProductStock);
        return BaseResponse.success();
    }

    @PostMapping("/searchByVariants")
    public List<CustomerStockVo> searchByVariants(@RequestBody CustomerStockSearchRequest request){
        return customerProductStockService.selectCustomerStockByVariantIds(request.getCustomerId(),request.getVariantIds());
    }

    @PostMapping("/reduceByWholesale")
    public BaseResponse reduceByWholesale(@RequestBody List<WholesaleOrderItemDischargeStockVo> wholesaleOrderItemDischargeStockVos){
        return customerProductStockService.reduceByWholesale(wholesaleOrderItemDischargeStockVos);
    }
}
