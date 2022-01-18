package com.upedge.oms.modules.cart.controller;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.component.annotation.Permission;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.model.cart.request.CartAddRequest;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.utils.ListUtils;
import com.upedge.common.web.util.UserUtil;
import com.upedge.oms.modules.cart.entity.Cart;
import com.upedge.oms.modules.cart.request.*;
import com.upedge.oms.modules.cart.response.CartDelResponse;
import com.upedge.oms.modules.cart.response.CartInfoResponse;
import com.upedge.oms.modules.cart.response.CartListResponse;
import com.upedge.oms.modules.cart.response.CartUpdateResponse;
import com.upedge.oms.modules.cart.service.CartService;
import com.upedge.oms.modules.stock.controller.StockOrderController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

/**
 * 
 *
 * @author author
 */
@Api(tags = "购物车")
@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Autowired
    StockOrderController stockOrderController;

    @ApiOperation("详情")
    @RequestMapping(value="/info/{id}", method= RequestMethod.GET)
    @Permission(permission = "cart:cart:info:id")
    public CartInfoResponse info(@PathVariable Long id) {
        Cart result = cartService.selectByPrimaryKey(id);
        CartInfoResponse res = new CartInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    @ApiOperation("购物车列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "state",value = "默认为0",required = false),
            @ApiImplicitParam(name = "cartType",value = "备库=0，批发=1",required = true)
    })
    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "cart:cart:list")
    public CartListResponse list(@RequestBody @Valid CartListRequest request) {
        Session session = UserUtil.getSession(redisTemplate);

        if(null == request.getT() || null == request.getT().getCartType()){
            return new CartListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_FAIL);
        }
        request.getT().setCustomerId(session.getCustomerId());

        List<Cart> results = cartService.select(request);
        Long total = cartService.count(request);
        request.setTotal(total);
        CartListResponse res = new CartListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    @ApiOperation("删除")
    @RequestMapping(value="/del/{id}", method=RequestMethod.POST)
    @Permission(permission = "cart:cart:del:id")
    public CartDelResponse del(@PathVariable Long id) {
        Cart entity=new Cart();
        entity.setId(id);
        entity.setState(2);
        entity.setUpdateTime(new Date());
        cartService.updateByPrimaryKeySelective(entity);
        CartDelResponse res = new CartDelResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @ApiOperation("修改")
    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    @Permission(permission = "cart:cart:update")
    public CartUpdateResponse update(@PathVariable Long id, @RequestBody @Valid CartUpdateRequest request) {
        Cart entity=request.toCart(id);
        cartService.updateByPrimaryKeySelective(entity);
        CartUpdateResponse res = new CartUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @ApiOperation("产品导入购物车")
    @PostMapping("/add")
    public BaseResponse addVariantToCart(@RequestBody @Valid CartAddRequest request){
        if (request.getCartType() == null){
            return BaseResponse.failed();
        }
        switch (request.getCartType()){
            case 0:
                return cartService.addStockCart(request);
            case 1:
                return cartService.addWholesaleCarts(request);
            default:break;
        }
        return BaseResponse.failed();
    }

//    @ApiOperation("添加备库订单购物车")
//    @PostMapping("/addStock")
//    public BaseResponse addToStockCart(@RequestBody CartAddStockRequest request){
//        Session session = UserUtil.getSession(redisTemplate);
//        return cartService.addStockCart(request,session);
//    }
//
//    @ApiOperation("购物车添加产品，看产品模块/pms/product/importCart接口")
//    @PostMapping("/addWholesale")
//    public BaseResponse cartAdd(@RequestBody @Valid CartAddRequest request){
//        cartService.addWholesaleCarts(request);
//        return new BaseResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
//
//    }

    @ApiOperation("购物车提交列表")
    @PostMapping("/submit/list")
    public BaseResponse cartSubmitList(@RequestBody CartSubmitListRequest request){
        List<Cart> carts = cartService.selectByIdsAndType(request.getIds(),request.getCartType());
        if(ListUtils.isEmpty(carts)){
            return new BaseResponse(ResultCode.FAIL_CODE,Constant.MESSAGE_FAIL);
        }
        return new BaseResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,carts);
    }

    @ApiOperation("购物车提交备库订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cartType",value = "购物车类型，0=备库订单，1=批发订单",required = true),
            @ApiImplicitParam(name = "warehouseId",value = "仓库ID",required = true),
            @ApiImplicitParam(name = "payMethod",value = "支付方式，0=balance，1=paypal,可以不传",required = false)
    })
    @PostMapping("/submit/stock")
    public BaseResponse cartSubmitOrder(@RequestBody CartSubmitOrderRequest request){
        Session session = UserUtil.getSession(redisTemplate);
        Long orderId = cartService.cartSubmitStockOrder(request,session);
        if(null == orderId){
            return BaseResponse.failed();
        }
//        if(null != request.getPayMethod()){
//            List<Long> orderIds = new ArrayList<>();
//            orderIds.add(orderId);
//            switch (request.getPayMethod()){
//                case PayOrderMethod
//                        .RECHARGE:
//                    return stockOrderController.payByBalance(orderIds);
//                case PayOrderMethod.PAYPAL:
//                    return stockOrderController.payByPaypal(orderIds);
//                default:
//                    break;
//            }
//        }
        return BaseResponse.success();
    }

    @ApiOperation("购物车提交批发订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cartType",value = "购物车类型，0=备库订单，1=批发订单",required = true),
            @ApiImplicitParam(name = "addressVo",value = "地址",required = true),
    })
    @PostMapping("/submit/wholesale")
    public BaseResponse cartSubmitWholesale(@RequestBody CartSubmitWholesaleRequest request){
        boolean b = cartService.cartSubmitWholesaleOrder(request);
        if(b){
            return BaseResponse.success();
        }
        return BaseResponse.failed();
    }

    @ApiOperation("批量删除")
    @RequestMapping(value="/delIds", method=RequestMethod.POST)
    @Permission(permission = "cart:cart:delIds")
    public BaseResponse delIds(@RequestBody @Validated DelCarts delCarts) {
        cartService.delIds(delCarts);
        return BaseResponse.success();
    }

}
