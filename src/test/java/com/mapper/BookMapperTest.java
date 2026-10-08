package com.mapper;

import com.entity.Book;
import com.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

/**
 * BookMapper 测试类
 * 验证功能：图书表的 CRUD 及多对一关联查询（图书 -> 所属分类）
 */
public class BookMapperTest {

    private SqlSession session;
    private BookMapper mapper;

    @Before
    public void setUp() {
        session = MyBatisUtil.openSession();
        mapper = session.getMapper(BookMapper.class);
    }

    @After
    public void tearDown() {
        session.close();
    }

    /**
     * 测试多对一关联查询：查询所有图书，同时查出每本书所属的分类信息
     * 验证：图书应该能关联到其所属分类对象
     */
    @Test
    public void testSelectAllWithCategory() {
        List<Book> list = mapper.selectAllWithCategory();
        System.out.println("===== 多对一：图书 + 所属分类 =====");
        for (Book b : list) {
            System.out.println("书名：「" + b.getName() + "」-> 分类：「" + b.getCategoryId() + "」");
        }
        assert list.size() > 0 : "查询结果为空";
        // 验证第一本书有 categoryId（说明 association 成功注入了分类信息）
        assert list.get(0).getCategoryId() != null : "多对一关联未生效";
    }

    /** 测试根据 ID 查询图书 */
    @Test
    public void testSelectById() {
        Book b = mapper.selectById(1);
        System.out.println("===== 根据 ID 查询图书 =====");
        System.out.println(b);
        assert b != null : "根据 ID 查询失败";
        assert "Java编程思想".equals(b.getName()) : "图书名称不匹配";
    }

    /** 测试新增图书 */
    @Test
    public void testInsert() {
        Book b = new Book();
        b.setName("测试图书");
        b.setAuthor("测试作者");
        b.setPrice(new BigDecimal("9.90"));
        b.setCategoryId(1);
        int rows = mapper.insert(b);
        session.commit();
        System.out.println("===== 新增图书 =====");
        System.out.println("影响行数：" + rows + "，自增 ID：" + b.getId());
        assert rows == 1 : "新增失败";
    }

    /** 测试更新图书 */
    @Test
    public void testUpdate() {
        Book b = mapper.selectById(1);
        String oldName = b.getName();
        b.setName("Java编程思想（更新版）");
        int rows = mapper.update(b);
        session.commit();
        System.out.println("===== 更新图书 =====");
        System.out.println("影响行数：" + rows);
        assert rows == 1 : "更新失败";
        // 恢复原名
        b.setName(oldName);
        mapper.update(b);
        session.commit();
    }

    /** 测试删除图书 */
    @Test
    public void testDelete() {
        // 先新增一本临时图书用于删除测试
        Book temp = new Book();
        temp.setName("临时图书");
        temp.setAuthor("临时作者");
        temp.setPrice(new BigDecimal("1.00"));
        temp.setCategoryId(1);
        mapper.insert(temp);
        session.commit();
        Integer tempId = temp.getId();

        int rows = mapper.delete(tempId);
        session.commit();
        System.out.println("===== 删除图书 =====");
        System.out.println("影响行数：" + rows);
        assert rows == 1 : "删除失败";
    }
}