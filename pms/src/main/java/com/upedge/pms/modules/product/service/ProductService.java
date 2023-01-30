package com.upedge.pms.modules.product.service;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.exception.CustomerException;
import com.upedge.common.model.product.VariantDetail;
import com.upedge.common.model.user.vo.Session;
import com.upedge.pms.modules.product.dto.ProductListDto;
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

    BaseResponse refresh1688Product(Long productId);

    BaseResponse copyProduct(Long productId,Session session);

    BaseResponse test();

    BaseResponse selectCustomerPrivateProduct(Page<ProductListDto> record);

    List<Product> selectByIds(List<Long> productIds);

    List<AppProductVo> checkImportProducts(List<AppProductVo> productVos,Long customerId);

    boolean sendUpdateVariantMessage(List<VariantDetail> variantDetails, String tag);

    BaseResponse updateInfo(Long id,UpdateInfoProductRequest request, Session session) throws Exception;

    AppProductVo showCustomerProductDetail(Long productId,Session session);

    String refreshProductPriceRange(Long productId);

    BaseResponse winningProductList(WinningProductListRequest request, Session session);

    ProductVo productDetail(Long id);

    BaseResponse importFrom1688(AlibabaProductVo AlibabaProductVo, Long operatorId);

    BaseResponse importFrom1688Url(String url, Long operatorId);

    void completedPurchaseInfo(AlibabaProductVo alibabaProductVo, Long productId, String productLink);

    List<Product> selectByProductSku(String productSku);

    Product select1688Product(String alibabaProductId);

    Product selectStoreTransformProduct(String storeProductId);

    Product selectByOriginalId(String originalId);

    BaseResponse putawayProduct(Long id, Session session) throws CustomerException;

    BaseResponse unshelveProduct(Long id, Session session);

    BaseResponse uploadToSaihe(Long productId,Long variantId) throws Exception;

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

