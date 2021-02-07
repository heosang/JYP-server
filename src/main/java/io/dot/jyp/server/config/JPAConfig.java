package io.dot.jyp.server.config;

import org.hibernate.boot.model.naming.ImplicitNamingStrategyComponentPathImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Configuration
@EnableJpaAuditing(dateTimeProviderRef = "utcDateTimeProvider")
public class JPAConfig {
    @Bean
    public DateTimeProvider utcDateTimeProvider() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        return () -> {
            String now = LocalDateTime.now(ZoneOffset.UTC).format(formatter);
            return Optional.of(LocalDateTime.parse(now, formatter));
        };
    }

    @Bean
    public ImplicitNamingStrategyComponentPathImpl implicitNamingStrategyComponentPath() {
        return new ImplicitNamingStrategyComponentPathImpl();
    }
}