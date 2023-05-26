package br.com.lamppit.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

@Component
public class CachingService {

	@Autowired
	CacheManager cacheManager;

	public void evictSingleCacheValue(String cacheName, String cacheKey) {
		Cache cache = cacheManager.getCache(cacheName);
		
		if (cache != null ) {
			cache.evict(cacheKey);
		}
	}

	public void evictAllCacheValues(String cacheName) {
		Cache cache = cacheManager.getCache(cacheName);

		if (cache != null) {
			cache.clear();
		}
	}

}