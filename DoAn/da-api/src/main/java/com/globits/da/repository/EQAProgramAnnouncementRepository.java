package com.globits.da.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.globits.da.domain.EQAProgramAnnouncement;

@Repository
public interface EQAProgramAnnouncementRepository extends JpaRepository<EQAProgramAnnouncement, UUID>{
	
	@Query("select entity FROM EQAProgramAnnouncement entity where entity.code =?1 ")
	EQAProgramAnnouncement getByCode(String code);
}
