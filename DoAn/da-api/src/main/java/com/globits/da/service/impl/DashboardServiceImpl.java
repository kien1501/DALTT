package com.globits.da.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.globits.da.dto.AnalyticsCountDto;
import com.globits.da.dto.AnalyticsDto;
import com.globits.da.dto.search.AnalyticsSearchDto;
import com.globits.da.service.DashboardService;

@Service
public class DashboardServiceImpl implements DashboardService {

	@Autowired
	EntityManager manager;

	@Override
	public AnalyticsDto getAnalytics(AnalyticsSearchDto analyticsSearchDto) {

		Long sanPhamNum = countSanPham();
		Long donHangNum = countDonHang();
		Long khoNum = countKho();
		Long userNum = countUser();

		AnalyticsDto analyticsDto = new AnalyticsDto();
		analyticsDto.setSanPhamNum(sanPhamNum);
		analyticsDto.setDonHangNum(donHangNum);
		analyticsDto.setKhoNum(khoNum);
		analyticsDto.setUserNum(userNum);

		analyticsDto.setSanPhamCountByDate(countSanPhamByCreatedDate());
		analyticsDto
				.setDonHangCountByDate(countDonHangByCreatedDate());
		analyticsDto
				.setKhoCountByDate(countKhoByIssueDate());
		analyticsDto.setUserCountByDate(countUserByIssueDate());
		return analyticsDto;
	}

	public Long countSanPham() {
		String sqlCount = "select count(a.id) FROM SanPham a where (1=1)";
		String whereClause = "";

		sqlCount += whereClause;

		Query qCount = manager.createQuery(sqlCount);

		long count = (long) qCount.getSingleResult();
		return count;
	}

	public Long countDonHang() {
		String sqlCount = "select count(v.id) FROM DonHang v where (1=1)";
		String whereClause = "";
		sqlCount += whereClause;
		Query qCount = manager.createQuery(sqlCount);

		long count = (long) qCount.getSingleResult();
		return count;
	}

	public Long countKho() {
		String sqlCount = "select count(m.id) FROM Kho m where 1=1";
		String whereClause = "";
		sqlCount += whereClause;
		Query qCount = manager.createQuery(sqlCount);

		long count = (long) qCount.getSingleResult();
		return count;
	}

	public Long countUser() {
		String sqlCount = "select count(m.id) FROM User m where 1=1";
		String whereClause = "";
		sqlCount += whereClause;
		Query qCount = manager.createQuery(sqlCount);

		long count = (long) qCount.getSingleResult();
		return count;
	}

	public List<AnalyticsCountDto> countSanPhamByCreatedDate() {
		String sqlCount = "select new com.globits.da.dto.AnalyticsCountDto(a.createDate, count(a.id)) FROM SanPham a where (1=1) ";
		String whereClause = "";
		String groupQuery = " GROUP BY cast(a.createDate as date) ";
		String orderQuery = " ORDER BY a.createDate ASC";

		sqlCount += whereClause + groupQuery + orderQuery;

		Query qCount = manager.createQuery(sqlCount);

		List<AnalyticsCountDto> listCounts = qCount.getResultList();
		return listCounts;
	}

	public List<AnalyticsCountDto> countDonHangByCreatedDate() {
		String sqlCount = "select new com.globits.da.dto.AnalyticsCountDto(v.issueDate, count(v.id)) FROM DonHang v where (1=1)";
		String groupQuery = " GROUP BY cast(v.issueDate as date)";
		String orderQuery = " ORDER BY v.issueDate ASC";
		String whereClause = "";
		sqlCount += whereClause + groupQuery + orderQuery;
		Query qCount = manager.createQuery(sqlCount);
		List<AnalyticsCountDto> listCounts = qCount.getResultList();
		return listCounts;
	}

	public List<AnalyticsCountDto> countKhoByIssueDate() {
		String sqlCount = "select new com.globits.da.dto.AnalyticsCountDto(v.issueDate, count(v.id)) FROM Kho v where (1=1)";
		String groupQuery = " GROUP BY cast(v.issueDate as date)";
		String orderQuery = " ORDER BY v.issueDate ASC";
		String whereClause = "";
		sqlCount += whereClause + groupQuery + orderQuery;
		Query qCount = manager.createQuery(sqlCount);
		List<AnalyticsCountDto> listCounts = qCount.getResultList();
		return listCounts;
	}
	
	public List<AnalyticsCountDto> countUserByIssueDate() {
		String sqlCount = "select new com.globits.da.dto.AnalyticsCountDto(v.issueDate, count(v.id)) FROM User v where (1=1)";
		String groupQuery = " GROUP BY cast(v.issueDate as date)";
		String orderQuery = " ORDER BY v.issueDate ASC";
		String whereClause = "";
		sqlCount += whereClause + groupQuery + orderQuery;
		Query qCount = manager.createQuery(sqlCount);
		List<AnalyticsCountDto> listCounts = qCount.getResultList();
		return listCounts;
	}

}
