package com.upedge.tms.mq;

import java.util.List;

/**
 * 运输模块mq生产者
 */

public interface TmsProcuderService {


    void  sendMessage(List<Long> shipUnitIds);

}
