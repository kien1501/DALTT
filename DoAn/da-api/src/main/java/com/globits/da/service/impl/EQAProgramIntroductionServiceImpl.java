package com.globits.da.service.impl;

import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
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
import com.globits.da.domain.EQAProgramIntroduction;
import com.globits.da.dto.EQAProgramIntroductionDto;
import com.globits.da.dto.search.SearchDto;
import com.globits.da.repository.EQAProgramIntroductionRepository;
import com.globits.da.repository.ProductCategoryRepository;
import com.globits.da.service.EQAProgramIntroductionService;

@Transactional
@Service
public class EQAProgramIntroductionServiceImpl extends GenericServiceImpl<EQAProgramIntroduction, UUID> implements EQAProgramIntroductionService{
	@Autowired
	EQAProgramIntroductionRepository repos;
	@Autowired
	private EntityManager manager;
	
	@Autowired
	private EQAProgramIntroductionRepository eQAProgramIntroductionRepository;
	
	@Override
	public Page<EQAProgramIntroductionDto> searchByDto(SearchDto dto){
		if(dto == null) {
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
		
		String sqlCount = "select count(eqap.id) from EQAProgramIntroduction as eqap where (1=1) ";
		String sql = "select new com.globits.da.dto.EQAProgramIntroductionDto(eqap) from EQAProgramIntroduction as eqap where (1=1) ";

		if (dto.getText() != null && StringUtils.hasText(dto.getText())) {
			whereClause += " AND ( eqap.name LIKE :text ) ";
		}
		sql += whereClause;
		sqlCount += whereClause;
		Query q = manager.createQuery(sql, EQAProgramIntroductionDto.class);
		Query qCount = manager.createQuery(sqlCount);

		if (dto.getText() != null && StringUtils.hasText(dto.getText())) {
			q.setParameter("text", '%' + dto.getText() + '%');
			qCount.setParameter("text", '%' + dto.getText() + '%');
		}

		int startPosition = pageIndex * pageSize;
		q.setFirstResult(startPosition);
		q.setMaxResults(pageSize);
		List<EQAProgramIntroductionDto> entities = q.getResultList();
		long count = (long) qCount.getSingleResult();
		
		Pageable pageable = PageRequest.of(pageIndex, pageSize);
		Page<EQAProgramIntroductionDto> result = new PageImpl<EQAProgramIntroductionDto>(entities, pageable, count);
		return result;
	}
	
	@Override
	public EQAProgramIntroductionDto getById(UUID id) {
		if (id != null) {
			EQAProgramIntroduction entity = eQAProgramIntroductionRepository.getOne(id);
			if (entity != null) {
				return new EQAProgramIntroductionDto(entity);
			}
		}
		return null;
	}
	
	@Override
	public EQAProgramIntroductionDto getByActive() {
		
		List<EQAProgramIntroduction> list = eQAProgramIntroductionRepository.findAll();
		if(list != null && list.size() > 0) {
			for (EQAProgramIntroduction item : list) {
				if(item.getActive() == true) {
					return new EQAProgramIntroductionDto(item);
				}
			}
		}
		return null;
	}
	
	@Override
	public Boolean checkDuplicateCode(UUID id, String code) {
		if (code != null && StringUtils.hasText(code)) {
			EQAProgramIntroduction entity = eQAProgramIntroductionRepository.getByCode(code);
			if (entity != null) {
				if (id != null && entity.getId().equals(id)) {
					return false;
				}
				return true;
			}
			return false;
		}
		return null;
	}
	
	@Override
	public Boolean deleteById(UUID id) {
		if (id != null) {
			EQAProgramIntroduction entity = eQAProgramIntroductionRepository.getOne(id);
			if (entity != null) {
				eQAProgramIntroductionRepository.deleteById(id);
				return true;
			}
		}
		return null;				
	}
	
	@Override
	public EQAProgramIntroductionDto saveOrUpdate(EQAProgramIntroductionDto dto, UUID id) {
		if(dto!=null) {
			EQAProgramIntroduction entity = null;
			
			if(id != null) {
				entity = eQAProgramIntroductionRepository.getOne(id);
			}else {
				entity = new EQAProgramIntroduction();
			}
			entity.setName(dto.getName());
			entity.setCode(dto.getCode());
			entity.setContent(dto.getContent());
			if(dto.getActive() != null && dto.getActive() ==  true) {
				List<EQAProgramIntroduction> list = eQAProgramIntroductionRepository.findAll();
				for(EQAProgramIntroduction item : list) {
					item.setActive(false);
					item = eQAProgramIntroductionRepository.save(item);
				}
			}
			entity.setActive(dto.getActive());
			
			entity = eQAProgramIntroductionRepository.save(entity);
			if(entity != null) {
				return new EQAProgramIntroductionDto(entity);
			}
		}
		return null;
	}

	@Override
	public List<EQAProgramIntroductionDto> getAllNews() {
		return repos.getAllNews() ;
	}
	
}
