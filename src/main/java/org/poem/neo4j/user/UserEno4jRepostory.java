package org.poem.neo4j.user;

import org.poem.neo4j.base.BaseRepository;
import org.poem.neo4j.user.entity.User;
import org.springframework.stereotype.Repository;

/**
 * @author Administrator
 */
@Repository
public interface UserEno4jRepostory  extends BaseRepository<User, String> {
}
