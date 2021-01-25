package com.globits.da.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.globits.da.domain.ProductWarehouse;
@Repository
public interface ProductWarehouseRepository extends JpaRepository<ProductWarehouse, UUID>{
	@Query("select entity from ProductWarehouse entity where entity.product.id =?1 and entity.warehouse.id =?2 ")
	List<ProductWarehouse> getListSanPhamKho(UUID sanPhamID, UUID khoID);

}
