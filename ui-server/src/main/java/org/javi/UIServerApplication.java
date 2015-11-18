package org.javi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

@SpringBootApplication
@EnableZuulProxy
public class UIServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(UIServerApplication.class, args);
	}

	// @RequestMapping("/token")
	// @ResponseBody
	// public Map<String, String> token(HttpSession session) {
	// return Collections.singletonMap("token", session.getId());
	// }
	//
	// @Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
	@Configuration
	@EnableOAuth2Sso
	protected static class SecurityConfiguration extends WebSecurityConfigurerAdapter {
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.authorizeRequests().antMatchers("/index.html", "/home.html", "/").permitAll().anyRequest()
					.authenticated().and().addFilterAfter(new CsrfHeaderFilter(), CsrfFilter.class).csrf()
					.csrfTokenRepository(csrfTokenRepository());
		}

		private CsrfTokenRepository csrfTokenRepository() {
			HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
			repository.setHeaderName("X-XSRF-TOKEN");
			return repository;
		}
	}

}
