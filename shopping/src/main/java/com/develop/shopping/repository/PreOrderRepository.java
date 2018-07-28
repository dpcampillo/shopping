package com.develop.shopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.develop.shopping.domain.PreOrder;

@Repository
public interface PreOrderRepository extends JpaRepository<PreOrder, Long>, JpaSpecificationExecutor<PreOrder>{
}
