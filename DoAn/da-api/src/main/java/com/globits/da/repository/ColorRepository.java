package com.globits.da.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.globits.da.domain.Color;
import com.globits.da.domain.Supplier;
import com.globits.da.dto.ColorDto;
import com.globits.da.dto.SupplierDto;
@Repository
public interface ColorRepository extends JpaRepository<Color, UUID>{
	@Query("select count(entity.id) from Color entity where entity.name =?1 and (entity.id <> ?2 or ?2 is null) ")
	Long checkCode(String code, UUID id);
	@Query("select new com.globits.da.dto.ColorDto(ed) from Color ed")
	Page<ColorDto> getListPage( Pageable pageable);
}
