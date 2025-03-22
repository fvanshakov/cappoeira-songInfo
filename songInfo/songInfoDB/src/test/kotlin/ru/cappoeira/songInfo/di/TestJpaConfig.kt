package ru.cappoeira.songInfo.di

import com.opentable.db.postgres.embedded.EmbeddedPostgres
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
import java.sql.Connection
import javax.sql.DataSource

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = ["ru.cappoeira.songInfo.songInfoDB.repository"])
@EnableJpaRepositories(basePackages = ["ru.cappoeira.songInfo.songInfoDB.repository"])
open class TestJpaConfig {

    @Bean
    open fun embeddedPostgres(): EmbeddedPostgres {
        return EmbeddedPostgres.builder().start()
    }

    @Bean
    open fun dataSource(pg: EmbeddedPostgres): DataSource {
        val dbName = "testdb"
        val username = "testuser"
        val password = "testpassword"

        val connection: Connection = pg.postgresDatabase.connection

        try {
            connection.createStatement().use { stmt ->
                stmt.executeUpdate("CREATE DATABASE $dbName;")
                stmt.executeUpdate("CREATE USER $username WITH PASSWORD '$password';")
                stmt.executeUpdate("GRANT ALL PRIVILEGES ON DATABASE $dbName TO $username;")
            }
        } finally {
            connection.close()
        }

        return DriverManagerDataSource().apply {
            setDriverClassName("org.postgresql.Driver")
            url = "jdbc:postgresql://localhost:${pg.port}/$dbName"
            this.username = username
            this.password = password
        }
    }

    @Bean
    open fun entityManagerFactory(dataSource: DataSource): EntityManagerFactory {
        val factoryBean = LocalContainerEntityManagerFactoryBean()
        factoryBean.dataSource = dataSource
        factoryBean.setPackagesToScan("ru.cappoeira.songInfo.songInfoDB.entity")
        factoryBean.jpaVendorAdapter = HibernateJpaVendorAdapter()
        factoryBean.setJpaPropertyMap(
            mapOf(
                "hibernate.hbm2ddl.auto" to "create-drop",
                "hibernate.dialect" to "org.hibernate.dialect.PostgreSQLDialect",
                "hibernate.show_sql" to "true",
                "hibernate.format_sql" to "true"
            )
        )
        factoryBean.afterPropertiesSet()
        return factoryBean.`object`!!
    }

    @Bean
    open fun transactionManager(entityManagerFactory: EntityManagerFactory): PlatformTransactionManager {
        return JpaTransactionManager(entityManagerFactory)
    }
}