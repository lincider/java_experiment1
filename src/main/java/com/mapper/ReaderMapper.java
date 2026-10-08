package com.mapper;

import com.entity.Reader;
import java.util.List;

/**
 * Reader Mapper - 读者表的 CRUD 及多对多关联查询
 */
public interface ReaderMapper {

    /**
     * 查询所有读者，同时查出每人借阅的图书列表（多对多关联）
     * 通过 book_borrow 中间表关联，XML 实现：ReaderMapper.xml -> resultMap + collection
     */
    List<Reader> selectAllWithBooks();

    /**
     * 根据 ID 查询单个读者
     */
    Reader selectById(Integer id);

    /**
     * 新增读者
     */
    int insert(Reader r);

    /**
     * 更新读者信息
     */
    int update(Reader r);

    /**
     * 根据 ID 删除读者
     */
    int delete(Integer id);
}
