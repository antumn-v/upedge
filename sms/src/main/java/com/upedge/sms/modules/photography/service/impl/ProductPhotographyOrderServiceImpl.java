package com.upedge.sms.modules.photography.service.impl;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.constant.OrderConstant;
import com.upedge.common.constant.OrderType;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.feign.UmsFeignClient;
import com.upedge.common.model.account.AccountPaymentRequest;
import com.upedge.common.model.order.PaymentDetail;
import com.upedge.common.model.order.TransactionDetail;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.utils.FileUtil;
import com.upedge.common.utils.IdGenerate;
import com.upedge.common.utils.ListUtils;
import com.upedge.sms.modules.center.entity.ServiceOrder;
import com.upedge.sms.modules.center.service.ServiceOrderService;
import com.upedge.sms.modules.photography.dao.ProductPhotographyOrderDao;
import com.upedge.sms.modules.photography.entity.ProductPhotographyOrder;
import com.upedge.sms.modules.photography.entity.ProductPhotographyOrderItem;
import com.upedge.sms.modules.photography.request.ProductPhotographyOrderCreateRequest;
import com.upedge.sms.modules.photography.request.ProductPhotographyOrderPayRequest;
import com.upedge.sms.modules.photography.service.ProductPhotographyOrderItemService;
import com.upedge.sms.modules.photography.service.ProductPhotographyOrderService;
import com.upedge.sms.modules.photography.vo.ProductPhotographyOrderVo;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class ProductPhotographyOrderServiceImpl implements ProductPhotographyOrderService {

    @Autowired
    private ProductPhotographyOrderDao productPhotographyOrderDao;

    @Autowired
    private ProductPhotographyOrderItemService productPhotographyOrderItemService;

    @Autowired
    ServiceOrderService serviceOrderService;

    @Autowired
    UmsFeignClient umsFeignClient;

    @Value("${files.image.local}")
    private String localPath;
    @Value("${files.image.prefix}")
    private String imageUrlPrefix;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        ProductPhotographyOrder record = new ProductPhotographyOrder();
        record.setId(id);
        return productPhotographyOrderDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(ProductPhotographyOrder record) {
        return productPhotographyOrderDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(ProductPhotographyOrder record) {
        return productPhotographyOrderDao.insert(record);
    }


    @GlobalTransactional
    @Override
    public BaseResponse pay(ProductPhotographyOrderPayRequest request, Session session) {
        Long id = request.getId();
        ProductPhotographyOrder productPhotographyOrder = selectByPrimaryKey(id);
        if (null == productPhotographyOrder
        || productPhotographyOrder.getPayState() != OrderConstant.PAY_STATE_UNPAID){
            return BaseResponse.failed();
        }
        //检查支付金额
        BigDecimal payAmount = request.getPayAmount();
        if (payAmount.compareTo(productPhotographyOrder.getPayAmount()) != 0){
            return BaseResponse.failed("Incorrect payment amount!");
        }
        //账户支付
        Long paymentId = IdGenerate.nextId();
        AccountPaymentRequest accountPaymentRequest = new AccountPaymentRequest(paymentId,session.getId(),session.getAccountId(),session.getCustomerId(), OrderType.EXTRA_SERVICE_PRODUCT_PHOTOGRAPHY,payAmount,BigDecimal.ZERO,0);
        BaseResponse paymentResponse = umsFeignClient.accountPayment(accountPaymentRequest);
        if (paymentResponse.getCode() != ResultCode.SUCCESS_CODE){
            return paymentResponse;
        }
        Date payTime = new Date();
        productPhotographyOrderDao.updateOrderAsPaid(id,paymentId,payTime);
        serviceOrderService.updateToPaidByRelateId(id,OrderType.EXTRA_SERVICE_PRODUCT_PHOTOGRAPHY,payAmount,payTime);
        return BaseResponse.success();
    }

    @Override
    public ProductPhotographyOrderVo orderDetail(Long id) {
        ProductPhotographyOrder productPhotographyOrder = selectByPrimaryKey(id);
        if (null == productPhotographyOrder){
            return null;
        }
        ProductPhotographyOrderVo productPhotographyOrderVo = new ProductPhotographyOrderVo();
        BeanUtils.copyProperties(productPhotographyOrder,productPhotographyOrderVo);

        List<ProductPhotographyOrderItem> productPhotographyOrderItems = productPhotographyOrderItemService.selectByOrderId(id);
        productPhotographyOrderVo.setOrderItems(productPhotographyOrderItems);
        return productPhotographyOrderVo;
    }

    @Transactional
    @Override
    public BaseResponse create(ProductPhotographyOrderCreateRequest request, Session session) {
        ProductPhotographyOrder productPhotographyOrder = new ProductPhotographyOrder();
        productPhotographyOrder.setReferenceLink(request.getReferenceLink());
        productPhotographyOrder.setPhotographyType(request.getPhotographyType());
        List<String> images = request.getReferenceImageBase64();
        if (ListUtils.isNotEmpty(images)){
            String referenceImage = null;
            for (String image : images) {
                String uploadImage = FileUtil.uploadImage(image,imageUrlPrefix,localPath);
                if (uploadImage == null){
                    continue;
                }
                if (StringUtils.isBlank(referenceImage)){
                    referenceImage = uploadImage;
                }else {
                    referenceImage = "," + uploadImage;
                }
            }
            productPhotographyOrder.setReferenceImage(referenceImage);
        }
        Long orderId = IdGenerate.nextId();
        productPhotographyOrder.setId(orderId);
        productPhotographyOrder.setCreateTime(new Date());
        productPhotographyOrder.setUpdateTime(new Date());
        productPhotographyOrder.setCustomerId(session.getCustomerId());
        productPhotographyOrder.setDescription(request.getDescription());

        List<ProductPhotographyOrderItem> orderItems = request.getItems();
        for (ProductPhotographyOrderItem orderItem : orderItems) {
            orderItem.setOrderId(orderId);
            orderItem.setId(IdGenerate.nextId());
        }
        insert(productPhotographyOrder);
        productPhotographyOrderItemService.insertByBatch(orderItems);

        ServiceOrder serviceOrder = new ServiceOrder();
        serviceOrder.setServiceTitle(request.getServiceTitle());
        serviceOrder.setId(orderId);
        serviceOrder.setRelateId(orderId);
        serviceOrder.setServiceState(0);
        serviceOrder.setCustomerId(session.getCustomerId());
        serviceOrder.setCreateTime(new Date());
        serviceOrder.setPayState(0);
        serviceOrder.setRefundState(0);
        serviceOrder.setServiceType(OrderType.EXTRA_SERVICE_PRODUCT_PHOTOGRAPHY);
        serviceOrder.setUpdateTime(new Date());
        serviceOrderService.insert(serviceOrder);
        return BaseResponse.success(productPhotographyOrder);
    }


    /**
     *
     */
    public ProductPhotographyOrder selectByPrimaryKey(Long id){
        ProductPhotographyOrder record = new ProductPhotographyOrder();
        record.setId(id);
        return productPhotographyOrderDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(ProductPhotographyOrder record) {
        return productPhotographyOrderDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(ProductPhotographyOrder record) {
        return productPhotographyOrderDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<ProductPhotographyOrder> select(Page<ProductPhotographyOrder> record){
        record.initFromNum();
        return productPhotographyOrderDao.select(record);
    }

    /**
    *
    */
    public long count(Page<ProductPhotographyOrder> record){
        return productPhotographyOrderDao.count(record);
    }

    @Override
    public void saveTransactionRecord(Long userId, Long orderId) {
        ProductPhotographyOrder order = selectByPrimaryKey(orderId);
        if (order.getPayState() != OrderConstant.PAY_STATE_PAID){
            return;
        }
        PaymentDetail detail = new PaymentDetail();
        detail.setPaymentId(order.getPaymentId());
        detail.setUserId(userId);
        detail.setCustomerId(order.getCustomerId());
        detail.setPayMethod(0);
        detail.setPayAmount(order.getPayAmount());
        detail.setOrderType(OrderType.EXTRA_SERVICE_PRODUCT_PHOTOGRAPHY);
        detail.setPayTime(order.getPayTime());

        List<TransactionDetail> transactionDetails = new ArrayList<>();
        TransactionDetail transactionDetail = new TransactionDetail();
        transactionDetail.setOrderId(order.getId());
        transactionDetail.setPaymentId(order.getPaymentId());
        transactionDetail.setPayTime(order.getPayTime());
        transactionDetail.setAmount(order.getPayAmount());
        transactionDetail.setOrderType(OrderType.EXTRA_SERVICE_PRODUCT_PHOTOGRAPHY);
        transactionDetails.add(transactionDetail);

        detail.setOrderTransactions(transactionDetails);

        umsFeignClient.saveTransactionDetails(detail);

    }

}