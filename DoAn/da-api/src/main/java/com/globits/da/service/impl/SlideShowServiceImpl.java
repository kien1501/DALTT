package com.globits.da.service.impl;

import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.poi.sl.usermodel.SlideShow;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.globits.da.domain.Slideshow;
import com.globits.da.dto.SlideShowDto;
import com.globits.da.dto.search.SearchDto;
import com.globits.da.repository.SlideShowRepository;
import com.globits.da.service.SlideShowService;

@Service
public class SlideShowServiceImpl implements SlideShowService {

	@Autowired
	EntityManager manager;

	public SessionFactory getSessionFactory() {
		Session session = manager.unwrap(Session.class);
		return session.getSessionFactory();
	}

//	@Autowired
//	CategoryRepository categoryRepository;
//	@Autowired
//	ProductCategoryRepository productCategoryRepository;
	@Autowired
	SlideShowRepository slideShowRepository;

	public SlideShowDto saveOne(SlideShowDto dto, UUID id) {
		if(dto != null){
			Slideshow entity = null;
			if(id != null){
				if(dto.getId() != null && !dto.getId().equals(id))
					return null;
				entity = slideShowRepository.getOne(id);
			}
			if(entity == null){
				entity = new Slideshow();
			}

			/* Set all the values */
			entity.setUrl(dto.getUrl());
			entity.setName(dto.getName());
			entity = slideShowRepository.save(entity);
			if(entity != null)
				return new SlideShowDto(entity);

		}

		return null;
	}

	public SlideShowDto getOne(UUID id) {
		Slideshow entity = slideShowRepository.getOne(id);

		if(entity != null)
			return new SlideShowDto(entity);

		return null;
	}
 
	public Page<SlideShowDto> searchByPage(SearchDto dto) {
		if(dto == null)
			return null;

		int pageIndex = dto.getPageIndex();
		int pageSize = dto.getPageSize();

		if(pageIndex > 0)
			pageIndex--;
		else
			pageIndex = 0;

		String whereClause = "";

		String orderBy = " ORDER BY entity.id DESC";
		if(dto.getOrderBy() != null && StringUtils.hasLength(dto.getOrderBy().toString()))
			orderBy = " ORDER BY entity." + dto.getOrderBy() + " ASC";

		String sqlCount = "select count(entity.id) from Slideshow as entity where (1=1)";
		String sql = "select new com.globits.da.dto.SlideShowDto(entity) from Slideshow as entity where (1=1)";

		if(dto.getKeyword() != null && StringUtils.hasText(dto.getKeyword()))
			whereClause += " AND ( UPPER(entity.name) LIKE UPPER(:text)   )";

		sql += whereClause + orderBy;
		sqlCount += whereClause;

		Query q = manager.createQuery(sql, SlideShowDto.class);
		Query qCount = manager.createQuery(sqlCount);

		if(dto.getKeyword() != null && StringUtils.hasText(dto.getKeyword())){
			q.setParameter("text", '%' + dto.getKeyword() + '%');
			qCount.setParameter("text", '%' + dto.getKeyword() + '%');
		}

		int startPosition = pageIndex * pageSize;
		q.setFirstResult(startPosition);
		q.setMaxResults(pageSize);
		List<SlideShowDto> entities = q.getResultList();
		long count = (long) qCount.getSingleResult();

		Pageable pageable = PageRequest.of(pageIndex, pageSize);
		Page<SlideShowDto> result = new PageImpl<>(entities, pageable, count);
		return result;
	}

	public void deleteById(UUID id) {
		slideShowRepository.deleteById(id);
	}

	@Override
	public SlideShowDto getCertificate(UUID id) {
		Slideshow entity =  slideShowRepository.getOne(id);
		if(entity!=null) {
			return new SlideShowDto(entity);
		}
		return null;
	}

	
}
