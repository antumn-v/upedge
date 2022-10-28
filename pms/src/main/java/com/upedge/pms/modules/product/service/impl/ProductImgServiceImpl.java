package com.upedge.pms.modules.product.service.impl;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.utils.FileUtil;
import com.upedge.common.utils.ListUtils;
import com.upedge.pms.modules.product.dao.ProductImgDao;
import com.upedge.pms.modules.product.entity.ProductImg;
import com.upedge.pms.modules.product.request.ProductUploadImageRequest;
import com.upedge.pms.modules.product.service.ProductImgService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class ProductImgServiceImpl implements ProductImgService {

    @Autowired
    private ProductImgDao productImgDao;

    @Value("${files.image.local}")
    private String imageLocalPath;
    @Value("${files.image.prefix}")
    private String imageUrlPrefix;


    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        ProductImg record = new ProductImg();
        record.setId(id);
        return productImgDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(ProductImg record) {
        return productImgDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(ProductImg record) {
        return productImgDao.insert(record);
    }

    @Override
    public List<ProductImg> selectByProductId(Long productId) {

        return productImgDao.selectByProductId(productId);
    }

    /**
     *
     */
    public ProductImg selectByPrimaryKey(Long id){
        ProductImg record = new ProductImg();
        record.setId(id);
        return productImgDao.selectByPrimaryKey(record);
    }

    @Override
    public BaseResponse uploadImage(ProductUploadImageRequest request) {
        if (StringUtils.isBlank(request.getBase64Img())
        || null == request.getProductId()){
            return BaseResponse.failed();
        }
        String image = FileUtil.uploadImage(request.getBase64Img(), imageUrlPrefix, imageLocalPath);
        if (StringUtils.isBlank(image)){
            return BaseResponse.failed("图片上传失败");
        }
        ProductImg productImg = new ProductImg();
        productImg.setProductId(request.getProductId());
        productImg.setState(1);
        productImg.setImageUrl(image);
        productImg.setImageSeq(0);
        insert(productImg);
        return BaseResponse.success();
    }

    @Override
    public int insertByBatch(List<ProductImg> productImgs) {
        if (ListUtils.isEmpty(productImgs)){
            return 0;
        }
        return productImgDao.insertByBatch(productImgs);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(ProductImg record) {
        return productImgDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(ProductImg record) {
        return productImgDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<ProductImg> select(Page<ProductImg> record){
        record.initFromNum();
        return productImgDao.select(record);
    }

    /**
    *
    */
    public long count(Page<ProductImg> record){
        return productImgDao.count(record);
    }

}