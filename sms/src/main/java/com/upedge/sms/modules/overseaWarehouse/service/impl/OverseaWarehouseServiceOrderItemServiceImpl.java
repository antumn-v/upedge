package com.upedge.sms.modules.overseaWarehouse.service.impl;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.utils.ListUtils;
import com.upedge.sms.modules.overseaWarehouse.dao.OverseaWarehouseServiceOrderItemDao;
import com.upedge.sms.modules.overseaWarehouse.entity.OverseaWarehouseServiceOrderItem;
import com.upedge.sms.modules.overseaWarehouse.entity.OverseaWarehouseSku;
import com.upedge.sms.modules.overseaWarehouse.request.OverseaWarehouseServiceOrderItemUploadFpxRequest;
import com.upedge.sms.modules.overseaWarehouse.service.OverseaWarehouseServiceOrderItemService;
import com.upedge.sms.modules.overseaWarehouse.service.OverseaWarehouseSkuService;
import com.upedge.thirdparty.fpx.api.FpxWmsApi;
import com.upedge.thirdparty.fpx.vo.FpxSku;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class OverseaWarehouseServiceOrderItemServiceImpl implements OverseaWarehouseServiceOrderItemService {

    @Autowired
    private OverseaWarehouseServiceOrderItemDao overseaWarehouseServiceOrderItemDao;

    @Autowired
    OverseaWarehouseSkuService overseaWarehouseSkuService;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        OverseaWarehouseServiceOrderItem record = new OverseaWarehouseServiceOrderItem();
        record.setId(id);
        return overseaWarehouseServiceOrderItemDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(OverseaWarehouseServiceOrderItem record) {
        return overseaWarehouseServiceOrderItemDao.insert(record);
    }

    @Override
    public int insertByBatch(List<OverseaWarehouseServiceOrderItem> orderItems) {
        if (ListUtils.isNotEmpty(orderItems)){
            return overseaWarehouseServiceOrderItemDao.insertByBatch(orderItems);
        }
        return 0;
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(OverseaWarehouseServiceOrderItem record) {
        return overseaWarehouseServiceOrderItemDao.insert(record);
    }

    @Override
    public BaseResponse uploadFpx(OverseaWarehouseServiceOrderItemUploadFpxRequest request, Session session) {
        OverseaWarehouseServiceOrderItem orderItem = selectByPrimaryKey(request.getItemId());
        if (orderItem == null){
            return BaseResponse.failed();
        }
        FpxSku fpxSku = new FpxSku();
        BeanUtils.copyProperties(request,fpxSku);
        if (request.isIfBox()){
            fpxSku.setSingleSkuCode(orderItem.getVariantSku());
            fpxSku.setSkuCode(System.currentTimeMillis() + "");
        }else {
            fpxSku.setSkuCode(orderItem.getVariantSku());
        }
        List<String> images = new ArrayList<>();
        images.add(orderItem.getVariantImage());
        fpxSku.setPictureUrl(images);
        try {
            fpxSku = FpxWmsApi.createSku(fpxSku);
            OverseaWarehouseSku overseaWarehouseSku = new OverseaWarehouseSku();
            overseaWarehouseSku.setWarehouseSkuId(fpxSku.getSkuId());
            overseaWarehouseSku.setWarehouseSkuCode(fpxSku.getSkuCode());
            overseaWarehouseSku.setCreateTime(new Date());
            overseaWarehouseSku.setVariantId(orderItem.getVariantId());
            overseaWarehouseSku.setVariantSku(orderItem.getVariantSku());
            overseaWarehouseSkuService.insert(overseaWarehouseSku);
        } catch (Exception e) {
            return BaseResponse.failed(e.getMessage());
        }
        return BaseResponse.success();
    }

    @Override
    public int updateWarehouseSkuByOrderId(Long orderId) {
        return overseaWarehouseServiceOrderItemDao.updateWarehouseSkuByOrderId(orderId);
    }

    @Override
    public List<OverseaWarehouseServiceOrderItem> selectByOrderId(Long orderId) {
        return overseaWarehouseServiceOrderItemDao.selectByOrderId(orderId);
    }

    /**
     *
     */
    public OverseaWarehouseServiceOrderItem selectByPrimaryKey(Long id){
        OverseaWarehouseServiceOrderItem record = new OverseaWarehouseServiceOrderItem();
        record.setId(id);
        return overseaWarehouseServiceOrderItemDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(OverseaWarehouseServiceOrderItem record) {
        return overseaWarehouseServiceOrderItemDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(OverseaWarehouseServiceOrderItem record) {
        return overseaWarehouseServiceOrderItemDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<OverseaWarehouseServiceOrderItem> select(Page<OverseaWarehouseServiceOrderItem> record){
        record.initFromNum();
        return overseaWarehouseServiceOrderItemDao.select(record);
    }

    /**
    *
    */
    public long count(Page<OverseaWarehouseServiceOrderItem> record){
        return overseaWarehouseServiceOrderItemDao.count(record);
    }

}