package br.com.pauta.config;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import br.com.pauta.entity.Associado;
import br.com.pauta.vendor.dto.UserDTO;

@Configuration
public class CacheConfig {

	@Value("${cache.security.timeDurationCacheInHours:24}")
	private int timeDurationCacheInHours;

	@Value("${cache.security.maximumSizeCacheInHours:4}")
	private int maximumSizeCacheInHours;

	@Bean
	public Cache<Integer, Associado> associadoCache() {
		return Caffeine.newBuilder().expireAfterWrite(timeDurationCacheInHours, TimeUnit.HOURS).maximumSize(maximumSizeCacheInHours).build();
	}
	
	@Bean
	public Cache<String, UserDTO> userCache() {
		return Caffeine.newBuilder().expireAfterWrite(timeDurationCacheInHours, TimeUnit.HOURS).maximumSize(maximumSizeCacheInHours).build();
	}
	
}
