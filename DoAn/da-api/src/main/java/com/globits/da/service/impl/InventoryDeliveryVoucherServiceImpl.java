package com.globits.da.service.impl;

import java.util.ArrayList;
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
import com.globits.da.domain.InventoryDeliveryVoucher;
import com.globits.da.domain.ProductInventoryDeliveryVoucher;
import com.globits.da.domain.ProductWarehouse;
import com.globits.da.domain.Staff;
import com.globits.da.domain.Warehouse;
import com.globits.da.dto.InventoryDeliveryVoucherDto;
import com.globits.da.dto.ProductInventoryDeliveryVoucherDto;
import com.globits.da.dto.search.ReportDto;
import com.globits.da.dto.search.SearchDto;
import com.globits.da.repository.ColorRepository;
import com.globits.da.repository.InventoryDeliveryVoucherRepository;
import com.globits.da.repository.ProductColorRepository;
import com.globits.da.repository.ProductInventoryDeliveryVoucherRepository;
import com.globits.da.repository.ProductRepository;
import com.globits.da.repository.ProductWarehouseRepository;
import com.globits.da.repository.StaffRepository;
import com.globits.da.repository.WarehouseRepository;
import com.globits.da.service.InventoryDeliveryVoucherService;
@Service
public class InventoryDeliveryVoucherServiceImpl extends GenericServiceImpl< InventoryDeliveryVoucher, UUID> implements  InventoryDeliveryVoucherService{
	@Autowired
	InventoryDeliveryVoucherRepository repos;
	@Autowired
	ProductInventoryDeliveryVoucherRepository sanPhamPhieuXuatRepository;
	
	@Autowired
	ProductRepository sanPhamRepository;
	@Autowired
	WarehouseRepository khoRepository;
	@Autowired
	StaffRepository nhanVienRepository;
	@Autowired
	ProductWarehouseRepository sanPhamKhoRepository;
	@Autowired
	ColorRepository colorRepository;
	@Autowired
	ProductColorRepository productColorRepository;
	@Override
	public Page<InventoryDeliveryVoucherDto> getPage(int pageSize, int pageIndex) {
		Pageable pageable = PageRequest.of(pageIndex-1, pageSize);
		return repos.getListPage(pageable);
	}

	@Override
	public InventoryDeliveryVoucherDto saveOrUpdate(UUID id, InventoryDeliveryVoucherDto dto) {
		if(dto != null ) {
			InventoryDeliveryVoucher entity = null;
			Staff nv = null;
			Warehouse kho = null;
			if(dto.getId() !=null) {
				if (dto.getId() != null && !dto.getId().equals(id)) {
					return null;
				}
				entity =  repos.getOne(dto.getId());
			}
			if(entity == null) {
				entity = new InventoryDeliveryVoucher();
			}
			entity.setCode(dto.getCode());
			entity.setName(dto.getName());
			entity.setExportDate(dto.getExportDate());
			if(dto.getExporter() != null) {
				nv = nhanVienRepository.getOne(dto.getExporter().getId());
			}
			if(dto.getWarehouse()!= null) {
				kho = khoRepository.getOne(dto.getWarehouse().getId());
			}
			entity.setWarehouse(kho);
			entity.setExporter(nv);
			//
			if (dto.getProductInventoryDeliveryVoucher() != null && dto.getProductInventoryDeliveryVoucher().size() > 0) {
				Set<ProductInventoryDeliveryVoucher> listSanPhamPhieuXuat = new HashSet<ProductInventoryDeliveryVoucher>();
				for (ProductInventoryDeliveryVoucherDto sanPhamPhieuXuatlDto : dto.getProductInventoryDeliveryVoucher()) {
					ProductInventoryDeliveryVoucher sanPhamPhieuXuat = null;
					if (sanPhamPhieuXuatlDto.getId() != null) {
						sanPhamPhieuXuat = sanPhamPhieuXuatRepository.getOne(sanPhamPhieuXuatlDto.getId());
					}
					
					if (sanPhamPhieuXuat == null) {
						sanPhamPhieuXuat = new ProductInventoryDeliveryVoucher();
					}
					

					if(sanPhamPhieuXuatlDto.getProductColor() != null) {
						sanPhamPhieuXuat.setProductColor(productColorRepository.getOne(sanPhamPhieuXuatlDto.getProductColor().getId()));
						ProductWarehouse sanPhamKho  = null;
						if(kho != null && kho.getId() != null) {
							List<ProductWarehouse> listData = sanPhamKhoRepository.getListSanPhamKho(sanPhamPhieuXuatlDto.getProductColor().getId(),kho.getId());
							if(listData != null && listData.size() > 0) {
								 sanPhamKho = listData.get(0);
							}
						}else {
							return null;
						}
						if(sanPhamKho == null) {
							return null;
						}
						if(sanPhamKho.getProductNumber() >= sanPhamPhieuXuatlDto.getProductNumber()) {
							sanPhamKho.setProductNumber(sanPhamKho.getProductNumber() - sanPhamPhieuXuatlDto.getProductNumber());
						}else {
							return null;
						}
					}else {
						return null;
					}
					
					sanPhamPhieuXuat.setProductNumber(sanPhamPhieuXuatlDto.getProductNumber());
					sanPhamPhieuXuat.setInventoryDeliveryVoucher(entity);
					listSanPhamPhieuXuat.add(sanPhamPhieuXuat);
				}

				if (entity.getProductInventoryDeliveryVoucher() == null) {
					entity.setProductInventoryDeliveryVoucher(listSanPhamPhieuXuat);
				} else {
					entity.getProductInventoryDeliveryVoucher().clear();
					entity.getProductInventoryDeliveryVoucher().addAll(listSanPhamPhieuXuat);
				}
			}
			entity = repos.save(entity);
			if (entity != null) {
				return new InventoryDeliveryVoucherDto(entity);
			}
			}
			return null;
	}

	@Override
	public Boolean deleteKho(UUID id) {
		if(id!=null) {
			repos.deleteById(id);
			return true;
		}
		return false;
	}

	@Override
	public InventoryDeliveryVoucherDto getCertificate(UUID id) {
		InventoryDeliveryVoucher entity = repos.getOne(id);
		if(entity!=null) {
			return new InventoryDeliveryVoucherDto(entity);
		}
		return null;
	}

	@Override
	public Page<InventoryDeliveryVoucherDto> searchByPage(SearchDto dto) {
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
		
		String sqlCount = "select count(entity.id) from InventoryDeliveryVoucher as entity where (1=1)   ";
		String sql = "select new com.globits.da.dto.InventoryDeliveryVoucherDto(entity) from InventoryDeliveryVoucher as entity where (1=1)  ";

		if (dto.getKeyword() != null && StringUtils.hasText(dto.getKeyword())) {
			whereClause += " AND ( entity.name LIKE :text or  entity.code LIKE :text )";
		}

		
		sql += whereClause + orderBy;
		sqlCount += whereClause;

		Query q = manager.createQuery(sql, InventoryDeliveryVoucherDto.class);
		Query qCount = manager.createQuery(sqlCount);

		if (dto.getKeyword() != null && StringUtils.hasText(dto.getKeyword())) {
			q.setParameter("text", '%' + dto.getKeyword() + '%');
			qCount.setParameter("text", '%' + dto.getKeyword() + '%');
		}
		int startPosition = pageIndex * pageSize;
		q.setFirstResult(startPosition);
		q.setMaxResults(pageSize);
		List<InventoryDeliveryVoucherDto> entities = q.getResultList();
		long count = (long) qCount.getSingleResult();

		Pageable pageable = PageRequest.of(pageIndex, pageSize);
		Page<InventoryDeliveryVoucherDto> result = new PageImpl<InventoryDeliveryVoucherDto>(entities, pageable, count);
		return result;
	}

	@Override
	public Boolean checkCode(UUID id, String code) {
		if(code != null && StringUtils.hasText(code)) {
			Long count = repos.checkCode(code,id);
				return count != 0l;
			}
		return null;
	}

	@Override
	public List<ReportDto> baoCao(SearchDto dto) {
		String whereClause = "";

		String orderBy = " ORDER BY entity.createDate DESC";

		String sql = "select new com.globits.da.dto.InventoryDeliveryVoucherDto(entity) from InventoryDeliveryVoucher as entity where (1=1)  ";

		if (dto.getKeyword() != null && StringUtils.hasText(dto.getKeyword())) {
			whereClause += " AND ( entity.name LIKE :text or  entity.code LIKE :text )";
		}
		if (dto.getFromDate() != null && dto.getToDate() != null) {
			whereClause += " AND ( entity.exportDate BETWEEN :fromDate and :toDate  )";
		}
		if (dto.getKhoId() != null && dto.getKhoId() != null) {
			whereClause += " AND ( entity.warehouse.id = :khoId )";
		}

		sql += whereClause + orderBy;

		Query q = manager.createQuery(sql, InventoryDeliveryVoucherDto.class);

		if (dto.getKeyword() != null && StringUtils.hasText(dto.getKeyword())) {
			q.setParameter("text", '%' + dto.getKeyword() + '%');
		}
		if (dto.getFromDate() != null && dto.getToDate() != null) {
			q.setParameter("fromDate", dto.getFromDate());
			q.setParameter("toDate", dto.getToDate());
		}
		if (dto.getKhoId() != null && dto.getKhoId() != null) {
			q.setParameter("khoId", dto.getKhoId());
		}
		List<InventoryDeliveryVoucherDto> entities = q.getResultList();
		List<ReportDto> result = new ArrayList<ReportDto>();
		List<ProductInventoryDeliveryVoucherDto> listSPPN = new ArrayList<ProductInventoryDeliveryVoucherDto>();
		
		if (entities != null && entities.size() > 0) {
			for (InventoryDeliveryVoucherDto nhapDto : entities) {
				if (nhapDto.getProductInventoryDeliveryVoucher() != null && nhapDto.getProductInventoryDeliveryVoucher().size() > 0) {
					for (ProductInventoryDeliveryVoucherDto sanPhamPhieuNhap : nhapDto.getProductInventoryDeliveryVoucher()) {
						listSPPN.add(sanPhamPhieuNhap);
					}
				}
			}
		}
		if (listSPPN != null && listSPPN.size() > 0) {
			for (ProductInventoryDeliveryVoucherDto spDto : listSPPN) {
				ReportDto bc = new ReportDto();
				bc.setSanPhamId(spDto.getProductColor().getProduct().getId());
				bc.setTenSP(spDto.getProductColor().getProduct().getName());
				bc.setMaSP(spDto.getProductColor().getProduct().getCode());
				bc.setKhoId(spDto.getInventoryDeliveryVoucher().getWarehouse().getId());
				bc.setTenKho(spDto.getInventoryDeliveryVoucher().getWarehouse().getName());
				bc.setSoLuong(0);
				if (spDto.getProductNumber() != null) {
					bc.setSoLuong(spDto.getProductNumber());
				}

				if (result != null && result.size() == 0) {
					result.add(bc);
				} else {
					Boolean check = false;
					for (ReportDto bcDto : result) {
						if (bc.getSanPhamId().equals(bcDto.getSanPhamId()) && bc.getKhoId().equals(bcDto.getKhoId())) {
							bcDto.setSoLuong(bcDto.getSoLuong() + bc.getSoLuong());
							check = true;
							break;
						} else {
							check = false;
						}
					}
					if (!check) {
						result.add(bc);
					}
				}
			}
		}

		return result;
	}


}
