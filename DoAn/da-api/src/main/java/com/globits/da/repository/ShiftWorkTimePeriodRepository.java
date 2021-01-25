package com.globits.da.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.globits.da.domain.ShiftWorkTimePeriod;
import com.globits.da.dto.ShiftWorkTimePeriodDto;

@Repository
public interface ShiftWorkTimePeriodRepository extends JpaRepository<ShiftWorkTimePeriod, UUID>{
	@Query("select new com.globits.da.dto.ShiftWorkTimePeriodDto(time) from ShiftWorkTimePeriod time")
	Page<ShiftWorkTimePeriodDto> getListPage( Pageable pageable);
	
	
}

