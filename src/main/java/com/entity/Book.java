package com.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;

/**
 * 图书表实体
 *
 * MP 注解说明：
 *   @TableName("book")              → 显式指定数据库表名（避免 MP 猜测偏差）
 *   @TableId(value = "id", type = IdType.AUTO) → 主键自增，insert 后自动回填 id
 *   @TableField(exist = false)       → 标记非数据库列（关联对象，由 XML resultMap 注入）
 */
@TableName("book")
public class Book {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String name;
    private String author;
    private BigDecimal price;
    private Integer categoryId;

    /**
     * 多对一关联：当前图书所属的分类对象
     * 不是 book 表的列，是 XML resultMap + association 注入的
     */
    @TableField(exist = false)
    private Category category;

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

    public Category getCategory()          { return category; }
    public void setCategory(Category category) { this.category = category; }

    @Override
    public String toString() {
        return "Book{id=" + id + ", name='" + name + "', author='" + author + "', price=" + price + ", categoryId=" + categoryId + "}";
    }
}