/*
 * Copyright (c) 2016, M. Isuru Tharanga Chrishantha Perera
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.chrishantha.msf4j.sample.greeting;

import com.github.chrishantha.msf4j.sample.greeting.service.GreetingService;
import org.h2.jdbcx.JdbcConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.wso2.msf4j.MicroservicesRunner;
import org.wso2.msf4j.analytics.metrics.MetricsInterceptor;

import java.sql.SQLException;
import javax.sql.DataSource;

/**
 * Main Application Class.
 */
public class Application {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) throws SQLException {
        DataSource dataSource = JdbcConnectionPool.create("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", "sa", "");
        JdbcTemplate template = new JdbcTemplate(dataSource);
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("h2.sql"));
        populator.populate(dataSource.getConnection());

        logger.info("Starting the Microservice with Metrics");
        new MicroservicesRunner()
                .addInterceptor(new MetricsInterceptor())
                .deploy(new GreetingService(template))
                .start();
    }

}
