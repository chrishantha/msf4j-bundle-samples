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
package com.github.chrishantha.msf4j.sample.greeting.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.wso2.carbon.metrics.core.annotation.Metered;
import org.wso2.carbon.metrics.core.annotation.Timed;

import java.util.List;
import java.util.Map;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("/greeting")
public class GreetingService {

    private JdbcTemplate template;

    public GreetingService(JdbcTemplate template) {
        this.template = template;
    }

    @GET
    @Path("/{name}")
    @Metered
    public String greeting(@PathParam("name") String name) {
        return "Hello, " + name;
    }

    @GET
    @Path("/meter")
    @Timed
    public List<Map<String, Object>> greetings() {
        return template.queryForList("SELECT * FROM METRIC_METER");
    }

}
