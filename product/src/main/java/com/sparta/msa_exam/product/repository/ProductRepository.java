package com.sparta.msa_exam.product.repository;

import com.sparta.msa_exam.product.entity.Product;
import com.sparta.msa_exam.product.entity.dto.ProductResDto;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByName(String name);

    Page<Product> findAll(Pageable pageable);

}
