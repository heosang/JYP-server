package io.dot.jyp.server.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({JypProperties.class})
public class JypConfig {
    @Bean
    public JypProperties jypProperties() {
        return new JypProperties();
    }
}
