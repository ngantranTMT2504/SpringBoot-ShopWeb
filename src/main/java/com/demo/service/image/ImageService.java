package com.demo.service.image;

import java.io.IOException;
import java.sql.SQLException;

import javax.sql.rowset.serial.SerialBlob;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.demo.exception.ResourceNotFoundException;
import com.demo.model.Image;
import com.demo.repository.ImageRepository;
import com.demo.service.book.IBookService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImageService implements IImageService {
	private final ImageRepository imageRepository;
	private final IBookService bookService;

	@Override
	public Image getImageById(Long id) {
		return imageRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Image not found"));
	}

	@Override
	public void deleteImageById(Long id) {
		imageRepository.findById(id).ifPresentOrElse(imageRepository::delete, () -> {
			throw new ResourceNotFoundException("Image not found");
		});
	}

	@Override
	public Image saveImage(MultipartFile file, Long bookId) {
		return null;
	}

	@Override
	public void updateImage(MultipartFile file, Long imageId) {
		Image image = getImageById(imageId);
		try {
			image.setFileName(file.getOriginalFilename());
			image.setFileName(file.getOriginalFilename());
			image.setImage(new SerialBlob(file.getBytes()));
			imageRepository.save(image);
		} catch (IOException | SQLException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

}
