package com.upedge.oms.modules.order.request;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.order.dto.PandaOrderListDto;

public class PandaOrderListRequest  extends Page<PandaOrderListDto> {

    Integer pageNum;

    Integer fromNum;

    Integer pageSize;

    @Override
    public void initFromNum() {
        if(this.pageNum==null){
            this.pageNum=1;
        }
        if(this.pageSize==null||this.pageSize==0){
            this.pageSize=20;
        }
        if(null != this.pageNum && null != this.pageSize) {
            this.fromNum = (this.pageNum-1)*this.pageSize;
        }
        else {
            this.fromNum = null;
        }
    }
}
