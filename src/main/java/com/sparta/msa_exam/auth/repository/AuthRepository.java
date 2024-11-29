package com.sparta.msa_exam.auth.repository;

import com.sparta.msa_exam.auth.entity.Auth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRepository extends JpaRepository<Long, Auth> {

}
