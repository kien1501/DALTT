package com.globits.da.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;

import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.globits.core.domain.Person;
import com.globits.core.service.impl.GenericServiceImpl;
import com.globits.core.utils.CommonUtils;
import com.globits.core.utils.HttpUtils;
import com.globits.da.HrConstants;
import com.globits.da.domain.Customer;
import com.globits.da.dto.CustomerDto;
import com.globits.da.dto.search.SearchDto;
import com.globits.da.repository.CustomerRepository;
import com.globits.da.service.CustomerService;
import com.globits.security.domain.Role;
import com.globits.security.domain.User;
import com.globits.security.repository.UserRepository;
import com.globits.security.service.RoleService;

@Transactional
@Service
public class CustomerServiceImpl extends GenericServiceImpl<Customer, UUID> implements CustomerService {
	
	@Autowired
	private EntityManager manager;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private CustomerRepository customerRepository;
	@Override
	public CustomerDto saveCustomer(CustomerDto dto) {
		if(dto!=null) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User modifiedUser = null;
			LocalDateTime currentDate = LocalDateTime.now();
			String currentUserName = "Unknown User";
			if (authentication != null) {
				modifiedUser = (User) authentication.getPrincipal();
				currentUserName = modifiedUser.getUsername();
			}
			
			ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
			HttpServletRequest request = attributes.getRequest();
			String ipAddr= HttpUtils.getClientIpAddr(request);
					 
		   Customer cy = null;
		   if(dto.getId()!=null) {
			   cy = customerRepository.getOne(dto.getId());
		   }
		   if(cy == null) {
			   cy = new Customer();
			}else{
			}
		   if(dto.getName() != null) {
			   cy.setName(dto.getName());
		   }
		   if(dto.getCode() != null) {
			   cy.setCode(dto.getCode());
		   }
		   if(dto.getAddress() != null) {
			   cy.setAddress(dto.getAddress());
		   }
		   if(dto.getPhoneNumber() != null) {
			   cy.setPhoneNumber(dto.getPhoneNumber());
		   }
		   if(dto.getEmail() != null) {
			   cy.setEmail(dto.getEmail());
		   }
		   
		   cy.setAge(dto.getAge());
		   cy.setIsCreate(dto.getIsCreate());
		   //
		   if(dto.getIsCreate() == true) {
		   User user = userRepository.findByUsername(cy.getCode());
			Person person = null;
			if(user==null) {
				user = new User();
				user.setUsername(cy.getCode());
				user.setEmail(dto.getEmail());
				if(dto.getPassword() != null) {
					user.setPassword(dto.getPassword());
				}else {
					user.setPassword(dto.getPassword());
				}
				

				Role roleUSER = roleService.findByName(HrConstants.ROLE_USER);
				Set<Role> roles = new HashSet<Role>();
				roles.add(roleUSER);
				user.setRoles(roles);
				
				person = new Person();
				person.setDisplayName(dto.getName());
				person.setPhoneNumber(dto.getPhoneNumber());
				person.setEmail(dto.getEmail());
				person.setUser(user);
				user.setPerson(person);
				user = userRepository.save(user);
			}
		   }
		   
		   cy = customerRepository.save(cy);
		   return new CustomerDto(cy);
		}
		return null;
	}

	@Override
	public Page<CustomerDto> getListPage(int pageIndex, int pageSize) {
		PageRequest pageable = new PageRequest(pageIndex-1, pageSize);
		return customerRepository.getListPage(pageable);
	}
	
	@Override
	public List<CustomerDto> getAllCustomers() {
		return customerRepository.getAllCustomers();
	}

	@Override
	public CustomerDto getCustomerDto(UUID id) {
		Customer Customer = customerRepository.getOne(id);
		return new CustomerDto(Customer);
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean deleteMultiple(CustomerDto[] dtos) {
		if (CommonUtils.isEmpty(dtos)) {
			return false;
		}

		for (CustomerDto dto : dtos) {
			Customer entity = customerRepository.getOne(dto.getId());
			if (entity != null) {
				customerRepository.delete(entity);
			}
		}

		return true;
	}

	@Override
	public Boolean deleteCustomer(UUID id) {
		if(id != null) {
			Customer customer = customerRepository.getOne(id);
			if(customer != null && customer.getId() != null) {
				customerRepository.delete(customer);
				return true;
			}
		}
		return false;
	}

	@Override
	public Page<CustomerDto> searchCustomer(SearchDto dto) {
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

		String sqlCount = "select count(entity.id) from Customer as entity where (1=1)   ";
		String sql = "select new com.globits.da.dto.CustomerDto(entity) from Customer as entity where (1=1)  ";

		if (dto.getKeyword() != null && StringUtils.hasText(dto.getKeyword())) {
			whereClause += " AND ( entity.code LIKE :text || entity.name LIKE :text )";
		}

		sql += whereClause + orderBy;
		sqlCount += whereClause;

		Query q = manager.createQuery(sql, CustomerDto.class);
		Query qCount = manager.createQuery(sqlCount);

		if (dto.getKeyword() != null && StringUtils.hasText(dto.getKeyword())) {
			q.setParameter("text", '%' + dto.getKeyword() + '%');
			qCount.setParameter("text", '%' + dto.getKeyword() + '%');
		}
		int startPosition = pageIndex * pageSize;
		q.setFirstResult(startPosition);
		q.setMaxResults(pageSize);
		List<CustomerDto> entities = q.getResultList();
		long count = (long) qCount.getSingleResult();

		Pageable pageable = PageRequest.of(pageIndex, pageSize);
		Page<CustomerDto> result = new PageImpl<CustomerDto>(entities, pageable, count);
		return result;
	}
	@Override
	public Boolean checkCode(UUID id, String code) {
		if(code != null && StringUtils.hasText(code)) {
			Long count = customerRepository.checkCode(code,id);
				return count != 0l;
			}
		return null;
	}
}
