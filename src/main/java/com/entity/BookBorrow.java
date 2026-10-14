package com.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDate;

/**
 * 借阅记录表实体（多对多中间表，带冗余字段）
 *
 * MP 注解：
 *   @TableName("book_borrow")      → 对应数据库表名
 *   @TableId(type = IdType.AUTO)   → 主键自增
 *
 * 注意：bookName/bookAuthor/readerName 是冗余字段，也在表里，是合法列，不需要 exist=false
 *       这张表没有关联对象/集合，所以全是数据库列
 */
@TableName("book_borrow")
public class BookBorrow {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer bookId;
    private String bookName;
    private String bookAuthor;
    private Integer readerId;
    private String readerName;
    private LocalDate borrowDate;

    public Integer getId()              { return id; }
    public void setId(Integer id)      { this.id = id; }

    public Integer getBookId()          { return bookId; }
    public void setBookId(Integer bookId) { this.bookId = bookId; }

    public String getBookName()         { return bookName; }
    public void setBookName(String bookName) { this.bookName = bookName; }

    public String getBookAuthor()       { return bookAuthor; }
    public void setBookAuthor(String bookAuthor) { this.bookAuthor = bookAuthor; }

    public Integer getReaderId()        { return readerId; }
    public void setReaderId(Integer readerId) { this.readerId = readerId; }

    public String getReaderName()       { return readerName; }
    public void setReaderName(String readerName) { this.readerName = readerName; }

    public LocalDate getBorrowDate()    { return borrowDate; }
    public void setBorrowDate(LocalDate borrowDate) { this.borrowDate = borrowDate; }

    @Override
    public String toString() {
        return "BookBorrow{id=" + id + ", bookId=" + bookId + ", bookName='" + bookName
                + "', bookAuthor='" + bookAuthor + "', readerId=" + readerId
                + ", readerName='" + readerName + "', borrowDate=" + borrowDate + "}";
    }
}