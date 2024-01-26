package es.uniovi.avib.morphing.projections.backend.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;

@Configuration
@Getter
public class AnalyticsConfig {
    @Value("${analytics.host: localhost}")
    String host;

    @Value("${analytics.port: 5000}")
    String port;
}
