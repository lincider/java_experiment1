package com.mapper;

import com.entity.Category;
import com.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * CategoryMapper 测试类
 * 验证功能：分类表的 CRUD 及一对多关联查询（分类 -> 图书列表）
 */
public class CategoryMapperTest {

    private SqlSession session;
    private CategoryMapper mapper;

    /** 每个测试方法执行前：获取 SqlSession 和 Mapper 实例 */
    @Before
    public void setUp() {
        session = MyBatisUtil.openSession();
        mapper = session.getMapper(CategoryMapper.class);
    }

    /** 每个测试方法执行后：关闭 SqlSession 释放连接 */
    @After
    public void tearDown() {
        session.close();
    }

    /**
     * 测试一对多关联查询：查询所有分类，同时查出每个分类下的图书列表
     * 验证：分类 1（计算机）下应该有 2 本书，分类 2（文学）下应该有 2 本书
     */
    @Test
    public void testSelectAllWithBooks() {
        List<Category> list = mapper.selectAllWithBooks();
        System.out.println("===== 一对多：分类 + 图书列表 =====");
        for (Category c : list) {
            System.out.println("分类：「" + c.getName() + "」，共 " + c.getBooks().size() + " 本书");
            c.getBooks().forEach(book ->
                System.out.println("  - " + book.getName() + " / " + book.getAuthor())
            );
        }
        // 断言：有数据
        assert list.size() > 0 : "查询结果为空";
        // 断言：第一个分类下有图书（验证一对多关联生效）
        assert list.get(0).getBooks().size() > 0 : "一对多关联未生效，books 为空";
    }

    /** 测试根据 ID 查询分类 */
    @Test
    public void testSelectById() {
        Category c = mapper.selectById(1);
        System.out.println("===== 根据 ID 查询分类 =====");
        System.out.println(c);
        assert c != null : "根据 ID 查询失败";
        assert "计算机".equals(c.getName()) : "分类名称不匹配";
    }

    /** 测试新增分类 */
    @Test
    public void testInsert() {
        Category c = new Category();
        c.setName("哲学");
        int rows = mapper.insert(c);
        session.commit();  // 增删改需要手动提交事务
        System.out.println("===== 新增分类 =====");
        System.out.println("影响行数：" + rows + "，自增 ID：" + c.getId());
        assert rows == 1 : "新增失败";
    }

    /** 测试更新分类 */
    @Test
    public void testUpdate() {
        Category c = mapper.selectById(1);
        String oldName = c.getName();
        c.setName("计算机技术");
        int rows = mapper.update(c);
        session.commit();
        System.out.println("===== 更新分类 =====");
        System.out.println("影响行数：" + rows);
        assert rows == 1 : "更新失败";
        // 恢复原名，避免影响其他测试
        c.setName(oldName);
        mapper.update(c);
        session.commit();
    }

    /** 测试删除分类 */
    @Test
    public void testDelete() {
        // 先新增一个临时分类用于删除测试
        Category temp = new Category();
        temp.setName("临时分类");
        mapper.insert(temp);
        session.commit();
        Integer tempId = temp.getId();

        int rows = mapper.delete(tempId);
        session.commit();
        System.out.println("===== 删除分类 =====");
        System.out.println("影响行数：" + rows);
        assert rows == 1 : "删除失败";
    }
}