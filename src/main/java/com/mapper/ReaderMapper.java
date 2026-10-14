package com.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.entity.Reader;

import java.util.List;

/**
 * Reader Mapper - 读者表的 CRUD 及多对多关联查询
 *
 * 继承 BaseMapper<Reader> 获得通用 CRUD：
 *   insert / selectById / updateById / deleteById / selectList / selectCount / deleteBatchIds ...
 *
 * 只保留 BaseMapper 替代不了的 selectAllWithBooks（多对多关联，XML collection 注入）
 */
public interface ReaderMapper extends BaseMapper<Reader> {

    /**
     * 查询所有读者，同时查出每人借阅的图书列表（多对多关联）
     * 通过 book_borrow 中间表关联，XML 实现：ReaderMapper.xml -> resultMap + collection
     */
    List<Reader> selectAllWithBooks();

    // insert / selectById / updateById / deleteById 全部由 BaseMapper 零 SQL 提供
    // 手写的 update/delete 已删掉（MP 会接管它们导致参数名不匹配）
    // 统一用 BaseMapper.updateById(reader) / BaseMapper.deleteById(id)
}