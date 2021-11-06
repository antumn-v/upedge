package com.upedge.thirdparty.trackingmore.entity.webhook;

import java.util.Date;

/**
 * Created by cjq on 2019/5/29.
 */
public class TrackingData {

    private String id;
    private String tracking_number;
    private String carrier_code;
    private String status;
    private String created_at;
    private String updated_at;
    private String customer_email;
    private String title;
    private String order_id;
    private String customer_name;
    private String original_country;
    private String destination_country;
    private Integer itemTimeLength;
    private String service_code;
    private String substatus;
    private Date lastUpdateTime;
    private String lastEvent;
    private DataInfo origin_info;
    private DataInfo destination_info;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTracking_number() {
        return tracking_number;
    }

    public void setTracking_number(String tracking_number) {
        this.tracking_number = tracking_number;
    }

    public String getCarrier_code() {
        return carrier_code;
    }

    public void setCarrier_code(String carrier_code) {
        this.carrier_code = carrier_code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getCustomer_email() {
        return customer_email;
    }

    public void setCustomer_email(String customer_email) {
        this.customer_email = customer_email;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getOriginal_country() {
        return original_country;
    }

    public void setOriginal_country(String original_country) {
        this.original_country = original_country;
    }

    public String getDestination_country() {
        return destination_country;
    }

    public void setDestination_country(String destination_country) {
        this.destination_country = destination_country;
    }

    public Integer getItemTimeLength() {
        return itemTimeLength;
    }

    public void setItemTimeLength(Integer itemTimeLength) {
        this.itemTimeLength = itemTimeLength;
    }

    public String getService_code() {
        return service_code;
    }

    public void setService_code(String service_code) {
        this.service_code = service_code;
    }

    public String getSubstatus() {
        return substatus;
    }

    public void setSubstatus(String substatus) {
        this.substatus = substatus;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getLastEvent() {
        return lastEvent;
    }

    public void setLastEvent(String lastEvent) {
        this.lastEvent = lastEvent;
    }

    public DataInfo getOrigin_info() {
        return origin_info;
    }

    public void setOrigin_info(DataInfo origin_info) {
        this.origin_info = origin_info;
    }

    public DataInfo getDestination_info() {
        return destination_info;
    }

    public void setDestination_info(DataInfo destination_info) {
        this.destination_info = destination_info;
    }
}
