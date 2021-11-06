package com.upedge.oms.modules.wholesale.vo;

import lombok.Data;

import java.util.List;

@Data
public class WholesaleNameNumber {

    String storeName;

    List<String> numbers;
}
