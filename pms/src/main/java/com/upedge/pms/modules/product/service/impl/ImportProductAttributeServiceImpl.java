package com.upedge.pms.modules.product.service.impl;

import com.upedge.common.base.Page;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.model.store.StoreVo;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.utils.ListUtils;
import com.upedge.pms.modules.product.dao.ImportProductAttributeDao;
import com.upedge.pms.modules.product.entity.ImportProductAttribute;
import com.upedge.pms.modules.product.request.ImportProductAttributeListRequest;
import com.upedge.pms.modules.product.request.UploadProductToStoreRequest;
import com.upedge.pms.modules.product.response.UploadProductToStoreResponse;
import com.upedge.pms.modules.product.service.ImportProductAttributeService;
import com.upedge.pms.modules.product.service.ImportProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;


@Slf4j
@Service
public class ImportProductAttributeServiceImpl implements ImportProductAttributeService {

    @Autowired
    private ImportProductAttributeDao importProductAttributeDao;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    ImportProductService importProductService;

    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;


    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        ImportProductAttribute record = new ImportProductAttribute();
        record.setId(id);
        return importProductAttributeDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(ImportProductAttribute record) {
        return importProductAttributeDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(ImportProductAttribute record) {
        return importProductAttributeDao.insert(record);
    }

    @Override
    public void updateStateById(Long id, Integer state) {
        ImportProductAttribute attribute = new ImportProductAttribute();
        attribute.setId(id);
        attribute.setState(state);
        importProductAttributeDao.updateByPrimaryKeySelective(attribute);
    }

    @Override
    public UploadProductToStoreResponse importProductToStore(UploadProductToStoreRequest request,Session session) {
        StoreVo storeVo = (StoreVo) redisTemplate.opsForValue().get(RedisKey.STRING_STORE + request.getStoreId());
        if(null == storeVo){
            return new UploadProductToStoreResponse(ResultCode.FAIL_CODE,"The shop does not exist");
        }
        if (request.isImportAll()){
            List<Long> ids = getCustomerAllUnImportIds(session.getCustomerId());
            request.setProductIds(ids);
        }
        if(ListUtils.isEmpty(request.getProductIds())){
            return new UploadProductToStoreResponse(ResultCode.FAIL_CODE, Constant.MESSAGE_SUCCESS);
        }
        // 异步商品上传店铺
        CompletableFuture.runAsync(() -> {
            importProductToStoreSync(request,storeVo);
        }, threadPoolExecutor);
        return new UploadProductToStoreResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,"已经创建后台任务序列！");
    }


    public void importProductToStoreSync(UploadProductToStoreRequest request, StoreVo storeVo) {
        List<Long> ids = request.getProductIds();
        importProductAttributeDao.updateStateByIds(request.getProductIds(),2);
        ids.forEach(id -> {
            boolean b = false;
            switch (storeVo.getStoreType()){
                case 0:
                    b = importProductService.uploadProductToShopify(storeVo,id);
                    break;
                case 1:
                    b = importProductService.uploadProductToWoocommerce(storeVo,id);
                    break;
                default:
                    break;
            }
            if(!b){
                log.error("importProductToStoreSync -- fail id：{}；storeVo{}",id,storeVo);
                updateStateById(id,0);
            }
        });
    }

    @Override
    public List<Long> getCustomerAllUnImportIds(Long customerId) {
        List<Long> ids = new ArrayList<>();
        ImportProductAttributeListRequest importProductAttributeListRequest = new ImportProductAttributeListRequest();
        importProductAttributeListRequest.setFields("id");
        importProductAttributeListRequest.setT(new ImportProductAttribute());
        importProductAttributeListRequest.getT().setState(0);
        importProductAttributeListRequest.getT().setCustomerId(customerId);
        List<ImportProductAttribute> importProductAttributes = importProductAttributeDao.select(importProductAttributeListRequest);
        if (ListUtils.isNotEmpty(importProductAttributes)){
            importProductAttributes.forEach(importProductAttribute -> {
                ids.add(importProductAttribute.getId());
            });
        }
        return ids;
    }

    /**
     *
     */
    public ImportProductAttribute selectByPrimaryKey(Long id){

        return importProductAttributeDao.selectByPrimaryKey(id);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(ImportProductAttribute record) {
        return importProductAttributeDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(ImportProductAttribute record) {
        return importProductAttributeDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<ImportProductAttribute> select(Page<ImportProductAttribute> record){
        record.initFromNum();
        return importProductAttributeDao.select(record);
    }

    /**
    *
    */
    public long count(Page<ImportProductAttribute> record){
        return importProductAttributeDao.count(record);
    }

}