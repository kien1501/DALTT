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
import com.globits.da.domain.Color;
import com.globits.da.domain.Image;
import com.globits.da.domain.Product;
import com.globits.da.domain.ProductCategory;
import com.globits.da.domain.ProductColor;
import com.globits.da.domain.ProductImage;
import com.globits.da.domain.ShiftWork;
import com.globits.da.domain.StaffWorkSchedule;
import com.globits.da.domain.StockKeepingUnit;
import com.globits.da.domain.Supplier;
import com.globits.da.dto.ColorDto;
import com.globits.da.dto.ProductColorDto;
import com.globits.da.dto.ProductDto;
import com.globits.da.dto.StaffShiftWorkDto;
import com.globits.da.dto.search.SearchDto;
import com.globits.da.repository.ColorRepository;
import com.globits.da.repository.ImageRepository;
import com.globits.da.repository.ProductCategoryRepository;
import com.globits.da.repository.ProductImageRepository;
import com.globits.da.repository.ProductRepository;
import com.globits.da.repository.StockKeepingUnitRepository;
import com.globits.da.repository.SupplierRepository;
import com.globits.da.service.ProductService;

@Service
public class ProductServiceImpl extends GenericServiceImpl<Product, UUID> implements ProductService {
	@Autowired
	ProductRepository repos;
	@Autowired
	StockKeepingUnitRepository donViTinhRepository;
	@Autowired
	ProductImageRepository productImageRepository;

	@Autowired
	ImageRepository imageRepository;
	@Autowired
	ProductCategoryRepository productCategoryRepository;
	@Autowired
	SupplierRepository supplierRepository;
	@Autowired
	ColorRepository colorRepository;

	@Override
	public Page<ProductDto> getPage(int pageSize, int pageIndex) {
		Pageable pageable = PageRequest.of(pageIndex - 1, pageSize);
		return repos.getListPage(pageable);
	}

	@Override
	public ProductDto saveOrUpdate(UUID id, ProductDto dto) {
		if (dto != null) {
			Product entity = null;
			if (dto.getId() != null) {
				if (dto.getId() != null && !dto.getId().equals(id)) {
					return null;
				}
				entity = repos.getOne(dto.getId());
			}
			if (entity == null) {
				entity = new Product();
			}
			entity.setCode(dto.getCode());
			entity.setName(dto.getName());
			entity.setPrice(dto.getPrice());
			entity.setCurrentSellingPrice(dto.getPrice());
			entity.setImageUrl(dto.getImageUrl());
			entity.setPosts(dto.getPosts());
			if (dto.getStockKeepingUnit() != null) {
				StockKeepingUnit nv = donViTinhRepository.getOne(dto.getStockKeepingUnit().getId());
				entity.setStockKeepingUnit(nv);
			}
			if (dto.getSupplier() != null) {
				Supplier nv = supplierRepository.getOne(dto.getSupplier().getId());
				entity.setSupplier(nv);
			}
			if (dto.getProductCategory() != null) {
				ProductCategory nv = productCategoryRepository.getOne(dto.getProductCategory().getId());
				entity.setProductCategory(nv);
			}
			Set<ProductColor> prouductColor = new HashSet<ProductColor>();
			if (dto.getProductColors() != null && dto.getProductColors().size() > 0) {
				for (ColorDto pcDto : dto.getProductColors()) {
					if (pcDto != null) {
						Color c = colorRepository.getOne(pcDto.getId());
						if (c != null) {
							ProductColor pc = new ProductColor();
							pc.setColor(c);
							pc.setProduct(entity);
							prouductColor.add(pc);
						}
					}
				}
				if (prouductColor != null && prouductColor.size() > 0) {
					if (entity.getProductColors() == null) {
						entity.setProductColors(prouductColor);
					} else {
						entity.getProductColors().clear();
						entity.getProductColors().addAll(prouductColor);
					}
				}

			} else {// Nếu submit list trống lên thì xóa hết
				if (entity.getProductColors() != null) {
					entity.getProductColors().clear();
				}
			}
			entity = repos.save(entity);
			if (entity != null) {
				return new ProductDto(entity);
			}
		}
		return null;
	}

	@Override
	public Boolean deleteKho(UUID id) {
		if (id != null) {
			repos.deleteById(id);
			return true;
		}
		return false;
	}

	@Override
	public ProductDto getCertificate(UUID id) {
		Product entity = repos.getOne(id);
		if (entity != null) {
			return new ProductDto(entity);
		}
		return null;
	}

	@Override
	public Page<ProductDto> searchByPage(SearchDto dto) {
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

		String sqlCount = "select count(entity.id) from Product as entity where (1=1)   ";
		String sql = "select new com.globits.da.dto.ProductDto(entity) from Product as entity where (1=1)  ";

		if (dto.getKeyword() != null && StringUtils.hasText(dto.getKeyword())) {
			whereClause += " AND ( entity.name LIKE :text or entity.code LIKE :text )";
		}

		if (dto.getProductCategory() != null) {
			whereClause += " AND ( entity.productCategory.id = :cate )";
		}

		sql += whereClause + orderBy;
		sqlCount += whereClause;

		Query q = manager.createQuery(sql, ProductDto.class);
		Query qCount = manager.createQuery(sqlCount);

		if (dto.getKeyword() != null && StringUtils.hasText(dto.getKeyword())) {
			q.setParameter("text", '%' + dto.getKeyword() + '%');
			qCount.setParameter("text", '%' + dto.getKeyword() + '%');
		}

		if (dto.getProductCategory() != null) {
			q.setParameter("cate", dto.getProductCategory());
			qCount.setParameter("cate", dto.getProductCategory());
		}
		int startPosition = pageIndex * pageSize;
		q.setFirstResult(startPosition);
		q.setMaxResults(pageSize);
		List<ProductDto> entities = q.getResultList();
		long count = (long) qCount.getSingleResult();

		Pageable pageable = PageRequest.of(pageIndex, pageSize);
		Page<ProductDto> result = new PageImpl<ProductDto>(entities, pageable, count);
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

	@Override
	public ProductDto updateImage(List<UUID> imageDtos, UUID id) {
		Product entity = repos.getOne(id);
		for (UUID productImage2 : imageDtos) {
			Image image = imageRepository.getOne(productImage2);
			ProductImage piEntity = new ProductImage();
			piEntity.setProduct(entity);
			piEntity.setImage(image);
			productImageRepository.save(piEntity);
		}

		return new ProductDto(entity);
	}

}
