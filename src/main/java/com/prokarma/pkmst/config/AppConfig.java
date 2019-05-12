package com.prokarma.pkmst.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.prokarma.pkmst.interceptor.AuditInterceptor;
import com.prokarma.pkmst.interceptor.BodyInterceptor;
import com.prokarma.pkmst.interceptor.CorrelationInterceptor;
import com.prokarma.pkmst.interceptor.RateLimitInterceptor;
import com.prokarma.pkmst.interceptor.TraceabilityInterceptor;

@Configuration
public class AppConfig extends WebMvcConfigurerAdapter {
	@Value("${interceptor.enable.audit:false}")
	boolean auditEnable;
	@Value("${interceptor.enable.body:false}")
	boolean bodyEnable;
	@Value("${interceptor.enable.rateLimit:false}")
	boolean rateLimitEnable;
	@Value("${interceptor.enable.traceability:false}")
	boolean traceabilityEnable;
	@Value("${interceptor.enable.correlation:false}")
	boolean correlationEnable;

	@Bean
	public HandlerInterceptor auditInterceptor() {
		return new AuditInterceptor();
	}

	@Bean
	public HandlerInterceptor bodyInterceptor() {
		return new BodyInterceptor();
	}

	@Bean
	public HandlerInterceptor rateLimitInterceptor() {
		return new RateLimitInterceptor();
	}

	@Bean
	public HandlerInterceptor traceabilityInterceptor() {
		return new TraceabilityInterceptor();
	}
	/*@Bean
	public HandlerInterceptor correlationInterceptor() {
		return new CorrelationInterceptor();
	}*/

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		if (auditEnable) {
			registry.addInterceptor(auditInterceptor());
		}
		if (bodyEnable) {
			registry.addInterceptor(bodyInterceptor());
		}
		if (rateLimitEnable) {
			registry.addInterceptor(rateLimitInterceptor());
		}
		if (traceabilityEnable) {
			registry.addInterceptor(traceabilityInterceptor());
		}
		if (correlationEnable) {
			// registry.addInterceptor(correlationInterceptor);
		}

	}

	/**
	 * enabling cors support at global level which can be applied at various
	 * level also as below registry.addMapping("/api/**")
	 * .allowedOrigins("http://domain2.com") .allowedMethods("PUT", "DELETE")
	 * .allowedHeaders("header1", "header2", "header3")
	 * .exposedHeaders("header1", "header2")
	 * .allowCredentials(false).maxAge(3600);
	 * 
	 * @return
	 */
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurerAdapter() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**");
			}
		};
	}
}