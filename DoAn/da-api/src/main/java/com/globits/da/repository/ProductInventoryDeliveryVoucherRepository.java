package com.globits.da.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.globits.da.domain.ProductInventoryDeliveryVoucher;
@Repository
public interface ProductInventoryDeliveryVoucherRepository extends JpaRepository<ProductInventoryDeliveryVoucher, UUID>{

}
