package com.odat.fastrans.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.odat.fastrans.entity.Product;

public interface ProductRepo extends JpaRepository<Product, Long>{

}	
