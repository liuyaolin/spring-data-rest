/*
 * Copyright 2012-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.data.rest.webmvc.mongodb;

import java.net.UnknownHostException;

import com.mongodb.Mongo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * @author Jon Brisbin
 */
@Configuration
@ComponentScan
@EnableMongoRepositories
public class MongoDbRepositoryConfig {

	@Bean
	public MongoDbFactory mongoDbFactory() throws UnknownHostException {
		return new SimpleMongoDbFactory(new Mongo("localhost"), "spring-data-rest-example");
	}

	@Bean
	public MongoTemplate mongoTemplate() throws UnknownHostException {
		return new MongoTemplate(mongoDbFactory());
	}
}
