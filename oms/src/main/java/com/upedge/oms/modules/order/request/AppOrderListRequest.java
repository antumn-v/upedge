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

}
