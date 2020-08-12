package br.com.pauta.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

	@Bean
	public ModelMapper defaultModelMapper() {
		ModelMapper modelmapper = new ModelMapper();
		modelmapper.getConfiguration().setAmbiguityIgnored(true);
		return modelmapper;
	}
}
