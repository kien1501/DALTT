package com.globits.da.utils;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Component;

import com.globits.core.dto.DepartmentDto;
import com.globits.da.dto.search.ReportDto;
@Component
public class ImportExportExcelUtil {
	private static Hashtable<String, Integer> hashStaffColumnConfig = new Hashtable<String, Integer>();
	private static Hashtable<String, Integer> hashDepartmentColumnConfig = new Hashtable<String, Integer>();
	private static DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	private static DecimalFormat numberFormatter = new DecimalFormat("######################");
	private static Hashtable<String, String> hashColumnPropertyConfig = new Hashtable<String, String>();
	
	private static void scanStaffColumnExcelIndex(Sheet datatypeSheet, int scanRowIndex) {
		Row row = datatypeSheet.getRow(scanRowIndex);
		int numberCell = row.getPhysicalNumberOfCells();

		hashColumnPropertyConfig.put("staffCode".toLowerCase(),"staffCode");
		hashColumnPropertyConfig.put("firstName".toLowerCase(), "firstName");
		hashColumnPropertyConfig.put("lastName".toLowerCase(), "lastName");
		hashColumnPropertyConfig.put("displayName".toLowerCase(), "displayName");
		hashColumnPropertyConfig.put("birthdate".toLowerCase(), "birthdate");
		hashColumnPropertyConfig.put("birthdateMale".toLowerCase(), "birthdateMale");
		hashColumnPropertyConfig.put("birthdateFeMale".toLowerCase(), "birthdateFeMale");
		hashColumnPropertyConfig.put("gender".toLowerCase(), "gender");
		hashColumnPropertyConfig.put("address".toLowerCase(), "address");// Cái này cần xem lại
		hashColumnPropertyConfig.put("userName".toLowerCase(), "userName");
		hashColumnPropertyConfig.put("password".toLowerCase(), "password");
		hashColumnPropertyConfig.put("email".toLowerCase(), "email");
		hashColumnPropertyConfig.put("BirthPlace".toLowerCase(), "BirthPlace");
		
		hashColumnPropertyConfig.put("departmentCode".toLowerCase(), "departmentCode");
		hashColumnPropertyConfig.put("MaNgach".toLowerCase(), "MaNgach");
		hashColumnPropertyConfig.put("IDCard".toLowerCase(), "IDCard");
		
		for(int i=0;i<numberCell;i++) {
			Cell cell = row.getCell(i);
			if(cell!=null && cell.getCellTypeEnum()==CellType.STRING) {
				String cellValue = cell.getStringCellValue();
				if(cellValue!=null && cellValue.length()>0) {
					cellValue = cellValue.toLowerCase().trim();
					String propertyName = hashColumnPropertyConfig.get(cellValue);
					if(propertyName!=null) {
						hashStaffColumnConfig.put(propertyName,i);
					}
				}	
			}
		}
	}
	
	public static List<DepartmentDto> getListDepartmentFromInputStream(InputStream is) {
		try {

			List<DepartmentDto> ret = new ArrayList<DepartmentDto>();
			// FileInputStream excelFile = new FileInputStream(new File(filePath));
			// Workbook workbook = new XSSFWorkbook(excelFile);
			@SuppressWarnings("resource")
			Workbook workbook = new XSSFWorkbook(is);
			Sheet datatypeSheet = workbook.getSheetAt(0);
			// Iterator<Row> iterator = datatypeSheet.iterator();
			int rowIndex = 4;

			hashDepartmentColumnConfig.put("code", 0);
			
			hashDepartmentColumnConfig.put("name", 1);

			int num = datatypeSheet.getLastRowNum();
			while (rowIndex <= num) {
				Row currentRow = datatypeSheet.getRow(rowIndex);
				Cell currentCell = null;
				if (currentRow != null) {
					DepartmentDto department = new DepartmentDto();
					Integer index = hashDepartmentColumnConfig.get("code");
					if (index != null) {
						currentCell = currentRow.getCell(index);// code
						if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC) {
							double value = currentCell.getNumericCellValue();
							String code = numberFormatter.format(value);
							department.setCode(code);
						} else if (currentCell != null && currentCell.getStringCellValue() != null) {
							String code = currentCell.getStringCellValue();
							department.setCode(code);
						}
					}
				index = hashDepartmentColumnConfig.get("name");
				if (index != null) {
					currentCell = currentRow.getCell(index);// name
					if (currentCell != null && currentCell.getStringCellValue() != null) {
						String name = currentCell.getStringCellValue();
						department.setName(name);
					}
				}
				ret.add(department);
			}
				rowIndex++;
			}
			return ret;
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static ByteArrayResource exportBCNToExcelTable(List<ReportDto> dataList) throws IOException {
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Sheet1");

		/* Tạo font */
		XSSFFont fontBold = workbook.createFont();
		fontBold.setBold(true); // set bold
		fontBold.setFontHeight(10); // add font size

		XSSFFont fontBoldTitle = workbook.createFont();
		fontBoldTitle.setBold(true); // set bold
		fontBoldTitle.setFontHeight(15); // add font size

		/* Tạo cell style */
		XSSFCellStyle titleCellStyle = workbook.createCellStyle();
		titleCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		titleCellStyle.setAlignment(HorizontalAlignment.CENTER);
		titleCellStyle.setFont(fontBoldTitle);

		XSSFCellStyle tableHeadCellStyle = workbook.createCellStyle();
		tableHeadCellStyle.setFont(fontBold);
		tableHeadCellStyle.setBorderBottom(BorderStyle.THIN);
		tableHeadCellStyle.setBorderTop(BorderStyle.THIN);
		tableHeadCellStyle.setBorderLeft(BorderStyle.THIN);
		tableHeadCellStyle.setBorderRight(BorderStyle.THIN);

		XSSFRow row = sheet.createRow(0);
		XSSFCell cell = null;

		cell = row.createCell(0);
		cell.setCellValue("STT");
		cell.setCellStyle(tableHeadCellStyle);

		cell = row.createCell(1);
		cell.setCellValue("Tên sản phẩm");
		cell.setCellStyle(tableHeadCellStyle);
		
		cell = row.createCell(2);
		cell.setCellValue("Màu sản phẩm");
		cell.setCellStyle(tableHeadCellStyle);

		cell = row.createCell(3);
		cell.setCellValue("Tên kho");
		cell.setCellStyle(tableHeadCellStyle);

		cell = row.createCell(4);
		cell.setCellValue("Số lượng nhập");
		cell.setCellStyle(tableHeadCellStyle);

//		cell = row.createCell(4);
//		cell.setCellValue("Tên kho");
//		cell.setCellStyle(tableHeadCellStyle);

		// Tạo các hàng cột dữ liệu
		XSSFRow tableDataRow;
		if (dataList != null && !dataList.isEmpty()) {
			for (int i = 0; i < dataList.size(); i++) {
				tableDataRow = sheet.createRow(i + 1);
				ReportDto data = dataList.get(i);

				tableDataRow.createCell(0).setCellValue(i + 1);
				tableDataRow.createCell(1).setCellValue(data.getTenSP());
				tableDataRow.createCell(2).setCellValue(data.getMau());
				tableDataRow.createCell(3).setCellValue(data.getTenKho());
				tableDataRow.createCell(4).setCellValue(data.getSoLuong());

				sheet.autoSizeColumn(i);

			}
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			workbook.write(out);
			out.close();
			return new ByteArrayResource(out.toByteArray());
		}
		return null;
	}

	public static ByteArrayResource exportBCXToExcelTable(List<ReportDto> dataList) throws IOException {
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Sheet1");

		/* Tạo font */
		XSSFFont fontBold = workbook.createFont();
		fontBold.setBold(true); // set bold
		fontBold.setFontHeight(10); // add font size

		XSSFFont fontBoldTitle = workbook.createFont();
		fontBoldTitle.setBold(true); // set bold
		fontBoldTitle.setFontHeight(15); // add font size

		/* Tạo cell style */
		XSSFCellStyle titleCellStyle = workbook.createCellStyle();
		titleCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		titleCellStyle.setAlignment(HorizontalAlignment.CENTER);
		titleCellStyle.setFont(fontBoldTitle);

		XSSFCellStyle tableHeadCellStyle = workbook.createCellStyle();
		tableHeadCellStyle.setFont(fontBold);
		tableHeadCellStyle.setBorderBottom(BorderStyle.THIN);
		tableHeadCellStyle.setBorderTop(BorderStyle.THIN);
		tableHeadCellStyle.setBorderLeft(BorderStyle.THIN);
		tableHeadCellStyle.setBorderRight(BorderStyle.THIN);

		XSSFRow row = sheet.createRow(0);
		XSSFCell cell = null;

		cell = row.createCell(0);
		cell.setCellValue("STT");
		cell.setCellStyle(tableHeadCellStyle);

		cell = row.createCell(1);
		cell.setCellValue("Tên sản phẩm");
		cell.setCellStyle(tableHeadCellStyle);
		
		cell = row.createCell(2);
		cell.setCellValue("Màu sản phẩm");
		cell.setCellStyle(tableHeadCellStyle);


		cell = row.createCell(3);
		cell.setCellValue("Tên kho");
		cell.setCellStyle(tableHeadCellStyle);

		cell = row.createCell(4);
		cell.setCellValue("Số lượng xuất");
		cell.setCellStyle(tableHeadCellStyle);

//		cell = row.createCell(4);
//		cell.setCellValue("Tên kho");
//		cell.setCellStyle(tableHeadCellStyle);

		// Tạo các hàng cột dữ liệu
		XSSFRow tableDataRow;
		if (dataList != null && !dataList.isEmpty()) {
			for (int i = 0; i < dataList.size(); i++) {
				tableDataRow = sheet.createRow(i + 1);
				ReportDto data = dataList.get(i);

				tableDataRow.createCell(0).setCellValue(i + 1);
				tableDataRow.createCell(1).setCellValue(data.getTenSP());
				tableDataRow.createCell(2).setCellValue(data.getMau());
				tableDataRow.createCell(3).setCellValue(data.getTenKho());
				tableDataRow.createCell(4).setCellValue(data.getSoLuong());

				sheet.autoSizeColumn(i);

			}
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			workbook.write(out);
			out.close();
			return new ByteArrayResource(out.toByteArray());
		}
		return null;
	}
	public static void main(String[] agrs) {
//		try {
//			
//				FileInputStream fileIn = new FileInputStream(new File("C:\\Projects\\Globits\\Education\\globits-ecosystem\\hr\\hr-app\\Document\\DanhSachNhanSuDHTL.xlsx"));
//				List lst = getListStaffFromInputStream(fileIn);
//				System.out.println(lst.size());
//			}catch (Exception ex) {
//					ex.printStackTrace();
//			}

	}
}
