package com.upedge.thirdparty.shipcompany.yunexpress.request;

import com.upedge.thirdparty.shipcompany.yunexpress.dto.WayBillCreateDto;
import lombok.Data;

import java.util.List;

@Data
public class WayBillCreateRequest {

    private String url = "/WayBill/CreateOrder";


   private List<WayBillCreateDto> wayBillCreateDtos;
}
