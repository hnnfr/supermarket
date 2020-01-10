package com.hnn.supermarket.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PricingPolicyRepository extends JpaRepository<PricingPolicy, Long> {
    PricingPolicy getPricingPolicyByCode(String code);
}
