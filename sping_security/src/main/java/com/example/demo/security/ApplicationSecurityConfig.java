package com.example.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.auth.ApplicationUserService;
import com.example.demo.jwt.JwtTokenVerifier;
import com.example.demo.jwt.JwtUsernameAndPasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {
	
	private final PasswordEncoder passwordEncoder;
	private final ApplicationUserService applicationUserService;
		
	public ApplicationSecurityConfig(PasswordEncoder passwordEncoder, ApplicationUserService applicationUserService) {
		super();
		this.passwordEncoder = passwordEncoder;
		this.applicationUserService = applicationUserService;
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http
//		 .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnnlse())
//		 .and()
		 .csrf().disable()
		 .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		 .and()
		 .addFilter(new JwtUsernameAndPasswordAuthenticationFilter(authenticationManager()))
		 .addFilterAfter(new JwtTokenVerifier(), JwtUsernameAndPasswordAuthenticationFilter.class)
		 .authorizeRequests()
		 .antMatchers("/","index","js") .permitAll()
		 .antMatchers("/api/**").hasRole(ApplicatonUserRole.STUDENT.name())  
//		 .antMatchers(HttpMethod.DELETE,"/management/api/**").hasAuthority(ApplicationUserPermission.COURSE_WRITE.getPermission())
//		 .antMatchers(HttpMethod.POST,"/management/api/**").hasAuthority(ApplicationUserPermission.COURSE_WRITE.getPermission())
//		 .antMatchers(HttpMethod.PUT,"/management/api/**").hasAuthority(ApplicationUserPermission.COURSE_WRITE.getPermission())
//		 .antMatchers(HttpMethod.GET,"/management/api/**").hasAnyRole(ApplicatonUserRole.ADMIN.name(),ApplicatonUserRole.ADMINTRAINEE.name())	 
		 .anyRequest()
		 .authenticated();
		 
		 /*.and()
		 .httpBasic()
		 .and()
		 .formLogin()
		 .loginPage("/login").permitAll()
		 .defaultSuccessUrl("/courses", true)
		 .and()
		 .rememberMe()
		 .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(21))
		 .key("everyname")
		 .rememberMeParameter("remember-me")
		 .and()
		 .logout()
		 .logoutUrl("/logout")
		 .clearAuthentication(true)
		 .invalidateHttpSession(true)
		 .deleteCookies("JSESSIONID", "remember-me");*/
	}

	
	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(passwordEncoder);
		provider.setUserDetailsService(applicationUserService);
		return provider;
		
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(daoAuthenticationProvider());
	}
	
	

}
