package com.Sit_Perahat.sit_it_event.SecurtyConfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class CorsConfig
        implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(
            CorsRegistry registry) {

        registry.addMapping("/**")
                .allowedOrigins("https://it31-mysticbond.sit.kmutt.ac.th/samosit/it31-mysticbond")
                .allowedMethods("GET", "POST", "PUT")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }

}
