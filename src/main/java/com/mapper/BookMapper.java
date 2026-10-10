package com.mapper;

import com.entity.Book;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
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

    /**
     * 多条件动态查询图书
     * 哪个参数非 null（且 name/author 非空串）就拼哪个条件
     * 所有参数都传 null 时返回全部图书
     */
    List<Book> selectByCondition(@Param("name") String name,
                                 @Param("author") String author,
                                 @Param("categoryId") Integer categoryId,
                                 @Param("minPrice") BigDecimal minPrice,
                                 @Param("maxPrice") BigDecimal maxPrice);
}