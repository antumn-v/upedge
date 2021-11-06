package com.upedge.oms.modules.reason.request;

import com.upedge.oms.modules.reason.entity.TrackAgainReason;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author author
 */
@Data
public class TrackAgainReasonUpdateRequest{

    /**
     * 
     */
    @NotBlank
    private String reason;

    @NotNull
    private Integer state;

    public TrackAgainReason toTrackAgainReason(Long id){
        TrackAgainReason trackAgainReason=new TrackAgainReason();
        trackAgainReason.setId(id);
        trackAgainReason.setReason(reason);
        trackAgainReason.setState(state);
        return trackAgainReason;
    }

}
