package com.entity;

import java.time.LocalDate;

public class BookBorrow {

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