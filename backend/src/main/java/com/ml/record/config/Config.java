package com.ml.record.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.ml.record.repository")
@ComponentScan(basePackages = { "com.ml.record.service" })
public class Config {

  @Value("${elastic.host}")
  private String host;

  @Value("${elastic.port}")
  private String port;

  @Value("${elastic.username}")
  private String userName;

  @Value("${elastic.password}")
  private String password;

  @Bean
  public RestHighLevelClient client() {
    RestHighLevelClient rest = null;
    ClientConfiguration clientConfiguration = ClientConfiguration.builder()
        .connectedTo(host + ":" + port)
        .withBasicAuth(userName, password)
        .build();
    try {
      rest = RestClients.create(clientConfiguration).rest();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return rest;
  }

  @Bean
  public ElasticsearchOperations elasticsearchTemplate() {
    return new ElasticsearchRestTemplate(client());
  }
}