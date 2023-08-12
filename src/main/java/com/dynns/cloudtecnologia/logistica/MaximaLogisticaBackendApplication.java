package com.dynns.cloudtecnologia.logistica;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MaximaLogisticaBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(MaximaLogisticaBackendApplication.class, args);
    }

}
