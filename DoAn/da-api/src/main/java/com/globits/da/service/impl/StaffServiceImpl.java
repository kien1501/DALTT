package com.globits.da.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Query;

import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.globits.core.Constants;
import com.globits.core.domain.Person;
import com.globits.core.repository.PersonRepository;
import com.globits.core.service.impl.GenericServiceImpl;
import com.globits.core.utils.SecurityUtils;
import com.globits.da.domain.ShiftWork;
import com.globits.da.domain.Staff;
import com.globits.da.domain.StaffWorkSchedule;
import com.globits.da.dto.StaffDto;
import com.globits.da.dto.StaffShiftWorkDto;
import com.globits.da.dto.search.SearchDto;
import com.globits.da.repository.ShiftWorkRepository;
import com.globits.da.repository.StaffRepository;
import com.globits.da.repository.StaffWorkScheduleRepository;
import com.globits.da.service.StaffService;
import com.globits.security.domain.Role;
import com.globits.security.domain.User;
import com.globits.security.dto.UserDto;
import com.globits.security.repository.UserRepository;
import com.globits.security.service.RoleService;
import com.globits.security.service.UserService;

@Service
public class StaffServiceImpl extends GenericServiceImpl<Staff, UUID> implements StaffService {
	@Autowired
	StaffRepository repos;
	@Autowired
	RoleService roleService;
	@Autowired
	UserRepository userRepository;
	@Autowired
	UserService userService;
	@Autowired
	PersonRepository personRepository;
	@Autowired
	ShiftWorkRepository shiftWorkRepository;
	@Autowired
	StaffWorkScheduleRepository staffWorkScheduleRepository;

	@Override
	public Page<StaffDto> getPage(int pageSize, int pageIndex) {
		Pageable pageable = PageRequest.of(pageIndex - 1, pageSize);
		return repos.getListPage(pageable);
	}

	@Override
	public StaffDto saveOrUpdate(UUID id, StaffDto dto) {
		LocalDateTime currentDate = LocalDateTime.now();
		String currentUserName = "Unknown User";
		StaffDto result = new StaffDto();
//		boolean isUpdate = false;
		if (dto != null) {
			Staff entity = null;
			User user = null;
			Person person = null;
			if (dto.getId() != null) {
				if (dto.getId() != null && !dto.getId().equals(id)) {
					return null;
				}
				entity = repos.getStaffById(dto.getId());
//				if (entity != null && entity.getId() != null) {
//					isUpdate = true;
//				}
			}
			if (entity == null) {
				entity = new Staff();
				user = new User();
				person = new Person();
			}
			
			// check userName tồn tại hay chưa
			UserDto userDto = userService.findByUsername(dto.getPhoneNumber());
			if (userDto != null) {
				result.setHasUserName(true);
				return result;
			}
			// check số điện thoại tồn tại hay chưa
			List<Person> pPhoneNumber = this.getPersonByPhoneNumber(dto.getPhoneNumber());
			if (pPhoneNumber != null && pPhoneNumber.size() > 0) {
				result.setHasPhoneNumber(true);
				return result;
			}
			// check email tồn tại hay chưa
			List<Person> pEmail = this.getPersonByEmail(dto.getPhoneNumber());
			if (pEmail != null && pEmail.size() > 0) {
				result.setHasEmail(true);
				return result;
			}

			entity.setCode(dto.getCode());
			entity.setType(dto.getType());
			entity.setDisplayName(dto.getDisplayName());
			entity.setEmail(dto.getEmail());
			entity.setPhoneNumber(dto.getPhoneNumber());
			if (dto.getShiftWork() != null && dto.getShiftWork().getId() != null) {
				ShiftWork sw = shiftWorkRepository.getOne(dto.getShiftWork().getId());
				if (sw != null) {
					entity.setShiftWork(sw);
				}
			}
			Set<StaffWorkSchedule> staffWorkSchedule = new HashSet<StaffWorkSchedule>();
			if (dto.getStaffWorkSchedule() != null && dto.getStaffWorkSchedule().size() > 0) {
				for (StaffShiftWorkDto sswDto : dto.getStaffWorkSchedule()) {
					if (sswDto != null) {
						ShiftWork sw = shiftWorkRepository.getOne(sswDto.getId());
						if (sw != null) {
							StaffWorkSchedule sws = new StaffWorkSchedule();
							sws.setShiftWork(sw);
							sws.setStaff(entity);
							staffWorkSchedule.add(sws);
						}
					}

				}
				if (staffWorkSchedule != null && staffWorkSchedule.size() > 0) {
					if (entity.getStaffWorkSchedule() == null) {
						entity.setStaffWorkSchedule(staffWorkSchedule);
					} else {
						entity.getStaffWorkSchedule().clear();
						entity.getStaffWorkSchedule().addAll(staffWorkSchedule);
					}
				}

			} else {// Nếu submit list trống lên thì xóa hết
				if (entity.getStaffWorkSchedule() != null) {
					entity.getStaffWorkSchedule().clear();
				}
			}
			if (dto.getPhoneNumber() != null) {
				user.setUsername(dto.getPhoneNumber());
				user.setCreateDate(currentDate);
				user.setCreatedBy(currentUserName);
				user.setEmail(dto.getEmail());
				String password = SecurityUtils.getHashPassword(dto.getPhoneNumber());
				if (password != null && password.length() > 0) {
					user.setPassword(password);
				}
				user.setRoles(new HashSet<Role>());
				Role userRole = roleService.findByName(Constants.ROLE_USER);
				user.getRoles().add(userRole);
				user = userRepository.save(user);
				person.setDisplayName(dto.getDisplayName());
				person.setEmail(dto.getEmail());
				person.setPhoneNumber(dto.getPhoneNumber());
				person.setUser(user);
				//person = personRepository.save(person);
			}
			entity = repos.save(entity);
			if (entity != null) {
				return new StaffDto(entity);
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
	public StaffDto getCertificate(UUID id) {
		Staff entity = repos.getOne(id);
		if (entity != null) {
			return new StaffDto(entity);
		}
		return null;
	}

	@Override
	public Page<StaffDto> searchByPage(SearchDto dto) {
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

		String sqlCount = "select count(entity.id) from Staff as entity where (1=1)   ";
		String sql = "select new com.globits.da.dto.StaffDto(entity) from Staff as entity where (1=1)  ";

		if (dto.getKeyword() != null && StringUtils.hasText(dto.getKeyword())) {
			whereClause += " AND ( entity.code LIKE :text or entity.displayName LIKE :text or entity.phoneNumber LIKE :text)";
		}

		sql += whereClause + orderBy;
		sqlCount += whereClause;

		Query q = manager.createQuery(sql, StaffDto.class);
		Query qCount = manager.createQuery(sqlCount);

		if (dto.getKeyword() != null && StringUtils.hasText(dto.getKeyword())) {
			q.setParameter("text", '%' + dto.getKeyword() + '%');
			qCount.setParameter("text", '%' + dto.getKeyword() + '%');
		}
		int startPosition = pageIndex * pageSize;
		q.setFirstResult(startPosition);
		q.setMaxResults(pageSize);
		List<StaffDto> entities = q.getResultList();
		long count = (long) qCount.getSingleResult();

		Pageable pageable = PageRequest.of(pageIndex, pageSize);
		Page<StaffDto> result = new PageImpl<StaffDto>(entities, pageable, count);
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

	private List<Person> getPersonByPhoneNumber(String phoneNumber) {
		String sql = "select p from Person p where p.phoneNumber =:phoneNumber ";
		Query q = manager.createQuery(sql, Person.class);
		q.setParameter("phoneNumber", phoneNumber);
		List<Person> entities = q.getResultList();
		return entities;
	}

	private List<Person> getPersonByEmail(String email) {
		String sql = "select p from Person p where (p.Email =:email OR p.user.email =:email ) ";
		Query q = manager.createQuery(sql, Person.class);
		q.setParameter("email", email);
		List<Person> entities = q.getResultList();
		return entities;
	}
}
