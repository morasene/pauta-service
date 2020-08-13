package br.com.pauta.vendor.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import br.com.pauta.config.CacheConfig;
import br.com.pauta.config.ValuesConfig;
import br.com.pauta.vendor.dto.UserDTO;

@Component
public class UserClient {

	private final ValuesConfig config;
	private final CacheConfig cacheConfig;
	private final RestTemplate restTemplate;

	public UserClient(ValuesConfig config, RestTemplate restTemplate, CacheConfig cacheConfig) {
		super();
		this.restTemplate = restTemplate;
		this.config = config;
		this.cacheConfig = cacheConfig;
	}

	public UserDTO findByCpf(String cpf) {
		return cacheConfig.userCache().get(cpf, key -> {
			return restTemplate.getForObject(config.getUrlUser() + "/" + key, UserDTO.class);
		});
	}
}
