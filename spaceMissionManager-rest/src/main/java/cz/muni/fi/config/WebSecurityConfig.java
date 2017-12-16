package cz.muni.fi.config;

import cz.muni.fi.dto.UserDTO;
import cz.muni.fi.facade.UserFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, jsr250Enabled = true)
@Import(ServiceConfiguration.class)
//@ComponentScan(basePackages = {"cz.muni.fi.controllers"})
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


	@Autowired
	private UserFacade userFacade;

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.authorizeRequests()
				.anyRequest()
				.fullyAuthenticated()
		.and();
		//.exceptionHandling().accessDeniedPage("/AccessDenied");
		http.httpBasic();
		http.csrf().disable();
//				.antMatchers("/", "/home").permitAll()
//				.antMatchers("/**").hasRole("MANAGER");
//				.antMatchers("/user/**").hasRole("USER")
//				.and()
//				.formLogin()
//				.loginPage("/login")
//				.permitAll()
//				.and()
//				.logout()
//				.permitAll();
	}


	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("ADMIN").password("ADMIN").roles("MANAGER");
		for (UserDTO p : userFacade.findAllUsers()) {
			auth.inMemoryAuthentication()
					.withUser(p.getEmail())
					.password(p.getPassword())
					.roles(p.isManager() ? "MANAGER" : "USER");
		}
	}


}
