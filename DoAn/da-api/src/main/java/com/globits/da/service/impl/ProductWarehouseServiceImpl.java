package com.globits.da.service.impl;

import java.util.List;
import java.util.UUID;

import javax.persistence.Query;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.globits.core.service.impl.GenericServiceImpl;
import com.globits.da.domain.ProductWarehouse;
import com.globits.da.dto.ProductWarehouseDto;
import com.globits.da.dto.search.SearchDto;
import com.globits.da.service.ProductWarehouseService;
@Service
public class ProductWarehouseServiceImpl extends GenericServiceImpl<ProductWarehouse, UUID> implements ProductWarehouseService{

	@Override
	public Page<ProductWarehouseDto> searchByPage(SearchDto dto) {
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
		
		String orderBy = " ORDER BY entity.createDate DESC";
		
		String sqlCount = "select count(entity.id) from ProductWarehouse as entity where (1=1)   ";
		String sql = "select new com.globits.da.dto.ProductWarehouseDto(entity) from ProductWarehouse as entity where (1=1)  ";

		if (dto.getKeyword() != null && StringUtils.hasText(dto.getKeyword())) {
			whereClause += " AND ( entity.product.name LIKE :text entity.warehouse.name LIKE :text )";
		}
		if(dto.getKhoId() != null ) {
			whereClause += " AND ( entity.warehouse.id =: khoId ) " ;
		}
		
		sql += whereClause + orderBy;
		sqlCount += whereClause;

		Query q = manager.createQuery(sql, ProductWarehouseDto.class);
		Query qCount = manager.createQuery(sqlCount);

		if (dto.getKeyword() != null && StringUtils.hasText(dto.getKeyword())) {
			q.setParameter("text", '%' + dto.getKeyword() + '%');
			qCount.setParameter("text", '%' + dto.getKeyword() + '%');
		}
		if(dto.getKhoId() != null ) {
			q.setParameter("khoId", dto.getKhoId());
			qCount.setParameter("khoId",dto.getKhoId());
		}
		int startPosition = pageIndex * pageSize;
		q.setFirstResult(startPosition);
		q.setMaxResults(pageSize);
		List<ProductWarehouseDto> entities = q.getResultList();
		long count = (long) qCount.getSingleResult();

		Pageable pageable = PageRequest.of(pageIndex, pageSize);
		Page<ProductWarehouseDto> result = new PageImpl<ProductWarehouseDto>(entities, pageable, count);
		return result;
	}

}
