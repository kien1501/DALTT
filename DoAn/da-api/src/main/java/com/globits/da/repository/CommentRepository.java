package com.globits.da.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.globits.da.domain.Color;
import com.globits.da.domain.Comment;
import com.globits.da.domain.Supplier;
import com.globits.da.dto.ColorDto;
import com.globits.da.dto.CommentDto;
import com.globits.da.dto.SupplierDto;
@Repository
public interface CommentRepository extends JpaRepository<Comment, UUID>{
	@Query("select new com.globits.da.dto.CommentDto(ed) from Comment ed where ed.product.id =?1 and ed.isHide is not Null and ed.isHide is FALSE")
	List<CommentDto> getListCommentByProduct(UUID productId);
}
