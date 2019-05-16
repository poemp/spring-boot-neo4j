package org.poem.neo4j.table.entity;

import lombok.Data;
import org.neo4j.ogm.annotation.NodeEntity;
import org.poem.neo4j.base.Entity;

/**
 * @author Administrator
 */
@NodeEntity
@Data
public class NodeEnt extends Entity {

    public static String relationShip = ":IN";
    private String name;
}
