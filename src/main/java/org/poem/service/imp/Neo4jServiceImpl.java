package org.poem.service.imp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.poem.neo4j.Relationship;
import org.poem.neo4j.user.UserEno4jRepostory;
import org.poem.neo4j.user.entity.User;
import org.poem.service.Neo4jService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 */
@Service
public class Neo4jServiceImpl  implements Neo4jService {

    @Autowired
    private UserEno4jRepostory userEno4jRepostory;

    private static final Logger logger = LoggerFactory.getLogger(  Neo4jServiceImpl.class);
    
    
    private Boolean NodeExist(String uuid) {
        return userEno4jRepostory.findById(uuid).isPresent();
    }


    /**
     *
     * @param user
     * @throws Exception
     */
    @Override
    public void addNode(User user) throws Exception {
        userEno4jRepostory.save(user);
        if (user.getParUuid() != null && NodeExist(user.getParUuid())) {
            System.out.println("开始建立关系");
            addRelationship(user.getParUuid(), user.getUuid(), "HAVE");
        }
    }

    /**
     *
     * @param user
     * @throws Exception
     */
    @Override
    public void updateNode(User user) throws Exception {
        List<User> tmp = userEno4jRepostory.findByUuid(user.getUuid());
        if (!tmp.isEmpty()) {
            user.setId(tmp.get(0).getId());
        }
        userEno4jRepostory.save(user);
        
    }

    /**
     *
     * @param srcuuid
     * @param desuuid
     * @param type
     * @throws Exception
     */
    @Override
    public void addRelationship(String srcuuid, String desuuid, String type) throws Exception {
        if ("HAVE".equals(type)) {
            userEno4jRepostory.AddReHave(srcuuid, desuuid);
        } else if ("USE".equals(type)) {
            userEno4jRepostory.AddReUse(srcuuid, desuuid);
        } else {
            System.out.println("关系不存在");
        }
    }

    /**
     *
     * @param srcuuid
     * @param desuuid
     * @param type
     * @throws Exception
     */
    @Override
    public void delRelationship(String srcuuid, String desuuid, String type) throws Exception {
        userEno4jRepostory.DelRe(srcuuid, desuuid);
    }

    /**
     *
     * @param uuid
     * @throws Exception
     */
    @Override
    public void delNode(String uuid) throws Exception {
// 等待删除规则
        List<User> tmp = userEno4jRepostory.findByUuid(uuid);
        tmp.forEach(user -> {
            userEno4jRepostory.delete(user);
        });
    }

    /**
     *
     * @param srcuuid
     * @param type
     */
    @Override
    public void delRelationshipByType(String srcuuid, String type) {
        userEno4jRepostory.DelReByType(srcuuid, type);
    }

    /**
     * FindNodeChildrenByRelationshipTypeAndNodeType:通过关系类型以及子节点类型查找一级子节点
     *
     * @author Administrator
     * @param relationship
     * @param uuid
     * @param nodetype
     * @return
     * @since JDK 1.7
     */
    private JSONObject FindNodeChildrenByRelationshipTypeAndNodeType(Relationship relationship, String uuid,
                                                                     String nodetype) {
        JSONObject res = new JSONObject();
        List<User> tmp;
        if (Relationship.All.equals(relationship) | Relationship.HAVE.equals(relationship)) {
            tmp = userEno4jRepostory.findReHaveChildrenByType(uuid, nodetype);
            JSONArray array = JSONArray.parseArray(JSON.toJSONString(tmp));
            res.put("HAVE", array);
        }
        if (Relationship.All.equals(relationship) | Relationship.USE.equals(relationship)) {
            tmp = userEno4jRepostory.findReUseChildrenByType(uuid, nodetype);
            JSONArray array = JSONArray.parseArray(JSON.toJSONString(tmp));
            res.put("USE", array);
        }
        return res;
    }
    /**
     *
     * @param type
     * @param Nodeuuid
     * @return
     * @throws Exception
     */
    @Override
    public JSONObject FindNodeChildrenByNodeType(String type, String Nodeuuid) throws Exception {
        return FindNodeChildrenByRelationshipTypeAndNodeType(Relationship.All, Nodeuuid, type);
    }

    /**
     *
     * @param Nodeuuid
     * @return
     * @throws Exception
     */
    @Override
    public JSONArray FindNodeAllChildren(String Nodeuuid) throws Exception {
        List<User> tmp = new ArrayList<>();
        tmp = userEno4jRepostory.findChildrenAll(Nodeuuid);
        JSONArray array = JSONArray.parseArray(JSON.toJSONString(tmp));
        return array;
    }


    /**
     * FindNodeChildrenByRelationshipType:通过关系类型查找一级子节点
     *
     * @author Administrator
     * @param relationship
     * @param uuid
     * @return
     * @since JDK 1.7
     */
    private JSONObject FindNodeChildrenByRelationshipType(Relationship relationship, String uuid) {
        JSONObject res = new JSONObject();
        List<User> tmp = new ArrayList<>();
        if (Relationship.All.equals(relationship) | Relationship.HAVE.equals(relationship)) {
            tmp = userEno4jRepostory.findReHaveChildren(uuid);
            JSONArray array = JSONArray.parseArray(JSON.toJSONString(tmp));
            res.put("HAVE", array);
        }
        if (Relationship.All.equals(relationship) | Relationship.USE.equals(relationship)) {
            tmp = userEno4jRepostory.findReUseChildren(uuid);
            JSONArray array = JSONArray.parseArray(JSON.toJSONString(tmp));
            res.put("USE", array);
        }
        return res;
    }
    /**
     *
     * @param Nodeuuid
     * @return
     * @throws Exception
     */
    @Override
    public JSONObject FindNodeChildren(String Nodeuuid) throws Exception {
        return FindNodeChildrenByRelationshipType(Relationship.All, Nodeuuid);
    }

    /**
     *
     * @param type
     * @param Nodeuuid
     * @return
     * @throws Exception
     */
    @Override
    public JSONObject FindNodeParentByNodeType(String type, String Nodeuuid) throws Exception {
        return FindNodeParentByRelationshipTypeAndNodeType(Relationship.All, Nodeuuid, type);
    }
    /**
     * FindNodeParentByRelationshipTypeAndNodeType:通过关系类型以及父节点类型查找一级父节点
     *
     * @author Administrator
     * @param relationship
     * @param uuid
     * @param nodetype
     * @return
     * @since JDK 1.7
     */
    private JSONObject FindNodeParentByRelationshipTypeAndNodeType(Relationship relationship, String uuid,
                                                                   String nodetype) {
        JSONObject res = new JSONObject();
        List<User> tmp = new ArrayList<>();
        if (Relationship.All.equals(relationship) | Relationship.HAVE.equals(relationship)) {
            tmp = userEno4jRepostory.findReHaveParentByType(uuid, nodetype);
            JSONArray array = JSONArray.parseArray(JSON.toJSONString(tmp));
            res.put("HAVE", array);
        }
        if (Relationship.All.equals(relationship) | Relationship.USE.equals(relationship)) {
            tmp = userEno4jRepostory.findReUseParentByType(uuid, nodetype);
            JSONArray array = JSONArray.parseArray(JSON.toJSONString(tmp));
            res.put("USE", array);
        }
        return res;
    }

    /**
     *
     * @param Nodeuuid
     * @return
     * @throws Exception
     */
    @Override
    public JSONObject FindNodeParent(String Nodeuuid) throws Exception {
        return FindNodeParentByRelationshipType(Relationship.All, Nodeuuid);
    }
    /**
     * FindNodeParentByRelationshipType:通过关系查找所有一级父节点
     *
     * @author Administrator
     * @param relationship
     * @param uuid
     * @return
     * @since JDK 1.7
     */
    private JSONObject FindNodeParentByRelationshipType(Relationship relationship, String uuid) {
        JSONObject res = new JSONObject();
        List<User> tmp = new ArrayList<>();
        if (Relationship.All.equals(relationship) | Relationship.HAVE.equals(relationship)) {
            tmp = userEno4jRepostory.findReHaveParent(uuid);
            JSONArray array = JSONArray.parseArray(JSON.toJSONString(tmp));
            res.put("HAVE", array);
        }
        if (Relationship.All.equals(relationship) | Relationship.USE.equals(relationship)) {
            tmp = userEno4jRepostory.findReUseParent(uuid);
            JSONArray array = JSONArray.parseArray(JSON.toJSONString(tmp));
            res.put("USE", array);
        }
        return res;
    }
    /**
     *
     * @return
     * @throws Exception
     */
    @Override
    public JSONArray FindAllRoot() throws Exception {
        List<User> tmp = new ArrayList<>();
        tmp = userEno4jRepostory.findROOTAll();
        JSONArray array = JSONArray.parseArray( JSON.toJSONString(tmp));
        return array;
    }

    /**
     *
     * @param uuid
     * @return
     * @throws Exception
     */
    @Override
    public JSONArray FindAllNode(String uuid) throws Exception {
        List<User> tmp = new ArrayList<>();
        tmp = userEno4jRepostory.findNodeAll(uuid);
        JSONArray array = JSONArray.parseArray(JSON.toJSONString(tmp));
        return array;
    }

    @Override
    public void AddReOut(String srcuuid, String desuuid) {
        userEno4jRepostory.AddReOut( srcuuid,desuuid );
    }

    @Override
    public void AddReIn(String srcuuid, String desuuid) {
        userEno4jRepostory.AddReIn( srcuuid,desuuid );
    }

    @Override
    public void deletAll() {
        userEno4jRepostory.deletAll();
    }

    @Override
    public List<User> findAllNodeByType(String type) {
        return  userEno4jRepostory.findAllNodeByType(type);
    }


}
