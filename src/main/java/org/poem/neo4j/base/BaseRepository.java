package org.poem.neo4j.base;

import org.poem.neo4j.base.Entity;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import java.io.Serializable;
import java.util.List;

/**
 * @author Administrator
 */
@NoRepositoryBean
public interface BaseRepository<T extends Entity, ID extends Serializable> extends Neo4jRepository<T, ID> {


    List<T> findByUuid(String uuid);

    /**
     * 添加拥有关系
     *
     * @param srcuuid
     * @param desuuid
     */
    @Query("MATCH(m),(n) WHERE m.uuid={srcuuid} AND n.uuid={desuuid} CREATE (m)-[:HAVE]->(n)")
    void AddReHave(@Param("srcuuid") String srcuuid, @Param("desuuid") String desuuid);

    /**
     * 添加使用关系
     *
     * @param srcuuid
     * @param desuuid
     */
    @Query("MATCH(m),(n) WHERE m.uuid={srcuuid} AND n.uuid={desuuid} CREATE (m)-[:USE]->(n)")
    void AddReUse(@Param("srcuuid") String srcuuid, @Param("desuuid") String desuuid);

    /**
     * 删除关系
     *
     * @param srcuuid
     * @param desuuid
     */
    @Query("MATCH(m)-[r]-(n) WHERE m.uuid={srcuuid} AND n.uuid={desuuid} DELETE r")
    void DelRe(@Param("srcuuid") String srcuuid, @Param("desuuid") String desuuid);


    /************************************查询********************************/

    /**
     *
     * @param srcuuid
     * @param desuuid
     */
    @Query("match(m),(n) where m.uuid={srcuuid} and n.uuid={desuuid} create (m)-[:In]->(n)")
    void AddReIn(@Param("srcuuid") String srcuuid, @Param("desuuid") String desuuid);

    /**
     * 添加使用关系
     * @param srcuuid
     * @param desuuid
     */
    @Query("match(m),(n) where m.uuid={srcuuid} and n.uuid={desuuid} create (m)-[:Out]->(n)")
    void AddReOut(@Param("srcuuid") String srcuuid, @Param("desuuid") String desuuid);
    /**
     * 查询uuid 下的属于关系 子节点
     *
     * @param uuid
     * @return
     */
    @Query("MATCH(from)-[:HAVE]->(to) WHERE from.uuid={uuid} RETURN to")
    List<T> findReHaveChildren(@Param("uuid") String uuid);

    /**
     * 查询uuid 下的属于关系 子节点  子节点指定类型
     *
     * @param uuid
     * @param type
     * @return
     */
    @Query("MATCH(from)-[:HAVE]->(to) WHERE from.uuid={uuid} AND to.type={type} RETURN to")
    List<T> findReHaveChildrenByType(@Param("uuid") String uuid, @Param("type") String type);


    /**
     * 查询uuid 下的被属于关系
     *
     * @param uuid
     * @return
     */
    @Query("MATCH(from)-[:HAVE]->(to) WHERE to.uuid={uuid} RETURN from")
    List<T> findReHaveParent(@Param("uuid") String uuid);

    /**
     * 查询uuid 下的被属于关系 子节点  子节点指定类型
     *
     * @param uuid
     * @param type
     * @return
     */
    @Query("MATCH(from)-[:HAVE]->(to) WHERE to.uuid={uuid} AND from.type={type} RETURN from")
    List<T> findReHaveParentByType(@Param("uuid") String uuid, @Param("type") String type);


    /**
     * 查询uuid 下的使用关系 子节点
     *
     * @param uuid
     * @return
     */
    @Query("MATCH(from)-[:USE]->(to) WHERE from.uuid={uuid} RETURN to")
    List<T> findReUseChildren(@Param("uuid") String uuid);

    /**
     * 查询uuid 下的使用关系 子节点 子节点指定类型
     *
     * @param uuid
     * @param type
     * @return
     */
    @Query("MATCH(from)-[:USE]->(to) WHERE from.uuid={uuid} AND to.type={type} RETURN to")
    List<T> findReUseChildrenByType(@Param("uuid") String uuid, @Param("type") String type);


    /**
     * 查询uuid 下的被使用关系
     *
     * @param uuid
     * @return
     */
    @Query("MATCH(from)-[:USE]->(to) WHERE to.uuid={uuid} RETURN from")
    List<T> findReUseParent(@Param("uuid") String uuid);

    /**
     * 查询uuid 下的被使用关系 子节点指定类型
     *
     * @param uuid
     * @param type
     * @return
     */
    @Query("MATCH(from)-[:USE]->(to) WHERE to.uuid={uuid} AND from.type={type} RETURN from")
    List<T> findReUseParentByType(@Param("uuid") String uuid, @Param("type") String type);


    /**
     * 查询uuid 有关系的所有指定类型
     *
     * @param uuid
     * @param type
     * @return
     */
    @Query("MATCH(from)-[*1..5]->(to) WHERE from.uuid={uuid} AND to.type={type} RETURN to")
    List<T> findChildrenByType(@Param("uuid") String uuid, @Param("type") String type);

    /**
     * 查询uuid 有关系的所有子节点
     *
     * @param uuid
     * @return
     */
    @Query("MATCH(from)-[*1..5]->(to) WHERE from.uuid={uuid} RETURN to")
    List<T> findChildrenAll(@Param("uuid") String uuid);


    /**
     * 查询所有根节点
     *
     * @return
     */
    @Query("MATCH(root) WHERE root.type='ROOT' RETURN root")
    List<T> findROOTAll();

    /**
     * DelReByType:删除一个节点下的指定的节点类型
     *
     * @param uuid
     * @param type
     */
    @Query("MATCH(from)-[r]-(to) WHERE from.uuid={uuid} AND to.type={type} DELETE r")
    void DelReByType(String uuid, String type);

    /**
     * 查询所有的节点
     *
     * @param uuid
     * @return
     */
    @Query("MATCH(from)-[*1..5]-(to) WHERE from.uuid={uuid} RETURN to")
    List<T> findNodeAll(@Param("uuid") String uuid);


    /**
     * 删除所有节点
     *
     */
    @Query("MATCH(from)-[r]-(to) DELETE from,r, to")
    void deletAll();

    /**
     * 根据类型查询所有顶级节点
     * @param type
     * @return
     */
    @Query("match(from)-[*1..5]-(to) where from.type={type} return from, to")
    List<T> findAllNodeByType(@Param("type") String type);

}
