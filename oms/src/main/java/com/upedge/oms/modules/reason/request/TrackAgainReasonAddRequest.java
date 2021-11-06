package com.upedge.oms.modules.reason.request;

import com.upedge.oms.modules.reason.entity.TrackAgainReason;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * @author author
 */
@Data
public class TrackAgainReasonAddRequest{

    /**
    * 
    */
    @NotBlank
    private String reason;

    public TrackAgainReason toTrackAgainReason(){
        TrackAgainReason trackAgainReason=new TrackAgainReason();
        trackAgainReason.setReason(reason);
        trackAgainReason.setState(1);
        trackAgainReason.setCreateTime(new Date());
        trackAgainReason.setUpdateTime(new Date());
        return trackAgainReason;
    }

}
