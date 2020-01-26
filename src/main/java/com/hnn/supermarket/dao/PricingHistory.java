package com.hnn.supermarket.dao;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "PRICING_HISTORY")
public class PricingHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PH_SEQ_GENERATOR")
    @SequenceGenerator(name = "PH_SEQ_GENERATOR", sequenceName = "PH_SEQ", allocationSize = 1)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "ITEM_ID")
    private Item item; 

    @ManyToOne
    @JoinColumn(name = "PRICING_POLICY_ID")
    private PricingPolicy pricingPolicy;

    @Column(name = "CREATION_DATE")
    private LocalDate creationDate;

    public PricingHistory() {
    }

    public PricingHistory(Item item, PricingPolicy pricingPolicy) {
    	this.item = item;
    	this.pricingPolicy = pricingPolicy; 
    	this.creationDate = LocalDate.now();
    	item.addPricingHistory(this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
        item.addPricingHistory(this);
    }

    public PricingPolicy getPricingPolicy() {
        return pricingPolicy;
    }

    public void setPricingPolicy(PricingPolicy pricingPolicy) {
        this.pricingPolicy = pricingPolicy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PricingHistory that = (PricingHistory) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(creationDate, that.creationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, creationDate);
    }

    @Override
    public String toString() {
        return "PricingHistory{" +
                "id=" + id +
                "item=" + item.getCode() +
                "pricingPolicy=" + pricingPolicy.getCode() + 
                ", creationDate=" + creationDate +
                '}';
    }
}
