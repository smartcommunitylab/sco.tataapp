package it.smartcommunitylab.tataapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import com.mongodb.MongoClient;

@Configuration
public class MongoConfig {

	public static final String DEFAULT_DB_NAME = "tataapp";

	@Autowired
	Environment env;

	@Bean
	public MongoDbFactory mongoDbFactory() throws Exception {
		MongoClient client = new MongoClient();
		return new SimpleMongoDbFactory(client, env.getProperty("mongodb.name", DEFAULT_DB_NAME));
	}

}
