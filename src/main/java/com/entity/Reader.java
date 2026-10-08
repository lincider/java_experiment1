package com.entity;

import java.util.ArrayList;
import java.util.List;

public class Reader {

    private Integer id;
    private String name;
    private String phone;
    private List<Book> books = new ArrayList<>();
    public void setName(String name) { this.name = name; }

    public String getPhone()         { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public List<Book> getBooks()           { return books; }
    public void setBooks(List<Book> books)  { this.books = books; }

    @Override
    public String toString() {
        return "Reader{id=" + id + ", name='" + name + "', phone='" + phone + "'}";
    }
}