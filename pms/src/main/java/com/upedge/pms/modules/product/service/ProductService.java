package com.upedge.pms.modules.product.service;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.model.product.VariantDetail;
import com.upedge.common.model.user.vo.Session;
import com.upedge.pms.modules.product.entity.Product;
import com.upedge.pms.modules.product.request.*;
import com.upedge.pms.modules.product.response.AbandonProductResponse;
import com.upedge.pms.modules.product.response.ImportFavoriteResponse;
import com.upedge.pms.modules.product.response.MultiReleaseResponse;
import com.upedge.pms.modules.product.response.ProductListResponse;
import com.upedge.pms.modules.product.vo.AddProductVo;
import com.upedge.pms.modules.product.vo.AppProductVo;
import com.upedge.pms.modules.product.vo.ProductVo;
import com.upedge.thirdparty.ali1688.vo.AlibabaProductVo;

import java.util.List;

/**
 * @author gx
 */
public interface ProductService{

    boolean sendUpdateVariantMessage(List<VariantDetail> variantDetails, String tag);

    BaseResponse updateInfo(Long id,UpdateInfoProductRequest request, Session session) throws Exception;

    AppProductVo showCustomerProductDetail(Long productId);

    String refreshProductPriceRange(Long productId);

    BaseResponse winningProductList(WinningProductListRequest request, Session session);

    ProductVo productDetail(Long id);

    BaseResponse importFrom1688(AlibabaProductVo AlibabaProductVo, Session session);

    Product selectByProductSku(String productSku);

    BaseResponse putawayProduct(Long id, Session session);

    BaseResponse unshelveProduct(Long id, Session session);

    BaseResponse uploadToSaihe(ProductUoloadToSaiheRequest request);

    BaseResponse publicProduct(Long id);

    BaseResponse privateProduct(Long id);

    BaseResponse addProduct(AddProductVo addProductVo, Session session);

    ProductListResponse selectionList(ProductListRequest request);

    ImportFavoriteResponse importFavorite(ImportFavoriteRequest request, Session session);

    ProductListResponse favoriteList(ProductListRequest request);

    MultiReleaseResponse multiRelease(MultiReleaseRequest request, Session session);

    AbandonProductResponse abandonProduct(Long id, Session session);

    Product selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(Product record);

    int updateByPrimaryKeySelective(Product record) throws Exception;

    int insert(Product record);

    int insertSelective(Product record);

    List<Product> select(Page<Product> record);

    long count(Page<Product> record);
}

