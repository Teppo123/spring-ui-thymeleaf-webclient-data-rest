package com.example.springuithymeleaf.config;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebConfig {

	@Value("${url.userRoot}")
	private String urlRoot;

	@Bean
	public WebClient localApiClient() {
		return WebClient.builder().baseUrl(this.urlRoot)
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.defaultUriVariables(Collections.singletonMap("url", this.urlRoot)).build();
	}

}
