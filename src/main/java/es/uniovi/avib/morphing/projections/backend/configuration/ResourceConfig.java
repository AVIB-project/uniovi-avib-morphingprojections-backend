package es.uniovi.avib.morphing.projections.backend.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;

@Configuration
@Getter
public class ResourceConfig {
    @Value("${resource.host:localhost}")
    String host;

    @Value("${resource.port:8083}")
    String port;     
}
