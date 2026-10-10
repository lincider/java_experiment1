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

    // ===================== 多条件动态查询（if / where）=====================

    /**
     * 全参数传 null → 不拼任何 WHERE，返回全部图书（>= 5 本种子数据）
     * 验证点：<where> 标签在所有 <if> 都不成立时不应拼出 "WHERE"
     */
    @Test
    public void testSelectByCondition_全不传_返回全部() {
        List<Book> list = mapper.selectByCondition(null, null, null, null, null);
        System.out.println("===== 动态查询：全不传 =====");
        System.out.println("共 " + list.size() + " 本");
        assert list.size() >= 5 : "全不传应返回至少 5 本种子数据，实际：" + list.size();
    }

    /**
     * 只按书名模糊搜 "Java" → 应匹配 1 本（Java编程思想）
     * 验证点：<if test="name != null and name != ''"> 分支生效
     */
    @Test
    public void testSelectByCondition_只传书名模糊搜() {
        List<Book> list = mapper.selectByCondition("Java", null, null, null, null);
        System.out.println("===== 动态查询：书名含 Java =====");
        list.forEach(b -> System.out.println("  - " + b.getName()));
        assert list.size() == 1 : "应匹配 1 本，实际：" + list.size();
        for (Book b : list) {
            assert b.getName().contains("Java") : "书名不含 Java：" + b.getName();
        }
    }

    /**
     * 书名 + 分类 交集：name="Java" + categoryId=1(计算机) → 只剩《Java编程思想》1 本
     * 验证点：多个 <if> 同时生效，AND 拼接正确
     */
    @Test
    public void testSelectByCondition_书名和分类交集() {
        List<Book> list = mapper.selectByCondition("Java", null, 1, null, null);
        System.out.println("===== 动态查询：书名含 Java + 计算机分类 =====");
        list.forEach(b -> System.out.println("  - " + b.getName() + " / categoryId=" + b.getCategoryId()));
        assert list.size() == 1 : "应匹配 1 本，实际：" + list.size();
        assert "Java编程思想".equals(list.get(0).getName()) : "书名应为 Java编程思想";
    }

    /**
     * 价格区间 [40, 60] → 应匹配 2 本（三体 56、明朝那些事儿 42）
     * 验证点：minPrice / maxPrice 两个数字条件同时生效
     */
    @Test
    public void testSelectByCondition_价格区间筛选() {
        List<Book> list = mapper.selectByCondition(
            null, null, null, new BigDecimal("40"), new BigDecimal("60"));
        System.out.println("===== 动态查询：价格区间 40~60 =====");
        list.forEach(b -> System.out.println("  - " + b.getName() + " / " + b.getPrice()));
        assert list.size() == 2 : "应匹配 2 本，实际：" + list.size();
        for (Book b : list) {
            assert b.getPrice().compareTo(new BigDecimal("40")) >= 0 : "价格低于下限：" + b.getPrice();
            assert b.getPrice().compareTo(new BigDecimal("60")) <= 0 : "价格高于上限：" + b.getPrice();
        }
    }

    /**
     * 书名传空串 "" → 应被当成"不传"处理，返回全部（与全不传结果一致）
     * 验证点：OGNL 表达式 name != '' 判空生效
     */
    @Test
    public void testSelectByCondition_书名传空串_视为不传() {
        int allCount = mapper.selectByCondition(null, null, null, null, null).size();
        int emptyStrCount = mapper.selectByCondition("", null, null, null, null).size();
        System.out.println("===== 动态查询：书名传空串 =====");
        System.out.println("全不传：" + allCount + " 本，空串：" + emptyStrCount + " 本");
        assert allCount == emptyStrCount : "空串应视为不传，两者结果应一致";
    }
}