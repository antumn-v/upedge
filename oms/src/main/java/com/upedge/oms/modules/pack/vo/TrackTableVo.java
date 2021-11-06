package com.upedge.oms.modules.pack.vo;

/**
 * Created by cjq on 2019/6/5.
 * 物流方式分析报表
 */
public class TrackTableVo {

    Integer transportId;

    //运输方式名称
    String transportName;
    //APP shipping method
    String shippingMethod;
    //平均上网时效
    String avgOnlineD;
    Long avgOnline;
    //平均签收时效
    String avgSignedD;
    Long avgSigned;
    //平均总时效
    String avgD;
    Long avg;
    //包裹总数
    Integer packCount=0;
    //查询中
    Integer pendingCount=0;
    //查询不到
    Integer notfoundCount=0;
    //运输途中
    Integer transitCount=0;
    //到达待取
    Integer pickupCount=0;
    //成功签收
    Integer deliveredCount=0;
    //投递失败
    Integer undeliveredCount=0;
    //可能异常
    Integer exceptionCount=0;
    //运输过久
    Integer expiredCount=0;

    public Integer getTransportId() {
        return transportId;
    }

    public void setTransportId(Integer transportId) {
        this.transportId = transportId;
    }

    public String getTransportName() {
        return transportName;
    }

    public void setTransportName(String transportName) {
        this.transportName = transportName;
    }

    public String getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    public String getAvgOnlineD() {
        return avgOnlineD;
    }

    public void setAvgOnlineD(String avgOnlineD) {
        this.avgOnlineD = avgOnlineD;
    }

    public String getAvgSignedD() {
        return avgSignedD;
    }

    public void setAvgSignedD(String avgSignedD) {
        this.avgSignedD = avgSignedD;
    }

    public String getAvgD() {
        return avgD;
    }

    public void setAvgD(String avgD) {
        this.avgD = avgD;
    }

    public Integer getPackCount() {
        return packCount;
    }

    public void setPackCount(Integer packCount) {
        this.packCount = packCount;
    }

    public Integer getPendingCount() {
        return pendingCount;
    }

    public void setPendingCount(Integer pendingCount) {
        this.pendingCount = pendingCount;
    }

    public Integer getNotfoundCount() {
        return notfoundCount;
    }

    public void setNotfoundCount(Integer notfoundCount) {
        this.notfoundCount = notfoundCount;
    }

    public Integer getTransitCount() {
        return transitCount;
    }

    public void setTransitCount(Integer transitCount) {
        this.transitCount = transitCount;
    }

    public Integer getPickupCount() {
        return pickupCount;
    }

    public void setPickupCount(Integer pickupCount) {
        this.pickupCount = pickupCount;
    }

    public Integer getDeliveredCount() {
        return deliveredCount;
    }

    public void setDeliveredCount(Integer deliveredCount) {
        this.deliveredCount = deliveredCount;
    }

    public Integer getUndeliveredCount() {
        return undeliveredCount;
    }

    public void setUndeliveredCount(Integer undeliveredCount) {
        this.undeliveredCount = undeliveredCount;
    }

    public Integer getExceptionCount() {
        return exceptionCount;
    }

    public void setExceptionCount(Integer exceptionCount) {
        this.exceptionCount = exceptionCount;
    }

    public Integer getExpiredCount() {
        return expiredCount;
    }

    public void setExpiredCount(Integer expiredCount) {
        this.expiredCount = expiredCount;
    }

    public Long getAvgOnline() {
        return avgOnline;
    }

    public void setAvgOnline(Long avgOnline) {
        this.avgOnline = avgOnline;
    }

    public long getAvgSigned() {
        return avgSigned;
    }

    public void setAvgSigned(long avgSigned) {
        this.avgSigned = avgSigned;
    }

    public Long getAvg() {
        return avg;
    }

    public void setAvg(Long avg) {
        this.avg = avg;
    }
}
