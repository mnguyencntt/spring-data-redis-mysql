package com.anz.platform.mysql;

import javax.annotation.PostConstruct;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@ComponentScan("com.anz.platform")
public class AppConfig {
  @PostConstruct
  public void init() {
    log.info("Loading AppConfig!!!");
  }
}
