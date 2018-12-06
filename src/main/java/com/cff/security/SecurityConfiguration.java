package com.cff.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	@Autowired
	private SimpleAuthenticationProvider simpleAuthenticationProvider;
	
	@Autowired
	@Qualifier("authenticationDetailsSource")
	private AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> authenticationDetailsSource;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(simpleAuthenticationProvider);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests()
				.antMatchers("/login.html").permitAll()
				.antMatchers("/register.html").permitAll()
				.antMatchers("/open/**").permitAll()
				.antMatchers("/images/**").permitAll()
				.antMatchers("/js/**").permitAll()
				.antMatchers("/css/**").permitAll()
				.antMatchers("/admin.html").access("hasRole('ADMIN')")
				.antMatchers("/admin/**").access("hasRole('ADMIN')")
				.anyRequest().authenticated().and()
				.formLogin().loginPage("/login.html").usernameParameter("userName")
				.authenticationDetailsSource(authenticationDetailsSource)
				.passwordParameter("passwd").loginProcessingUrl("/login")
				.defaultSuccessUrl("/control")
				.failureUrl("/login.html").and().logout().logoutUrl("/logout").logoutSuccessUrl("/login.html");

		// http.authorizeRequests().antMatchers("/user/**").authenticated();
		// http.authorizeRequests().antMatchers("/product/**").authenticated();
	}

}
