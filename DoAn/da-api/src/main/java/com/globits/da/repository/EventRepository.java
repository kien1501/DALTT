package com.globits.da.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.globits.da.domain.Event;
import com.globits.da.dto.EventDto;
@Repository
public interface EventRepository extends JpaRepository<Event, UUID>{
	@Query("select count(entity.id) from Event entity where entity.code =?1 and (entity.id <> ?2 or ?2 is null) ")
	Long checkCode(String code, UUID id);
	@Query("select new com.globits.da.dto.EventDto(ed) from Event ed")
	Page<EventDto> getListPage( Pageable pageable);
}
