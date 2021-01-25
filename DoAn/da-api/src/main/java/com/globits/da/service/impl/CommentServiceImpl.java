package com.globits.da.service.impl;

import java.util.List;
import java.util.UUID;

import javax.persistence.Query;

import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.globits.core.Constants;
import com.globits.core.service.impl.GenericServiceImpl;
import com.globits.core.utils.SecurityUtils;
import com.globits.da.domain.Comment;
import com.globits.da.domain.Customer;
import com.globits.da.domain.Product;
import com.globits.da.domain.Warehouse;
import com.globits.da.dto.CommentDto;
import com.globits.da.dto.WarehouseDto;
import com.globits.da.dto.search.SearchDto;
import com.globits.da.repository.CommentRepository;
import com.globits.da.repository.CustomerRepository;
import com.globits.da.repository.ProductRepository;
import com.globits.da.service.CommentService;
import com.globits.security.domain.User;
@Service
public class CommentServiceImpl extends GenericServiceImpl<Comment, UUID> implements CommentService{
	@Autowired
	CommentRepository repos;
	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	ProductRepository productRepository;
	
	@Override
	public List<CommentDto> getListByProduct(UUID productId) {
		return repos.getListCommentByProduct(productId);
	}
	@Override
	public CommentDto saveOrUpdate(UUID id, CommentDto dto) {
		if(dto != null ) {
			Comment entity = null;
			if(dto.getId() !=null) {
				if (dto.getId() != null && !dto.getId().equals(id)) {
					return null;
				}
				entity =  repos.getOne(dto.getId());
			}
			if(entity == null) {
				entity = new Comment();
			}
			entity.setContent(dto.getContent());
			entity.setIsHide(false);
			if(dto.getCustomer() != null && dto.getCustomer().getId() != null) {
				Customer c = customerRepository.getOne(dto.getCustomer().getId());
				if(c != null) {
					entity.setCustomer(c);
				}
			}
			if(dto.getProduct() != null && dto.getProduct().getId() != null) {
				Product p = productRepository.getOne(dto.getProduct().getId());
				if(p != null) {
					entity.setProduct(p);
				}
			}
			entity = repos.save(entity);
			if (entity != null) {
				return new CommentDto(entity);
			}
			}
			return null;
	}

	@Override
	public Boolean deleteComment(UUID id) {
		if(id!=null) {
			repos.deleteById(id);
			return true;
		}
		return false;
	}

	@Override
	public CommentDto getCertificate(UUID id) {
		Comment entity = repos.getOne(id);
		if(entity!=null) {
			return new CommentDto(entity);
		}
		return null;
	}

	@Override
	public Page<CommentDto> searchByPage(SearchDto dto) {
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
		
		String sqlCount = "select count(entity.id) from Comment as entity where (1=1)   ";
		String sql = "select new com.globits.da.dto.CommentDto(entity) from Comment as entity where (1=1)  ";

		if (dto.getKeyword() != null && StringUtils.hasText(dto.getKeyword())) {
			whereClause += " AND ( entity.content LIKE :text OR entity.customer.name LIKE :text )";
		}
		sql += whereClause + orderBy;
		sqlCount += whereClause;

		Query q = manager.createQuery(sql, CommentDto.class);
		Query qCount = manager.createQuery(sqlCount);

		if (dto.getKeyword() != null && StringUtils.hasText(dto.getKeyword())) {
			q.setParameter("text", '%' + dto.getKeyword() + '%');
			qCount.setParameter("text", '%' + dto.getKeyword() + '%');
		}
		int startPosition = pageIndex * pageSize;
		q.setFirstResult(startPosition);
		q.setMaxResults(pageSize);
		List<CommentDto> entities = q.getResultList();
		long count = (long) qCount.getSingleResult();

		Pageable pageable = PageRequest.of(pageIndex, pageSize);
		Page<CommentDto> result = new PageImpl<CommentDto>(entities, pageable, count);
		return result;
	}
	@Override
	public CommentDto hideComment(UUID id) {
		if(id != null) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User currentUser = null;
			LocalDateTime currentDate = LocalDateTime.now();
			String currentUserName = "Unknown User";
			if (authentication != null) {
				currentUser = (User) authentication.getPrincipal();
				currentUserName = currentUser.getUsername();
			}
			boolean isAdmin = SecurityUtils.isUserInRole(currentUser, Constants.ROLE_ADMIN);
			if(isAdmin == true) {
				Comment c = repos.getOne(id);
				if(c != null && c.getId() != null) {
					c.setIsHide(true);
					return new CommentDto(c);
				}
			}
			return null;
		}
		return null;
	}
	
}
