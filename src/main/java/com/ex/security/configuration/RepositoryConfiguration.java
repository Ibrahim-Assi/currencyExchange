package com.ex.security.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.ex",
                       repositoryFactoryBeanClass = EnversRevisionRepositoryFactoryBean.class)
public class RepositoryConfiguration {

}
