package com.globits.da.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.globits.da.domain.StockKeepingUnit;
import com.globits.da.dto.StockKeepingUnitDto;

@Repository
public interface StockKeepingUnitRepository extends JpaRepository<StockKeepingUnit, UUID>{
	@Query("select count(entity.id) from StockKeepingUnit entity where entity.code =?1 and (entity.id <> ?2 or ?2 is null) ")
	Long checkCode(String code, UUID id);
	@Query("select new com.globits.da.dto.StockKeepingUnitDto(ed) from StockKeepingUnit ed")
	Page<StockKeepingUnitDto> getListPage( Pageable pageable);
}
