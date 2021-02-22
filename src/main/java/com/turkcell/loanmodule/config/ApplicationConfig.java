package com.turkcell.loanmodule.config;

import javax.sql.DataSource;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

@Configuration
public class ApplicationConfig {

  @Autowired
  private DataSource dataSource;

  @Bean
  public  ModelMapper modelMapper(){
    return new ModelMapper();
  }

  @Bean
  public TokenStore tokenStore()
  {
    return new JdbcTokenStore(dataSource);
  }

}
