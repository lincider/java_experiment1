package com.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.ArrayList;
import java.util.List;

/**
 * 分类表实体
 *
 * MP 注解：
 *   @TableName("category")          → 对应数据库表名
 *   @TableId(type = IdType.AUTO)     → 主键自增
 *   @TableField(exist = false)       → books 不是 category 表的列（一对多集合，由 XML collection 注入）
 */
@TableName("category")
public class Category {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String name;

    /**
     * 一对多关联：当前分类下的图书列表
     * 不是 category 表的列，是 XML resultMap + collection 注入的
     */
    @TableField(exist = false)
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