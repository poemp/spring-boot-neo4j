package org.poem.config;

import lombok.Data;
import org.neo4j.ogm.session.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.transaction.Neo4jTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Data
@Configuration
@EnableNeo4jRepositories(basePackages = "org.poem")
@EnableTransactionManagement
@ConfigurationProperties(prefix = "spring.data.neo4j")
public class Neo4jConfig{

    private static final Logger logger = LoggerFactory.getLogger(Neo4jConfig.class);
    /**
     * URI used by the driver. Auto-detected by default.
     */
    private String uri;

    /**
     * Login user of the server.
     */
    private String username;

    /**
     * Login password of the server.
     */
    private String password;


    @Bean
    public Neo4jTransactionManager transactionManager() throws Exception {
        return new Neo4jTransactionManager(sessionFactory());
    }

    @Bean
    public org.neo4j.ogm.config.Configuration configuration() {
        logger.info("neo4j: " +
                "\n\t\t [uri]:" + uri +
                "\n\t\t [username]:" + username +
                "\n\t\t [password]:" + password
        );
        return  new org.neo4j.ogm.config.Configuration.Builder()
                .uri(uri).verifyConnection(true)
                .credentials(username, password)
                .build();
    }

    @Bean
    public SessionFactory sessionFactory() {
        return new SessionFactory(configuration(),"org.poem");
    }

}