package com.upedge.sms.modules.center.service.impl;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.constant.OrderConstant;
import com.upedge.common.constant.OrderType;
import com.upedge.common.model.account.vo.InvoiceProductVo;
import com.upedge.common.model.account.vo.InvoiceVo;
import com.upedge.common.model.user.vo.Session;
import com.upedge.sms.modules.center.dao.ServiceOrderDao;
import com.upedge.sms.modules.center.entity.ServiceOrder;
import com.upedge.sms.modules.center.service.ServiceOrderService;
import com.upedge.sms.modules.overseaWarehouse.entity.OverseaWarehouseServiceOrderItem;
import com.upedge.sms.modules.overseaWarehouse.service.OverseaWarehouseServiceOrderService;
import com.upedge.sms.modules.overseaWarehouse.vo.OverseaWarehouseServiceOrderVo;
import com.upedge.sms.modules.photography.entity.ProductPhotographyOrder;
import com.upedge.sms.modules.photography.service.ProductPhotographyOrderService;
import com.upedge.sms.modules.wholesale.WholesaleOrderVo;
import com.upedge.sms.modules.wholesale.entity.WholesaleOrderItem;
import com.upedge.sms.modules.wholesale.service.WholesaleOrderService;
import com.upedge.sms.modules.winningProduct.entity.WinningProductServiceOrder;
import com.upedge.sms.modules.winningProduct.service.WinningProductServiceOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class ServiceOrderServiceImpl implements ServiceOrderService {

    @Autowired
    private ServiceOrderDao serviceOrderDao;

    @Autowired
    OverseaWarehouseServiceOrderService overseaWarehouseServiceOrderService;

    @Autowired
    WholesaleOrderService wholesaleOrderService;

    @Autowired
    WinningProductServiceOrderService winningProductServiceOrderService;

    @Autowired
    ProductPhotographyOrderService productPhotographyOrderService;


    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        ServiceOrder record = new ServiceOrder();
        record.setId(id);
        return serviceOrderDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(ServiceOrder record) {
        return serviceOrderDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(ServiceOrder record) {
        return serviceOrderDao.insert(record);
    }

    @Override
    public void saveTransaction() {
        ServiceOrder serviceOrder = new ServiceOrder();
        serviceOrder.setPayState(1);
        Page<ServiceOrder> page = new Page<>();
        page.setT(serviceOrder);
        page.setPageSize(-1);
        List<ServiceOrder> serviceOrders = select(page);
        for (ServiceOrder order : serviceOrders) {
            switch (order.getServiceType()){
                case OrderType.EXTRA_SERVICE_OVERSEA_WAREHOUSE:
                    overseaWarehouseServiceOrderService.saveTransactionRecord(order.getCustomerId(),order.getId());
                    break;
                case OrderType.EXTRA_SERVICE_WHOLESALE:
                    wholesaleOrderService.saveTransactionRecordMessage(order.getCustomerId(),order.getId());
                    break;
                case OrderType.EXTRA_SERVICE_WINNING_PRODUCT:
                    winningProductServiceOrderService.saveTransactionRecordMessage(order.getCustomerId(),order.getId());
                    break;
                case OrderType.EXTRA_SERVICE_PRODUCT_PHOTOGRAPHY:
                    productPhotographyOrderService.saveTransactionRecord(order.getCustomerId(), order.getId());
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public BaseResponse cancelOrder(Long id, Session session) {
        ServiceOrder serviceOrder = selectByPrimaryKey(id);
        if (serviceOrder == null
        || !serviceOrder.getCustomerId().equals(session.getCustomerId())
        || serviceOrder.getPayState() != OrderConstant.PAY_STATE_UNPAID){
            return BaseResponse.failed();
        }
        String orderTable = null;


        switch (serviceOrder.getServiceType()){
            case OrderType.EXTRA_SERVICE_OVERSEA_WAREHOUSE:
                orderTable = "oversea_warehouse_service_order";
                break;
            case OrderType.EXTRA_SERVICE_WHOLESALE:
                orderTable = "wholesale_order";
                break;
            case OrderType.EXTRA_SERVICE_WINNING_PRODUCT:
                orderTable = "winning_product_service_order";
                break;
            case OrderType.EXTRA_SERVICE_PRODUCT_PHOTOGRAPHY:
                orderTable = "product_photography_order";
                break;
            default:
                return BaseResponse.failed();
        }
        serviceOrderDao.cancelOrder(id,orderTable);
        return BaseResponse.success();
    }

    @Override
    public BaseResponse restoreCanceledOrder(Long id, Session session) {
        ServiceOrder serviceOrder = selectByPrimaryKey(id);
        if (serviceOrder == null
                || !serviceOrder.getCustomerId().equals(session.getCustomerId())
                || serviceOrder.getPayState() != OrderConstant.PAY_STATE_CANCELED){
            return BaseResponse.failed();
        }
        String orderTable = null;


        switch (serviceOrder.getServiceType()){
            case OrderType.EXTRA_SERVICE_OVERSEA_WAREHOUSE:
                orderTable = "oversea_warehouse_service_order";
                break;
            case OrderType.EXTRA_SERVICE_WHOLESALE:
                orderTable = "wholesale_order";
                break;
            case OrderType.EXTRA_SERVICE_WINNING_PRODUCT:
                orderTable = "winning_product_service_order";
                break;
            case OrderType.EXTRA_SERVICE_PRODUCT_PHOTOGRAPHY:
                orderTable = "product_photography_order";
                break;
            default:
                return BaseResponse.failed();
        }
        serviceOrderDao.restoreCanceledOrder(id,orderTable);
        return BaseResponse.success();
    }

    @Override
    public BaseResponse orderInvoice(Long id) {
        ServiceOrder serviceOrder = selectByPrimaryKey(id);
        if (null == serviceOrder
        || OrderConstant.PAY_STATE_PAID != serviceOrder.getPayState()){
            return BaseResponse.failed();
        }
        InvoiceVo invoiceVo = new InvoiceVo();
        switch (serviceOrder.getServiceType()){
            case OrderType.EXTRA_SERVICE_OVERSEA_WAREHOUSE:
                invoiceVo = overseaWarehouseOrderInvoice(id);
                break;
            case OrderType.EXTRA_SERVICE_WHOLESALE:
                invoiceVo = warehouseOrderInvoice(id);
                break;
            case OrderType.EXTRA_SERVICE_WINNING_PRODUCT:
                invoiceVo = winningProductOrderInvoice(id);
                break;
            case OrderType.EXTRA_SERVICE_PRODUCT_PHOTOGRAPHY:
                invoiceVo = photographyOrderInvoice(id);
                break;
            default:
                break;
        }

        return BaseResponse.success(invoiceVo);
    }

    public InvoiceVo overseaWarehouseOrderInvoice(Long id){
        OverseaWarehouseServiceOrderVo order = overseaWarehouseServiceOrderService.orderDetail(id);
        InvoiceVo invoiceVo = new InvoiceVo();
        invoiceVo.setOrderType(OrderType.EXTRA_SERVICE_OVERSEA_WAREHOUSE);
        invoiceVo.setCustomerId(order.getCustomerId());
        invoiceVo.setPaymentId(order.getPaymentId());
        invoiceVo.setPayTime(order.getPayTime());
        invoiceVo.setShipPrice(order.getShipPrice());
        invoiceVo.setProductAmount(order.getProductAmount());
        invoiceVo.setPayAmount(order.getPayAmount());
        
        List<InvoiceProductVo> productVos = new ArrayList<>();
        List<OverseaWarehouseServiceOrderItem> items = order.getOrderItems();
        for (OverseaWarehouseServiceOrderItem item : items) {
            InvoiceProductVo productVo = new InvoiceProductVo();
            productVo.setPrice(item.getPrice());
            productVo.setQuantity(item.getQuantity());
            productVo.setTotal(new BigDecimal(item.getQuantity()));
            productVo.setSku(item.getVariantSku());
            productVo.setImage(item.getVariantImage());
            productVo.setVariantTitle(item.getVariantName());
            productVo.setTitle(item.getProductTitle());
            productVos.add(productVo);
        }
        invoiceVo.setProductVos(productVos);
        return invoiceVo;
    }

    public InvoiceVo warehouseOrderInvoice(Long id){
        WholesaleOrderVo order = wholesaleOrderService.orderDetail(id);
        InvoiceVo invoiceVo = new InvoiceVo();
        invoiceVo.setOrderType(OrderType.EXTRA_SERVICE_WHOLESALE);
        invoiceVo.setCustomerId(order.getCustomerId());
        invoiceVo.setPaymentId(order.getPaymentId());
        invoiceVo.setPayTime(order.getPayTime());
        invoiceVo.setShipPrice(order.getShipPrice());
        invoiceVo.setProductAmount(order.getProductAmount());
        invoiceVo.setPayAmount(order.getPayAmount());

        List<InvoiceProductVo> productVos = new ArrayList<>();
        List<WholesaleOrderItem> wholesaleOrderItem = order.getWholesaleOrderItems();
        for (WholesaleOrderItem item : wholesaleOrderItem) {
            InvoiceProductVo productVo = new InvoiceProductVo();
            productVo.setPrice(item.getPrice());
            productVo.setQuantity(item.getQuantity());
            productVo.setTotal(new BigDecimal(item.getQuantity()).multiply(item.getPrice()).setScale(2));
            productVo.setSku(item.getVariantSku());
            productVo.setImage(item.getVariantImage());
            productVo.setVariantTitle(item.getVariantName());
            productVo.setTitle(item.getProductTitle());
            productVos.add(productVo);
        }
        invoiceVo.setProductVos(productVos);
        return invoiceVo;
    }

    public InvoiceVo winningProductOrderInvoice(Long id){
        WinningProductServiceOrder order = winningProductServiceOrderService.selectByPrimaryKey(id);
        InvoiceVo invoiceVo = new InvoiceVo();
        invoiceVo.setOrderType(OrderType.EXTRA_SERVICE_WINNING_PRODUCT);
        invoiceVo.setCustomerId(order.getCustomerId());
        invoiceVo.setPaymentId(order.getPaymentId());
        invoiceVo.setPayTime(order.getPayTime());
        invoiceVo.setShipPrice(BigDecimal.ZERO);
        invoiceVo.setProductAmount(BigDecimal.ZERO);
        invoiceVo.setPayAmount(order.getPayAmount());
        invoiceVo.setProductVos(new ArrayList<>());
        return invoiceVo;
    }

    public InvoiceVo photographyOrderInvoice(Long id){
        ProductPhotographyOrder order = productPhotographyOrderService.selectByPrimaryKey(id);
        InvoiceVo invoiceVo = new InvoiceVo();
        invoiceVo.setOrderType(OrderType.EXTRA_SERVICE_PRODUCT_PHOTOGRAPHY);
        invoiceVo.setCustomerId(order.getCustomerId());
        invoiceVo.setPaymentId(order.getPaymentId());
        invoiceVo.setPayTime(order.getPayTime());
        invoiceVo.setShipPrice(BigDecimal.ZERO);
        invoiceVo.setProductAmount(BigDecimal.ZERO);
        invoiceVo.setPayAmount(order.getPayAmount());
        invoiceVo.setProductVos(new ArrayList<>());
        return invoiceVo;
    }



    @Override
    public int updateToPaidByRelateId(Long relateId, Integer serviceType, BigDecimal payAmount, Date updateTime) {
        return serviceOrderDao.updateToPaidByRelateId(relateId, serviceType, payAmount, updateTime);
    }

    @Override
    public ServiceOrder selectByRelateId(Long relateId, Integer serviceType) {
        return serviceOrderDao.selectByRelateId(relateId, serviceType);
    }

    /**
     *
     */
    public ServiceOrder selectByPrimaryKey(Long id){
        ServiceOrder record = new ServiceOrder();
        record.setId(id);
        return serviceOrderDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(ServiceOrder record) {
        return serviceOrderDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(ServiceOrder record) {
        return serviceOrderDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<ServiceOrder> select(Page<ServiceOrder> record){
        record.initFromNum();
        return serviceOrderDao.select(record);
    }

    /**
    *
    */
    public long count(Page<ServiceOrder> record){
        return serviceOrderDao.count(record);
    }

}