package com.cff;

import java.nio.charset.Charset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class App {
	@Autowired
	private RestTemplateBuilder builder;

	// 使用RestTemplateBuilder来实例化RestTemplate对象，spring默认已经注入了RestTemplateBuilder实例
	@Bean
	public RestTemplate restTemplate() {
		StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter(
				Charset.forName("UTF-8"));
		return builder.additionalMessageConverters(stringHttpMessageConverter).build();
	}

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
}
