package com.globits.da.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.globits.da.domain.Warehouse;
import com.globits.da.dto.WarehouseDto;
@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, UUID>{
	@Query("select count(entity.id) from Warehouse entity where entity.name =?1 and (entity.id <> ?2 or ?2 is null) ")
	Long checkCode(String code, UUID id);
	@Query("select new com.globits.da.dto.WarehouseDto(ed) from Warehouse ed")
	Page<WarehouseDto> getListPage( Pageable pageable);
}
