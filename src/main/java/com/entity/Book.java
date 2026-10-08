package com.entity;

import java.math.BigDecimal;

public class Book {

    private Integer id;
    private String name;
    private String author;
    private BigDecimal price;
    private Integer categoryId;

    public Integer getId()             { return id; }
    public void setId(Integer id)     { this.id = id; }

    public String getName()            { return name; }
    public void setName(String name)  { this.name = name; }

    public String getAuthor()          { return author; }
    public void setAuthor(String author) { this.author = author; }

    public BigDecimal getPrice()       { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public Integer getCategoryId()         { return categoryId; }
    public void setCategoryId(Integer categoryId) { this.categoryId = categoryId; }

    @Override
    public String toString() {
        return "Book{id=" + id + ", name='" + name + "', author='" + author + "', price=" + price + ", categoryId=" + categoryId + "}";
    }
}