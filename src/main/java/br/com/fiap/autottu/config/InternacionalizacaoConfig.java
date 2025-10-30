package br.com.fiap.autottu.config;

import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

/**
 * Configuração de Internacionalização (i18n)
 * Permite alternar entre Português e Inglês usando o parâmetro ?lang=pt ou ?lang=en
 */
@Configuration
public class InternacionalizacaoConfig implements WebMvcConfigurer {
	
	/**
	 * Define o MessageSource que carrega as mensagens dos arquivos .properties
	 * Procura por arquivos: mensagens_pt.properties, mensagens_en.properties
	 */
	@Bean
	public ReloadableResourceBundleMessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = 
				new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:mensagens");
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}
	
	/**
	 * Define o LocaleResolver que armazena o idioma escolhido em um cookie
	 * Idioma padrão: Português (pt)
	 */
	@Bean
	public LocaleResolver localeResolver() {
		CookieLocaleResolver localeResolver = new CookieLocaleResolver();
		localeResolver.setDefaultLocale(Locale.forLanguageTag("pt"));
		localeResolver.setCookieName("autottu-locale");
		return localeResolver;
	}
	
	/**
	 * Interceptor que permite mudar o idioma via parâmetro de URL
	 * Exemplo: ?lang=en (Inglês) ou ?lang=pt (Português)
	 */
	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
		interceptor.setParamName("lang");
		return interceptor;
	}
	
	/**
	 * Registra o interceptor de mudança de idioma
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(localeChangeInterceptor());
	}
}

