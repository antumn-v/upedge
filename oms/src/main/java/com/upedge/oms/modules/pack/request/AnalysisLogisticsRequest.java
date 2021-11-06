package com.upedge.oms.modules.pack.request;

import com.upedge.common.base.Page;
import com.upedge.common.utils.DateUtils;
import com.upedge.oms.modules.pack.dto.PackageTrackingAnalysisDto;
import lombok.Data;

import java.util.Calendar;

/**
 * 物流分析   查询参数接收实体
 */
@Data
public class AnalysisLogisticsRequest extends Page<PackageTrackingAnalysisDto> {

    public void initDateRange(){
        PackageTrackingAnalysisDto analysisDto = getT();
        if (null == analysisDto){
            analysisDto = new PackageTrackingAnalysisDto();
        }
        String start = DateUtils.getDate("yyyy-MM-dd", -30, Calendar.DAY_OF_MONTH);
        String end = DateUtils.getDate("yyyy-MM-dd", 0, Calendar.DAY_OF_MONTH);
        analysisDto.setShipDateStart(start);
        analysisDto.setShipDateEnd(end);
        setT(analysisDto);
    }


}
