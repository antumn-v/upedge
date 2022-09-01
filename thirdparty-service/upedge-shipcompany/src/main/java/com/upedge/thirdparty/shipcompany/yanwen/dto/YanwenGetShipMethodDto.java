package com.upedge.thirdparty.shipcompany.yanwen.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.upedge.thirdparty.shipcompany.yanwen.vo.YanwenShipMethodVo;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class YanwenGetShipMethodDto {


    @JSONField(name = "GetChannelCollectionResponseType")
    private GetChannelCollectionResponseTypeDTO getChannelCollectionResponseType;

    @NoArgsConstructor
    @Data
    public static class GetChannelCollectionResponseTypeDTO {
        @JSONField(name = "ChannelCollection")
        private ChannelCollectionDTO channelCollection;
        @JSONField(name = "CallSuccess")
        private String callSuccess;

        @NoArgsConstructor
        @Data
        public static class ChannelCollectionDTO {
            @JSONField(name = "ChannelType")
            private List<YanwenShipMethodVo> yanwenShipMethodVos;

        }
    }
}
