package com.upedge.pms.modules.product.dao;

import com.upedge.pms.modules.product.entity.Product;
import com.upedge.pms.modules.product.request.WinningProductListRequest;
import com.upedge.pms.modules.product.vo.AppProductVo;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigDecimal;
import java.util.List;
import com.upedge.common.base.Page;
import org.apache.ibatis.annotations.Param;

/**
 * @author gx
 */
public interface ProductDao{

    List<AppProductVo> selectWinningProducts(WinningProductListRequest request);

    Long countWinningProduct(WinningProductListRequest request);

    int updatePriceRangeById(@Param("priceRange") String priceRange,
                             @Param("id") Long id,
                             @Param("minPrice") BigDecimal minPrice ,
                             @Param("maxPrice") BigDecimal maxPrice);

    int importFavorite(@Param("ids") List<Long> ids,@Param("userId") Long userId);

    int multiRelease(@Param("ids") List<Long> ids,@Param("userId") Long userId);

    int abandonProduct(@Param("id") Long id,@Param("userId") Long userId);

    void publicProduct(Long id);

    void privateProduct(Long id);

    Product selectByProductSku(String productSku);

    void updateSaiheState(@Param("ids") List<Long> ids, @Param("size")Integer size);

    Product selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Product record);

    int updateByPrimaryKey(Product record);

    int updateByPrimaryKeySelective(Product record);

    int insert(Product record);

    int insertSelective(Product record);

    int insertByBatch(List<Product> list);

    List<Product> select(Page<Product> record);

    long count(Page<Product> record);

}
