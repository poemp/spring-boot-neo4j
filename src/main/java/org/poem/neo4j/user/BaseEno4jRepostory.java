package org.poem.neo4j.user;

import org.poem.neo4j.base.BaseRepository;
import org.poem.neo4j.user.entity.Base;
import org.poem.neo4j.user.entity.User;
import org.springframework.stereotype.Repository;

/**
 * @author Administrator
 */
@Repository
public interface BaseEno4jRepostory extends BaseRepository<Base, String> {

}
