package com.util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * MyBatis 工具类：单例 SqlSessionFactory
 * 整个应用共享一个 SqlSessionFactory 实例，避免重复构建带来的性能开销
 */
public class MyBatisUtil {

    /** 全局单例 SqlSessionFactory，类加载时初始化一次 */
    private static final SqlSessionFactory SQL_SESSION_FACTORY;

    /**
     * static 块：类加载时执行，构建 SqlSessionFactory
     * 读取 classpath 下的 mybatis-config.xml 配置文件
     */
    static {
        try {
            String resource = "mybatis-config.xml";
            InputStream inputStream = Resources.getResourceAsStream(resource);
            SQL_SESSION_FACTORY = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("初始化 SqlSessionFactory 失败，请检查 mybatis-config.xml", e);
        }
    }

    /** 获取全局 SqlSessionFactory 实例 */
    public static SqlSessionFactory getSqlSessionFactory() {
        return SQL_SESSION_FACTORY;
    }

    /**
     * 获取 SqlSession（默认手动提交事务）
     * 注意：使用完后需要手动调用 session.close() 释放连接
     */
    public static SqlSession openSession() {
        return SQL_SESSION_FACTORY.openSession();
    }

    /**
     * 获取 SqlSession，可指定是否自动提交事务
     * @param autoCommit true = 自动提交，false = 手动提交（默认 false）
     */
    public static SqlSession openSession(boolean autoCommit) {
        return SQL_SESSION_FACTORY.openSession(autoCommit);
    }
}