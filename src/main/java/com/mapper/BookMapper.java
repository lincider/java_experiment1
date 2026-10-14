package com.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.entity.Book;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * Book Mapper - 图书表的 CRUD 及多对一关联查询
 *
 * 继承 BaseMapper<Book> 后，免费获得以下通用方法（零 SQL）：
 *   insert(Book)           → INSERT INTO book (..) VALUES (..)
 *   selectById(id)         → SELECT * FROM book WHERE id = ?
 *   updateById(Book)       → UPDATE book SET .. WHERE id = ?（全字段覆盖）
 *   deleteById(id)         → DELETE FROM book WHERE id = ?
 *   selectList(null)       → SELECT * FROM book
 *   selectCount(null)      → SELECT COUNT(*) FROM book
 *   selectOne(Wrapper)     → 条件查一条
 *   selectList(Wrapper)    → 条件查列表
 *   deleteBatchIds(ids)    → DELETE FROM book WHERE id IN (..)
 *
 * 下面手写的方法是 BaseMapper 替代不了的（多对一关联 / 动态 SQL / foreach 批量插入）
 * 这些方法的 SQL 仍然写在 BookMapper.xml 里
 */
public interface BookMapper extends BaseMapper<Book> {

    /**
     * 查询所有图书，同时查出每本书所属的分类信息（多对一关联）
     * XML 实现：BookMapper.xml -> resultMap + association
     */
    List<Book> selectAllWithCategory();

    // insert / selectById / updateById / deleteById 全部由 BaseMapper 零 SQL 提供
    // 手写的 update/delete 已删掉（MP 会接管它们导致参数名不匹配）
    // 统一用 BaseMapper.updateById(book) / BaseMapper.deleteById(id)

    /**
     * 动态更新图书（set 标签）
     * 只更新 Book 对象中非 null 的字段，null 字段对应的列保持原值不变
     * 业务场景：管理员只想改图书价格 / 分类 / 书名中的某一个时
     */
    int dynamicUpdate(Book b);

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

    // ===================== foreach：批量操作 =====================

    /**
     * 批量删除图书（用 foreach 拼接 IN 子句）
     * 生成 SQL ：DELETE FROM book WHERE id IN (?, ?, ...)
     *
     * 注：BaseMapper 也有 deleteBatchIds(ids)，功能一样但名字不同，两个共存
     *
     * @param ids 要删除的图书 ID 集合
     * @return 实际删除的行数
     */
    int batchDelete(@Param("ids") List<Integer> ids);

    /**
     * 批量新增图书（用 foreach 拼接多组 VALUES）
     * 生成 SQL ：INSERT INTO book (name, author, price, category_id)
     *           VALUES (?, ?, ?, ?), (?, ?, ?, ?), ...
     *
     * @param books 要新增的图书集合
     * @return 实际插入的行数
     */
    int batchInsert(@Param("list") List<Book> books);
}