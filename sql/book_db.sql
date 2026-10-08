-- 先创建数据库（如果还没）
CREATE DATABASE book_db DEFAULT CHARSET utf8mb4;
USE book_db;

-- 然后把上面 init.sql 的内容粘进去执行
-- =============================================
-- MyBatis 图书管理系统 - 建表 & 测试数据
-- 数据库：book_db（mybatis-config.xml 中已指定）
-- =============================================

DROP TABLE IF EXISTS book_borrow;
DROP TABLE IF EXISTS book;
DROP TABLE IF EXISTS reader;
DROP TABLE IF EXISTS category;

-- =============================================
-- 表 1：图书分类表
-- =============================================
CREATE TABLE category (
     id   INT PRIMARY KEY AUTO_INCREMENT COMMENT '分类编号',
     name VARCHAR(50) NOT NULL COMMENT '分类名称'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='图书分类表';

-- =============================================
-- 表 2：图书表
-- 业务关系：category(1) ──> book(N)   一对多
-- =============================================
CREATE TABLE book (
     id          INT PRIMARY KEY AUTO_INCREMENT COMMENT '图书编号',
     name        VARCHAR(100) NOT NULL COMMENT '书名',
     author      VARCHAR(50)  NOT NULL COMMENT '作者',
     price       DECIMAL(10,2) NOT NULL COMMENT '价格',
     category_id INT          NOT NULL COMMENT '所属分类（外键 → category.id）',
     CONSTRAINT fk_book_category FOREIGN KEY (category_id) REFERENCES category(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='图书表';

-- =============================================
-- 表 3：读者表
-- =============================================
CREATE TABLE reader (
    id    INT PRIMARY KEY AUTO_INCREMENT COMMENT '读者编号',
    name  VARCHAR(50) NOT NULL COMMENT '姓名',
    phone VARCHAR(20) NOT NULL COMMENT '手机号'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='读者表';

-- =============================================
-- 表 4：借阅记录中间表
-- 业务关系：book(N) ──> book_borrow <── reader(M)   多对多
-- =============================================
CREATE TABLE book_borrow (
     id          INT PRIMARY KEY AUTO_INCREMENT COMMENT '借阅记录编号',
     book_id     INT NOT NULL COMMENT '图书编号（外键 → book.id）',
     reader_id   INT NOT NULL COMMENT '读者编号（外键 → reader.id）',
     borrow_date DATE NOT NULL COMMENT '借阅日期',
     CONSTRAINT fk_borrow_book   FOREIGN KEY (book_id)   REFERENCES book(id),
     CONSTRAINT fk_borrow_reader FOREIGN KEY (reader_id) REFERENCES reader(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='借阅记录表（多对多中间表）';

-- =============================================
-- 测试数据
-- =============================================

-- 3 个分类
INSERT INTO category (name) VALUES ('计算机'), ('文学'), ('历史');

-- 5 本书（体现一对多：一个分类下有多本书）
INSERT INTO book (name, author, price, category_id) VALUES
    ('Java编程思想',         'Bruce Eckel',   108.00, 1),
    ('深入理解计算机系统',   'Randal E.Bryant', 139.00, 1),
    ('活着',                 '余华',            39.00, 2),
    ('三体',                 '刘慈欣',          56.00, 2),
    ('明朝那些事儿',         '当年明月',        42.00, 3);

-- 2 个读者
INSERT INTO reader (name, phone) VALUES
    ('张三', '13800000001'),
    ('李四', '13800000002');

-- 3 条借阅记录（体现多对多：一本书被多人借，一个人借多本书）
INSERT INTO book_borrow (book_id, reader_id, borrow_date) VALUES
    (1, 1, '2026-09-01'),   -- 张三 借了 《Java编程思想》
    (3, 1, '2026-09-15'),   -- 张三 借了 《活着》
    (1, 2, '2026-10-01');   -- 李四 借了 《Java编程思想》（同一本书被两人借过）
-- 1. 给 book_borrow 表增加 3 个冗余字段
ALTER TABLE book_borrow
    ADD COLUMN book_name   VARCHAR(100) NOT NULL COMMENT '图书名称（冗余）'  AFTER book_id,
    ADD COLUMN book_author VARCHAR(50)  NOT NULL COMMENT '图书作者（冗余）' AFTER book_name,
    ADD COLUMN reader_name VARCHAR(50)  NOT NULL COMMENT '借阅者名称（冗余）' AFTER reader_id;

-- 2. 回填已有数据（book_name + book_author 从 book 表关联更新）
UPDATE book_borrow bb
    JOIN book b ON bb.book_id = b.id
    SET bb.book_name   = b.name,
        bb.book_author = b.author;

-- 3. 回填已有数据（reader_name 从 reader 表关联更新）
UPDATE book_borrow bb
    JOIN reader r ON bb.reader_id = r.id
    SET bb.reader_name = r.name;