package com.upedge.pms.modules.product.dao;

import com.upedge.common.base.Page;
import com.upedge.pms.modules.product.dto.ProductListDto;
import com.upedge.pms.modules.product.entity.Product;
import com.upedge.pms.modules.product.request.WinningProductListRequest;
import com.upedge.pms.modules.product.vo.AppProductVo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author gx
 */
public interface ProductDao {

    List<Product> selectByIds(@Param("ids") List<Long> ids);

    List<Long> selectShippingIdByIds(@Param("ids") Set<Long> ids);

    List<AppProductVo> selectWinningProducts(WinningProductListRequest request);

    Long countWinningProduct(WinningProductListRequest request);

    int updatePriceRangeById(@Param("priceRange") String priceRange,
                             @Param("id") Long id);

    int importFavorite(@Param("ids") List<Long> ids,
                       @Param("userId") Long userId,
                       @Param("date") Date date);

    int multiRelease(@Param("ids") List<Long> ids, @Param("userId") Long userId);

    int abandonProduct(@Param("id") Long id, @Param("userId") Long userId);

    void publicProduct(Long id);

    void privateProduct(Long id);

    Product selectByProductSku(String productSku);

    Product selectByOriginalId(String originalId);

    void updateSaiheState(@Param("ids") List<Long> ids, @Param("size") Integer size);

    Product selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Product record);

    int updateByPrimaryKey(Product record);

    int updateByPrimaryKeySelective(Product record);

    int insert(Product record);

    int insertSelective(Product record);

    int insertByBatch(List<Product> list);

    List<Product> select(Page<Product> record);

    List<Product> selectCustomerPrivateProduct(Page<ProductListDto> record);

    long count(Page<Product> record);

    long countCustomerPrivateProduct(Page<ProductListDto> record);
}
