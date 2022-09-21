package com.upedge.ums.modules.user.controller;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.component.annotation.Permission;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.model.user.vo.UserVo;
import com.upedge.ums.modules.user.entity.Customer;
import com.upedge.ums.modules.user.request.CustomerAddRequest;
import com.upedge.ums.modules.user.request.CustomerUpdateRequest;
import com.upedge.ums.modules.user.response.CustomerAddResponse;
import com.upedge.ums.modules.user.response.CustomerDelResponse;
import com.upedge.ums.modules.user.response.CustomerInfoResponse;
import com.upedge.ums.modules.user.response.CustomerUpdateResponse;
import com.upedge.ums.modules.user.service.CustomerService;
import com.upedge.ums.modules.user.service.UserService;
import com.upedge.ums.modules.user.vo.CustomerDetailVo;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 *
 * @author gx
 */
@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @Autowired
    UserService userService;

    @Autowired
    RedisTemplate redisTemplate;


    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    @Permission(permission = "user:customer:info:id")
    public CustomerInfoResponse info(@PathVariable Long id) {
        Customer result = customerService.selectByPrimaryKey(id);
        CustomerInfoResponse res = new CustomerInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "user:customer:list")
    public BaseResponse list(@RequestBody @Valid Page<CustomerDetailVo> request) {
        return customerService.selectCustomerDetail(request);
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    @Permission(permission = "user:customer:add")
    public CustomerAddResponse add(@RequestBody @Valid CustomerAddRequest request) {
        Customer entity=request.toCustomer();
        customerService.insertSelective(entity);
        CustomerAddResponse res = new CustomerAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
        return res;
    }

    @RequestMapping(value="/del/{id}", method=RequestMethod.POST)
    @Permission(permission = "user:customer:del:id")
    public CustomerDelResponse del(@PathVariable Long id) {
        customerService.deleteByPrimaryKey(id);
        CustomerDelResponse res = new CustomerDelResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @RequestMapping(value="/update", method=RequestMethod.POST)
    @Permission(permission = "user:customer:update")
    public CustomerUpdateResponse update(@PathVariable Long id,@RequestBody @Valid CustomerUpdateRequest request) {
        Customer entity=request.toCustomer(id);
        customerService.updateByPrimaryKeySelective(entity);
        if (StringUtils.isNotBlank(request.getRemark())){
            UserVo userVo = (UserVo) redisTemplate.opsForHash().get(RedisKey.STRING_CUSTOMER_INFO,id.toString());
            userVo.setRemark(request.getRemark());
            redisTemplate.opsForHash().put(RedisKey.STRING_CUSTOMER_INFO,id.toString(),userVo);
        }

        CustomerUpdateResponse res = new CustomerUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @ApiOperation("根据用户名查询客户列表")
    @GetMapping("/search/{name}")
    public BaseResponse searchByName(@PathVariable String name){
        List<UserVo> userVos = redisTemplate.opsForHash().values(RedisKey.STRING_CUSTOMER_INFO);
        List<Customer> customers = new ArrayList<>();
        userVos.forEach(userVo -> {
            if (userVo.getUsername().contains(name)){
                Customer customer = new Customer();
                customer.setCname(userVo.getUsername());
                customer.setId(userVo.getCustomerId());
                customers.add(customer);
            }
        });
        return BaseResponse.success(customers);
    }


}
