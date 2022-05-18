package com.upedge.sms.modules.photography.controller;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.component.annotation.Permission;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.OrderConstant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.utils.ListUtils;
import com.upedge.common.web.util.UserUtil;
import com.upedge.sms.modules.photography.entity.ProductPhotographyOrder;
import com.upedge.sms.modules.photography.entity.ProductPhotographyOrderItem;
import com.upedge.sms.modules.photography.request.ProductPhotographyOrderCreateRequest;
import com.upedge.sms.modules.photography.request.ProductPhotographyOrderListRequest;
import com.upedge.sms.modules.photography.request.ProductPhotographyOrderPayRequest;
import com.upedge.sms.modules.photography.request.ProductPhotographyOrderUpdateRequest;
import com.upedge.sms.modules.photography.response.ProductPhotographyOrderInfoResponse;
import com.upedge.sms.modules.photography.response.ProductPhotographyOrderListResponse;
import com.upedge.sms.modules.photography.response.ProductPhotographyOrderUpdateResponse;
import com.upedge.sms.modules.photography.service.ProductPhotographyOrderService;
import com.upedge.sms.modules.photography.vo.ProductPhotographyOrderVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 
 *
 * @author gx
 */
@Api(tags = "产品拍摄服务订单管理")
@RestController
@RequestMapping("/productPhotographyOrder")
public class ProductPhotographyOrderController {
    @Autowired
    private ProductPhotographyOrderService productPhotographyOrderService;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    ThreadPoolExecutor threadPoolExecutor;

    @Value("${files.image.local}")
    private String localPath;
    @Value("${files.image.prefix}")
    private String imageUrlPrefix;

    @ApiOperation("创建订单")
    @PostMapping("/create")
    public BaseResponse create(@RequestBody@Valid ProductPhotographyOrderCreateRequest request){
        Session session = UserUtil.getSession(redisTemplate);
        return productPhotographyOrderService.create(request,session);
    }

    @PostMapping("/testImage")
    public BaseResponse testImage(MultipartFile file,List<MultipartFile> files,String sss,ProductPhotographyOrderItem[] items){

        if (file != null
        && !file.isEmpty()){
            String fileName=file.getOriginalFilename();
            //文件上传
            try {
                file.transferTo(new File(localPath+fileName));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (ListUtils.isNotEmpty(files)){
            for (MultipartFile m: files) {
                try {
                    m.transferTo(new File(localPath+m.getOriginalFilename()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println(sss);
        System.out.println(items);
        return BaseResponse.success();
    }

    @ApiOperation("订单详情")
    @RequestMapping(value="/detail/{id}", method=RequestMethod.GET)
    @Permission(permission = "photography:productphotographyorder:info:id")
    public ProductPhotographyOrderInfoResponse info(@PathVariable Long id) {
        ProductPhotographyOrderVo result = productPhotographyOrderService.orderDetail(id);
        ProductPhotographyOrderInfoResponse res = new ProductPhotographyOrderInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    @ApiOperation("列表")
    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "photography:productphotographyorder:list")
    public ProductPhotographyOrderListResponse list(@RequestBody @Valid ProductPhotographyOrderListRequest request) {
        Session session = UserUtil.getSession(redisTemplate);
        if (session.getApplicationId() != Constant.ADMIN_APPLICATION_ID){
            if (null == request.getT()){
                request.setT(new ProductPhotographyOrder());
            }
            request.getT().setCustomerId(session.getCustomerId());
        }
        List<ProductPhotographyOrder> results = productPhotographyOrderService.select(request);
        Long total = productPhotographyOrderService.count(request);
        request.setTotal(total);
        ProductPhotographyOrderListResponse res = new ProductPhotographyOrderListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    @ApiOperation("修改订单金额")
    @RequestMapping(value="/updateAmount", method=RequestMethod.POST)
    @Permission(permission = "photography:productphotographyorder:update")
    public BaseResponse update(@RequestBody @Valid ProductPhotographyOrderUpdateRequest request) {
        ProductPhotographyOrder order = productPhotographyOrderService.selectByPrimaryKey(request.getId());
        if (order == null
        || order.getPayState() != OrderConstant.PAY_STATE_UNPAID) {
            return BaseResponse.failed("订单不存在或订单已支付");
        }
        ProductPhotographyOrder entity=request.toProductPhotographyOrder();
        productPhotographyOrderService.updateByPrimaryKeySelective(entity);
        ProductPhotographyOrderUpdateResponse res = new ProductPhotographyOrderUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @ApiOperation("订单支付")
    @PostMapping("/pay")
    public BaseResponse pay(@RequestBody@Valid ProductPhotographyOrderPayRequest request){
        Session session = UserUtil.getSession(redisTemplate);
        BaseResponse response = productPhotographyOrderService.pay(request,session);
        if (response.getCode() == ResultCode.SUCCESS_CODE){
            productPhotographyOrderService.saveTransactionRecord(session.getId(), request.getId());
        }
        return response;
    }


    @ApiOperation("上传产品文件")
    @PostMapping("/uploadFile/{id}")
    public BaseResponse uploadProductFile(@PathVariable Long id, MultipartFile file){
        ProductPhotographyOrder order = productPhotographyOrderService.selectByPrimaryKey(id);
        if (null == order){
            return BaseResponse.failed("订单不存在");
        }
        if (file == null
                || file.isEmpty()){
            return BaseResponse.failed("空文件");
        }
        String fileName=file.getOriginalFilename();
        //文件上传
        try {
            file.transferTo(new File(localPath+fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String path = imageUrlPrefix + fileName;
        order = new ProductPhotographyOrder();
        order.setPhotographyLink(path);
        order.setId(id);
        order.setUpdateTime(new Date());
        productPhotographyOrderService.updateByPrimaryKeySelective(order);
        return BaseResponse.success();
    }



}
