package com.dynns.cloudtecnologia.logistica.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperBean {

    @Bean
    public ModelMapper mapper() {
        return new ModelMapper();
    }

}
