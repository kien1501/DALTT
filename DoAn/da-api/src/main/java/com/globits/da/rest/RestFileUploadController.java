package com.globits.da.rest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.globits.da.dto.ProductDto;
import com.globits.da.service.DAFileUploadService;
import com.globits.da.service.impl.DAFileUploadServiceImpl;

@RestController
@RequestMapping(path = "/api/upload")
public class RestFileUploadController {

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
