package org.poem.neo4j.base;

import lombok.Data;
import org.neo4j.ogm.annotation.Id;

import java.io.Serializable;

/**
 * @author Administrator
 */
@Data
public class Entity implements Serializable {

    @Id
    private String id;
}
