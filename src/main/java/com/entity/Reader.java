package com.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.ArrayList;
import java.util.List;

/**
 * 读者表实体
 *
 * MP 注解：
 *   @TableName("reader")           → 对应数据库表名
 *   @TableId(type = IdType.AUTO)   → 主键自增
 *   @TableField(exist = false)     → books 不是 reader 表的列（多对多集合，XML collection 注入）
 */
@TableName("reader")
public class Reader {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String name;
    private String phone;

    /**
     * 多对多关联：当前读者借阅过的图书列表
     * 不是 reader 表的列，是 XML resultMap + collection 注入的
     */
    @TableField(exist = false)
    private List<Book> books = new ArrayList<>();

    public Integer getId()          { return id; }
    public void setId(Integer id)  { this.id = id; }

    public String getName()         { return name; }
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