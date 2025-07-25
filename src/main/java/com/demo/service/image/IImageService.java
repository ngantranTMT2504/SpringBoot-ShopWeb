package com.demo.service.image;

import org.springframework.web.multipart.MultipartFile;

import com.demo.model.Image;

public interface IImageService {
	Image getImageById(Long id);
	void deleteImageById(Long id);
	Image saveImage(MultipartFile file, Long bookId);
	void updateImage(MultipartFile file, Long imageId);
}
