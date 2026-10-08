package com.mapper;

import com.entity.Reader;
import com.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * ReaderMapper 测试类
 * 验证功能：读者表的 CRUD 及多对多关联查询（读者 -> 借阅的图书列表）
 */
public class ReaderMapperTest {

    private SqlSession session;
    private ReaderMapper mapper;

    @Before
    public void setUp() {
        session = MyBatisUtil.openSession();
        mapper = session.getMapper(ReaderMapper.class);
    }

    @After
    public void tearDown() {
        session.close();
    }

    /**
     * 测试多对多关联查询：查询所有读者，同时查出每人借阅的图书列表
     * 验证：读者张三应该借了 2 本书（Java编程思想、活着），
     *       读者李四应该借了 1 本书（Java编程思想）
     */
    @Test
    public void testSelectAllWithBooks() {
        List<Reader> list = mapper.selectAllWithBooks();
        System.out.println("===== 多对多：读者 + 借阅图书列表 =====");
        for (Reader r : list) {
            System.out.println("读者：「" + r.getName() + "」（" + r.getPhone() + "），已借 " + r.getBooks().size() + " 本书");
            r.getBooks().forEach(book ->
                System.out.println("  - " + book.getName() + " / " + book.getAuthor())
            );
        }
        assert list.size() > 0 : "查询结果为空";
        // 验证第一个读者有借阅记录（验证多对多关联生效）
        assert list.get(0).getBooks().size() > 0 : "多对多关联未生效，books 为空";
    }

    /** 测试根据 ID 查询读者 */
    @Test
    public void testSelectById() {
        Reader r = mapper.selectById(1);
        System.out.println("===== 根据 ID 查询读者 =====");
        System.out.println(r);
        assert r != null : "根据 ID 查询失败";
        assert "张三".equals(r.getName()) : "读者名称不匹配";
    }

    /** 测试新增读者 */
    @Test
    public void testInsert() {
        Reader r = new Reader();
        r.setName("王五");
        r.setPhone("13800000005");
        int rows = mapper.insert(r);
        session.commit();
        System.out.println("===== 新增读者 =====");
        System.out.println("影响行数：" + rows + "，自增 ID：" + r.getId());
        assert rows == 1 : "新增失败";
    }

    /** 测试更新读者 */
    @Test
    public void testUpdate() {
        Reader r = mapper.selectById(1);
        String oldName = r.getName();
        r.setName("张三（更新）");
        int rows = mapper.update(r);
        session.commit();
        System.out.println("===== 更新读者 =====");
        System.out.println("影响行数：" + rows);
        assert rows == 1 : "更新失败";
        // 恢复原名
        r.setName(oldName);
        mapper.update(r);
        session.commit();
    }

    /** 测试删除读者 */
    @Test
    public void testDelete() {
        // 先新增一个临时读者用于删除测试
        Reader temp = new Reader();
        temp.setName("临时读者");
        temp.setPhone("13900000000");
        mapper.insert(temp);
        session.commit();
        Integer tempId = temp.getId();

        int rows = mapper.delete(tempId);
        session.commit();
        System.out.println("===== 删除读者 =====");
        System.out.println("影响行数：" + rows);
        assert rows == 1 : "删除失败";
    }
}