package com.mapper;

import com.entity.Book;
import java.util.List;

/**
 * Book Mapper - 图书表的 CRUD 及多对一关联查询
 */
public interface BookMapper {

    /**
     * 查询所有图书，同时查出每本书所属的分类信息（多对一关联）
     * XML 实现：BookMapper.xml -> resultMap + association
     */
    List<Book> selectAllWithCategory();

    /**
     * 根据 ID 查询单本书
     */
    Book selectById(Integer id);

    /**
     * 新增图书
     */
    int insert(Book b);

    /**
     * 更新图书信息
     */
    int update(Book b);

    /**
     * 根据 ID 删除图书
     */
    int delete(Integer id);
}
