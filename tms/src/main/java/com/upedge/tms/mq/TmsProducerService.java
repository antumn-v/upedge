package com.upedge.tms.mq;

import java.util.List;

/**
 * 运输模块mq生产者
 */

public interface TmsProducerService {


    boolean sendMessage(List<Long> shipUnitIds);

}
