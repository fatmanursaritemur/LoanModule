package com.turkcell.loanmodule.security.services;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

  private static final String[] AUTH_WHITELIST = {

      "/h2-console/**",
  };

  @Override
  public void configure(HttpSecurity http) throws Exception {


    http.requestMatchers().antMatchers(AUTH_WHITELIST).and().
        authorizeRequests().antMatchers("/person/**").authenticated();


    http.headers().frameOptions().disable();
  }
}