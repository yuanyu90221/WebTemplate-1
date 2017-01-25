package com.exfantasy.template.config;

import java.lang.reflect.Method;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * <a href="http://wiselyman.iteye.com/blog/2184884">參考網站</a>
 * 
 * @author tommy.feng
 *
 */
@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {

	@Bean
	public KeyGenerator classMethodKeyGenerator() {
		return new KeyGenerator() {
			@Override
			public Object generate(Object target, Method method, Object... params) {
				StringBuilder sb = new StringBuilder();
				sb.append(target.getClass().getName()).append("_");
				sb.append(method.getName()).append("_");
				for (Object obj : params) {
					sb.append(obj.toString());
				}
				return sb.toString();
			}
		};

	}

	@Bean
	public CacheManager cacheManager(@SuppressWarnings("rawtypes") RedisTemplate redisTemplate) {
		return new RedisCacheManager(redisTemplate);
	}

	@Bean
	public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {
		StringRedisTemplate template = new StringRedisTemplate(factory);

		// ref: http://stackoverflow.com/questions/39324717/spring-boot-caching-with-redis-key-have-xac-xed-x00-x05t-x00-x06
		template.setKeySerializer(new GenericToStringSerializer<Object>(Object.class));
		
		Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = getRedisTemplateValueSerializer();
		template.setValueSerializer(jackson2JsonRedisSerializer);

		template.afterPropertiesSet();

		return template;
	}

	private Jackson2JsonRedisSerializer<Object> getRedisTemplateValueSerializer() {
		Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
		ObjectMapper om = new ObjectMapper();
		om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		jackson2JsonRedisSerializer.setObjectMapper(om);
		return jackson2JsonRedisSerializer;
	}
}