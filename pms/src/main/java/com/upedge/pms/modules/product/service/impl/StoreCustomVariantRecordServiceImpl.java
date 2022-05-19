package com.upedge.pms.modules.product.service.impl;

import com.upedge.common.base.Page;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.model.oms.order.OrderExcelItemDto;
import com.upedge.common.model.pms.quote.CustomerProductQuoteVo;
import com.upedge.common.model.pms.request.StoreCustomVariantRecordSaveRequest;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.utils.IdGenerate;
import com.upedge.common.utils.ListUtils;
import com.upedge.pms.modules.product.dao.StoreCustomVariantRecordDao;
import com.upedge.pms.modules.product.entity.StoreCustomVariantRecord;
import com.upedge.pms.modules.product.entity.StoreProductAttribute;
import com.upedge.pms.modules.product.entity.StoreProductVariant;
import com.upedge.pms.modules.product.service.StoreCustomVariantRecordService;
import com.upedge.pms.modules.product.service.StoreProductAttributeService;
import com.upedge.pms.modules.product.service.StoreProductVariantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
public class StoreCustomVariantRecordServiceImpl implements StoreCustomVariantRecordService {

    @Autowired
    private StoreCustomVariantRecordDao storeCustomVariantRecordDao;

    @Autowired
    StoreProductAttributeService storeProductAttributeService;

    @Autowired
    StoreProductVariantService storeProductVariantService;

    @Autowired
    RedisTemplate redisTemplate;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long customerId) {
        StoreCustomVariantRecord record = new StoreCustomVariantRecord();
        record.setCustomerId(customerId);
        return storeCustomVariantRecordDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(StoreCustomVariantRecord record) {
        return storeCustomVariantRecordDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(StoreCustomVariantRecord record) {
        return storeCustomVariantRecordDao.insert(record);
    }

    @Transactional
    @Override
    public List<CustomerProductQuoteVo> saveCustomVariant(StoreCustomVariantRecordSaveRequest request) {
        Session session = request.getSession();
        Long customerId = session.getCustomerId();
        List<OrderExcelItemDto> orderExcelItemDtos = request.getOrderExcelItemDtos();

        StoreProductAttribute storeProductAttribute = storeProductAttributeService.saveDefaultCustomProduct(session.getCustomerId());
        List<CustomerProductQuoteVo> customerProductQuoteVos = new ArrayList<>();

        List<StoreProductVariant> storeProductVariants = saveNewCustomVariant(session.getCustomerId(),storeProductAttribute.getId(),orderExcelItemDtos);
        for (StoreProductVariant storeProductVariant : storeProductVariants) {
            CustomerProductQuoteVo customerProductQuoteVo = (CustomerProductQuoteVo) redisTemplate.opsForValue().get(RedisKey.STRING_QUOTED_STORE_VARIANT + storeProductVariant.getId());
            if (null != customerProductQuoteVo){
                customerProductQuoteVos.add(customerProductQuoteVo);
            }else {
                customerProductQuoteVo = new CustomerProductQuoteVo();
                customerProductQuoteVo.setCustomerId(customerId);
                customerProductQuoteVo.setStoreVariantImage(storeProductVariant.getImage());
                customerProductQuoteVo.setStoreParentVariantId(0L);
                customerProductQuoteVo.setStoreVariantSku(storeProductVariant.getSku());
                customerProductQuoteVo.setStoreVariantName(storeProductVariant.getTitle());
                customerProductQuoteVo.setQuoteType(-1);
                customerProductQuoteVo.setQuoteScale(1);
                customerProductQuoteVo.setStoreProductId(storeProductVariant.getProductId());
                customerProductQuoteVos.add(customerProductQuoteVo);
            }
        }
        return customerProductQuoteVos;
    }

    private List<StoreProductVariant> saveNewCustomVariant(Long customerId,Long productId,List<OrderExcelItemDto> orderExcelItemDtos){
        Set<String> skus = new HashSet<>();
        for (OrderExcelItemDto orderExcelItemDto : orderExcelItemDtos) {
            skus.add(orderExcelItemDto.getSku());
        }
        List<StoreCustomVariantRecord> storeCustomVariantRecordList = storeCustomVariantRecordDao.selectByCustomerAndSkus(customerId,skus);
        Date date = new Date();
        List<StoreCustomVariantRecord> storeCustomVariantRecords = new ArrayList<>();
        List<StoreProductVariant> storeProductVariants = new ArrayList<>();
        List<Long> storeVariantIds = new ArrayList<>();
        a:
        for (OrderExcelItemDto orderExcelItemDto : orderExcelItemDtos) {
            b:
            for (StoreCustomVariantRecord storeCustomVariantRecord : storeCustomVariantRecordList) {
                if (skus.contains(storeCustomVariantRecord.getSku())){
                    storeVariantIds.add(storeCustomVariantRecord.getStoreVariantId());
                    storeCustomVariantRecordList.remove(storeCustomVariantRecord);
                    continue a;
                }
            }
            Long id = IdGenerate.nextId();
            StoreProductVariant storeProductVariant = new StoreProductVariant();
            storeProductVariant.setId(id);
            storeProductVariant.setSku(orderExcelItemDto.getSku());
            storeProductVariant.setImage(orderExcelItemDto.getImage());
            storeProductVariant.setProductId(productId);
            storeProductVariant.setSplitType(0);
            storeProductVariant.setState(1);
            storeProductVariant.setPlatVariantId("0");
            storeProductVariant.setParentVariantId(0L);
            storeProductVariant.setPlatProductId("0");
            storeProductVariants.add(storeProductVariant);

            StoreCustomVariantRecord storeCustomVariantRecord = new StoreCustomVariantRecord();
            storeCustomVariantRecord.setStoreVariantId(id);
            storeCustomVariantRecord.setCustomerId(customerId);
            storeCustomVariantRecord.setCreateTime(date);
            storeCustomVariantRecord.setSellingLink(orderExcelItemDto.getSellingLink());
            storeCustomVariantRecord.setSku(orderExcelItemDto.getSku());
            storeCustomVariantRecords.add(storeCustomVariantRecord);
        }
        if (ListUtils.isNotEmpty(storeProductVariants)){
            storeProductVariantService.insertByBatch(storeProductVariants);
            storeCustomVariantRecordDao.insertByBatch(storeCustomVariantRecords);
        }
        if (ListUtils.isNotEmpty(storeVariantIds)){
            List<StoreProductVariant> storeProductVariantList = storeProductVariantService.selectByIds(storeVariantIds);
            storeProductVariants.addAll(storeProductVariantList);
        }

        return storeProductVariants;
    }



    /**
     *
     */
    public StoreCustomVariantRecord selectByPrimaryKey(Long customerId, String sku){
        return storeCustomVariantRecordDao.selectByPrimaryKey(customerId, sku);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(StoreCustomVariantRecord record) {
        return storeCustomVariantRecordDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(StoreCustomVariantRecord record) {
        return storeCustomVariantRecordDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<StoreCustomVariantRecord> select(Page<StoreCustomVariantRecord> record){
        record.initFromNum();
        return storeCustomVariantRecordDao.select(record);
    }

    /**
    *
    */
    public long count(Page<StoreCustomVariantRecord> record){
        return storeCustomVariantRecordDao.count(record);
    }

}