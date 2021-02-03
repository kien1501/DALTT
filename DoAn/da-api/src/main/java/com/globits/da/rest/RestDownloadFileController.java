package com.globits.da.rest;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.globits.da.dto.search.ReportDto;
import com.globits.da.dto.search.SearchDto;
import com.globits.da.service.InventoryDeliveryVoucherService;
import com.globits.da.service.InventoryReceivingVoucherService;
import com.globits.da.utils.ImportExportExcelUtil;

@RestController
@RequestMapping("/api/fileDownload")
public class RestDownloadFileController {
	@Autowired
	ImportExportExcelUtil importExportExcelUtil;
	@Autowired
	InventoryReceivingVoucherService phieuNhapKhoService;
	@Autowired
	InventoryDeliveryVoucherService phieuXuatKhoService;
	@RequestMapping(value="/baocaonhap", method = RequestMethod.POST)
	public void exportHealthOrgEQARoundToExcelTable(HttpSession session, HttpServletResponse response, @RequestBody SearchDto dto) throws IOException {
		List<ReportDto> dataList = phieuNhapKhoService.baoCao(dto);
		ByteArrayResource excelFile = null;
		if (dataList != null && dataList.size() > 0) {
			excelFile = ImportExportExcelUtil.exportBCNToExcelTable(dataList);
			InputStream ins = new ByteArrayInputStream(excelFile.getByteArray());
			org.apache.commons.io.IOUtils.copy(ins, response.getOutputStream());	 
		}	
		
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.addHeader("Content-Disposition", "attachment; filename=BaoCaoNhap.xlsx");
	}
	@RequestMapping(value="/baocaoxuat", method = RequestMethod.POST)
	public void exportBCXToExcelTable(HttpSession session, HttpServletResponse response, @RequestBody SearchDto dto) throws IOException {
		List<ReportDto> dataList = phieuXuatKhoService.baoCao(dto);
		ByteArrayResource excelFile = null;
		if (dataList != null && dataList.size() > 0) {
			excelFile = ImportExportExcelUtil.exportBCXToExcelTable(dataList);
			InputStream ins = new ByteArrayInputStream(excelFile.getByteArray());
			org.apache.commons.io.IOUtils.copy(ins, response.getOutputStream());	 
		}	
		
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.addHeader("Content-Disposition", "attachment; filename=BaoCaoXuat.xlsx");
	}
}
