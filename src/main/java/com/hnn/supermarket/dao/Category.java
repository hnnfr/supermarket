package com.hnn.supermarket.dao;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Category {
    public static final String FRUITS_LEGUMES = "FRUITS_LEGUMES";
    public static final String VIANDES_POISSONS = "VIANDES_POISSONS";
    public static final String PAINS_PATISSERIES = "PAINS_PATISSERIES";
    public static final String FROMAGE_CHARCUTERIE = "FROMAGE_CHARCUTERIE";
    public static final String SURGELES = "SURGELES";
    public static final String BOISSONS_SANS_ALCOOL = "BOISSONS_SANS_ALCOOL";
    public static final String ALCOOLS = "ALCOOLS";
    public static final String OEUF_LAIT = "OEUF_LAIT";
    public static final String DEFAULT_CATEGORY_CODE = "DEFAULT_CATEGORY";

    public static final Category DEFAULT_CATEGORY = new Category(Category.DEFAULT_CATEGORY_CODE, "Default Category");

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CATEGORY_SEQ_GENERATOR")
    @SequenceGenerator(name = "CATEGORY_SEQ_GENERATOR", sequenceName = "CATEGORY_SEQ", allocationSize = 1)
    private Long id;
    private String code;
    private String name;
    private String description;

    @OneToMany(mappedBy = "category", cascade = CascadeType.DETACH)
    private Set<Item> items;

    public Category() {
    }

    public Category(String code, String name) {
        this(code, name, null);
    }

    public Category(String code, String name, String description) {
        this.code = code;
        this.name = name;
        this.description = description;
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

    public Set<Item> getItems() {
        return items;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }

    public void addItem(Item item) {
        if (items == null) items = new HashSet<>();
        items.add(item);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(id, category.id) &&
                Objects.equals(code, category.code) &&
                Objects.equals(name, category.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, name);
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
