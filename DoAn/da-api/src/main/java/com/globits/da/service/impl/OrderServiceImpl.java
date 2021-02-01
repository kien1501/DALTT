package com.globits.da.service.impl;

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
import com.globits.da.domain.Order;
import com.globits.da.domain.Product;
import com.globits.da.domain.ProductColor;
import com.globits.da.domain.ProductOrder;
import com.globits.da.domain.Staff;
import com.globits.da.domain.StockKeepingUnit;
import com.globits.da.dto.OrderDto;
import com.globits.da.dto.ProductOrderDto;
import com.globits.da.dto.search.SearchDto;
import com.globits.da.repository.OrderRepository;
import com.globits.da.repository.ProductColorRepository;
import com.globits.da.repository.ProductOrderRepository;
import com.globits.da.repository.ProductRepository;
import com.globits.da.repository.StaffRepository;
import com.globits.da.repository.StockKeepingUnitRepository;
import com.globits.da.service.OrderService;

@Service
public class OrderServiceImpl extends GenericServiceImpl<Order, UUID> implements OrderService {
	@Autowired
	OrderRepository repos;
	@Autowired
	private StaffRepository nhanVienRepository;
	@Autowired
	private ProductOrderRepository sanPhamDonHangRepository;
	@Autowired
	private ProductRepository sanPhamRepository;
	@Autowired
	private StockKeepingUnitRepository donViTinhRepository;
	@Autowired
	ProductColorRepository productColorRepository;
	@Override
	public Page<OrderDto> getPage(int pageSize, int pageIndex) {
		Pageable pageable = PageRequest.of(pageIndex - 1, pageSize);
		return repos.getListPage(pageable);
	}

	@Override
	public OrderDto saveOrUpdate(UUID id, OrderDto dto) {
		if (dto != null) {
			Order entity = null;
			if (dto.getId() != null) {
				if (dto.getId() != null && !dto.getId().equals(id)) {
					return null;
				}
				entity = repos.getOne(dto.getId());
			}
			if (entity == null) {
				entity = new Order();
			}
			entity.setName(dto.getName());
			entity.setCode(dto.getCode());
			entity.setOrderDate(dto.getOrderDate());
			entity.setDeliveryDate(dto.getDeliveryDate());
			entity.setTotalPrice(dto.getTotalPrice());
			entity.setDiscount(dto.getDiscount());
			entity.setIntoMoney(dto.getIntoMoney());
			entity.setStatus(dto.getStatus());
			entity.setNote(dto.getNote());
			Staff nguoiBan = null;
			if (dto.getSeller() != null && dto.getSeller().getId() != null) {
				nguoiBan = nhanVienRepository.getOne(dto.getSeller().getId());
				if (nguoiBan == null) {
					return null;
				}
			}
			entity.setSeller(nguoiBan);

			if (dto.getProductOrder() != null && dto.getProductOrder().size() > 0) {
				Set<ProductOrder> listSanPhamDonHang = new HashSet<ProductOrder>();
				for (ProductOrderDto sanPhamDonHangDto : dto.getProductOrder()) {
					ProductOrder sanPhamDonHang = null;
					if (sanPhamDonHangDto.getId() != null) {
						sanPhamDonHang = sanPhamDonHangRepository.getOne(sanPhamDonHangDto.getId());
					}

					if (sanPhamDonHang == null) {
						sanPhamDonHang = new ProductOrder();
					}
					
					ProductColor productColor = null;
					if(sanPhamDonHangDto.getProductColor() != null && sanPhamDonHangDto.getProductColor().getId() != null) {
						productColor = productColorRepository.getOne(sanPhamDonHangDto.getProductColor().getId());
						if(productColor == null) {
							return null;
						}
					}
					sanPhamDonHang.setProductColor(productColor);
					sanPhamDonHang.setOrder(entity);;
					sanPhamDonHang.setProductNumber(sanPhamDonHangDto.getProductNumber());
					sanPhamDonHang.setUnitPrice(sanPhamDonHangDto.getUnitPrice());
					sanPhamDonHang.setIntoMoney(sanPhamDonHangDto.getIntoMoney());
					sanPhamDonHang.setDiscount(sanPhamDonHangDto.getDiscount());
					StockKeepingUnit donViTinh = null;
					if(sanPhamDonHangDto.getStockKeepingUnit() != null && sanPhamDonHangDto.getStockKeepingUnit().getId() != null) {
						donViTinh = donViTinhRepository.getOne(sanPhamDonHangDto.getStockKeepingUnit().getId());
						if(donViTinh == null) {
							return null;
						}
					}
					sanPhamDonHang.setStockKeepingUnit(donViTinh);
					listSanPhamDonHang.add(sanPhamDonHang);
				}

				if (entity.getProductOrder() == null) {
					entity.setProductOrder(listSanPhamDonHang);;
				} else {
					entity.getProductOrder().clear();
					entity.getProductOrder().addAll(listSanPhamDonHang);
				}
			}

			entity = repos.save(entity);
			if (entity != null) {
				return new OrderDto(entity);
			}
		}
		return null;
	}

	@Override
	public Boolean deleteDonHang(UUID id) {
		if (id != null) {
			repos.deleteById(id);
			return true;
		}
		return false;
	}

	@Override
	public OrderDto getCertificate(UUID id) {
		Order entity = repos.getOne(id);
		if (entity != null) {
			return new OrderDto(entity);
		}
		return null;
	}

	@Override
	public Page<OrderDto> searchByPage(SearchDto dto) {
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

		String sqlCount = "select count(entity.id) from Order as entity where (1=1)   ";
		String sql = "select new com.globits.da.dto.OrderDto(entity) from Order as entity where (1=1)  ";

		if (dto.getKeyword() != null && StringUtils.hasText(dto.getKeyword())) {
			whereClause += " AND ( entity.name LIKE :text OR entity.code LIKE :text )";
		}

		sql += whereClause + orderBy;
		sqlCount += whereClause;

		Query q = manager.createQuery(sql, OrderDto.class);
		Query qCount = manager.createQuery(sqlCount);

		if (dto.getKeyword() != null && StringUtils.hasText(dto.getKeyword())) {
			q.setParameter("text", '%' + dto.getKeyword() + '%');
			qCount.setParameter("text", '%' + dto.getKeyword() + '%');
		}
		int startPosition = pageIndex * pageSize;
		q.setFirstResult(startPosition);
		q.setMaxResults(pageSize);
		List<OrderDto> entities = q.getResultList();
		long count = (long) qCount.getSingleResult();

		Pageable pageable = PageRequest.of(pageIndex, pageSize);
		Page<OrderDto> result = new PageImpl<OrderDto>(entities, pageable, count);
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
