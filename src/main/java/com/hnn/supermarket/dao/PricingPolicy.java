package com.hnn.supermarket.dao;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "PRICING_POLICY")
public class PricingPolicy {
    public static final String PP_BASIC = "BASIC";
    public static final String PP_2019_NOEL_REDUCTION = "2019_NOEL_REDUCTION";
    public static final String PP_NOV_2019_BUY_2_GET_1_FREE = "Nov_2019_BUY_2_GET_1_FREE";
    public static final String PP_GROUP_3_FOR_1_EURO = "GROUP_3_FOR_1_EURO";
    public static final String PP_DEC_2019_BUY_2_GET_1_FREE = "Dec_2019_BUY_2_GET_1_FREE";
    public static final String PP_JAN_2020_BUY_2_GET_1_FREE = "Jan_2020_BUY_2_GET_1_FREE";
    
    public static final PricingPolicy DEFAULT_PRICING_POLICY = new PricingPolicy(PricingPolicy.PP_BASIC, "No special policy", "", LocalDate.now());

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PP_SEQ_GENERATOR")
    @SequenceGenerator(name = "PP_SEQ_GENERATOR", sequenceName = "PP_SEQ", allocationSize = 1)
    private Long id;
    private String code;
    private String name;
    private String description;

    @Column(name = "CREATION_DATE")
    private LocalDate creationDate;
    @Column(name = "APPLIED_START_DATE")
    private LocalDate appliedStartDate;
    @Column(name = "APPLIED_END_DATE")
    private LocalDate appliedEndDate;

    @OneToMany(mappedBy = "pricingPolicy", fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    private Set<Item> items;

    public PricingPolicy() {
    }

    public PricingPolicy(String code, String name, String description, LocalDate creationDate) {
        this(code, name, description, creationDate, null, null);
    }

    public PricingPolicy(String code, String name, String description, LocalDate creationDate, LocalDate appliedStartDate, LocalDate appliedEndDate) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.creationDate = creationDate;
        this.appliedStartDate = appliedStartDate;
        this.appliedEndDate = appliedEndDate;
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
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDate getAppliedStartDate() {
        return appliedStartDate;
    }

    public void setAppliedStartDate(LocalDate appliedStartDate) {
        this.appliedStartDate = appliedStartDate;
    }

    public LocalDate getAppliedEndDate() {
        return appliedEndDate;
    }

    public void setAppliedEndDate(LocalDate appliedEndDate) {
        this.appliedEndDate = appliedEndDate;
    }

    public Set<Item> getItems() {
        return items;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
        if (items != null && items.size() > 0)
            items.stream().forEach(i -> i.setPricingPolicy(this));
    }

    public void addItem(Item item) {
        if (items == null) items = new HashSet<>();
        items.add(item);
        item.setPricingPolicy(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PricingPolicy that = (PricingPolicy) o;
        return Objects.equals(id, that.id) &&
                code.equals(that.code) &&
                Objects.equals(name, that.name) &&
                Objects.equals(creationDate, that.creationDate) &&
                Objects.equals(appliedStartDate, that.appliedStartDate) &&
                Objects.equals(appliedEndDate, that.appliedEndDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, name, creationDate, appliedStartDate, appliedEndDate);
    }

    @Override
    public String toString() {
        return "PricingPolicy{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", creationDate=" + creationDate +
                ", appliedStartDate=" + appliedStartDate +
                ", appliedEndDate=" + appliedEndDate +
                '}';
    }
}
