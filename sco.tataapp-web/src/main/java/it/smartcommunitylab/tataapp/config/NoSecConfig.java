package it.smartcommunitylab.tataapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import it.smartcommunitylab.tataapp.sec.DefaultIdentityLookup;
import it.smartcommunitylab.tataapp.sec.IdentityLookupService;

@Configuration
@EnableWebSecurity
@Profile({ "no-sec", "default" })
public class NoSecConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public IdentityLookupService identityLookup() {
		return new DefaultIdentityLookup();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().anyRequest();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication();

	}

}
