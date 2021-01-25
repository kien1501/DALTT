package com.globits.da.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.globits.da.domain.InventoryReceivingVoucher;
import com.globits.da.dto.InventoryReceivingVoucherDto;
@Repository
public interface InventoryReceivingVoucherRepository extends JpaRepository<InventoryReceivingVoucher, UUID>{
	@Query("select count(entity.id) from InventoryReceivingVoucher entity where entity.code =?1 and (entity.id <> ?2 or ?2 is null) ")
	Long checkCode(String code, UUID id);
	@Query("select new com.globits.da.dto.InventoryReceivingVoucherDto(ed) from InventoryReceivingVoucher ed")
	Page<InventoryReceivingVoucherDto> getListPage( Pageable pageable);
}
