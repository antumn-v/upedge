package com.upedge.oms.modules.statistics.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.upedge.common.base.BaseResponse;
import com.upedge.common.config.HostConfig;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.OrderType;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.exception.CustomerException;
import com.upedge.common.feign.UmsFeignClient;
import com.upedge.common.model.user.vo.AddressVo;
import com.upedge.common.utils.DateUtils;
import com.upedge.common.utils.ListUtils;
import com.upedge.common.utils.ObjectUtils;
import com.upedge.oms.modules.statistics.dao.InvoiceDao;
import com.upedge.oms.modules.statistics.entity.InvoiceExportRequest;
import com.upedge.oms.modules.statistics.request.InvoiceDetailRequest;
import com.upedge.oms.modules.statistics.request.InvoiceListRequest;
import com.upedge.oms.modules.statistics.request.InvoiceSearchRequest;
import com.upedge.oms.modules.statistics.service.InvoiceExportRequestService;
import com.upedge.oms.modules.statistics.service.InvoiceService;
import com.upedge.oms.modules.statistics.vo.InvoiceDetailVo;
import com.upedge.oms.modules.statistics.vo.InvoiceExcelVo;
import com.upedge.oms.modules.statistics.vo.InvoiceProductVo;
import com.upedge.oms.modules.statistics.vo.InvoiceVo;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.*;

@Service
public class InvoiceServiceImpl implements InvoiceService {


    @Autowired
    private InvoiceDao invoiceDao;

    @Autowired
    private UmsFeignClient umsFeignClient;

    @Autowired
    private InvoiceExportRequestService invoiceExportRequestService;

    @Override
    public BaseResponse customerInvoiceList(InvoiceListRequest request) {
        List<InvoiceVo> invoiceVos = invoiceDao.selectCustomerInvoiceList(request);

        Long count = invoiceDao.selectCustomerInvoiceCount(request);
        request.setTotal(count);
        return new BaseResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS, invoiceVos, request);
    }

    @Override
    public InvoiceDetailVo customerInvoiceDetail(InvoiceDetailRequest request) {
        Long paymentId = request.getPaymentId();
        InvoiceDetailVo invoiceDetailVo = new InvoiceDetailVo();
        List<InvoiceProductVo> invoiceProductVos = new ArrayList<>();
        switch (request.getOrderType()) {
            case OrderType.NORMAL:
                invoiceDetailVo = invoiceDao.selectOrderInvoiceDetailByPaymentId(paymentId);
                invoiceProductVos = invoiceDao.selectOrderInvoiceProductByPaymentId(paymentId);
                break;
            case OrderType.STOCK:
                invoiceDetailVo = invoiceDao.selectStockInvoiceDetailByPaymentId(paymentId);
                invoiceProductVos = invoiceDao.selectStockInvoiceProductByPaymentId(paymentId);
                break;
            case OrderType.WHOLESALE:
                invoiceDetailVo = invoiceDao.selectWholesaleInvoiceDetailByPaymentId(paymentId);
                invoiceProductVos = invoiceDao.selectWholesaleInvoiceProductByPaymentId(paymentId);
                break;
        }
        invoiceDetailVo.setProductVos(invoiceProductVos);

        return invoiceDetailVo;
    }

    @Override
    public InvoiceDetailVo customerInvoiceSearch(InvoiceSearchRequest request) {
        InvoiceDetailVo invoiceDetailVo = new InvoiceDetailVo();
        List<InvoiceProductVo> invoiceProductVos = new ArrayList<>();
        switch (request.getOrderType()) {
            case OrderType.NORMAL:
                invoiceDetailVo = invoiceDao.selectOrderInvoiceDetailByTime(request);
                invoiceProductVos = invoiceDao.selectOrderInvoiceProductByTime(request);
                break;
            case OrderType.STOCK:
                invoiceDetailVo = invoiceDao.selectStockInvoiceDetailByTime(request);
                invoiceProductVos = invoiceDao.selectStockInvoiceProductByTime(request);
                break;
            case OrderType.WHOLESALE:
                invoiceDetailVo = invoiceDao.selectWholesaleInvoiceDetailByTime(request);
                invoiceProductVos = invoiceDao.selectWholesaleInvoiceProductByTime(request);
                break;
        }
        invoiceDetailVo.setProductVos(invoiceProductVos);

        return invoiceDetailVo;
    }

    @Override
    public String customerSearchInvoice(InvoiceSearchRequest request) throws CustomerException {
        /**
         * 查询账单地址
         */
        BaseResponse baseResponse = umsFeignClient.getBillingAddress(request.getCustomerId());
        ObjectMapper objectMapper = new ObjectMapper();
        AddressVo addressVo = objectMapper.convertValue(baseResponse.getData(), AddressVo.class);

        InvoiceExcelVo invoiceExcelVo = new InvoiceExcelVo();

        /**
         * 普通订单
         */
        InvoiceDetailVo invoiceDetailVoOrder = new InvoiceDetailVo();
        invoiceExcelVo.setAddressVo(addressVo);
        InvoiceDetailVo invoiceDetailVoNor = invoiceDao.selectOrderInvoiceDetailByTime(request);
        if (invoiceDetailVoNor != null) {
            BeanUtils.copyProperties(invoiceDetailVoNor, invoiceDetailVoOrder);
        }
        List<InvoiceProductVo> invoiceProductVosOrder = invoiceDao.selectOrderInvoiceProductByTime(request);
        invoiceDetailVoOrder.setProductVos(invoiceProductVosOrder);
        invoiceExcelVo.setNormal(invoiceDetailVoOrder);

        /**
         * 库存订单
         */
        InvoiceDetailVo invoiceDetailVoStock = new InvoiceDetailVo();
        InvoiceDetailVo invoiceDetailVoSto = invoiceDao.selectStockInvoiceDetailByTime(request);
        if (invoiceDetailVoSto != null) {
            BeanUtils.copyProperties(invoiceDetailVoSto, invoiceDetailVoStock);
        }
        List<InvoiceProductVo> invoiceProductVosStock = invoiceDao.selectStockInvoiceProductByTime(request);
        invoiceDetailVoStock.setProductVos(invoiceProductVosStock);
        invoiceExcelVo.setStockOrderInvoice(invoiceDetailVoStock);

        /**
         * 批发订单
         */
        InvoiceDetailVo invoiceDetailVoWholesale = new InvoiceDetailVo();
        InvoiceDetailVo invoiceDetailVoWho = invoiceDao.selectWholesaleInvoiceDetailByTime(request);
        if (invoiceDetailVoWho != null) {
            BeanUtils.copyProperties(invoiceDetailVoWho, invoiceDetailVoWholesale);
        }
        List<InvoiceProductVo> invoiceProductVosWholesale = invoiceDao.selectWholesaleInvoiceProductByTime(request);
        invoiceDetailVoWholesale.setProductVos(invoiceProductVosWholesale);
        invoiceExcelVo.setWholesaleOrderInvoice(invoiceDetailVoWholesale);
        //生成excel并保存记录
        return  creatExcel(DateUtils.parseDate(request.getBeginTime()), DateUtils.parseDate(request.getEndTime()), request.getCustomerId(),invoiceExcelVo);
    }

    /**
     * 将数据写入excel并上传服务器  未完成 测试时jar冲突
     *  @param beginTime
     * @param endTime
     * @param customerId
     * @param invoiceExcelVo
     */
    public String creatExcel(@NotNull Date beginTime, @NotNull Date endTime, Long customerId, InvoiceExcelVo invoiceExcelVo) {
        /**
         * 地址map
         */
        HashMap<String, String> address = new HashMap<>();
        address.put("name", invoiceExcelVo.getAddressVo().getFirstName() + " " + invoiceExcelVo.getAddressVo().getLastName());
        address.put("address", invoiceExcelVo.getAddressVo().getAddress1() + " " + invoiceExcelVo.getAddressVo().getAddress2());
        address.put("state", invoiceExcelVo.getAddressVo().getCity() + " " + invoiceExcelVo.getAddressVo().getProvince());
        address.put("country", invoiceExcelVo.getAddressVo().getCountry());

        InvoiceExportRequest exportRequest = new InvoiceExportRequest();

        String fileName = "invoice_" + System.currentTimeMillis() + ".xlsx";
        String filePath = "/root/files/excel/" + fileName;
        String url = HostConfig.HOST +"/oms/excel/invoice/" + fileName;
        String invoiceExcelTemplate = "/root/files/excel/invoiceTemplete.xlsx";

        ExcelWriter excelWriter = EasyExcel.write(filePath).withTemplate(invoiceExcelTemplate).build();

        try {
            FillConfig fillConfig = FillConfig.builder().forceNewRow(Boolean.TRUE).build();
            // 普通订单
            Map<String, String> normalOrder = ObjectUtils.objectToMap(invoiceExcelVo.getNormal());
            if (MapUtils.isNotEmpty(normalOrder) && ListUtils.isNotEmpty(invoiceExcelVo.getNormal().getProductVos())) {
                WriteSheet normalSheet = EasyExcel.writerSheet(0, "Normal Order").build();
                normalOrder.putAll(address);
                excelWriter.fill(invoiceExcelVo.getNormal().getProductVos(), fillConfig, normalSheet);
                excelWriter.fill(normalOrder, normalSheet);

            }

            //备库订单
            Map<String, String> stockOrder = ObjectUtils.objectToMap(invoiceExcelVo.getStockOrderInvoice());
            if(MapUtils.isNotEmpty(stockOrder) && ListUtils.isNotEmpty(invoiceExcelVo.getStockOrderInvoice().getProductVos())){
            if(invoiceExcelVo.getNormal() != null){
                WriteSheet stockSheet = EasyExcel.writerSheet(1,"Inventory Order").build();
                stockOrder.putAll(address);
                excelWriter.fill(invoiceExcelVo.getStockOrderInvoice().getProductVos(), fillConfig,stockSheet);
                excelWriter.fill(stockOrder, stockSheet);
            }

            // 批发订单
                Map<String, String> wholesaleOrder = ObjectUtils.objectToMap(invoiceExcelVo.getWholesaleOrderInvoice());
            if(wholesaleOrder != null){
                WriteSheet wholesaleSheet = EasyExcel.writerSheet(2,"Wholesale Order").build();
                wholesaleOrder.putAll(address);
                excelWriter.fill(invoiceExcelVo.getWholesaleOrderInvoice().getProductVos(), fillConfig,wholesaleSheet);
                excelWriter.fill(wholesaleOrder, wholesaleSheet);
            }

        }
    }
    finally {
            excelWriter.finish();
        }
        exportRequest.setCreateTime(new Date());
        exportRequest.setExcelUrl(url);
        exportRequest.setRangeBegin(DateUtils.formatDate(beginTime, "yyyy-MM-dd"));
        exportRequest.setRangeEnd(DateUtils.formatDate(endTime, "yyyy-MM-dd"));
        exportRequest.setExcelName(fileName);
        exportRequest.setCustomerId(customerId);
        invoiceExportRequestService.insert(exportRequest);
//        System.err.println(url);
        return url;
    }
}