package com.globits.da.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.globits.core.domain.FileDescription;
import com.globits.core.repository.FileDescriptionRepository;
import com.globits.core.service.impl.GenericServiceImpl;
import com.globits.da.domain.EQAProgramAnnouncement;
import com.globits.da.domain.EQAProgramAnnouncementFileAttachment;
import com.globits.da.dto.EQAProgramAnnouncementDto;
import com.globits.da.dto.EQAProgramAnnouncementFileAttachmentDto;
import com.globits.da.dto.search.SearchDto;
import com.globits.da.repository.EQAProgramAnnouncementFileAttachmentRepository;
import com.globits.da.repository.EQAProgramAnnouncementRepository;
import com.globits.da.service.EQAProgramAnnouncementService;
@Transactional
@Service
public class EQAProgramAnnouncementServiceImpl extends GenericServiceImpl<EQAProgramAnnouncement, UUID> implements EQAProgramAnnouncementService{
	
	@Autowired
	private EntityManager manager;

	@Autowired
	private EQAProgramAnnouncementRepository eQAProgramAnnouncementRepository;
	
	@Autowired
	private EQAProgramAnnouncementFileAttachmentRepository eQAProgramAnnouncementFileAttachmentRepository;
	
	@Autowired
	FileDescriptionRepository fileDescriptionRepository;
	@Override
	public Page<EQAProgramAnnouncementDto> searchByDto(SearchDto dto){
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
		String orderBy = " ORDER BY eqap.createDate DESC ";
		String sqlCount = "select count(eqap.id) from EQAProgramAnnouncement as eqap where (1=1) ";
		String sql = "select new com.globits.da.dto.EQAProgramAnnouncementDto(eqap) from EQAProgramAnnouncement as eqap where (1=1) ";

		if (dto.getText() != null && StringUtils.hasText(dto.getText())) {
			whereClause += " AND ( eqap.name LIKE :text ) ";
		}
		sql += whereClause + orderBy;
		sqlCount += whereClause + orderBy;
		Query q = manager.createQuery(sql, EQAProgramAnnouncementDto.class);
		Query qCount = manager.createQuery(sqlCount);

		if (dto.getText() != null && StringUtils.hasText(dto.getText())) {
			q.setParameter("text", '%' + dto.getText() + '%');
			qCount.setParameter("text", '%' + dto.getText() + '%');
		}

		int startPosition = pageIndex * pageSize;
		q.setFirstResult(startPosition);
		q.setMaxResults(pageSize);
		List<EQAProgramAnnouncementDto> entities = q.getResultList();
		long count = (long) qCount.getSingleResult();
		
		Pageable pageable = PageRequest.of(pageIndex, pageSize);
		Page<EQAProgramAnnouncementDto> result = new PageImpl<EQAProgramAnnouncementDto>(entities, pageable, count);
		return result;
	}
	@Override
	public EQAProgramAnnouncementDto getById(UUID id) {
		if (id != null) {
			EQAProgramAnnouncement entity = eQAProgramAnnouncementRepository.getOne(id);
			if (entity != null) {
				return new EQAProgramAnnouncementDto(entity);
			}
		}
		return null;
	}
	@Override
	public EQAProgramAnnouncementDto getByActive() {
		
		List<EQAProgramAnnouncement> list = eQAProgramAnnouncementRepository.findAll();
		if(list != null && list.size() > 0) {
			for (EQAProgramAnnouncement item : list) {
				if(item.getActive() == true) {
					return new EQAProgramAnnouncementDto(item);
				}
			}
		}
		return null;
	}
	
	@Override
	public Boolean checkDuplicateCode(UUID id, String code) {
		if (code != null && StringUtils.hasText(code)) {
			EQAProgramAnnouncement entity = eQAProgramAnnouncementRepository.getByCode(code);
			if (entity != null) {
				if (id != null && entity.getId().equals(id)) {
					return false;
				}
				return true;
			}
			return false;
		}
		return null;
	}
	@Override
	public Boolean deleteById(UUID id) {
		if (id != null) {
			EQAProgramAnnouncement entity = eQAProgramAnnouncementRepository.getOne(id);
			if (entity != null) {
				eQAProgramAnnouncementRepository.deleteById(id);
				return true;
			}
		}
		return null;				
	}

	@Override
	public EQAProgramAnnouncementDto saveOrUpdate(EQAProgramAnnouncementDto dto, UUID id) {
		if(dto != null) {
			EQAProgramAnnouncement entity = null;
			if(id != null) {
				entity = eQAProgramAnnouncementRepository.getOne(id);
			}else {
				entity = new EQAProgramAnnouncement();
			}
			entity.setName(dto.getName());
			entity.setCode(dto.getCode());
			entity.setContent(dto.getContent());
			entity.setMessageContent(dto.getMessageContent());
			if(dto.getActive() != null && dto.getActive() ==  true) {
				List<EQAProgramAnnouncement> list = eQAProgramAnnouncementRepository.findAll();
				for(EQAProgramAnnouncement item : list) {
					item.setActive(false);
					item = eQAProgramAnnouncementRepository.save(item);
				}
			}
			entity.setActive(dto.getActive());
			//Đính kèm file
			Set<EQAProgramAnnouncementFileAttachment> documentAttachments = new HashSet<EQAProgramAnnouncementFileAttachment>();
			if (dto.getDocuments() != null && dto.getDocuments().size() > 0) {
				for (EQAProgramAnnouncementFileAttachmentDto documentAttachmentDto : dto.getDocuments()) {
					if (documentAttachmentDto != null && documentAttachmentDto.getFile() != null) {
						EQAProgramAnnouncementFileAttachment documentAttachment = null;
						if (documentAttachmentDto.getId() != null) {
							documentAttachment = eQAProgramAnnouncementFileAttachmentRepository.getOne(documentAttachmentDto.getId());
						}
						if (documentAttachment == null) {
							documentAttachment = new EQAProgramAnnouncementFileAttachment();
						}
						FileDescription file = null;
						if (documentAttachmentDto.getFile() != null && documentAttachmentDto.getFile().getId() != null) {
							file = fileDescriptionRepository.getOne(documentAttachmentDto.getFile().getId());
						}
						if(documentAttachmentDto.getName() != null) {
							documentAttachment.setName(documentAttachmentDto.getName());
						}
						documentAttachment.setFile(file);
						documentAttachment.setDescription(documentAttachmentDto.getDescription());
						documentAttachment.seteQAProgramAnnouncement(entity);
						documentAttachments.add(documentAttachment);
					}
				}
			}
			if (documentAttachments != null && documentAttachments.size() >0 ) {
				if(entity.getDocuments() == null) {
					entity.setDocuments(documentAttachments);
				}else {
					entity.getDocuments().clear();
					entity.getDocuments().addAll(documentAttachments);
				}
			}
			
			entity = eQAProgramAnnouncementRepository.save(entity);
			if(entity != null) {
				return new EQAProgramAnnouncementDto(entity);
			}
		}
		
		return null;
	}
}
