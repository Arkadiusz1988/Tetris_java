package pl.java.tetris.config;


import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.Validator;

@Configuration
public class AppConfig {

    @Bean
    public Validator validator() {
        return new LocalValidatorFactoryBean();
    }

//    @Bean
//    ServletWebServerFactory servletWebServerFactory(){
//        return new TomcatServletWebServerFactory();
//    }


//orika - mapper badz dozer - dodajemy beana jako

}