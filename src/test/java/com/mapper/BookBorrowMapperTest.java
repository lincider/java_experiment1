package com.mapper;

import com.entity.BookBorrow;
import com.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

/**
 * BookBorrowMapper 测试类
 * 验证功能：借阅记录表的 CRUD
 * 注意：book_borrow 表含冗余字段（bookName/bookAuthor/readerName），
 *       selectAll() 直接返回借阅详情，无需 JOIN 其他表
 */
public class BookBorrowMapperTest {

    private SqlSession session;
    private BookBorrowMapper mapper;

    @Before
    public void setUp() {
        session = MyBatisUtil.openSession();
        mapper = session.getMapper(BookBorrowMapper.class);
    }

    @After
    public void tearDown() {
        session.close();
    }

    /**
     * 测试查询所有借阅记录（直接读冗余字段）
     * 预期返回 3 条记录，每条记录包含冗余的图书名称、作者、读者姓名
     */
    @Test
    public void testSelectAll() {
        List<BookBorrow> list = mapper.selectAll();
        System.out.println("===== 借阅记录列表（冗余字段直接返回）=====");
        for (BookBorrow bb : list) {
            System.out.println("读者：「" + bb.getReaderName() + "」借了「" + bb.getBookName()
                    + "」（" + bb.getBookAuthor() + "），借阅日期：" + bb.getBorrowDate());
        }
        assert list.size() > 0 : "查询结果为空";
        // 验证冗余字段已正确回填
        assert list.get(0).getBookName() != null : "冗余字段 bookName 为空";
        assert list.get(0).getReaderName() != null : "冗余字段 readerName 为空";
    }

    /** 测试根据 ID 查询借阅记录 */
    @Test
    public void testSelectById() {
        BookBorrow bb = mapper.selectById(1);
        System.out.println("===== 根据 ID 查询借阅记录 =====");
        System.out.println(bb);
        assert bb != null : "根据 ID 查询失败";
    }

    /**
     * 测试新增借阅记录
     * 注意：book_borrow 表的冗余字段需要在 insert 时手动赋值
     */
    @Test
    public void testInsert() {
        BookBorrow bb = new BookBorrow();
        bb.setBookId(2);
        bb.setBookName("深入理解计算机系统");
        bb.setBookAuthor("Randal E.Bryant");
        bb.setReaderId(2);
        bb.setReaderName("李四");
        bb.setBorrowDate(LocalDate.of(2026, 10, 5));
        int rows = mapper.insert(bb);
        session.commit();
        System.out.println("===== 新增借阅记录 =====");
        System.out.println("影响行数：" + rows + "，自增 ID：" + bb.getId());
        assert rows == 1 : "新增失败";
    }

    /** 测试删除借阅记录 */
    @Test
    public void testDelete() {
        // 先新增一条借阅记录用于删除测试
        BookBorrow temp = new BookBorrow();
        temp.setBookId(4);
        temp.setBookName("三体");
        temp.setBookAuthor("刘慈欣");
        temp.setReaderId(1);
        temp.setReaderName("张三");
        temp.setBorrowDate(LocalDate.now());
        mapper.insert(temp);
        session.commit();
        Integer tempId = temp.getId();

        int rows = mapper.delete(tempId);
        session.commit();
        System.out.println("===== 删除借阅记录 =====");
        System.out.println("影响行数：" + rows);
        assert rows == 1 : "删除失败";
    }
}