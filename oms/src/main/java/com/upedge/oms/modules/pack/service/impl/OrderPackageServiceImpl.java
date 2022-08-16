package com.upedge.oms.modules.pack.service.impl;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.exception.CustomerException;
import com.upedge.common.model.ship.vo.ShippingMethodRedis;
import com.upedge.common.model.user.vo.CustomerIossVo;
import com.upedge.common.model.user.vo.UserVo;
import com.upedge.common.utils.DateUtils;
import com.upedge.common.web.util.RedisUtil;
import com.upedge.oms.modules.order.entity.Order;
import com.upedge.oms.modules.order.entity.OrderAddress;
import com.upedge.oms.modules.order.entity.OrderItem;
import com.upedge.oms.modules.order.service.OrderAddressService;
import com.upedge.oms.modules.order.service.OrderItemService;
import com.upedge.oms.modules.order.service.OrderService;
import com.upedge.oms.modules.order.vo.AppOrderVo;
import com.upedge.oms.modules.pack.dao.OrderPackageDao;
import com.upedge.oms.modules.pack.entity.OrderPackage;
import com.upedge.oms.modules.pack.service.OrderPackageService;
import com.upedge.oms.modules.pack.vo.OrderPackageInfoVo;
import com.upedge.thirdparty.fpx.api.FpxOrderApi;
import com.upedge.thirdparty.fpx.dto.FpxOrderCreateDto;
import com.upedge.thirdparty.fpx.dto.FpxOrderCreateDto.ParcelListDTO;
import com.upedge.thirdparty.fpx.dto.FpxOrderCreateDto.RecipientInfoDTO;
import com.upedge.thirdparty.fpx.vo.FpxCreateOrderSuccessVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderPackageServiceImpl implements OrderPackageService {

    @Autowired
    OrderPackageDao orderPackageDao;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderItemService orderItemService;

    @Autowired
    OrderAddressService orderAddressService;

    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public OrderPackageInfoVo packageInfo(Integer packageNo) {
        OrderPackage orderPackage = selectByPrimaryKey(packageNo);

        if (null == packageNo){
            return null;
        }
        OrderPackageInfoVo orderPackageInfoVo = new OrderPackageInfoVo();
        BeanUtils.copyProperties(orderPackage,orderPackageInfoVo);
        AppOrderVo appOrderVo = orderService.appOrderDetail(orderPackage.getOrderId());
        orderPackageInfoVo.setOrderVo(appOrderVo);
        return orderPackageInfoVo;
    }

    @Override
    public BaseResponse createPackage(Long orderId) {
        Order order = orderService.selectByPrimaryKey(orderId);

        if (order.getPayState() != 1
        || order.getPackState() == 1){
            return BaseResponse.failed("订单未支付或已生成包裹");
        }

        FpxCreateOrderSuccessVo.FpxCreateOrderDataDTO fpxCreateOrderDataDTO = null;

        try {
            fpxCreateOrderDataDTO = createFpxPackage(order);
        } catch (Exception e) {
            orderService.updateOrderPackInfo(orderId,2,null);
            redisTemplate.opsForHash().put(RedisKey.HASH_ORDER_CREATE_PACKAGE_FAILED_REASON,orderId.toString(),e.getMessage());
            return BaseResponse.failed(e.getMessage());
        }
        ShippingMethodRedis shippingMethodRedis = (ShippingMethodRedis) redisTemplate.opsForHash().get(RedisKey.SHIPPING_METHOD,String.valueOf(order.getShipMethodId()));

        Integer packageNo = getPackageNo();
        OrderPackage orderPackage = new OrderPackage();
        orderPackage.setId(packageNo);
        orderPackage.setPackageNo(packageNo);
        orderPackage.setOrderId(orderId);
        orderPackage.setPackageState(0);
        orderPackage.setCreateTime(new Date());
        orderPackage.setLogisticsOrderNo(fpxCreateOrderDataDTO.getLogisticsChannelNo());
        orderPackage.setTrackingCode(fpxCreateOrderDataDTO.getDsConsignmentNo());
        orderPackage.setPlatId(fpxCreateOrderDataDTO.getLabelBarcode());
        orderPackage.setStoreId(order.getStoreId());
        orderPackage.setCustomerId(order.getCustomerId());
        orderPackage.setTrackingMethodName(shippingMethodRedis.getName());
        orderPackage.setTrackingMethodCode(shippingMethodRedis.getMethodCode());
        orderPackage.setTrackingCompany(shippingMethodRedis.getTrackingCompany());
        insert(orderPackage);

        orderService.updateOrderPackInfo(orderId,1,packageNo);

        return BaseResponse.success(orderPackage);
    }

    public FpxCreateOrderSuccessVo.FpxCreateOrderDataDTO createFpxPackage(Order order) throws CustomerException {
       Long orderId = order.getId();

        List<OrderItem> orderItems = orderItemService.selectByOrderId(orderId);

        UserVo userVo = (UserVo) redisTemplate.opsForHash().get(RedisKey.STRING_CUSTOMER_INFO,order.getCustomerId().toString());

        CustomerIossVo customerIossVo = (CustomerIossVo) redisTemplate.opsForValue().get(RedisKey.STRING_CUSTOMER_IOSS + order.getCustomerId());

        ShippingMethodRedis shippingMethodRedis = (ShippingMethodRedis) redisTemplate.opsForHash().get(RedisKey.SHIPPING_METHOD,order.getShipMethodId().toString());

        OrderAddress orderAddress = orderService.getOrderAddress(orderId);

        FpxOrderCreateDto fpxOrderCreateDto = new FpxOrderCreateDto();
        fpxOrderCreateDto.setRefNo(order.getId().toString());
        fpxOrderCreateDto.setBuyerId(userVo.getUsername());
        if (customerIossVo != null){
            fpxOrderCreateDto.setVatNo(customerIossVo.getIossNum());
        }

        List<ParcelListDTO> parcelListDTOS = new ArrayList<>();

        List<ParcelListDTO.DeclareProductInfoDTO> declareProductInfoDTOS = new ArrayList<>();
        orderItems.forEach(orderItem -> {
            ParcelListDTO.DeclareProductInfoDTO declareProductInfoDTO = new ParcelListDTO.DeclareProductInfoDTO();
            declareProductInfoDTO.setDeclareProductNameEn("pet clothes");
            declareProductInfoDTO.setDeclareProductNameCn("宠物服装");
            declareProductInfoDTO.setDeclareProductCodeQty(orderItem.getQuantity());
            declareProductInfoDTO.setDeclareUnitPriceExport(orderItem.getUsdPrice());
            declareProductInfoDTO.setDeclareUnitPriceImport(orderItem.getUsdPrice());
            declareProductInfoDTOS.add(declareProductInfoDTO);
        });

        ParcelListDTO parcelListDTO = new ParcelListDTO();
        parcelListDTO.setWeight(order.getTotalWeight().intValue());
        parcelListDTO.setParcelValue(order.getProductAmount().doubleValue());
        parcelListDTO.setCurrency("USD");
        parcelListDTOS.add(parcelListDTO);
        parcelListDTO.setDeclareProductInfo(declareProductInfoDTOS);
        fpxOrderCreateDto.setParcelList(parcelListDTOS);

        RecipientInfoDTO recipientInfo = fpxOrderCreateDto.getRecipientInfo();
        BeanUtils.copyProperties(orderAddress,recipientInfo);
        recipientInfo.setFirstName("测");
        recipientInfo.setLastName("试");
        recipientInfo.setPostCode(orderAddress.getZip());
        recipientInfo.setDistrict(orderAddress.getAddress2());
        recipientInfo.setStreet(orderAddress.getAddress1());
        recipientInfo.setState(orderAddress.getProvince());
        recipientInfo.setCountry(orderAddress.getCountryCode());
        recipientInfo.setPhone("8956232659");
        recipientInfo.setPhone2("18562356856");

        fpxOrderCreateDto.getLogisticsServiceInfo().setLogisticsProductCode("F3");

        try {
            FpxCreateOrderSuccessVo.FpxCreateOrderDataDTO fpxCreateOrderDataDTO = FpxOrderApi.createFpxOrder(fpxOrderCreateDto);
            return fpxCreateOrderDataDTO;
        } catch (Exception e) {
            throw new CustomerException(e.getMessage());
        }


    }


    @Transactional
    public int deleteByPrimaryKey(Integer id) {
        OrderPackage record = new OrderPackage();
        record.setId(id);
        return orderPackageDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(OrderPackage record) {
        return orderPackageDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(OrderPackage record) {
        return orderPackageDao.insert(record);
    }

    /**
     *
     */
    public OrderPackage selectByPrimaryKey(Integer id){
        OrderPackage record = new OrderPackage();
        record.setId(id);
        return orderPackageDao.selectByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int updateByPrimaryKeySelective(OrderPackage record) {
        return orderPackageDao.updateByPrimaryKeySelective(record);
    }

    /**
     *
     */
    @Transactional
    public int updateByPrimaryKey(OrderPackage record) {
        return orderPackageDao.updateByPrimaryKey(record);
    }

    /**
     *
     */
    public List<OrderPackage> select(Page<OrderPackage> record){
        record.initFromNum();
        return orderPackageDao.select(record);
    }

    /**
     *
     */
    public long count(Page<OrderPackage> record){
        return orderPackageDao.count(record);
    }

    public Integer getPackageNo(){
        String key = "order:package:no";
        boolean b = RedisUtil.lock(redisTemplate,key,5L,10L);
        if (!b){
            return null;
        }
        String date = DateUtils.getDate("yyyyMMdd");
        Integer no = (Integer) redisTemplate.opsForHash().get(key,date);
        if (null == no){
            no = Integer.parseInt(date + "00001");
        }else {
            no = no + 1;
        }
        redisTemplate.opsForHash().put(key,date,no);
        RedisUtil.unLock(redisTemplate,key);
        return no;
    }

}
