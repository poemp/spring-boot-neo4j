package org.poem.neo4j.table;

import org.poem.neo4j.base.BaseRepository;
import org.poem.neo4j.table.entity.NodeEnt;
import org.springframework.stereotype.Repository;

/**
 * @author Administrator
 */
@Repository
public interface TableEno4jRepostory extends BaseRepository<NodeEnt, String> {
}
