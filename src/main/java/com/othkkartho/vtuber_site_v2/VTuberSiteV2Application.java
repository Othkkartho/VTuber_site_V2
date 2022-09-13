package com.othkkartho.vtuber_site_v2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class VTuberSiteV2Application {
    public static void main(String[] args) {
        SpringApplication.run(VTuberSiteV2Application.class, args);
    }

}
