package com.upedge.oms.modules.order.request;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.order.dto.AppOrderListDto;
import lombok.Data;

@Data
public class AppOrderListRequest extends Page<AppOrderListDto> {



    Integer pageNum;

    Integer fromNum;

    Integer pageSize;

    @Override
    public void initFromNum() {
        if(this.pageNum==null){
            this.pageNum=1;
        }
        if (this.pageSize == -1){
            this.pageSize = null;
        }
        if(null != this.pageNum && null != this.pageSize) {
            this.fromNum = (this.pageNum-1)*this.pageSize;
        }
        else {
            this.fromNum = null;
        }
    }

    public void init(Long customerId){
        AppOrderListDto appOrderListDto = this.getT();
        if (null == appOrderListDto) {
            appOrderListDto = new AppOrderListDto();
        } else {
            appOrderListDto.initOrderState();
        }
//        if (null != appOrderListDto.getEndTime()){
//            Date endTime = appOrderListDto.getEndTime();
//            endTime = DateUtils.addDays(endTime,2);
//            appOrderListDto.setEndTime(endTime);
//        }

        appOrderListDto.setCustomerId(customerId);

        this.initFromNum();
//        if (appOrderListDto.getTags().equals("PAID")) {
//            appOrderListDto.setPayState(null);
//            this.setCondition("o.pay_state > 0 and o.ship_state != 1");
//        }
        if (appOrderListDto.getTags().equals("REFUNDS")) {
            appOrderListDto.setRefundState(null);
            this.setCondition("o.refund_state > 0");
        }

        this.setT(appOrderListDto);
    }

}
