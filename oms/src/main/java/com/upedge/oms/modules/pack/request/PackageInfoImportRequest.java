package com.upedge.oms.modules.pack.request;

import com.upedge.oms.modules.pack.dto.PackageInfoImportDto;
import lombok.Data;

import javax.validation.constraints.Size;
import java.util.List;

@Data
public class PackageInfoImportRequest {

    @Size(min = 1)
    private List<PackageInfoImportDto> packageInfoImportDtos;
}
