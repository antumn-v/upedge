package com.upedge.pms.modules.inventory.service.impl;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.enums.CustomerExceptionEnum;
import com.upedge.common.exception.CustomerException;

import com.upedge.common.model.order.vo.CustomerProductStockNumVo;
import com.upedge.common.model.product.ProductSaiheInventoryVo;
import com.upedge.pms.modules.inventory.dao.ProductSaiheInventoryDao;
import com.upedge.pms.modules.inventory.entity.ProductSaiheInventory;
import com.upedge.pms.modules.inventory.request.SaiheInvebtoryRequest;
import com.upedge.pms.modules.inventory.service.ProductSaiheInventoryService;
import com.upedge.pms.modules.inventory.vo.SaiheInvebtoryVo;
import com.upedge.thirdparty.saihe.config.SaiheConfig;
import com.upedge.thirdparty.saihe.entity.GetProductInventory.ApiProductInventory;
import com.upedge.thirdparty.saihe.entity.GetProductInventory.ProductInventoryList;
import com.upedge.thirdparty.saihe.response.GetProductInventoryResponse;
import com.upedge.thirdparty.saihe.service.SaiheService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Service
public class ProductSaiheInventoryServiceImpl implements ProductSaiheInventoryService {

    @Autowired
    private ProductSaiheInventoryDao productSaiheInventoryDao;





    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(String variantSku) {
        ProductSaiheInventory record = new ProductSaiheInventory();
        record.setVariantSku(variantSku);
        return productSaiheInventoryDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(ProductSaiheInventory record) {
        return productSaiheInventoryDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(ProductSaiheInventory record) {
        return productSaiheInventoryDao.insert(record);
    }

    /**
     *
     */
    public ProductSaiheInventory selectByPrimaryKey(String variantSku){
        ProductSaiheInventory record = new ProductSaiheInventory();
        record.setVariantSku(variantSku);
        return productSaiheInventoryDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(ProductSaiheInventory record) {
        return productSaiheInventoryDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(ProductSaiheInventory record) {
        return productSaiheInventoryDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<ProductSaiheInventory> select(Page<ProductSaiheInventory> record){
        record.initFromNum();
        return productSaiheInventoryDao.select(record);
    }

    /**
    *
    */
    public long count(Page<ProductSaiheInventory> record){
        return productSaiheInventoryDao.count(record);
    }

    /**
     * 从赛盒同步库存信息
     */
    @Override
    public void batchRefreshInventory() throws CustomerException {

        // 获取赛盒库存
        batchRefreshInventory(null);
        // 获取订单模块下  根据sku分组后的库存总数
//        BaseResponse baseResponse = omsFeignClient.customerStockNum();
//        if (0 ==  baseResponse.getCode() ){
//            throw new CustomerException(CustomerExceptionEnum.QUERY_FAILS);
//        }
//        List<CustomerProductStockNumVo> result = (List<CustomerProductStockNumVo>)baseResponse.getData();
//
//        // 根据订单模块获取的信息更新 Customer_Stock
//        productSaiheInventoryDao.updateCustomerStock(result);
    }

    /**
     * 根据条件查询list
     * @param productSaiheInventory
     * @return
     */
    @Override
    public List<ProductSaiheInventoryVo> queryProductSaiheInventory(ProductSaiheInventoryVo productSaiheInventory) {
      return    productSaiheInventoryDao.queryProductSaiheInventory(productSaiheInventory);
    }

    @Override
    public void insertProductSaiheInventory(ProductSaiheInventoryVo productSaiheInventory) {
        productSaiheInventoryDao.insertProductSaiheInventory(productSaiheInventory);
    }

    @Override
    public BaseResponse selectProductSaiheInventoryPage(SaiheInvebtoryRequest request) {
        request.initFromNum();
        List<SaiheInvebtoryVo> results = productSaiheInventoryDao.selectSSPage(request);
        Long total = productSaiheInventoryDao.pageSSCount(request);
        request.setTotal(total);
        return BaseResponse.success(results,request);
    }

    /**
     *      * 客户库存分析列表   所有库存金额
     * @param request
     * @return
     */
    @Override
    public BaseResponse selectProductSaiheInventoryListSumMoney(SaiheInvebtoryRequest request) {

     BigDecimal sumMoney = productSaiheInventoryDao.selectSumMoney(request);

        return  BaseResponse.success(sumMoney);
    }


    @Transactional(readOnly = false)
    public void batchRefreshInventory(Integer token) {

        GetProductInventoryResponse getProductInventoryResponse=
                SaiheService.listProductInventory(SaiheConfig.UPEDGE_DEFAULT_WAREHOUSE_ID,token);
        if (getProductInventoryResponse.getGetProductInventoryResult().getStatus().equals("OK")) {
            ProductInventoryList productInventoryList=getProductInventoryResponse.
                    getGetProductInventoryResult().getProductInventoryList();
            Integer nextToken=getProductInventoryResponse.getGetProductInventoryResult().getNextToken();
            if(productInventoryList.getProductInventoryList()!=null||
                    productInventoryList.getProductInventoryList().size()>0){
                List<ApiProductInventory> apiProductInventoryList=productInventoryList.getProductInventoryList();
                List<ProductSaiheInventory> inventoryList=new ArrayList<>();
                for(ApiProductInventory a:apiProductInventoryList) {
                    if(StringUtils.isBlank(a.getClientSKU())){
                        continue;
                    }
                    ProductSaiheInventory productSaiheInventory=new ProductSaiheInventory();
                    productSaiheInventory.setVariantSku(a.getClientSKU());
                    productSaiheInventory.setGoodNum(a.getGoodNum());
                    productSaiheInventory.setLockNum(a.getLockNum());
                    productSaiheInventory.setWarehouseCode(SaiheConfig.UPEDGE_DEFAULT_WAREHOUSE_ID);
                    productSaiheInventory.setUpdateTime(a.getUpdateTime());
                    productSaiheInventory.setActiveDays(a.getActiveDays());
                    productSaiheInventory.setActiveTime(a.getActiveTime());
                    productSaiheInventory.setPositionCode(a.getPositionCode());
                    productSaiheInventory.setProcessingNum(a.getProcessingNum());
                    productSaiheInventory.setHistoryInNum(a.getHistoryInNum());
                    productSaiheInventory.setHistoryOutNum(a.getHistoryOutNum());
                    inventoryList.add(productSaiheInventory);
                }
                if(inventoryList.size()>0) {
                    productSaiheInventoryDao.saveProductSaiheInventory(inventoryList);
                }
            }
            if(nextToken!=null&&nextToken!=-1){
                batchRefreshInventory(nextToken);
            }else{
                System.out.println("结束");
            }
        }
    }

}