package br.com.lamppit.core.configuration;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
public class MessageConfiguration {

	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasenames("classpath:messages/cache-names", "classpath:messages/business-messages", "classpath:messages/entities-names");
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}
}
