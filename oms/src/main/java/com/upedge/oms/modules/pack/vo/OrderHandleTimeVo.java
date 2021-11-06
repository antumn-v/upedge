package com.upedge.oms.modules.pack.vo;

import lombok.Data;

import java.util.List;

@Data
public class OrderHandleTimeVo {

    List<DailyOrderHandleVo> dailyOrderHandleVos;

    private long avgOrderHandleSecond;

    private String avgOrderHandleDay;

    public void setAvgHandleTime(long totalHandleTime, long orderCount) {
        if (orderCount != 0) {
            long  avgDurationD = 0, avgDurationH = 0, avgDurationM = 0, avgDurationS = 0;
            this.avgOrderHandleSecond = totalHandleTime / orderCount;
            long avgD = this.avgOrderHandleSecond;
            //天
            avgDurationD = totalHandleTime / 24 / 3600;
            avgD = avgD - avgDurationD * 3600 * 24;
            //小时
            avgDurationH = avgD / 3600;
            avgD = avgD - avgDurationH * 3600;
            //分
            avgDurationM = avgD / 60;
            avgD = avgD - avgDurationM * 60;
            //秒
            avgDurationS = avgD;

            StringBuffer stringBuffer = new StringBuffer();
            this.avgOrderHandleDay = stringBuffer.append(avgDurationD).append("天")
                    .append(avgDurationH).append("时")
                    .append(avgDurationM).append("分")
                    .append(avgDurationS).append("秒").toString();
        }
    }
}
