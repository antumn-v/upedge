package com.upedge.pms.modules.product.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author 海桐
 */
@Data
public class UploadProductToStoreRequest {

    Long storeId;

    @NotNull
    List<Long> productIds;

    boolean importAll = false;
}
