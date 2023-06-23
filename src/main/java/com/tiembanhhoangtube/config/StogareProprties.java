package com.tiembanhhoangtube.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("stogare")
@Data
public class StogareProprties {
    private String location;
}
