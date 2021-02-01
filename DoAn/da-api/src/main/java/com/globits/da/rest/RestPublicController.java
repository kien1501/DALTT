package com.globits.da.rest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.globits.core.service.FileDescriptionService;
import com.globits.da.dto.CustomerDto;
import com.globits.da.dto.EventDto;
import com.globits.da.dto.ProductCategoryDto;
import com.globits.da.dto.ProductDto;
import com.globits.da.dto.search.SearchDto;
import com.globits.da.service.CustomerService;
import com.globits.da.service.DAFileUploadService;
import com.globits.da.service.EventService;
import com.globits.da.service.ProductCategoryService;
import com.globits.da.service.ProductService;
import com.globits.da.service.impl.DAFileUploadServiceImpl;

@RestController
@RequestMapping("/public")
public class RestPublicController {
	@Autowired
	private Environment env;
	@Autowired
	FileDescriptionService fileDescriptionService;
	@Autowired
	ProductService sanPhamService;
	@Autowired
	ProductCategoryService productCategoryService;
	@Autowired
	EventService eventService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private DAFileUploadService fileUploadService;
	@Autowired
	private DAFileUploadServiceImpl fileUploadServiceImpl;
	
	
	@Value("${attachment.path}")
	private String attachmentPath;

	@Value("${localhost.path}")
	private String hostPath;

	@Value("${server.servlet.context-path}")
	private String contextPath;

	@Value("${attachment.context.path}")
	private String attachmentContextPath;
	@RequestMapping(value = "/getListProductByPage", method = RequestMethod.POST)
	public ResponseEntity<List<ProductDto>> getPage(@RequestBody SearchDto dto ) {
		List<ProductDto> results = sanPhamService.searchByPage(dto).getContent();
		return new ResponseEntity<List <ProductDto>>(results, HttpStatus.OK);
	}
	
	@RequestMapping(value="/getProductCategory", method = RequestMethod.GET)
	public List<ProductCategoryDto> getAllCategory() {
		return productCategoryService.getAllCategory();
	}
	
	@RequestMapping(value="/getProductEvent", method = RequestMethod.GET)
	public List<EventDto> getProductEvent() {
		return eventService.getPage(9, 1).getContent();
	}
	
	@RequestMapping(path = "/getImage/{filename}/{type}", method = RequestMethod.GET)
	public void getImage(HttpServletResponse response, @PathVariable String filename, @PathVariable String type) throws IOException {
		String path = "";
		if(env.getProperty("da.file.folder") != null) {
			path = env.getProperty("da.file.folder");
		}
	    File file = new File(path+filename+"."+type);
//	    if(file.exists()) {
	        String contentType = "application/octet-stream";
	        response.setContentType(contentType);
	        OutputStream out = response.getOutputStream();
	        FileInputStream in = new FileInputStream(file);
	        // copy from in to out
	        IOUtils.copy(in, out);
	        out.close();
	        in.close();
//	    }else {
//	        throw new FileNotFoundException();
//	    }
	}
	
	@RequestMapping(path = "/image/{filename:.+}", method = RequestMethod.GET)
	public void getImageByName(HttpServletResponse response, @PathVariable(value = "filename") String filename) throws IOException {
		String path = "";
		if(env.getProperty("da.file.folder") != null) {
			path = env.getProperty("da.file.folder");
		}
	    File file = new File(path+filename);
	    if(file.exists()) {
	        String contentType = "application/octet-stream";
	        response.setContentType(contentType);
	        OutputStream out = response.getOutputStream();
	        FileInputStream in = new FileInputStream(file);
	        // copy from in to out
	        IOUtils.copy(in, out);
	        out.close();
	        in.close();
	    }else {
	        throw new FileNotFoundException();
	    }
	}
	@RequestMapping(method = RequestMethod.POST)
	public CustomerDto saveCustomer(@RequestBody CustomerDto customerDto) {
		return customerService.saveCustomer(customerDto);
	}
	
	@RequestMapping(value = "/image", method = RequestMethod.POST)
	public ResponseEntity<ProductDto> uploadImage(@RequestParam("file") MultipartFile uploadFile,
			@RequestParam("productID") UUID productID) {
		ProductDto dto = fileUploadService.uploadProductImage(uploadFile, productID);
		return new ResponseEntity<>(dto, dto != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/imageSlide", method = RequestMethod.POST)
	public void imageSlide(@RequestParam("file") MultipartFile uploadFile,
			@RequestParam("slideShow") UUID slideShowId) {
		fileUploadServiceImpl.uploadSlideShow(uploadFile, slideShowId);
	}
 

	@RequestMapping(path = "/getImage/{filename}", method = RequestMethod.GET)
	public void getImage(HttpServletResponse response, @PathVariable String filename) throws IOException {
		String path = "";
		File file = new File(attachmentPath + filename);
		if (file.exists()) {
			String contentType = "application/octet-stream";
			response.setContentType(contentType);
			OutputStream out = response.getOutputStream();
			FileInputStream in = new FileInputStream(file);
			// copy from in to out
			IOUtils.copy(in, out);
			out.close();
			in.close();
		} else
			throw new FileNotFoundException();
	}
}

