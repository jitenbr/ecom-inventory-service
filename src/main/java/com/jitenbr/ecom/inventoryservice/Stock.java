package com.jitenbr.ecom.inventoryservice;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="stock")
public class Stock {

    @Id
    private String productid;

    @Column
    private Integer quantity;

}
