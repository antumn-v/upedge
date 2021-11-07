package com.upedge.tms.modules.ship.controller;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.model.ship.request.*;
import com.upedge.common.model.ship.response.ShipMethodBatchSearchResponse;
import com.upedge.common.model.ship.response.ShipMethodSearchResponse;
import com.upedge.common.model.ship.vo.ShipDetail;
import com.upedge.common.model.ship.vo.ShipMethodNameVo;
import com.upedge.common.model.ship.vo.ShippingMethodVo;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.web.util.UserUtil;
import com.upedge.tms.modules.ship.entity.ShippingMethod;
import com.upedge.tms.modules.ship.request.ShippingMethodAddRequest;
import com.upedge.tms.modules.ship.request.ShippingMethodListRequest;
import com.upedge.tms.modules.ship.request.ShippingMethodUpdateRequest;
import com.upedge.tms.modules.ship.response.*;
import com.upedge.tms.modules.ship.service.ShippingMethodService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.lang.reflect.Field;
import java.util.List;

/**
 * 运输方式表
 *
 * @author author
 */
@RestController
@RequestMapping("/shippingMethod")
public class ShippingMethodController {
    @Autowired
    private ShippingMethodService shippingMethodService;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @RequestMapping(value="/all", method=RequestMethod.POST)
    public BaseResponse all() {
        List<ShippingMethod> results = shippingMethodService.allShippingMethod();
        return new BaseResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results);
    }

    /**
     * 运输方式列表
     * @param request
     * @return
     */
    @RequestMapping(value="/list", method=RequestMethod.POST)
    public ShippingMethodListResponse list(@RequestBody @Valid ShippingMethodListRequest request) {
        return shippingMethodService.list(request);
    }

    /**
     * 运输方式列表
     * @param request
     * @return
     */
    @RequestMapping(value="/listShippingMethod", method=RequestMethod.POST)
    public ShippingMethodListResponse listShippingMethod(@RequestBody @Valid ShippingMethodsRequest request) {
        return shippingMethodService.listShippingMethod(request);
    }

    /**
     * 单个运输方式信息
     * @param id
     * @return
     */
    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    public BaseResponse shippingMethodInfo(@PathVariable Long id) {
        ShippingMethodVo shippingMethodVo=new ShippingMethodVo();
        ShippingMethod result = shippingMethodService.selectByPrimaryKey(id);
        BeanUtils.copyProperties(result,shippingMethodVo);
        updateShipMethodInRedis(shippingMethodVo);
        ShippingMethodInfoResponse res = new ShippingMethodInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,shippingMethodVo,id);
        return res;
    }

    /**
     * 新增运输方式
     * @param request
     * @return
     */
    @RequestMapping(value="/add", method=RequestMethod.POST)
    public ShippingMethodAddResponse add(@RequestBody @Valid ShippingMethodAddRequest request) {
        ShippingMethod entity=request.toShippingMethod();
        shippingMethodService.insertSelective(entity);
        ShippingMethodAddResponse res = new ShippingMethodAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
        return res;
    }

    /**
     * 更新运输方式
     * @param id
     * @param request
     * @return
     */
    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    public ShippingMethodUpdateResponse update(@PathVariable Long id, @RequestBody @Valid ShippingMethodUpdateRequest request) {
        ShippingMethod entity=request.toShippingMethod(id);
        shippingMethodService.updateByPrimaryKeySelective(entity);
        //调用mq
        shippingMethodService.senMq(id);
        ShippingMethodVo shippingMethodVo = new ShippingMethodVo();
        BeanUtils.copyProperties(entity,shippingMethodVo);
        updateShipMethodInRedis(shippingMethodVo);
        ShippingMethodUpdateResponse res = new ShippingMethodUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    /**
     * 启用运输方式
     * @return
     */
    @RequestMapping(value="/enable/{id}", method=RequestMethod.POST)
    public ShippingMethodEnableResponse enableShippingMethod(@PathVariable Long id) {
        Session session = UserUtil.getSession(redisTemplate);
        return shippingMethodService.enableShippingMethod(id);
    }

    /**
     * 禁用运输方式
     * @return
     */
    @RequestMapping(value="/disable/{id}", method=RequestMethod.POST)
    public ShippingMethodDisableResponse disableShippingMethod(@PathVariable Long id) {
        Session session = UserUtil.getSession(redisTemplate);
        redisTemplate.delete(RedisKey.HASH_SHIP_METHOD + id);
        return shippingMethodService.disableShippingMethod(id);
    }

    /**
     * 根据赛盒运输id查询admin运输方式
     * @return
     */
    @RequestMapping(value="/getShippingMethodByTransportId", method=RequestMethod.POST)
    public BaseResponse getShippingMethodByTransportId(@RequestBody ShippingMethodVo shippingMethodVo) {
        return shippingMethodService.getShippingMethodByTransportId(shippingMethodVo.getSaiheTransportId(),shippingMethodVo.getId());
    }

    //=================================================================
    @PostMapping("/search")
    public ShipMethodSearchResponse shipSearch(@RequestBody ShipMethodSearchRequest request){
        List<ShipDetail> shipDetails =  shippingMethodService.searchShipMethods(request);
        return new ShipMethodSearchResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,shipDetails);
    }

    @PostMapping("/search/batch")
    public ShipMethodBatchSearchResponse batchShipSearch(@RequestBody ShipMethodBatchSearchRequest request){
        return shippingMethodService.batchSearchShipMethods(request);
    }

    @PostMapping("/search/mixShipMethodByCountries")
    public BaseResponse mixShipMethodNameByCountries(@RequestBody @Valid CountriesMixShipMethodRequest request){
        List<ShipMethodNameVo> shipMethodNameVos = shippingMethodService.selectMixedShipMethodNamesByCountries(request.getCountries());
        return new BaseResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,shipMethodNameVos);
    }

    @PostMapping("/search/price")
    public BaseResponse shipMethodPrice(@RequestBody ShipMethodPriceRequest request){
        ShipDetail shipDetail = shippingMethodService.searchShipPriceByMethodId(request);
        if(null != shipDetail){
            return new BaseResponse(ResultCode.SUCCESS_CODE,shipDetail);
        }
        return BaseResponse.failed();
    }

    void updateShipMethodInRedis(ShippingMethodVo shippingMethodVo){
        Field[] fields = shippingMethodVo.getClass().getDeclaredFields();
        for(int i=0,len = fields.length;i<len;i++){
            //这个是，有的字段是用private修饰的 将他设置为可读
            fields[i].setAccessible(true);
            try {
                redisTemplate.opsForHash().put(RedisKey.HASH_SHIP_METHOD + shippingMethodVo.getId(),fields[i].getName(),fields[i].get(shippingMethodVo));
            } catch (Exception e) {
                e.printStackTrace();
                redisTemplate.delete(RedisKey.HASH_SHIP_METHOD + shippingMethodVo.getId());
                break;
            }
        }
    }

}