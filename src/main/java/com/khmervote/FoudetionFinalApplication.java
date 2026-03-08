package com.khmervote;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.khmervote.entity")
@EnableJpaRepositories("com.khmervote.repository")
public class FoudetionFinalApplication {
    public static void main(String[] args) {
        SpringApplication.run(FoudetionFinalApplication.class, args);
    }
}













//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//
//@SpringBootApplication
//public class FoudetionFinalApplication {
//    public static void main(String[] args){
//        SpringApplication.run(FoudetionFinalApplication.class,args);
//    }
//}
