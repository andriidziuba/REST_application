package com.example.demo;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;


@SpringBootApplication
@ImportResource("classpath:spring_config.xml")
public class DemoApplication {


    @Bean
    public SessionFactory dbConnector() {
        return new Configuration().configure().buildSessionFactory();
    }

    @Bean
    public Logger logger(){
        return LoggerFactory.getLogger("application");
    }

    public static void main(String[] args) {
        //System.setProperty("server.port","31444");
        ApplicationContext ctx = SpringApplication.run(DemoApplication.class, args);
    }


}
