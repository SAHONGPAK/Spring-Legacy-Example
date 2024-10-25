package com.example.springlegacy.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * 데이터베이스 관련 설정을 담당하는 설정 클래스입니다.
 * MyBatis, DataSource, Transaction 등 데이터 액세스 계층의 설정을 관리합니다.
 *
 * <p>다음과 같은 설정을 제공합니다:</p>
 * <ul>
 *     <li>DataSource: HikariCP 커넥션 풀 설정</li>
 *     <li>MyBatis: SqlSessionFactory 및 Mapper 설정</li>
 *     <li>Transaction: 트랜잭션 매니저 설정</li>
 * </ul>
 *
 * <p>데이터베이스 연결 정보는 database.properties 파일에서 관리됩니다.</p>
 *
 * @author SAHONG PAK
 * @version 1.0
 * @see Configuration
 * @see MapperScan
 * @see EnableTransactionManagement
 */
@Configuration
@MapperScan(basePackages = "com.example.springlegacy.*.repository.mapper")
@EnableTransactionManagement
@PropertySource("classpath:database.properties")
public class DBConfig {

    @Autowired
    private Environment env;

    /**
     * HikariCP 데이터소스를 설정합니다.
     * database.properties의 설정값을 사용하여 데이터베이스 연결 풀을 구성합니다.
     *
     * @return 설정된 DataSource 객체
     */
    @Bean
    public DataSource dataSource() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName(env.getProperty("db.driver"));
        hikariConfig.setJdbcUrl(env.getProperty("db.url"));
        hikariConfig.setUsername(env.getProperty("db.username"));
        hikariConfig.setPassword(env.getProperty("db.password"));

        // 커넥션 풀 설정
        hikariConfig.setMaximumPoolSize(10);
        hikariConfig.setMinimumIdle(5);
        hikariConfig.setConnectionTimeout(30000);

        return new HikariDataSource(hikariConfig);
    }

    /**
     * MyBatis SqlSessionFactory를 설정합니다.
     * 데이터소스 및 MyBatis 관련 설정을 구성합니다.
     *
     * @return 설정된 SqlSessionFactory 객체
     * @throws Exception SqlSessionFactory 생성 중 발생할 수 있는 예외
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());

        // MyBatis 설정
        org.apache.ibatis.session.Configuration configuration =
                new org.apache.ibatis.session.Configuration();
        configuration.setMapUnderscoreToCamelCase(true);    // camelCase 자동 매핑
        configuration.setJdbcTypeForNull(org.apache.ibatis.type.JdbcType.NULL);
        configuration.setCallSettersOnNulls(true);
        sessionFactory.setConfiguration(configuration);

        return sessionFactory.getObject();
    }

    /**
     * 트랜잭션 매니저를 설정합니다.
     * 데이터소스 기반의 트랜잭션 관리를 제공합니다.
     *
     * @return 설정된 PlatformTransactionManager 객체
     */
    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }
}