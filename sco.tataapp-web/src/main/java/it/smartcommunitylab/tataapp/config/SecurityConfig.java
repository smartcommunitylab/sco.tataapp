
package it.smartcommunitylab.tataapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import it.smartcommunitylab.tataapp.sec.AuthUser;
import it.smartcommunitylab.tataapp.sec.IdentityLookupService;
import it.smartcommunitylab.tataapp.sec.SpringSecurityIdentityLookup;
import it.smartcommunitylab.tataapp.sec.UsersProvider;

@Configuration
@EnableWebSecurity
@Profile({ "sec" })
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UsersProvider usersProvider;

	@Bean
	public IdentityLookupService identityLookup() {
		return new SpringSecurityIdentityLookup();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

		for (AuthUser user : usersProvider.getUsers()) {
			auth.inMemoryAuthentication().withUser(user.getUsername()).password(user.getPassword())
					.roles(user.getRole());
		}
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/", "/console/api/**").authenticated().anyRequest().permitAll();

		http.formLogin().loginPage("/login.html").permitAll().and().logout().permitAll();

		// disable csrf permits POST http call to ConsoleController
		// without using csrf token
		http.csrf().disable();

	}
}
