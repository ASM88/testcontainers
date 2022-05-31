package com.example.testcontainers;

import com.example.testcontainers.persistence.model.User;
import com.example.testcontainers.persistence.repository.UserRepository;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import junit.framework.AssertionFailedError;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.testcontainers.containers.OracleContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ContextConfiguration(classes = {TestcontainersApplicationTests.Config.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
class TestcontainersApplicationTests {

	@Autowired
	UserRepository users;
	@Container
	static OracleContainer DB = new OracleContainer(DockerImageName.parse("gvenzl/oracle-xe:18"));

	@Test
	void contextLoads() {
		assertThat(DB.isRunning()).isTrue();
		assertThat(users).isNotNull();
	}

	@Test
	void saveAndRetrieveUser() {
		users.save(User.builder().username("User1").displayname("Displayname1").build());
		User retrieved = users.findByUsername("User1").orElseThrow(AssertionFailedError::new);

		assertThat(retrieved.getId()).isNotNull();
		assertThat(retrieved.getDisplayname()).isEqualTo("Displayname1");
	}

	@Configuration
	@EnableTransactionManagement
	@EnableJpaRepositories
	@EntityScan
	static class Config {
		@Bean
		public DataSource dataSource() {
			HikariConfig dbConfig = new HikariConfig();
			dbConfig.setJdbcUrl(DB.getJdbcUrl());
			dbConfig.setUsername(DB.getUsername());
			dbConfig.setPassword(DB.getPassword());
			dbConfig.setDriverClassName(DB.getDriverClassName());
			return new HikariDataSource(dbConfig);
		}
	}
}
