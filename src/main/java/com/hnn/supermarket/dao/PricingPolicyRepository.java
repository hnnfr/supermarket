package com.hnn.supermarket.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PricingPolicyRepository extends JpaRepository<PricingPolicy, Long> {
    PricingPolicy getPricingPolicyByCode(String code);

    @Query("select pp from PricingPolicy pp left join fetch pp.items where pp.code = :code")
    PricingPolicy getPricingPolicyWithItems(String code);
}
