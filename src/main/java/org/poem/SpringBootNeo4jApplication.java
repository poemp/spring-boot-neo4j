package org.poem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

/**
 * @author Administrator
 */
@EnableNeo4jRepositories
@SpringBootApplication
public class SpringBootNeo4jApplication {

    public static void main(String[] args) {
        SpringApplication.run( SpringBootNeo4jApplication.class, args );
    }

}
