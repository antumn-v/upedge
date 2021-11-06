package com.upedge.thirdparty.trackingmore.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by cjq on 2019/5/8.
 */
public class GetTracking {

    String id;
    String tracking_number;
    String carrier_code;
    String status;
    String created_at;
    Date updated_at;
    String order_create_time;
    List<String> customer_email=new ArrayList<>();
    String title;
    String order_id;
    String comment;
    String customer_name;
    boolean archived;
    String original_country;
    String destination_country;
    Integer itemTimeLength;
    Integer stayTimeLength;
    OriginInfo originInfo;
    String service_code;
    String lastEvent;
    Date lastUpdateTime;

    public String getService_code() {
        return service_code;
    }

    public void setService_code(String service_code) {
        this.service_code = service_code;
    }

    public String getLastEvent() {
        return lastEvent;
    }

    public void setLastEvent(String lastEvent) {
        this.lastEvent = lastEvent;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

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

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    public String getOrder_create_time() {
        return order_create_time;
    }

    public void setOrder_create_time(String order_create_time) {
        this.order_create_time = order_create_time;
    }

    public List<String> getCustomer_email() {
        return customer_email;
    }

    public void setCustomer_email(List<String> customer_email) {
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
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

    public Integer getStayTimeLength() {
        return stayTimeLength;
    }

    public void setStayTimeLength(Integer stayTimeLength) {
        this.stayTimeLength = stayTimeLength;
    }

    public OriginInfo getOriginInfo() {
        return originInfo;
    }

    public void setOriginInfo(OriginInfo originInfo) {
        this.originInfo = originInfo;
    }
}
