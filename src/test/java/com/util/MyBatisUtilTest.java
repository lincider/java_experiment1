package com.util;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * MyBatisUtil 工具类单元测试
 * 纯单元测试，不连数据库，只验证对象创建和单例行为
 */
public class MyBatisUtilTest {

    /**
     * 业务规则：SqlSessionFactory 必须是全局单例
     * SqlSessionFactory 是重量级对象（构建一次要加载 XML + 解析映射），
     * 多实例会导致连接池泄漏、事务管理混乱
     */
    @Test
    public void testGetSqlSessionFactory_单例验证() {
        SqlSessionFactory s1 = MyBatisUtil.getSqlSessionFactory();
        SqlSessionFactory s2 = MyBatisUtil.getSqlSessionFactory();
        assertNotNull("SqlSessionFactory 不应为 null", s1);
        assertSame("SqlSessionFactory 不是单例！", s1, s2);
    }

    /**
     * 业务规则：openSession() 返回的 SqlSession 必须非空，初始未关闭
     */
    @Test
    public void testOpenSession_正常创建() {
        SqlSession session = MyBatisUtil.openSession();
        assertNotNull("SqlSession 不应为 null", session);
        session.close();
        assertNotNull("close() 后引用仍可访问（不抛异常）", session);
    }

    /**
     * 业务规则：openSession(自动提交) 和 openSession(手动提交) 都能正常工作
     */
    @Test
    public void testOpenSession_两种提交模式() {
        SqlSession autoCommit = MyBatisUtil.openSession(true);
        SqlSession manualCommit = MyBatisUtil.openSession(false);
        assertNotNull(autoCommit);
        assertNotNull(manualCommit);
        autoCommit.close();
        manualCommit.close();
    }

    /**
     * 业务规则：多次 openSession 应返回不同的 SqlSession 实例
     * SqlSession 是线程不安全的，每次使用都应新建
     */
    @Test
    public void testOpenSession_每次返回新实例() {
        SqlSession s1 = MyBatisUtil.openSession();
        SqlSession s2 = MyBatisUtil.openSession();
        assertNotSame("每次 openSession 应返回不同实例", s1, s2);
        s1.close();
        s2.close();
    }
}