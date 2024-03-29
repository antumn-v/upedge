package com.upedge.pms.modules.init;

import com.upedge.common.base.Page;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.model.pms.quote.CustomerProductQuoteVo;
import com.upedge.common.model.pms.vo.VariantWarehouseStockModel;
import com.upedge.common.model.product.AlibabaApiVo;
import com.upedge.common.utils.ListUtils;
import com.upedge.pms.modules.alibaba.entity.AlibabaApi;
import com.upedge.pms.modules.alibaba.service.Ali1688Service;
import com.upedge.pms.modules.alibaba.service.AlibabaApiService;
import com.upedge.pms.modules.product.service.StoreProductVariantService;
import com.upedge.pms.modules.product.vo.SplitVariantIdVo;
import com.upedge.pms.modules.purchase.entity.VariantWarehouseStock;
import com.upedge.pms.modules.purchase.service.VariantWarehouseStockService;
import com.upedge.pms.modules.quote.service.CustomerProductQuoteService;
import com.upedge.pms.modules.quote.service.QuoteApplyItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
@RestController
@RequestMapping("/redis")
public class RedisInit {

    @Autowired
    StoreProductVariantService storeProductVariantService;

    @Autowired
    AlibabaApiService alibabaApiService;

    @Autowired
    QuoteApplyItemService quoteApplyItemService;

    @Autowired
    CustomerProductQuoteService customerProductQuoteService;

    @Autowired
    VariantWarehouseStockService variantWarehouseStockService;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    ThreadPoolExecutor threadPoolExecutor;

    /**
     * 1688token
     */
    @PostMapping("/init")
    public void AlibabaConfigInit(){
        long now = System.currentTimeMillis();
        AlibabaApi alibabaApi = alibabaApiService.selectAlibabaApi();
        AlibabaApiVo alibabaApiVo = new AlibabaApiVo();
        BeanUtils.copyProperties(alibabaApi,alibabaApiVo);
        long time = alibabaApi.getAccessTokenExpireTime() - now ;
        if (time <= 60 * 60 * 1000){
            alibabaApiVo = Ali1688Service.refreshAccessToken(alibabaApiVo);
            if (null == alibabaApiVo){
                return;
            }
            BeanUtils.copyProperties(alibabaApiVo,alibabaApi);
            alibabaApiService.updateByPrimaryKey(alibabaApi);
        }
        redisTemplate.opsForValue().set(RedisKey.STRING_ALI1688_API,alibabaApiVo);

        splitVariantIdListInit();
    }

    /**
     * 拆分变体集合
     */
//    @PostConstruct
    public void splitVariantIdListInit(){
        CompletableFuture<Void> split = CompletableFuture.runAsync(new Runnable() {
            @Override
            public void run() {
                List<SplitVariantIdVo> splitVariantIds = storeProductVariantService.selectSplitVariantIds();
                Map<String,List<Long>> map = new HashMap<>();
                for (SplitVariantIdVo splitVariantId : splitVariantIds) {
                    map.put(splitVariantId.getParentVariantId(), splitVariantId.getSplitVariantIds());
                }
                redisTemplate.delete(RedisKey.HASH_STORE_SPLIT_VARIANT);
                redisTemplate.opsForHash().putAll(RedisKey.HASH_STORE_SPLIT_VARIANT,map);
                log.warn("已拆分变体ID集合初始化完成-------------------------------------");
            }
        },threadPoolExecutor);

        CompletableFuture<Void> quoting = CompletableFuture.runAsync(new Runnable() {
            @Override
            public void run() {
                //所有报价中的店铺产品ID
                List<Long> quotingVariantIds = quoteApplyItemService.selectAllQuotingStoreVariantIds();
                if (ListUtils.isNotEmpty(quotingVariantIds)){
                    for (Long quotingVariantId : quotingVariantIds) {
                        String key = RedisKey.STRING_QUOTED_STORE_VARIANT + quotingVariantId;
                        CustomerProductQuoteVo customerProductQuoteVo = new CustomerProductQuoteVo();
                        customerProductQuoteVo.setStoreVariantId(quotingVariantId);
                        customerProductQuoteVo.setQuoteType(5);
                        customerProductQuoteVo.setStoreParentVariantId(0L);
                        redisTemplate.opsForValue().set(key,customerProductQuoteVo);
                    }
                }else {
                    quotingVariantIds =new ArrayList<>();
                }
                redisTemplate.delete(RedisKey.LIST_QUOTING_STORE_VARIANT);
                redisTemplate.opsForList().leftPushAll(RedisKey.LIST_QUOTING_STORE_VARIANT,quotingVariantIds);
                log.warn("报价中变体ID集合初始化完成-------------------------------------");
            }
        },threadPoolExecutor);

        CompletableFuture<Void> quoted = CompletableFuture.runAsync(new Runnable() {
            @Override
            public void run() {
                List<CustomerProductQuoteVo> customerProductQuoteVos = customerProductQuoteService.selectAllQuoteDetail();
                for (CustomerProductQuoteVo customerProductQuoteVo : customerProductQuoteVos) {
                    String key = RedisKey.STRING_QUOTED_STORE_VARIANT + customerProductQuoteVo.getStoreVariantId();
                    redisTemplate.opsForValue().set(key,customerProductQuoteVo);
                }
                log.warn("已报价变体ID集合初始化完成-------------------------------------");
            }
        },threadPoolExecutor);

        CompletableFuture<Void> stock = CompletableFuture.runAsync(new Runnable() {
            @Override
            public void run() {
                initVariantStock();
            }
        },threadPoolExecutor);

        try {
            CompletableFuture.allOf(quoted,quoting,split,stock).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initVariantStock(){
        VariantWarehouseStock variantWarehouseStock = new VariantWarehouseStock();
        variantWarehouseStock.setStockState(1);
        Page<VariantWarehouseStock> page = new Page<>();
        page.setT(variantWarehouseStock);
        page.setPageSize(-1);
        List<VariantWarehouseStock> variantWarehouseStocks = variantWarehouseStockService.select(page);
        for (VariantWarehouseStock warehouseStock : variantWarehouseStocks) {
            VariantWarehouseStockModel variantWarehouseStockModel = new VariantWarehouseStockModel();
            BeanUtils.copyProperties(warehouseStock,variantWarehouseStockModel);
            redisTemplate.opsForHash().put(RedisKey.HASH_VARIANT_WAREHOUSE_STOCK + warehouseStock.getWarehouseCode(),warehouseStock.getVariantId().toString(),variantWarehouseStockModel);
        }
    }

}
