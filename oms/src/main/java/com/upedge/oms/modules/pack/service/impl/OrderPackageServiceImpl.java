package com.upedge.oms.modules.pack.service.impl;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.model.ship.vo.ShippingMethodRedis;
import com.upedge.common.model.user.vo.CustomerIossVo;
import com.upedge.common.model.user.vo.UserVo;
import com.upedge.oms.modules.order.entity.Order;
import com.upedge.oms.modules.order.entity.OrderAddress;
import com.upedge.oms.modules.order.entity.OrderItem;
import com.upedge.oms.modules.order.service.OrderAddressService;
import com.upedge.oms.modules.order.service.OrderItemService;
import com.upedge.oms.modules.order.service.OrderService;
import com.upedge.oms.modules.pack.service.OrderPackageService;
import com.upedge.thirdparty.fpx.api.FpxOrderApi;
import com.upedge.thirdparty.fpx.dto.FpxOrderCreateDto;
import com.upedge.thirdparty.fpx.dto.FpxOrderCreateDto.ParcelListDTO;
import com.upedge.thirdparty.fpx.dto.FpxOrderCreateDto.RecipientInfoDTO;
import com.upedge.thirdparty.fpx.vo.FpxCreateOrderSuccessVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderPackageServiceImpl implements OrderPackageService {


    @Autowired
    OrderService orderService;

    @Autowired
    OrderItemService orderItemService;

    @Autowired
    OrderAddressService orderAddressService;

    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public BaseResponse createFpxPackage(Long orderId) {
        Order order = orderService.selectByPrimaryKey(orderId);

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
        recipientInfo.setPostCode(orderAddress.getZip());
        recipientInfo.setDistrict(orderAddress.getAddress2());
        recipientInfo.setStreet(orderAddress.getAddress1());
        recipientInfo.setState(orderAddress.getProvince());
        recipientInfo.setCountry(orderAddress.getCountryCode());
        recipientInfo.setPhone("8956232659");
        recipientInfo.setPhone2("18562356856");

        fpxOrderCreateDto.getLogisticsServiceInfo().setLogisticsProductCode(shippingMethodRedis.getMethodCode());

        try {
            FpxCreateOrderSuccessVo.FpxCreateOrderDataDTO fpxCreateOrderDataDTO = FpxOrderApi.createFpxOrder(fpxOrderCreateDto);
            return BaseResponse.success(fpxCreateOrderDataDTO);
        } catch (Exception e) {
            return BaseResponse.failed(e.getMessage());
        }


    }
}
