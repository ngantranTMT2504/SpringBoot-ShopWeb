package com.demo.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.model.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {

}
