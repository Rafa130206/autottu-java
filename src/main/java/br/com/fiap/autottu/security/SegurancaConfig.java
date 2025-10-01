package br.com.fiap.autottu.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SegurancaConfig {

	@Autowired
	private UserDetailsService usuarioDetailsService;

	@Bean
	public SecurityFilterChain chain(HttpSecurity http) throws Exception {
		http
			.userDetailsService(usuarioDetailsService)
			.authorizeHttpRequests(auth -> auth
			.requestMatchers("/integrantes").hasRole("ADMIN")
			.requestMatchers("/menu", "/motos/**", "/checkins/**",
							 "/manutencoes/**", "/testrides/**", "/slots/**")
				.authenticated()
			.anyRequest().permitAll()
		)
			.formLogin(form -> form
				.loginPage("/login")
				.defaultSuccessUrl("/", true)
				.permitAll()
			)
			.logout(logout -> logout
				.logoutUrl("/logout")
				.logoutSuccessUrl("/login?logout=true")
			)
			.csrf(csrf -> csrf.disable());

		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
