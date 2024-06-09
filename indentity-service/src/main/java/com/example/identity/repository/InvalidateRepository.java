package com.example.identity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.identity.entity.InvalidateToken;

@Repository
public interface InvalidateRepository extends JpaRepository<InvalidateToken, String> {}
