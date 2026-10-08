package com.mapper;

import com.entity.Category;
import java.util.List;

/**
 * Category Mapper - 图书分类表的 CRUD 及一对多关联查询
 */
public interface CategoryMapper {

    /**
     * 查询所有分类，同时查出每个分类下的图书列表（一对多关联）
     * XML 实现：CategoryMapper.xml -> resultMap + collection
     */
    List<Category> selectAllWithBooks();

    /**
     * 根据 ID 查询单个分类
     */
    Category selectById(Integer id);

    /**
     * 新增分类
     */
    int insert(Category c);

    /**
     * 更新分类信息
     */
    int update(Category c);

    /**
     * 根据 ID 删除分类
     */
    int delete(Integer id);
}
