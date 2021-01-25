package com.globits.da.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.globits.da.domain.ShiftWork;
import com.globits.da.domain.StaffWorkSchedule;
import com.globits.da.dto.ShiftWorkDto;

@Repository
public interface StaffWorkScheduleRepository extends JpaRepository<StaffWorkSchedule, UUID>{
//	@Query("select new com.globits.da.dto.StaffWorkScheduleDto(shiff) from StaffWorkSchedule shiff")
//	Page<ShiftWorkDto> getListPage( Pageable pageable);
//	
//	@Query("select new com.globits.da.dto.StaffWorkScheduleDto(sw) from StaffWorkSchedule sw where sw.name like ?1 or sw.code like ?2")
//	Page<ShiftWorkDto> searchByPage(String name, String code, Pageable pageable);
}

