package org.poem.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.poem.neo4j.user.entity.User;

import java.util.List;

/**
 * @author Administrator
 */
public interface Neo4jService {
    /**
     * addNode:添加一个节点
     *
     * @param user
     * @throws Exception
     * @author Administrator
     * @since JDK 1.7
     */
    void addNode(User user) throws Exception;

    /**
     * updateNode:更新一个节点
     *
     * @param user
     * @throws Exception
     * @author Administrator
     * @since JDK 1.7
     */
    void updateNode(User user) throws Exception;

    /**
     * addRelationship:添加节点之间的关系
     *
     * @param srcuuid
     * @param desuuid
     * @param type
     * @throws Exception
     * @author Administrator
     * @since JDK 1.7
     */
    void addRelationship(String srcuuid, String desuuid, String type) throws Exception;

    /**
     * delRelationship:接触节点之间的关系
     *
     * @param srcuuid
     * @param desuuid
     * @param type
     * @throws Exception
     * @author Administrator
     * @since JDK 1.7
     */
    void delRelationship(String srcuuid, String desuuid, String type) throws Exception;

    /**
     * delNode:删除一个节点
     *
     * @param uuid
     * @throws Exception
     * @author Administrator
     * @since JDK 1.7
     */
    void delNode(String uuid) throws Exception;

    /**
     * delRelationshipByType:删除该节符合类型的所有关系
     *
     * @param srcuuid
     * @param type
     * @author Administrator
     * @since JDK 1.7
     */
    void delRelationshipByType(String srcuuid, String type);

    /**
     * FindNodeChildren:查找该节点下的所有指定类型的一级子节点
     *
     * @param type
     * @param Nodeuuid
     * @return
     * @throws Exception
     * @author Administrator
     * @since JDK 1.7
     */
    JSONObject FindNodeChildrenByNodeType(String type, String Nodeuuid) throws Exception;

    /**
     * FindNodeAllChildren:找到该节点下的所有子节点
     *
     * @param Nodeuuid
     * @return
     * @throws Exception
     * @author Administrator
     * @since JDK 1.7
     */
    JSONArray FindNodeAllChildren(String Nodeuuid) throws Exception;

    /**
     * FindNodeChildren:找到该节点下所有一级子节点
     *
     * @param Nodeuuid
     * @return
     * @throws Exception
     * @author Administrator
     * @since JDK 1.7
     */
    JSONObject FindNodeChildren(String Nodeuuid) throws Exception;

    /**
     * FindNodeParent:查找该节点的所有指定类型的一级父节点
     *
     * @param type
     * @param Nodeuuid
     * @return
     * @throws Exception
     * @author Administrator
     * @since JDK 1.7
     */
    JSONObject FindNodeParentByNodeType(String type, String Nodeuuid) throws Exception;

    /**
     * FindNodeParent:查找该节点下所有一级父节点
     *
     * @param Nodeuuid
     * @return
     * @throws Exception
     * @author Administrator
     * @since JDK 1.7
     */
    JSONObject FindNodeParent(String Nodeuuid) throws Exception;

    /**
     * FindAllRoot:获取所有根节点
     *
     * @return
     * @throws Exception
     * @author Administrator
     * @since JDK 1.7
     */
    JSONArray FindAllRoot() throws Exception;

    /**
     * FindAllRoot:获取所有节点
     *
     * @return
     * @throws Exception
     * @author Administrator
     * @since JDK 1.7
     */
    JSONArray FindAllNode(String uuid) throws Exception;


    /**
     *
     * @param srcuuid
     * @param desuuid
     */
    void AddReOut(String srcuuid,  String desuuid);

    /**
     *
     * @param srcuuid
     * @param desuuid
     */
    void AddReIn(String srcuuid,  String desuuid);


    void deletAll();

    List<User> findAllNodeByType(String type);
}
