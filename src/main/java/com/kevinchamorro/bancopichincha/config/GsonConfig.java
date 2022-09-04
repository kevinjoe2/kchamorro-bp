package com.kevinchamorro.bancopichincha.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kevinchamorro.bancopichincha.adapter.LocalDateAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
public class GsonConfig {

    @Bean
    public Gson defaultConfig(){
        return new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
    }

}
