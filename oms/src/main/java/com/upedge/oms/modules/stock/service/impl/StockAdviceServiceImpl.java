package com.upedge.oms.modules.stock.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.feign.PmsFeignClient;
import com.upedge.common.model.product.*;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.utils.ListUtils;
import com.upedge.oms.modules.cart.entity.Cart;
import com.upedge.oms.modules.cart.request.CartSubmitOrderRequest;
import com.upedge.oms.modules.cart.service.CartService;
import com.upedge.oms.modules.sales.dao.CustomerProductSalesLogDao;
import com.upedge.oms.modules.stock.dao.CustomerProductStockDao;
import com.upedge.oms.modules.stock.dao.StockAdviceSettingDao;
import com.upedge.oms.modules.stock.entity.CustomerProductStock;
import com.upedge.oms.modules.stock.entity.StockAdviceSetting;
import com.upedge.oms.modules.stock.request.StockAdviceCreateOrderRequest;
import com.upedge.oms.modules.stock.service.StockAdviceService;
import com.upedge.oms.modules.stock.vo.CustomerStockAdviceVo;
import com.upedge.oms.modules.stock.vo.StockAdviceCreatOrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class StockAdviceServiceImpl implements StockAdviceService {

    @Autowired
    private StockAdviceSettingDao stockAdviceSettingDao;

    @Autowired
    CustomerProductSalesLogDao customerProductSalesLogDao;

    @Autowired
    CustomerProductStockDao customerProductStockDao;

    @Autowired
    PmsFeignClient pmsFeignClient;

    @Autowired
    CartService cartService;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;


    @Override
    public BaseResponse customerStockAdvice(Session session, Integer fromNum, Integer pageSize) {
        Long customerId = session.getCustomerId();

        List<Long> variantIds = customerProductSalesLogDao.selectVariantIdByCustomerId(customerId);

        if(ListUtils.isEmpty(variantIds)){
            return new BaseResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS,new ArrayList<>());
        }

        ListVariantsRequest request = new ListVariantsRequest();
        request.setVariantIds(variantIds);
        BaseResponse response = pmsFeignClient.listVariantDetailByIds(request);
        if(ResultCode.FAIL_CODE == response.getCode()
                || null == response.getData()){
            return new BaseResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS,new ArrayList<>());
        }
        List<VariantDetail> variantDetails = JSONArray.parseArray(JSON.toJSONString(response.getData())).toJavaList(VariantDetail.class);
        Map<Long,VariantDetail> map = new HashMap<>();
        variantDetails.forEach(variantDetail -> {
            map.put(variantDetail.getVariantId(),variantDetail);
        });

        StockAdviceSetting stockAdviceSetting = stockAdviceSettingDao.selectByCustomerId(customerId);
        if(null == stockAdviceSetting){
            stockAdviceSetting = new StockAdviceSetting();
            stockAdviceSetting.setCustomerId(customerId);
        }
        CustomerProductStock customerProductStock = new CustomerProductStock();
        customerProductStock.setCustomerId(customerId);
        Page<CustomerProductStock> page = new Page<>();
        page.setT(customerProductStock);
        page.setCondition("stock > 0");
        List<CustomerProductStock> productStocks = customerProductStockDao.select(page);
        Map<Long, Integer> stockMap = new HashMap<>();
        if (ListUtils.isNotEmpty(productStocks)){
            productStocks.forEach(productStock -> {
                stockMap.put(productStock.getVariantId(),productStock.getStock());
            });
        }

        List<CustomerProductDailySales> dailySalesList = customerProductSalesLogDao.selectCustomerProductSales(customerId);

        String key = RedisKey.ZSET_CUSTOMER_STOCK_ADVICE + customerId;
        redisTemplate.delete(key);
        final StockAdviceSetting finalStockAdviceSetting = stockAdviceSetting;
        dailySalesList.forEach(customerProductDailySales -> {
            CustomerStockAdviceVo adviceVo = new CustomerStockAdviceVo();
            VariantDetail variantDetail = map.get(customerProductDailySales.getVariantId());
            adviceVo.setCustomerId(customerId);
            adviceVo.setPrice(variantDetail.getUsdPrice());
            adviceVo.setVariantId(variantDetail.getVariantId());
            adviceVo.setProductId(variantDetail.getProductId());
            adviceVo.setProductTitle(variantDetail.getProductTitle());
            adviceVo.setVariantImage(variantDetail.getVariantImage());
            adviceVo.setVariantName(variantDetail.getVariantName());
            adviceVo.setVariantSku(variantDetail.getVariantSku());
            List<DailySales> dailySales = customerProductDailySales.getDailySalesList();
            for (DailySales sales: dailySales) {
                switch (sales.getDays()){
                    case "5":
                        adviceVo.setFive(sales.getSales());
                        break;
                    case "10":
                        adviceVo.setTen(sales.getSales());
                        break;
                    case "15":
                        adviceVo.setFifteen(sales.getSales());
                        break;
                    default:
                        break;
                }
            }
            double dailyAverage = adviceVo.initDailyAverage(finalStockAdviceSetting);
            Integer stock = stockMap.get(variantDetail.getVariantId());
            adviceVo.initStockAdvice(stock);
            redisTemplate.opsForZSet().add(key,adviceVo,dailyAverage);
        });
        Long count = redisTemplate.opsForZSet().size(key);
        Set<Object> adviceVos = redisTemplate.opsForZSet().rangeByScore(key,fromNum,fromNum + pageSize);
        redisTemplate.expire(key,10, TimeUnit.MINUTES);

        page.setTotal(count);

        return new BaseResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,adviceVos,page);
    }

    @Override
    public BaseResponse stockAdviceCreateOrder(StockAdviceCreateOrderRequest request, Session session) {

        List<VariantQuantity> variantQuantities = request.getVariantQuantities();
        List<Long> variantIds = new ArrayList<>();
        Map<Long, Integer> variantQuantity = new HashMap<>();
        variantQuantities.forEach(v -> {
            variantQuantity.put(v.getVariantId(),v.getQuantity());
            variantIds.add(v.getVariantId());
        });

        ListVariantsRequest listVariantsRequest = new ListVariantsRequest();
        listVariantsRequest.setVariantIds(variantIds);
        BaseResponse response = pmsFeignClient.listVariantDetailByIds(listVariantsRequest);
        if(ResultCode.FAIL_CODE == response.getCode()
                || null == response.getData()){
            return new BaseResponse(ResultCode.FAIL_CODE, Constant.MESSAGE_FAIL);
        }
        Date date = new Date();
        List<Cart> carts = new ArrayList<>();
        List<VariantDetail> variantDetails = JSONArray.parseArray(JSON.toJSONString(response.getData())).toJavaList(VariantDetail.class);
        variantDetails.forEach(variantDetail -> {
            Cart cart = new Cart(variantDetail,date);
            cart.setId(0L);
            cart.setCustomerId(session.getCustomerId());
            cart.setQuantity(variantQuantity.get(variantDetail.getVariantId()));
            cart.setCartType(0);
            carts.add(cart);
        });

        CartSubmitOrderRequest cartSubmitOrderRequest = new CartSubmitOrderRequest();
        cartSubmitOrderRequest.setCarts(carts);
        cartSubmitOrderRequest.setCartType(0);
        cartSubmitOrderRequest.setWarehouseCode(request.getWarehouseCode());
        Long orderId = cartService.cartSubmitStockOrder(cartSubmitOrderRequest,session);
        if(null != orderId) {
            StockAdviceCreatOrderVo stockAdviceCreatOrderVo = new StockAdviceCreatOrderVo();
            stockAdviceCreatOrderVo.setCarts(carts);
            stockAdviceCreatOrderVo.setOrderId(orderId);
            return new BaseResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,stockAdviceCreatOrderVo);
        }
        return BaseResponse.failed();
    }
}
