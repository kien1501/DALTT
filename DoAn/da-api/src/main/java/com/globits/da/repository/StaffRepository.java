package com.globits.da.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.globits.da.domain.Staff;
import com.globits.da.dto.StaffDto;

@Repository
public interface StaffRepository extends JpaRepository<Staff, UUID>{
	@Query("select count(entity.id) from Staff entity where entity.code =?1 and (entity.id <> ?2 or ?2 is null) ")
	Long checkCode(String code, UUID id);
	@Query("select new com.globits.da.dto.StaffDto(ed) from Staff ed")
	Page<StaffDto> getListPage( Pageable pageable);
	@Query("select entity from Staff entity where entity.id =?1")
	Staff getStaffById(UUID id);
	
}
