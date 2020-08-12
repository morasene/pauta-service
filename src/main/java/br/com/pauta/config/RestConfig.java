package br.com.pauta.config;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestConfig {

	private static final Integer CONNECTION_TIMEOUT = 25;

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
		MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
		mappingJackson2HttpMessageConverter
				.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM, MediaType.MULTIPART_FORM_DATA));
		
		final List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
	    messageConverters.add(new ByteArrayHttpMessageConverter());
	    messageConverters.add(new ResourceHttpMessageConverter());
	    messageConverters.add(new AllEncompassingFormHttpMessageConverter());
	    messageConverters.add(new FormHttpMessageConverter());
	    messageConverters.add(mappingJackson2HttpMessageConverter);

		return restTemplateBuilder.setConnectTimeout(Duration.ofSeconds(CONNECTION_TIMEOUT))
				.setReadTimeout(Duration.ofSeconds(CONNECTION_TIMEOUT))
				.messageConverters(messageConverters).build();
	}
}
