package br.com.fiap.flux.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;

@Configuration
public class ReactiveMongoTemplateConfig {

    @Bean
    MongoClient mongoClient() {
        return MongoClients.create("mongodb://admin:pass@localhost/techVideoDb?authSource=admin");
    }

    @Bean
    ReactiveMongoTemplate reactiveMongoTemplate(MongoClient mongoClient) {
        ReactiveMongoTemplate mongoTemplate = new ReactiveMongoTemplate(mongoClient, "techVideoDb");
        return mongoTemplate;
    }


}
