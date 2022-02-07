package com.upedge.pms.modules.product.service.impl;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.utils.ListUtils;
import com.upedge.pms.modules.product.dao.CustomerPrivateProductDao;
import com.upedge.pms.modules.product.entity.CustomerPrivateProduct;
import com.upedge.pms.modules.product.entity.Product;
import com.upedge.pms.modules.product.request.AllocationPrivateProductRequest;
import com.upedge.pms.modules.product.request.PrivateWinningProductsRequest;
import com.upedge.pms.modules.product.service.CustomerPrivateProductService;
import com.upedge.pms.modules.product.service.ProductService;
import com.upedge.pms.modules.product.vo.AppProductVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class CustomerPrivateProductServiceImpl implements CustomerPrivateProductService {

    @Autowired
    private CustomerPrivateProductDao customerPrivateProductDao;


    @Autowired
    ProductService productService;


    /**
     *
     */
    public int deleteByPrimaryKey(Long id) {
        CustomerPrivateProduct record = new CustomerPrivateProduct();
        record.setId(id);
        return customerPrivateProductDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    public int insert(CustomerPrivateProduct record) {
        return customerPrivateProductDao.insert(record);
    }

    /**
     *
     */
    public int insertSelective(CustomerPrivateProduct record) {
        return customerPrivateProductDao.insert(record);
    }

    @Override
    public List<AppProductVo> selectPrivateWinningProducts(PrivateWinningProductsRequest request) {
        if (null == request.getCustomerId()){
            return new ArrayList<>();
        }
        return customerPrivateProductDao.selectPrivateWinningProducts(request);
    }

    @Override
    public Long countPrivateWinningProducts(PrivateWinningProductsRequest request) {
        if (null == request.getCustomerId()){
            return 0L;
        }
        return customerPrivateProductDao.countPrivateWinningProducts(request);
    }

    @Override
    public BaseResponse allocationPrivateProduct(AllocationPrivateProductRequest request, Session session) {
        List<Long> productIds = request.getProductIds();
        List<Long> customerIds = request.getCustomerIds();;
        if (ListUtils.isEmpty(customerIds)
        || ListUtils.isEmpty(productIds)){
            return BaseResponse.failed("产品列表和客户列表不能为空");
        }
        List<Product> products = productService.selectByIds(productIds);
        if (ListUtils.isEmpty(products)){
            return BaseResponse.failed("产品ID有误");
        }
        //判断去重字符串
        List<String> customerProductIds = new ArrayList<>();
        List<CustomerPrivateProduct> customerPrivateProducts = new ArrayList<>();
        for (Long customerId : customerIds) {
            for (Long productId : productIds) {
                String customerProductId = customerId + "-" + productId;
                if (customerProductIds.contains(customerProductId)){
                    continue;
                }
                customerProductIds.add(customerProductId);
                CustomerPrivateProduct customerPrivateProduct = new CustomerPrivateProduct();
                customerPrivateProduct.setProductId(productId);
                customerPrivateProduct.setCustomerId(customerId);
                customerPrivateProducts.add(customerPrivateProduct);
            }
        }
        customerPrivateProductDao.insertByBatch(customerPrivateProducts);
        return BaseResponse.success();
    }

    @Override
    public int countByProductId(Long productId) {
        return customerPrivateProductDao.countByProductId(productId);
    }

    /**
     *
     */
    public CustomerPrivateProduct selectByPrimaryKey(Long id){
        CustomerPrivateProduct record = new CustomerPrivateProduct();
        record.setId(id);
        return customerPrivateProductDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    public int updateByPrimaryKeySelective(CustomerPrivateProduct record) {
        return customerPrivateProductDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    public int updateByPrimaryKey(CustomerPrivateProduct record) {
        return customerPrivateProductDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<CustomerPrivateProduct> select(Page<CustomerPrivateProduct> record){
        record.initFromNum();
        return customerPrivateProductDao.select(record);
    }

    /**
    *
    */
    public long count(Page<CustomerPrivateProduct> record){
        return customerPrivateProductDao.count(record);
    }

}