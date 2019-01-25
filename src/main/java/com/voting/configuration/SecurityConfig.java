package com.voting.configuration;

import com.voting.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.servlet.support.csrf.CsrfRequestDataValueProcessor;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.support.RequestDataValueProcessor;



@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Configuration
    @Order(1)
    public class RestConfig extends WebSecurityConfigurerAdapter {
        @Override
        public void configure(WebSecurity web) throws Exception {
            web.ignoring()
                    .antMatchers("/resources/**","/webjars/**");
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.authenticationProvider(authenticationProvider());
        }

        public DaoAuthenticationProvider authenticationProvider() {
            DaoAuthenticationProvider authProvider
                    = new DaoAuthenticationProvider();
            authProvider.setUserDetailsService(userService);
            authProvider.setPasswordEncoder(passwordEncoder);
            return authProvider;
        }


        @Override
        protected void configure(HttpSecurity http) throws Exception {

            http
                    .httpBasic().and()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                    .requestMatchers()
                    .antMatchers("/rest/**")
                    .and().authorizeRequests()
                    .antMatchers("/rest/admin/**").hasRole("ADMIN")
                    .antMatchers("/rest/profile/register").anonymous()
                    .antMatchers("/**").authenticated()
                    .and().csrf().disable();
        }

        @Bean
        public RequestDataValueProcessor requestDataValueProcessor() {
            return new CsrfRequestDataValueProcessor();
        }


    }

    @Configuration
    @Order(2)
    public class FormConf extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {

            http
                    .authorizeRequests()
                    .antMatchers("/login").permitAll()
                    .antMatchers("/register").anonymous()
                    .antMatchers("/**/admin/**").hasRole("ADMIN")
                    .antMatchers("/**").authenticated()
                    .and().authorizeRequests()
                    .anyRequest()
                    .authenticated().and().formLogin()
                    .loginPage("/login").defaultSuccessUrl("/voting")
                    .failureUrl("/login?error=true")
                    .loginProcessingUrl("/spring_security_check")
                    .and().logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .logoutSuccessUrl("/login").and().exceptionHandling()
                    .and().csrf();
        }

        @Bean
        public RequestDataValueProcessor requestDataValueProcessor() {
            return new CsrfRequestDataValueProcessor();
        }

    }


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserServiceImpl userService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }




}