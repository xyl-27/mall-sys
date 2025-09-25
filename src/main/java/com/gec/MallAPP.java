package com.gec;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SpringBootApplication
@MapperScan(
   basePackages = {"com.gec.dao"}
)

public class MallAPP {
    public static void main(String[] args){
        SpringApplication.run( MallAPP.class, args );
        System.out.println("+---------------------------------+");
        System.out.println("服务器地址: http://localhost:8090/mall-sys/");
        System.out.println("STARTED TIME: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        System.out.println("HELLO WORLD");
        System.out.println("+---------------------------------+");
    }
}
