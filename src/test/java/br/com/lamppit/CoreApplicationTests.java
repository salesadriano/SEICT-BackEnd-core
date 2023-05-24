package br.com.lamppit;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.context.MessageSourceProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;

@SpringBootTest
class CoreApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void testeProperties() {
//		System.out.println(messageSource.getMessage("cache.list", null, null));
	}

}
