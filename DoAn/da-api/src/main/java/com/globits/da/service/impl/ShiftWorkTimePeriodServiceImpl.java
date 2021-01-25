package com.globits.da.service.impl;

import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.globits.core.service.impl.GenericServiceImpl;
import com.globits.da.domain.ShiftWork;
import com.globits.da.domain.ShiftWorkTimePeriod;
import com.globits.da.dto.ShiftWorkTimePeriodDto;
import com.globits.da.repository.ShiftWorkRepository;
import com.globits.da.repository.ShiftWorkTimePeriodRepository;
import com.globits.da.service.ShiftWorkTimePeriodService;
import com.globits.security.domain.User;



@Transactional
@Service
public class ShiftWorkTimePeriodServiceImpl extends GenericServiceImpl<ShiftWorkTimePeriod, UUID> implements ShiftWorkTimePeriodService {
	@Autowired
	private ShiftWorkTimePeriodRepository shiftWorkTimePeriodRepository;
	@Autowired
	private EntityManager manager;
	@Autowired
	private ShiftWorkRepository shiftWorkRepository;
	
	@Override
	public ShiftWorkTimePeriodDto saveShiftWorkTimePeriod(ShiftWorkTimePeriodDto dto) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User modifiedUser = null;
		LocalDateTime currentDate = LocalDateTime.now();
		String currentUserName = "Unknown User";
		if (authentication != null) {
			modifiedUser = (User) authentication.getPrincipal();
			currentUserName = modifiedUser.getUsername();
		}

		ShiftWorkTimePeriod time = null;
		if (dto.getId() != null) {
			time = this.findById(dto.getId());
		}
		if (time == null) {
			time = new ShiftWorkTimePeriod();
			time.setCreateDate(currentDate);
			time.setCreatedBy(currentUserName);
		}
		time.setModifiedBy(currentUserName);
		time.setModifyDate(currentDate);
		if (dto.getStartTime() != null) {
			time.setStartTime(dto.getStartTime());
		}
		if (dto.getEndTime() != null) {
			time.setEndTime(dto.getEndTime());;
		}
		 if(dto.getShiftWork()!=null) {
			   ShiftWork shiftwork = shiftWorkRepository.getOne(dto.getShiftWork().getId());
			   time.setShiftWork(shiftwork);;
		   }
		return new ShiftWorkTimePeriodDto(time);
	}

	@Override
	public ShiftWorkTimePeriodDto getShiftWorkTimePeriod(UUID id) {
		ShiftWorkTimePeriod entity = this.findById(id);

		if (entity == null) {
			return null;
		} else {
			ShiftWorkTimePeriodDto dto = new ShiftWorkTimePeriodDto(entity);
			return dto;
		}
	}

	@Override
	public Page<ShiftWorkTimePeriodDto> getPage(int pageSize, int pageIndex) {
		Pageable pageable = PageRequest.of(pageIndex-1, pageSize);
		return shiftWorkTimePeriodRepository.getListPage(pageable);
	}

	@Override
	public Boolean deleteShiftWorkTimePeriod(UUID id) {
		ShiftWorkTimePeriod time = this.findById(id);
		if (time != null) {
			shiftWorkTimePeriodRepository.delete(time);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int deleteShiftWorkTimePeriods(List<ShiftWorkTimePeriodDto> dtos) {
		if(dtos==null) {
			return 0;
		}
		int ret = 0;
		for(ShiftWorkTimePeriodDto dto:dtos) {
			if(dto.getId()!=null) {
				shiftWorkTimePeriodRepository.deleteById(dto.getId());
			}
			ret++;
		}
		return ret;
	}
	
	@Override
	public List<ShiftWorkTimePeriodDto> getAllByShiftWorkId(UUID shiftworkId) {
		String sqlCount ="select count(sw) FROM ShiftWorkTimePeriod as sw WHERE sw.shiftwork.id=:shiftworkId";
		String sql ="select new com.globits.da.dto.ShiftWorkTimePeriodDto(sw) FROM ShiftWorkTimePeriod as sw WHERE sw.shiftwork.id=:shiftworkId";
		
		Query q = manager.createQuery(sql,ShiftWorkTimePeriodDto.class);
		Query qCount = manager.createQuery(sqlCount);
		q.setParameter("shiftworkId", shiftworkId);
		qCount.setParameter("shiftworkId", shiftworkId);
		List<ShiftWorkTimePeriodDto> entities = q.getResultList();
		
		return entities;
	}
	
	


}
