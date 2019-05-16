package org.poem.neo4j.user.entity;

import lombok.Data;
import org.neo4j.ogm.annotation.NodeEntity;
import org.poem.neo4j.base.Entity;

/**
 * @author Administrator
 */
@NodeEntity
@Data
public class User extends Entity {
    private String uuid;
    private String name;
    private String type;
    private String parUuid;
}
