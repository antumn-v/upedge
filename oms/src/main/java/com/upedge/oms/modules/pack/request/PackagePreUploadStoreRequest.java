package com.upedge.oms.modules.pack.request;

import lombok.Data;

import javax.validation.constraints.Size;
import java.util.List;

@Data
public class PackagePreUploadStoreRequest {

    @Size(min = 1)
    private List<Long> orderIds;
}
