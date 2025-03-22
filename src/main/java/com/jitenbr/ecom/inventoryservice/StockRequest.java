package com.jitenbr.ecom.inventoryservice;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockRequest {

    private String productid;

    private Integer quantity;

}
