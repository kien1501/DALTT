package com.globits.da.repository;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.globits.da.domain.EQAProgramIntroduction;
import com.globits.da.dto.EQAProgramIntroductionDto;
import com.globits.da.dto.ProductCategoryDto;

@Repository
public interface EQAProgramIntroductionRepository extends JpaRepository<EQAProgramIntroduction, UUID>{
	@Query("select entity FROM EQAProgramIntroduction entity where entity.code =?1 ")
	EQAProgramIntroduction getByCode(String code);
	
	@Query("select new com.globits.da.dto.EQAProgramIntroductionDto(ed) from EQAProgramIntroduction ed")
	List<EQAProgramIntroductionDto> getAllNews();
}
