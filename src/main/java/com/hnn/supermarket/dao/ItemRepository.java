package com.hnn.supermarket.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    Item getItemByCode(String code);

    @Query(value = "select i from Item i left join fetch i.pricingHistories where i.code = :code")
    Item getItemWithPricingHistories(String code);
}
