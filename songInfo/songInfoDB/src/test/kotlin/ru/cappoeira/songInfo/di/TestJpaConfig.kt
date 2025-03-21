package ru.cappoeira.songInfo.di

import jakarta.persistence.EntityManagerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.jdbc.datasource.DriverManagerDataSource
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.sql.DataSource

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = ["ru.cappoeira.songInfo.songInfoDB.repository"])
@EnableJpaRepositories(basePackages = ["ru.cappoeira.songInfo.songInfoDB.repository"])
open class TestJpaConfig {

    @Bean
    open fun dataSource(): DataSource {
        return DriverManagerDataSource().apply {
            url = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;MODE=PostgreSQL"
            username = "testUser"
            password = "testPassword"
            setDriverClassName("org.h2.Driver")
        }
    }

    @Bean
    open fun entityManagerFactory(dataSource: DataSource): EntityManagerFactory {
        val factoryBean = LocalContainerEntityManagerFactoryBean()
        factoryBean.dataSource = dataSource
        factoryBean.setPackagesToScan("ru.cappoeira.songInfo.songInfoDB.entity")
        factoryBean.jpaVendorAdapter = HibernateJpaVendorAdapter()
        factoryBean.setJpaPropertyMap(mapOf(
            "hibernate.hbm2ddl.auto" to "create",
            "hibernate.dialect" to "org.hibernate.dialect.H2Dialect",
            "hibernate.show_sql" to "true",
            "hibernate.format_sql" to "true"
        ))
        factoryBean.afterPropertiesSet()
        return factoryBean.`object`!!
    }

    @Bean
    open fun transactionManager(entityManagerFactory: EntityManagerFactory): PlatformTransactionManager {
        return JpaTransactionManager(entityManagerFactory)
    }
}