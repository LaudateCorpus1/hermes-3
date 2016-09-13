package pl.allegro.tech.hermes.management.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.javers.core.Javers;
import org.javers.core.JaversBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.allegro.tech.hermes.management.domain.Auditor;
import pl.allegro.tech.hermes.management.infrastructure.audit.LoggingAuditor;

@Configuration
@EnableConfigurationProperties({AuditProperties.class})
public class AuditConfiguration {

    @Bean
    public Auditor auditor(ObjectMapper objectMapper, AuditProperties auditProperties) {
        if (auditProperties.isEnabled()) {
            return new LoggingAuditor(javers(), objectMapper);
        } else {
            return Auditor.noOpAuditor();
        }
    }

    private Javers javers() {
        return JaversBuilder.javers().withPrettyPrint(false).build();
    }
}
