package com.jitenbr.ecom.inventoryservice;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock,String> {

}
