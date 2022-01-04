package com.upedge.pms.modules.product.request;

import lombok.Data;

import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author 海桐
 */
@Data
public class UploadProductToStoreRequest {

    Long storeId;

    @Size(min = 1)
    List<Long> productIds;

    boolean importAll = false;
}
