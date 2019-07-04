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

    /* 添加拥有关系 */
    @Query("match(m),(n) where m.uuid={srcuuid} and n.uuid={desuuid} create (m)-[:HAVE]->(n)")
    void AddReHave(@Param("srcuuid") String srcuuid, @Param("desuuid") String desuuid);

    /* 添加使用关系 */
    @Query("match(m),(n) where m.uuid={srcuuid} and n.uuid={desuuid} create (m)-[:USE]->(n)")
    void AddReUse(@Param("srcuuid") String srcuuid, @Param("desuuid") String desuuid);

    /* 添加拥有关系 */
    @Query("match(m),(n) where m.uuid={srcuuid} and n.uuid={desuuid} create (m)-[:In]->(n)")
    void AddReIn(@Param("srcuuid") String srcuuid, @Param("desuuid") String desuuid);

    /* 添加使用关系 */
    @Query("match(m),(n) where m.uuid={srcuuid} and n.uuid={desuuid} create (m)-[:Out]->(n)")
    void AddReOut(@Param("srcuuid") String srcuuid, @Param("desuuid") String desuuid);

    /* 删除关系 */
    @Query("match(m)-[r]-(n) where m.uuid={srcuuid} and n.uuid={desuuid} delete r")
    void DelRe(@Param("srcuuid") String srcuuid, @Param("desuuid") String desuuid);

    /************************************ 查询 ********************************/

    /******************************* HAVE ********************************/
    /* 查询uuid 下的属于关系 子节点 */
    @Query("match(from)-[:HAVE]->(to) where from.uuid={uuid} return to")
    List<T> findReHaveChildren(@Param("uuid") String uuid);

    /* 查询uuid 下的属于关系 子节点 子节点指定类型 */
    @Query("match(from)-[:HAVE]->(to) where from.uuid={uuid} and to.type={type} return to")
    List<T> findReHaveChildrenByType(@Param("uuid") String uuid, @Param("type") String type);

    /* 查询uuid 下的被属于关系 */
    @Query("match(from)-[:HAVE]->(to) where to.uuid={uuid} return from")
    List<T> findReHaveParent(@Param("uuid") String uuid);

    /* 查询uuid 下的被属于关系 子节点 子节点指定类型 */
    @Query("match(from)-[:HAVE]->(to) where to.uuid={uuid} and from.type={type} return from")
    List<T> findReHaveParentByType(@Param("uuid") String uuid, @Param("type") String type);

    /************************* In ************************/

    /* 查询uuid 下的属于关系 子节点 */
    @Query("match(from)-[:In]->(to) where from.uuid={uuid} return to")
    List<T> findReInChildren(@Param("uuid") String uuid);

    /* 查询uuid 下的属于关系 子节点 子节点指定类型 */
    @Query("match(from)-[:In]->(to) where from.uuid={uuid} and to.type={type} return to")
    List<T> findReInChildrenByType(@Param("uuid") String uuid, @Param("type") String type);

    /* 查询uuid 下的被属于关系 */
    @Query("match(from)-[:In]->(to) where to.uuid={uuid} return from")
    List<T> findReInParent(@Param("uuid") String uuid);

    /* 查询uuid 下的被属于关系 子节点 子节点指定类型 */
    @Query("match(from)-[:In]->(to) where to.uuid={uuid} and from.type={type} return from")
    List<T> findReInParentByType(@Param("uuid") String uuid, @Param("type") String type);

    /************************* Out ************************/

    /* 查询uuid 下的属于关系 子节点 */
    @Query("match(from)-[:Out]->(to) where from.uuid={uuid} return to")
    List<T> findReOutChildren(@Param("uuid") String uuid);

    /* 查询uuid 下的属于关系 子节点 子节点指定类型 */
    @Query("match(from)-[:Out]->(to) where from.uuid={uuid} and to.type={type} return to")
    List<T> findReOutChildrenByType(@Param("uuid") String uuid, @Param("type") String type);

    /* 查询uuid 下的被属于关系 */
    @Query("match(from)-[:Out]->(to) where to.uuid={uuid} return from")
    List<T> findReOutParent(@Param("uuid") String uuid);

    /* 查询uuid 下的被属于关系 子节点 子节点指定类型 */
    @Query("match(from)-[:Out]->(to) where to.uuid={uuid} and from.type={type} return from")
    List<T> findReOutParentByType(@Param("uuid") String uuid, @Param("type") String type);

    /*************************** USE ************************/
    /* 查询uuid 下的使用关系 子节点 */
    @Query("match(from)-[:USE]->(to) where from.uuid={uuid} return to")
    List<T> findReUseChildren(@Param("uuid") String uuid);

    /* 查询uuid 下的使用关系 子节点 子节点指定类型 */
    @Query("match(from)-[:USE]->(to) where from.uuid={uuid} and to.type={type} return to")
    List<T> findReUseChildrenByType(@Param("uuid") String uuid, @Param("type") String type);

    /* 查询uuid 下的被使用关系 */
    @Query("match(from)-[:USE]->(to) where to.uuid={uuid} return from")
    List<T> findReUseParent(@Param("uuid") String uuid);

    /* 查询uuid 下的被使用关系 子节点指定类型 */
    @Query("match(from)-[:USE]->(to) where to.uuid={uuid} and from.type={type} return from")
    List<T> findReUseParentByType(@Param("uuid") String uuid, @Param("type") String type);

    /* 查询uuid 有关系的所有指定类型 */
    @Query("match(from)-[*1..5]->(to) where from.uuid={uuid} and to.type={type} return to")
    List<T> findChildrenByType(@Param("uuid") String uuid, @Param("type") String type);

    /* 查询uuid 有关系的所有子节点 */
    @Query("match(from)-[*1..5]->(to) where from.uuid={uuid} return to")
    List<T> findChildrenAll(@Param("uuid") String uuid);

    /* 查询uuid 有关系的所有子节点 */
    @Query("match(from)-[*1..5]->(to) where to.uuid={uuid} return from")
    List<T> findParentAll(@Param("uuid") String uuid);

    /* 查询所有根节点 */
    @Query("match(root) where root.type='ROOT' return root")
    List<T> findROOTAll();

    /* DelReByType:删除一个节点下的指定的节点类型. */
    @Query("match(from)-[r]-(to) where from.uuid={uuid} and to.type={type} delete r")
    void DelReByType(String uuid, String type);

    @Query("match(from)-[*1..5]-(to) where from.uuid={uuid} return to")
    List<T> findNodeAll(@Param("uuid") String uuid);

    /**
     * lyw
     * 根据类型查询所有顶级节点
     *
     * @param type
     * @return
     */
    //@Query("match(from)-[:Out]-(to) where from.type={type} return from")
    @Query("match(from) where from.type={type} and from.parUuid = '-1' return from")
    List<T> findAllNodeByType(@Param("type") String type);


    /**
     * lyw
     * 根据名称查询数据
     * @param name
     * @return
     */
    @Query("match(from)-[:HAVE]-(to) where from.name={name} and from.type={type} return to")
    List<T> findHAVEDataSourceNextNode(@Param("name") String name , @Param("type") String type);

    /**
     * lyw
     * 根据名称查询数据
     * @param name
     * @return
     */
    @Query("match(from)-[:Out]-(to) where from.name={name} and from.type={type} return to")
    List<T> findOutDataSourceNextNode(@Param("name") String name , @Param("type") String type);

    /**
     * lyw
     * 根据名称，查询输出表的下一个节点
     * @param name
     * @return
     */
    @Query("match(from)-[:In]-(to) where from.name={name} and from.type={type}  return to")
    List<T> findAllInFeedNextInNode(@Param("name") String name,@Param("type") String type);



    @Query("match(from) where from.type={type} and from.name={name} return from")
    List<T> findAllNodeByTypeAndName(@Param("name") String name,@Param("type") String type);


    @Query("match(from)-[*]->(to) where from.uuid={id} and  to.parUuid={id}   return to")
    List<T> findAllNextNodeById(@Param("id") String  id );


    /**
     * 删除所有节点
     *
     */
    @Query("MATCH(from)-[r]-(to) DELETE from, r, to")
    void deletAll();

}
