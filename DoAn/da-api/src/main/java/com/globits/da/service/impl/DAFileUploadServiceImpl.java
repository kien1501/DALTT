package com.globits.da.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.globits.da.domain.Image;
import com.globits.da.domain.Slideshow;
import com.globits.da.dto.ProductDto;
import com.globits.da.repository.ImageRepository;
import com.globits.da.repository.SlideShowRepository;
import com.globits.da.service.DAFileUploadService;
import com.globits.da.service.ProductService;

@Service
public class DAFileUploadServiceImpl implements DAFileUploadService {

	@Value("${attachment.path}")
	private String attachmentPath;

	@Value("${localhost.path}")
	private String hostPath;

	@Value("${server.servlet.context-path}")
	private String contextPath;

	@Value("${attachment.context.path}")
	private String attachmentContextPath;

	@Autowired
	private ProductService productService;

	@Autowired
	ImageRepository imageRepository;

	@Autowired
	SlideShowRepository slideShowRepository;

	@Autowired
	ServletContext context;

	@Override
	public ProductDto uploadProductImage(MultipartFile uploadFile, UUID productID) {
		List<UUID> list = new ArrayList<>();
		Image dto = new Image();
		String absolutePath = attachmentPath + "/";
		String fileName = uploadFile.getOriginalFilename().split("\\.(?=[^\\.]+$)")[0];
		String extension = uploadFile.getOriginalFilename().split("\\.(?=[^\\.]+$)")[1];
		UUID fileNameImage = UUID.randomUUID();
		try{
			File fileToBeSaved = new File(absolutePath, fileNameImage + "." + extension);
			uploadFile.transferTo(fileToBeSaved);
		} catch(Exception e){
			System.out.println(e.getMessage());
		}

		String mainImageUrl = hostPath + "/api/upload/getImage/" + fileNameImage + "." + extension;
		dto.setUrl(mainImageUrl);
		Image entity = imageRepository.save(dto);
		list.add(entity.getId());

		ProductDto dtoa = productService.updateImage(list, productID);
		return dtoa;
	}

	public void uploadSlideShow(MultipartFile uploadFile, UUID slideShowId) {
		Slideshow dto;
		if(slideShowId == null)
			dto = new Slideshow();
		else
			dto = slideShowRepository.getOne(slideShowId);

		String extension = uploadFile.getOriginalFilename().split("\\.(?=[^\\.]+$)")[1];
		UUID fileNameImage = UUID.randomUUID();

		try{
			File fileToBeSaved = new File(attachmentPath, fileNameImage + "." + extension);
			uploadFile.transferTo(fileToBeSaved);
		} catch(Exception e){
			System.out.println(e.getMessage());
		}

		String mainImageUrl = hostPath + "/api/upload/getImage/" + fileNameImage + "." + extension;
		dto.setUrl(mainImageUrl);

		slideShowRepository.save(dto);
	}

}
