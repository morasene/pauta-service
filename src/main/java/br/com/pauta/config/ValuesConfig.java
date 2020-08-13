package br.com.pauta.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ValuesConfig {

	@Value("${url.api-user}")
	private String urlUser;

	public String getUrlUser() {
		return urlUser;
	}
}
