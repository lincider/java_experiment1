package com.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.entity.BookBorrow;

/**
 * BookBorrow Mapper - 借阅记录表的 CRUD
 *
 * 继承 BaseMapper<BookBorrow> 后，全部通用方法零 SQL 获得：
 *   insert(BookBorrow)       → INSERT INTO book_borrow (..) VALUES (..)
 *   selectById(id)           → SELECT * FROM book_borrow WHERE id = ?
 *   updateById(BookBorrow)   → UPDATE book_borrow SET .. WHERE id = ?
 *   deleteById(id)           → DELETE FROM book_borrow WHERE id = ?
 *   selectList(null)         → SELECT * FROM book_borrow（替代了原来的 selectAll）
 *   selectCount(null)        → SELECT COUNT(*) FROM book_borrow
 *   selectOne(Wrapper)       → 条件查一条
 *   selectList(Wrapper)      → 条件查列表
 *
 * 这个表没有关联查询（冗余字段设计），所以 BaseMapper 全能覆盖
 */
public interface BookBorrowMapper extends BaseMapper<BookBorrow> {

    // 原来手写的 insert / selectById / selectAll 全删掉
    // BaseMapper.selectList(null) 就能返回全部，等价于原来的 selectAll()
    // BaseMapper.insert() 就能插入，BaseMapper.selectById() 就能按 ID 查

    // deleteById 已由 BaseMapper 零 SQL 提供
    // 手写的 delete 已删掉（MP 会接管它导致参数名不匹配）
}