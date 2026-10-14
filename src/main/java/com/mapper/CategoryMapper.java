package com.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.entity.Category;

import java.util.List;

/**
 * Category Mapper - 图书分类表的 CRUD 及一对多关联查询
 *
 * 继承 BaseMapper<Category> 获得通用 CRUD：
 *   insert / selectById / updateById / deleteById / selectList / selectCount / deleteBatchIds ...
 *
 * 只保留 BaseMapper 替代不了的 selectAllWithBooks（一对多关联，XML collection 注入）
 */
public interface CategoryMapper extends BaseMapper<Category> {

    /**
     * 查询所有分类，同时查出每个分类下的图书列表（一对多关联）
     * XML 实现：CategoryMapper.xml -> resultMap + collection
     */
    List<Category> selectAllWithBooks();

    // insert / selectById / updateById / deleteById 全部由 BaseMapper 零 SQL 提供
    // 手写的 update/delete 已删掉（MP 会接管它们导致参数名不匹配）
    // 统一用 BaseMapper.updateById(category) / BaseMapper.deleteById(id)
}