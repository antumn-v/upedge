package com.upedge.thirdparty.trackingmore.entity;

/**
 * Created by cjq on 2019/5/8.
 */
public class Trackinfo {

    String Date;
    String StatusDescription;
    String Details;
    String checkpoint_status;

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getStatusDescription() {
        return StatusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        StatusDescription = statusDescription;
    }

    public String getDetails() {
        return Details;
    }

    public void setDetails(String details) {
        Details = details;
    }

    public String getCheckpoint_status() {
        return checkpoint_status;
    }

    public void setCheckpoint_status(String checkpoint_status) {
        this.checkpoint_status = checkpoint_status;
    }
}
