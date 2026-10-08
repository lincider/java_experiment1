package com.entity;

import java.util.ArrayList;
import java.util.List;

public class Category {

    private Integer id;
    private String name;
    private List<Book> books = new ArrayList<>();

    public Integer getId()          { return id; }
    public void setId(Integer id)   { this.id = id; }

    public String getName()         { return name; }
    public void setName(String name) { this.name = name; }

    public List<Book> getBooks()           { return books; }
    public void setBooks(List<Book> books) { this.books = books; }

    @Override
    public String toString() {
        return "Category{id=" + id + ", name='" + name + "'}";
    }
}