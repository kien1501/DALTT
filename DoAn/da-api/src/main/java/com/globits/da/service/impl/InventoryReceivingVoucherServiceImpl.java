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
import com.globits.da.domain.InventoryReceivingVoucher;
import com.globits.da.domain.ProductInventoryReceivingVoucher;
import com.globits.da.domain.ProductWarehouse;
import com.globits.da.domain.Staff;
import com.globits.da.domain.Warehouse;
import com.globits.da.dto.InventoryReceivingVoucherDto;
import com.globits.da.dto.ProductInventoryReceivingVoucherDto;
import com.globits.da.dto.search.ReportDto;
import com.globits.da.dto.search.SearchDto;
import com.globits.da.repository.ColorRepository;
import com.globits.da.repository.InventoryDeliveryVoucherRepository;
import com.globits.da.repository.InventoryReceivingVoucherRepository;
import com.globits.da.repository.ProductColorRepository;
import com.globits.da.repository.ProductInventoryReceivingVoucherRepository;
import com.globits.da.repository.ProductRepository;
import com.globits.da.repository.ProductWarehouseRepository;
import com.globits.da.repository.StaffRepository;
import com.globits.da.repository.WarehouseRepository;
import com.globits.da.service.InventoryDeliveryVoucherService;
import com.globits.da.service.InventoryReceivingVoucherService;
@Service
public class InventoryReceivingVoucherServiceImpl extends GenericServiceImpl< InventoryReceivingVoucher, UUID> implements  InventoryReceivingVoucherService{
	@Autowired
	InventoryReceivingVoucherRepository repos;
	@Autowired
	ProductInventoryReceivingVoucherRepository sanPhamPhieuNhapRepository;
	
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
	@Autowired
	InventoryDeliveryVoucherService phieuXuatKhoService;
	@Override
	public Page<InventoryReceivingVoucherDto> getPage(int pageSize, int pageIndex) {
		Pageable pageable = PageRequest.of(pageIndex-1, pageSize);
		return repos.getListPage(pageable);
	}

	@Override
	public InventoryReceivingVoucherDto saveOrUpdate(UUID id, InventoryReceivingVoucherDto dto) {
		if(dto != null ) {
			InventoryReceivingVoucher entity = null;
			Staff nv = null;
			Warehouse kho = null;
			if(dto.getId() !=null) {
				if (dto.getId() != null && !dto.getId().equals(id)) {
					return null;
				}
				entity =  repos.getOne(dto.getId());
			}
			if(entity == null) {
				entity = new InventoryReceivingVoucher();
			}
			if(dto.getReceiver() != null && dto.getReceiver().getId() != null) {
				nv = nhanVienRepository.getOne(dto.getReceiver().getId());
			}
			if(dto.getWarehouse() != null && dto.getWarehouse().getId() != null) {
				kho = khoRepository.getOne(dto.getWarehouse().getId());
			}
			entity.setCode(dto.getCode());
			entity.setName(dto.getName());
			entity.setWarehouse(kho);
			entity.setDateAdded(dto.getDateAdded());
			entity.setReceiver(nv);
			//
			if (dto.getProductInventoryReceivingVoucher() != null && dto.getProductInventoryReceivingVoucher().size() > 0) {
				Set<ProductInventoryReceivingVoucher> listSanPhamPhieuNhap = new HashSet<ProductInventoryReceivingVoucher>();
				for (ProductInventoryReceivingVoucherDto sanPhamPhieuNhaplDto : dto.getProductInventoryReceivingVoucher()) {
					ProductInventoryReceivingVoucher sanPhamPhieuNhap = null;
					ProductWarehouse sanPhamKho = null;
					if (sanPhamPhieuNhaplDto.getId() != null) {
						sanPhamPhieuNhap = sanPhamPhieuNhapRepository.getOne(sanPhamPhieuNhaplDto.getId());
					}
					
					if (sanPhamPhieuNhap == null) {
						sanPhamPhieuNhap = new ProductInventoryReceivingVoucher();
					}
					if(sanPhamPhieuNhaplDto.getProductColor() != null && sanPhamPhieuNhaplDto.getProductColor().getId() != null) {
						sanPhamPhieuNhap.setProductColor(productColorRepository.getOne(sanPhamPhieuNhaplDto.getProductColor().getId()));
						if(kho != null && kho.getId() != null) {
							List<ProductWarehouse> listData = sanPhamKhoRepository.getListSanPhamKho(sanPhamPhieuNhaplDto.getProductColor().getId(),kho.getId());
							if(listData != null && listData.size() > 0) {
								 sanPhamKho = listData.get(0);
								 if(sanPhamKho != null) {
									 if(entity.getId() == null) {
									 if(sanPhamKho.getProductNumber() != null) {
											sanPhamKho.setProductNumber(sanPhamPhieuNhaplDto.getProductNumber() + sanPhamKho.getProductNumber());
										}else {
											sanPhamKho.setProductNumber(sanPhamPhieuNhaplDto.getProductNumber());
										} 
									 }else {
										 if(sanPhamKho.getProductNumber() != null) {
												sanPhamKho.setProductNumber(sanPhamPhieuNhaplDto.getProductNumber());
											}else {
												sanPhamKho.setProductNumber(sanPhamPhieuNhaplDto.getProductNumber());
											} 
									 }
								 }
								
							}
							if(sanPhamKho == null) {
								sanPhamKho = new ProductWarehouse();
								sanPhamKho.setProductColor(productColorRepository.getOne(sanPhamPhieuNhaplDto.getProductColor().getId()));
								sanPhamKho.setWarehouse(kho);
								sanPhamKho.setProductNumber(sanPhamPhieuNhaplDto.getProductNumber());
							}
						}
						
					}
					
					if(sanPhamKho !=null) {
						sanPhamKho = sanPhamKhoRepository.save(sanPhamKho);
					}
					sanPhamPhieuNhap.setWarehouse(kho);
					sanPhamPhieuNhap.setProductNumber(sanPhamPhieuNhaplDto.getProductNumber());
					sanPhamPhieuNhap.setPrice(sanPhamPhieuNhaplDto.getPrice());
					sanPhamPhieuNhap.setInventoryReceivingVoucher(entity);
					listSanPhamPhieuNhap.add(sanPhamPhieuNhap);
				}

				if (entity.getProductInventoryReceivingVoucher() == null) {
					entity.setProductInventoryReceivingVoucher(listSanPhamPhieuNhap);
				} else {
					entity.getProductInventoryReceivingVoucher().clear();
					entity.getProductInventoryReceivingVoucher().addAll(listSanPhamPhieuNhap);
				}
			}
			entity = repos.save(entity);
			if (entity != null) {
				return new InventoryReceivingVoucherDto(entity);
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
	public InventoryReceivingVoucherDto getCertificate(UUID id) {
		InventoryReceivingVoucher entity = repos.getOne(id);
		if(entity!=null) {
			return new InventoryReceivingVoucherDto(entity);
		}
		return null;
	}

	@Override
	public Page<InventoryReceivingVoucherDto> searchByPage(SearchDto dto) {
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
		
		String sqlCount = "select count(entity.id) from InventoryReceivingVoucher as entity where (1=1)   ";
		String sql = "select new com.globits.da.dto.InventoryReceivingVoucherDto(entity) from InventoryReceivingVoucher as entity where (1=1)  ";

		if (dto.getKeyword() != null && StringUtils.hasText(dto.getKeyword())) {
			whereClause += " AND ( entity.name LIKE :text or  entity.code LIKE :text )";
		}

		
		sql += whereClause + orderBy;
		sqlCount += whereClause;

		Query q = manager.createQuery(sql, InventoryReceivingVoucherDto.class);
		Query qCount = manager.createQuery(sqlCount);

		if (dto.getKeyword() != null && StringUtils.hasText(dto.getKeyword())) {
			q.setParameter("text", '%' + dto.getKeyword() + '%');
			qCount.setParameter("text", '%' + dto.getKeyword() + '%');
		}
		int startPosition = pageIndex * pageSize;
		q.setFirstResult(startPosition);
		q.setMaxResults(pageSize);
		List<InventoryReceivingVoucherDto> entities = q.getResultList();
		long count = (long) qCount.getSingleResult();

		Pageable pageable = PageRequest.of(pageIndex, pageSize);
		Page<InventoryReceivingVoucherDto> result = new PageImpl<InventoryReceivingVoucherDto>(entities, pageable, count);
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

		String sql = "select new com.globits.da.dto.InventoryReceivingVoucherDto(entity) from InventoryReceivingVoucher as entity where (1=1)  ";

		if (dto.getKeyword() != null && StringUtils.hasText(dto.getKeyword())) {
			whereClause += " AND ( entity.code LIKE :text or  entity.name LIKE :text )";
		}
		if (dto.getFromDate() != null && dto.getToDate() != null) {
			whereClause += " AND ( entity.dateAdded BETWEEN :fromDate and :toDate  )";
		}
		if (dto.getKhoId() != null && dto.getKhoId() != null) {
			whereClause += " AND ( entity.warehouse.id = :khoId )";
		}

		sql += whereClause + orderBy;

		Query q = manager.createQuery(sql, InventoryReceivingVoucherDto.class);

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
		List<InventoryReceivingVoucherDto> entities = q.getResultList();
		List<ReportDto> result = new ArrayList<ReportDto>();
		List<ProductInventoryReceivingVoucherDto> listSPPN = new ArrayList<ProductInventoryReceivingVoucherDto>();
		if (entities != null && entities.size() > 0) {
			for (InventoryReceivingVoucherDto nhapDto : entities) {
				if (nhapDto.getProductInventoryReceivingVoucher() != null && nhapDto.getProductInventoryReceivingVoucher().size() > 0) {
					for (ProductInventoryReceivingVoucherDto sanPhamPhieuNhap : nhapDto.getProductInventoryReceivingVoucher()) {
						listSPPN.add(sanPhamPhieuNhap);
					}
				}
			}
		}
		if (listSPPN != null && listSPPN.size() > 0) {
			for (ProductInventoryReceivingVoucherDto spDto : listSPPN) {
				ReportDto bc = new ReportDto();
				bc.setSanPhamId(spDto.getProductColor().getProduct().getId());
				bc.setTenSP(spDto.getProductColor().getProduct().getName());
				bc.setMaSP(spDto.getProductColor().getProduct().getCode());
				bc.setKhoId(spDto.getWarehouse().getId());
				bc.setTenKho(spDto.getWarehouse().getName());
				bc.setTongTienNhap(0.0);
				if(spDto.getPrice() != null) {
					bc.setTongTienNhap(spDto.getPrice());
				}
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
							bcDto.setTongTienNhap(bcDto.getTongTienNhap() + bc.getTongTienNhap());
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

	@Override
	public List<ReportDto> baoCaoTon(SearchDto dto) {
		// TODO Auto-generated method stub
				if (dto == null) {
					return null;
				}

				List<ReportDto> baoCaoNhap = this.baoCao(dto);
				List<ReportDto> baoCaoXuat = phieuXuatKhoService.baoCao(dto);

				if (baoCaoNhap == null && baoCaoNhap.size() == 0) {
					return null;
				}

				if (baoCaoXuat != null && baoCaoXuat.size() > 0) {
					for (ReportDto bc : baoCaoXuat) {
						bc.setSoLuong(-bc.getSoLuong());
						baoCaoNhap.add(bc);
					}
				}

				List<ReportDto> baoCaoTon = new ArrayList<ReportDto>();
				if (baoCaoNhap != null && baoCaoNhap.size() > 0) {
					for (ReportDto bc : baoCaoNhap) {
						if (baoCaoTon != null && baoCaoTon.size() == 0) {
							baoCaoTon.add(bc);
						} else {
							Boolean check = false;
							for (ReportDto bcDto : baoCaoTon) {
								if (bc.getSanPhamId().equals(bcDto.getSanPhamId()) && bc.getKhoId().equals(bcDto.getKhoId())) {
									bcDto.setSoLuong(bcDto.getSoLuong() + bc.getSoLuong());
									check = true;
									break;
								} else {
									check = false;
								}
							}
							if (!check) {
								baoCaoTon.add(bc);
							}
						}
					}
				}

				return baoCaoTon;
			}

}
