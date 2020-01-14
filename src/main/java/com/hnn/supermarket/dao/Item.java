package com.hnn.supermarket.dao;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ITEM_SEQ_GENERATOR")
    @SequenceGenerator(name = "ITEM_SEQ_GENERATOR", sequenceName = "ITEM_SEQ", allocationSize = 1)
    private Long id;
    private String code;
    private String name;
    private String description;

    @ManyToOne
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;
    private String labels;
    private BigDecimal price;

    @OneToOne
    @JoinColumn(name = "PRICING_POLICY_ID")
    private PricingPolicy pricingPolicy;

    public Item() {
    }

    public Item(String code, String name, BigDecimal price) {
        this.code = code;
        this.name = name;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
    	if (code == null) {
			throw new IllegalArgumentException("The code of item should not be null"); 
		}
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
    	if (name == null) {
			throw new IllegalArgumentException("The name of item should not be null"); 
		}
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getLabels() {
        return labels;
    }

    public void addLabel(String label) {
        if (labels == null) {
            labels = label;
        } else {
            labels = labels + ";" + label;
        }
    }

    public void setLabels(String labels) {
        this.labels = labels;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
    	if (price == null) {
			throw new IllegalArgumentException("The price of item should not be null"); 
		}
        this.price = price;
    }

    public PricingPolicy getPricingPolicy() {
        return pricingPolicy;
    }

    /**
     * If the method is called with a null value, then replace it with the DEFAULT_PRICING_POLICY
     * @param pricingPolicy
     */
    public void setPricingPolicy(PricingPolicy pricingPolicy) {
    	if (pricingPolicy == null) {
    		this.pricingPolicy = PricingPolicy.DEFAULT_PRICING_POLICY;
    	} else {
    		this.pricingPolicy = pricingPolicy;
    	}
    }

    public boolean hasPricingPolicy() {
        return !getPricingPolicy().getCode().equals(PricingPolicy.PP_BASIC);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(id, item.id) &&
                Objects.equals(code, item.code) &&
                Objects.equals(name, item.name) &&
                Objects.equals(labels, item.labels) &&
                Objects.equals(price, item.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, name, labels, price);
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", category=" + category +
                ", labels=" + labels +
                ", price=" + price +
                ", pricingPolicy=" + (pricingPolicy == null ? "null" : pricingPolicy.getCode()) +
                '}';
    }
}
