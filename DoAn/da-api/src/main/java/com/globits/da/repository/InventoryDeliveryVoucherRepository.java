package com.globits.da.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.globits.da.domain.InventoryDeliveryVoucher;
import com.globits.da.dto.InventoryDeliveryVoucherDto;
@Repository
public interface InventoryDeliveryVoucherRepository extends JpaRepository<InventoryDeliveryVoucher, UUID>{
	@Query("select count(entity.id) from InventoryDeliveryVoucher entity where entity.code =?1 and (entity.id <> ?2 or ?2 is null) ")
	Long checkCode(String code, UUID id);
	@Query("select new com.globits.da.dto.InventoryDeliveryVoucherDto(ed) from InventoryDeliveryVoucher ed")
	Page<InventoryDeliveryVoucherDto> getListPage( Pageable pageable);
}
