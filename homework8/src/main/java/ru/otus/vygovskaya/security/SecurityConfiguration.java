package ru.otus.vygovskaya.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    UserDetailsService userDetailsService;
    RestAuthEntryPoint restAuthEntryPoint;

    @Autowired
    public SecurityConfiguration(MongoUserDetailsService userDetailsService, RestAuthEntryPoint restAuthEntryPoint) {
        this.userDetailsService = userDetailsService;
        this.restAuthEntryPoint = restAuthEntryPoint;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests().anyRequest().authenticated()
                .and()
                .formLogin().loginProcessingUrl("/login")
                .successHandler(new RefererRedirectionAuthenticationSuccessHandler())
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(restAuthEntryPoint);
    }

    @Autowired
    public void configure( AuthenticationManagerBuilder auth ) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.debug(true);

    }
}
