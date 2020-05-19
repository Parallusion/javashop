package jshop.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("jshop.model") //указываем где искать bean
public class SpringConfig {
}
