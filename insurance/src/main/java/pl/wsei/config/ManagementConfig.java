package pl.wsei.config;

import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@ComponentScan(basePackages = {
        "pl.wsei.controller", "pl.wsei.services",
        "pl.wsei.config", "pl.wsei.dto", "pl.wsei.security"
})
@EntityScan(basePackages = {"pl.wsei.model"})
@EnableJpaRepositories(basePackages = {"pl.wsei.repository"})
@EnableTransactionManagement
public class ManagementConfig {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}

