//package com.mooc.mall.pojo;
//
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClientBuilder;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
//import org.springframework.web.client.RestTemplate;
//
//@Configuration
//public class SpringConfig {
//
//	@Value("${rest.service.maxConnTotal}")
//	private int maxConnTotal;
//	@Value("${rest.service.maxConnPerRoute}")
//	private int maxConnPerRoute;
//
//	@Bean
//	public RestTemplate restTemplate() {
//		CloseableHttpClient httpClient = HttpClientBuilder.create().disableCookieManagement().useSystemProperties()
//				.setMaxConnTotal(maxConnTotal).setMaxConnPerRoute(maxConnPerRoute).build();
//
//		return new RestTemplate(new HttpComponentsClientHttpRequestFactory(httpClient));
//		//		return new RestTemplateBuilder().build();
//	}
//
//
//
//}
