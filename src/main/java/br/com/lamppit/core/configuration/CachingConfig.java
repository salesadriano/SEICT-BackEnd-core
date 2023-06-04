package br.com.lamppit.core.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CachingConfig {
	/**
	 * Informar os names dos atributos para cache
	 * 
	 * @return
	 */
	@Autowired
	private MessageSource messageSource;

	@Bean
	public CacheManager cacheManager() {
		String[] cacheNames = messageSource.getMessage("cache.list", null, null).split(",");
		return new ConcurrentMapCacheManager(cacheNames);
	}

}