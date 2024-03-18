package es.uniovi.avib.morphing.projections.backend.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;

@Configuration
@Getter
public class FlowConfig {
    @Value("${flow.host:localhost}")
    String host;

    @Value("${flow.port:8084}")
    String port;
}
