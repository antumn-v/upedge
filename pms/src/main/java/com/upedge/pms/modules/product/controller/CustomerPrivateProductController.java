package com.upedge.pms.modules.product.controller;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.component.annotation.Permission;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.model.store.StoreVo;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.model.user.vo.UserVo;
import com.upedge.common.utils.ListUtils;
import com.upedge.common.web.util.UserUtil;
import com.upedge.pms.modules.product.entity.CustomerPrivateProduct;
import com.upedge.pms.modules.product.request.AllocationPrivateProductRequest;
import com.upedge.pms.modules.product.request.CustomerPrivateProductListRequest;
import com.upedge.pms.modules.product.response.CustomerPrivateProductListResponse;
import com.upedge.pms.modules.product.service.CustomerPrivateProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author gx
 */
@Api(tags = "私有产品管理")
@RestController
@RequestMapping("/customerPrivateProduct")
public class CustomerPrivateProductController {
    @Autowired
    private CustomerPrivateProductService customerPrivateProductService;

    @Autowired
    RedisTemplate redisTemplate;

    @ApiOperation("分配私有产品")
    @PostMapping("/allocation")
    public BaseResponse allocationPrivateProduct(@RequestBody@Valid AllocationPrivateProductRequest request){
        Session session = UserUtil.getSession(redisTemplate);
        return customerPrivateProductService.allocationPrivateProduct(request,session);
    }

    @ApiOperation("私有产品分配客户")
    @PostMapping("/assignedUser/{productId}")
    public BaseResponse productAssignedUser(@PathVariable Long productId){
        CustomerPrivateProductListRequest request = new CustomerPrivateProductListRequest();
        CustomerPrivateProduct customerPrivateProduct = new CustomerPrivateProduct();
        customerPrivateProduct.setProductId(productId);
        request.setT(customerPrivateProduct);
        request.setPageSize(-1);
        Map<String,Object> map = new HashMap<>();
        List<CustomerPrivateProduct> customerPrivateProducts = customerPrivateProductService.select(request);
        if (ListUtils.isNotEmpty(customerPrivateProducts)){
            List<UserVo> userVos = new ArrayList<>();
            List<StoreVo> storeVos = new ArrayList<>();
            for (CustomerPrivateProduct privateProduct : customerPrivateProducts) {
                if (privateProduct.getCustomerId() != null && !privateProduct.getCustomerId().equals(0L)){
                    UserVo userVo = (UserVo) redisTemplate.opsForHash().get(RedisKey.STRING_CUSTOMER_INFO,String.valueOf(privateProduct.getCustomerId()));
                    userVos.add(userVo);
                }else if ((privateProduct.getStoreId() != null) && !privateProduct.getStoreId().equals(0L)){
                    StoreVo storeVo = (StoreVo) redisTemplate.opsForValue().get(RedisKey.STRING_STORE + privateProduct.getStoreId());
                    storeVos.add(storeVo);
                }
            }
            map.put("store",storeVos);
            map.put("user",userVos);
            return BaseResponse.success(map);
        }
        return BaseResponse.success(new ArrayList<>());
    }

    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "product:customerprivateproduct:list")
    public CustomerPrivateProductListResponse list(@RequestBody @Valid CustomerPrivateProductListRequest request) {
        List<CustomerPrivateProduct> results = customerPrivateProductService.select(request);
        Long total = customerPrivateProductService.count(request);
        request.setTotal(total);
        CustomerPrivateProductListResponse res = new CustomerPrivateProductListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }






}
