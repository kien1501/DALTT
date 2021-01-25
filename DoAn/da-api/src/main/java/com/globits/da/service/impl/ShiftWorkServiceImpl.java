package com.globits.da.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.globits.core.service.impl.GenericServiceImpl;
import com.globits.da.domain.ShiftWork;
import com.globits.da.domain.ShiftWorkTimePeriod;
import com.globits.da.dto.SearchShiftWorkDto;
import com.globits.da.dto.ShiftWorkDto;
import com.globits.da.dto.ShiftWorkTimePeriodDto;
import com.globits.da.dto.search.SearchDto;
import com.globits.da.repository.ShiftWorkRepository;
import com.globits.da.repository.ShiftWorkTimePeriodRepository;
import com.globits.da.service.ShiftWorkService;

@Transactional
@Service
public class ShiftWorkServiceImpl extends GenericServiceImpl<ShiftWork, UUID> implements ShiftWorkService {
	@Autowired
	ShiftWorkRepository shiftWorkRepository;
	@Autowired
	ShiftWorkTimePeriodRepository shiftWorkTimePeriodRepository;

	@Override
	public Page<ShiftWorkDto> getPage(int pageSize, int pageIndex) {
		Pageable pageable = PageRequest.of(pageIndex - 1, pageSize);
		return shiftWorkRepository.getListPage(pageable);
	}

	@Override
	public ShiftWorkDto saveShiftWork(ShiftWorkDto dto) {
		ShiftWork shiftWork = null;
		if (dto != null) {
			if (dto.getId() != null)
				shiftWork = this.findById(dto.getId());

			if (shiftWork == null) {// Nếu không tìm thấy thì tạo mới 1 đối tượng
				shiftWork = new ShiftWork();
			}

			shiftWork.setCode(dto.getCode());
			shiftWork.setName(dto.getName());
			shiftWork.setTotalHours(dto.getTotalHours());
			List<ShiftWorkTimePeriod> tps = new ArrayList<>();

			if (dto.getTimePeriods() != null) {
				for (ShiftWorkTimePeriodDto swtDto : dto.getTimePeriods()) {
					ShiftWorkTimePeriod swt = null;

					if (swtDto.getId() != null)
						swt = shiftWorkTimePeriodRepository.getOne(swtDto.getId());

					if (swt == null) {
						swt = new ShiftWorkTimePeriod();
						swt.setShiftWork(shiftWork);
					}

					swt.setStartTime(swtDto.getStartTime());
					swt.setEndTime(swtDto.getEndTime());

					tps.add(swt);
				}
			}

			shiftWork.getTimePeriods().clear();
			shiftWork.getTimePeriods().addAll(tps);

			shiftWork = shiftWorkRepository.save(shiftWork);

			return new ShiftWorkDto(shiftWork);
		}
		return null;
	}

	@Override
	public Boolean deleteShiftWork(UUID id) {
		ShiftWork shiftWork = this.findById(id);
		if (shiftWork != null) {
			shiftWorkRepository.deleteById(id);
			return true;
		}
		return false;
	}

	@Override
	public ShiftWorkDto getShiftWork(UUID id) {
		ShiftWork shiftWork = this.findById(id);
		if (shiftWork != null) {
			return new ShiftWorkDto(shiftWork);
		}
		return null;
	}

	@Override
	public int deleteShiftWorks(List<ShiftWorkDto> dtos) {
		if (dtos == null) {
			return 0;
		}
		int ret = 0;
		for (ShiftWorkDto dto : dtos) {
			if (dto.getId() != null) {
				shiftWorkRepository.deleteById(dto.getId());
			}
			ret++;
		}
		return ret;
	}

	@Override
	public Page<ShiftWorkDto> searchShiftWork(SearchShiftWorkDto dto, int pageIndex, int pageSize) {
		String name = '%' + dto.getName() + '%';
		String code = '%' + dto.getCode() + '%';
		Pageable pageable = PageRequest.of(pageIndex - 1, pageSize);
		return shiftWorkRepository.searchByPage(name, code, pageable);
	}
	@Override
	public Page<ShiftWorkDto> searchByPage(SearchDto dto) {
		if (dto == null) {
			return null;
		}
		
		int pageIndex = dto.getPageIndex();
		int pageSize = dto.getPageSize();
		
		if (pageIndex > 0) {
			pageIndex--;
		} else {
			pageIndex = 0;
		}

		String whereClause = "";
		String orderBy = " ORDER BY entity.createDate ";
		
		String sqlCount = "select count(entity.id) from ShiftWork as entity where (1=1) ";
		String sql = "select new  com.globits.da.dto.ShiftWorkDto(entity) from ShiftWork as entity where (1=1) ";

		if (dto.getKeyword() != null && StringUtils.hasText(dto.getKeyword())) {
			whereClause += " AND ( entity.name LIKE :text OR entity.code LIKE :text OR entity.totalHours LIKE :text) ";
		}
		
		
		sql+=whereClause + orderBy;
		sqlCount+=whereClause;

		Query q = manager.createQuery(sql, ShiftWorkDto.class);
		Query qCount = manager.createQuery(sqlCount);

		if (dto.getKeyword() != null && StringUtils.hasText(dto.getKeyword())) {
			q.setParameter("text", '%' + dto.getKeyword() + '%');
			qCount.setParameter("text", '%' + dto.getKeyword() + '%');
		}
		

		int startPosition = pageIndex * pageSize;
		q.setFirstResult(startPosition);
		q.setMaxResults(pageSize);
		List<ShiftWorkDto> entities = q.getResultList();
		long count = (long) qCount.getSingleResult();
		
		Pageable pageable = PageRequest.of(pageIndex, pageSize);
		Page<ShiftWorkDto> result = new PageImpl<ShiftWorkDto>(entities, pageable, count);
		return result;
	}

}
