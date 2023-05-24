package br.com.lamppit.core.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	
	@Value("${core.allowed-origins}")
	private String[] allowedOrigins;

	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		for (String origin : allowedOrigins) {
			config.addAllowedOrigin(origin);
		}
		config.setAllowCredentials(true);
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");

		source.registerCorsConfiguration("/**", config);

		return new CorsFilter(source);
	}

	@Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception { 
		return authenticationConfiguration.getAuthenticationManager();
	}

    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

		http
			.csrf(csrf -> csrf.disable())
            .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authorizeHttpRequests(request -> request
				.requestMatchers(new AntPathRequestMatcher("/**")).permitAll()
				.anyRequest()
				.authenticated()
		);        
		
		return http.build();
    }

}