package com.mapper;

import com.entity.BookBorrow;
import java.util.List;

/**
 * BookBorrow Mapper - 借阅记录表的 CRUD
 * 注意：book_borrow 表含冗余字段（bookName/bookAuthor/readerName），
 *       selectAll() 可直接返回借阅详情，无需 JOIN 其他表
 */
public interface BookBorrowMapper {

    /**
     * 查询所有借阅记录（直接读取冗余字段，不做关联查询）
     */
    List<BookBorrow> selectAll();

    /**
     * 根据 ID 查询借阅记录
     */
    BookBorrow selectById(Integer id);

    /**
     * 新增借阅记录
     */
    int insert(BookBorrow bb);

    /**
     * 根据 ID 删除借阅记录
     */
    int delete(Integer id);
}
