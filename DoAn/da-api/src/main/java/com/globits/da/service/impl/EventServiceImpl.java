package com.globits.da.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.globits.core.service.impl.GenericServiceImpl;
import com.globits.da.domain.Event;
import com.globits.da.domain.Product;
import com.globits.da.domain.ProductEvent;
import com.globits.da.dto.EventDto;
import com.globits.da.dto.ProductEventDto;
import com.globits.da.dto.search.SearchDto;
import com.globits.da.repository.EventRepository;
import com.globits.da.repository.ProductEventRepository;
import com.globits.da.repository.ProductRepository;
import com.globits.da.service.EventService;

@Service
public class EventServiceImpl extends GenericServiceImpl< Event, UUID> implements  EventService{
	@Autowired
	EventRepository repos;
	@Autowired
	ProductEventRepository productEventRepository;
	@Autowired
	private ProductRepository sanPhamRepository;
	@Override
	public Page<EventDto> getPage(int pageSize, int pageIndex) {
		Pageable pageable = PageRequest.of(pageIndex - 1, pageSize);
		return repos.getListPage(pageable);
	}

	@Override
	public EventDto saveOrUpdate(UUID id, EventDto dto) {
		if (dto != null) {
			Event entity = null;
			if (dto.getId() != null) {
				if (dto.getId() != null && !dto.getId().equals(id)) {
					return null;
				}
				entity = repos.getOne(dto.getId());
			}
			if (entity == null) {
				entity = new Event();
			}
			Date date  = new Date();
			if(dto.getIsActivate() != null && dto.getIsActivate() && !dto.getStartDate().before(date) || !dto.getEndDate().after(date)) {
				return null;
			}
			entity.setName(dto.getName());
			entity.setCode(dto.getCode());
			entity.setDescription(dto.getDescription());
			entity.setStartDate(dto.getStartDate());
			entity.setEndDate(dto.getEndDate());
			entity.setIsActivate(dto.getIsActivate());

			if (dto.getProductEvent() != null && dto.getProductEvent().size() > 0) {
				Set<ProductEvent> listSanPhamDonHang = new HashSet<ProductEvent>();
				for (ProductEventDto sanPhamDonHangDto : dto.getProductEvent()) {
					ProductEvent sanPhamDonHang = null;
					if (sanPhamDonHangDto.getId() != null) {
						sanPhamDonHang = productEventRepository.getOne(sanPhamDonHangDto.getId());
					}

					if (sanPhamDonHang == null) {
						sanPhamDonHang = new ProductEvent();
					}
					
					Product sanPham = null;
					if(sanPhamDonHangDto.getProduct() != null && sanPhamDonHangDto.getProduct().getId() != null) {
						sanPham = sanPhamRepository.getOne(sanPhamDonHangDto.getProduct().getId());
						if(sanPham == null) {
							return null;
						}
					}
					sanPhamDonHang.setProduct(sanPham);
					sanPhamDonHang.setDiscountPercent(sanPhamDonHangDto.getDiscountPercent());
					
					if(dto.getIsActivate() != null &&  dto.getIsActivate()) {
						if(sanPham.getPrice() > (sanPhamDonHang.getDiscountPercent()/100)*sanPham.getPrice()) {
							sanPham.setCurrentSellingPrice(sanPham.getPrice() - (sanPhamDonHang.getDiscountPercent()/100)*sanPham.getPrice());
						}else {
							sanPham.setCurrentSellingPrice(0.0);
						}
					}else {
						sanPham.setCurrentSellingPrice(sanPham.getPrice());
					}
					sanPham = sanPhamRepository.save(sanPham);
					sanPhamDonHang.setEvent(entity);
					listSanPhamDonHang.add(sanPhamDonHang);
				}

				if (entity.getProductEvent() == null) {
					entity.setProductEvent(listSanPhamDonHang);
				} else {
					entity.getProductEvent().clear();
					entity.getProductEvent().addAll(listSanPhamDonHang);
				}
			}

			entity = repos.save(entity);
			if (entity != null) {
				return new EventDto(entity);
			}
		}
		return null;
	}

	@Override
	public Boolean deleteEvent(UUID id) {
		if (id != null) {
			repos.deleteById(id);
			return true;
		}
		return false;
	}

	@Override
	public EventDto getCertificate(UUID id) {
		Event entity = repos.getOne(id);
		if (entity != null) {
			return new EventDto(entity);
		}
		return null;
	}

	@Override
	public Page<EventDto> searchByPage(SearchDto dto) {
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

		String sqlCount = "select count(entity.id) from Event as entity where (1=1)   ";
		String sql = "select new com.globits.da.dto.EventDto(entity) from Event as entity where (1=1)  ";

		if (dto.getKeyword() != null && StringUtils.hasText(dto.getKeyword())) {
			whereClause += " AND ( entity.name LIKE :text OR entity.code LIKE :text )";
		}

		sql += whereClause + orderBy;
		sqlCount += whereClause;

		Query q = manager.createQuery(sql, EventDto.class);
		Query qCount = manager.createQuery(sqlCount);

		if (dto.getKeyword() != null && StringUtils.hasText(dto.getKeyword())) {
			q.setParameter("text", '%' + dto.getKeyword() + '%');
			qCount.setParameter("text", '%' + dto.getKeyword() + '%');
		}
		int startPosition = pageIndex * pageSize;
		q.setFirstResult(startPosition);
		q.setMaxResults(pageSize);
		List<EventDto> entities = q.getResultList();
		long count = (long) qCount.getSingleResult();

		Pageable pageable = PageRequest.of(pageIndex, pageSize);
		Page<EventDto> result = new PageImpl<EventDto>(entities, pageable, count);
		return result;
	}

	@Override
	public Boolean checkCode(UUID id, String code) {
		if (code != null && StringUtils.hasText(code)) {
			Long count = repos.checkCode(code, id);
			return count != 0l;
		}
		return null;
	}

	@Override
	public Boolean deleteCheckById(UUID id) {
		// TODO Auto-generated method stub
		return null;
	}

}
