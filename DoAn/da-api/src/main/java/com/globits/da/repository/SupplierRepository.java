package com.globits.da.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.globits.da.domain.Supplier;
import com.globits.da.dto.SupplierDto;
@Repository
public interface SupplierRepository extends JpaRepository<Supplier, UUID>{
	@Query("select count(entity.id) from Supplier entity where entity.name =?1 and (entity.id <> ?2 or ?2 is null) ")
	Long checkCode(String code, UUID id);
	@Query("select new com.globits.da.dto.SupplierDto(ed) from Supplier ed")
	Page<SupplierDto> getListPage( Pageable pageable);
}
