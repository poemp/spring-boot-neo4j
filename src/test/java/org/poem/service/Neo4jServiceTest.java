package org.poem.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.poem.neo4j.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Neo4jServiceTest {

    @Autowired
    private Neo4jService neo4jService;

    @Test
    public void getAll() {
        List<User> bases = this.neo4jService.findAllNodeByType( "DATASOURCE" );
        List<List<User>> lists = new ArrayList<>(  );
        List<User> t = Lists.list(  );
        for (int i = 0; i < bases.size(); i++) {
            if (i != 0 && i % 5 == 0){
                lists.add( t );
                t = Lists.list(  );
            }
            t.add( bases.get( i ) );
        }
        lists.add( t );
        System.err.println( lists );
    }

    @Test
    public void deletAll() {
        this.neo4jService.deletAll();
    }


    @Test
    public void ttt() throws Exception {
        this.neo4jService.deletAll();
        for (int i = 0; i < 100; i++) {
            User user = new User();
            user.setName( "OUT-DATABASE-" + i );
            user.setType( "DATASOURCE" );
            user.setUuid( "OUT-DATABASE-" + i );
            user.setId( user.getUuid() );
            this.neo4jService.addNode( user );


            User user2 = new User();
            user2.setName( "OUT-ASSETS-TABLE-" + i );
            user2.setType( "ASSETSTABLE" );
            user2.setUuid( "OUT-ASSETS-TABLE-" + i );
            user2.setId( user2.getUuid() );
            this.neo4jService.addNode( user2 );
            this.neo4jService.AddReOut( user.getUuid(), user2.getUuid() );

            User user3 = new User();
            user3.setName( "FEED-" + i );
            user3.setType( "FEED" );
            user3.setUuid( "FEED-" + i );
            user3.setId( user3.getUuid() );
            this.neo4jService.addNode( user3 );
            this.neo4jService.AddReIn( user2.getUuid(), user3.getUuid() );

            User user5 = new User();
            user5.setName( "IN-ASSETS-TABLE-" + i );
            user5.setType( "ASSETSTABLE" );
            user5.setUuid( "IN-ASSETS-TABLE-" + i );
            user5.setId( user5.getUuid() );
            this.neo4jService.addNode( user5 );
            this.neo4jService.AddReOut( user3.getUuid(), user5.getUuid() );

            User user4 = new User();
            user4.setName( "IN-DATABASE-" + i );
            user4.setType( "DATASOURCE" );
            user4.setUuid( "IN-DATABASE-" + i );
            user4.setId( user4.getUuid() );
            this.neo4jService.addNode( user4 );
            this.neo4jService.AddReIn( user5.getUuid(), user4.getUuid() );
        }
    }

    @Test
    public void addNode() throws Exception {
        User user = new User();
        user.setName( "lyw-node-2" );
        user.setType( "HIVE" );
        user.setUuid( "1234567" );
        user.setId( user.getUuid() );
        user.setParUuid( "123456" );
        this.neo4jService.addNode( user );

        User user2 = new User();
        user2.setName( "lyw-node-3" );
        user2.setType( "HIVE" );
        user2.setUuid( "12345678" );
        user2.setId( user2.getUuid() );
        user.setParUuid( "123456" );
        this.neo4jService.addNode( user2 );
    }

    @Test
    public void updateNode() throws Exception {
        User user = new User();
        user.setName( "lyw-node-3" );
        user.setType( "HAVE" );
        user.setUuid( "1234567" );
        user.setId( user.getUuid() );
        user.setParUuid( "123456" );
        this.neo4jService.updateNode( user );
    }


    @Test
    public void addRelationship() throws Exception {
        String srcuuid = "1234567";
        String desuuid = "12345678";
        String type = "HAVE";
        this.neo4jService.addRelationship( srcuuid, desuuid, type );
    }

    @Test
    public void delRelationship() throws Exception {
        String srcuuid = "1234567";
        String desuuid = "12345678";
        String type = "HAVE";
        this.neo4jService.delRelationship( srcuuid, desuuid, type );
    }

    @Test
    public void delNode() throws Exception {
        String uuid = "a044f0f2-bb0f-44a2-9c8f-bc03a7ca5fed";
        this.neo4jService.delNode( uuid );
    }

    @Test
    public void delRelationshipByType() {
        String srcuuid;
        String type;
    }

    @Test
    public void FindNodeChildrenByNodeType() throws Exception {
        String type = "HAVE";
        String Nodeuuid = "1234567";
        JSONObject jsonObject = this.neo4jService.FindNodeChildrenByNodeType( type, Nodeuuid );
        System.out.println( jsonObject );
    }

    @Test
    public void FindNodeAllChildren() throws Exception {
        String Nodeuuid = "123456";
        JSONArray jsonArray = this.neo4jService.FindNodeAllChildren( Nodeuuid );
        System.out.println( jsonArray );
    }

    @Test
    public void FindNodeChildren() throws Exception {
        String Nodeuuid = "123456";
        JSONObject jsonObject = this.neo4jService.FindNodeChildren( Nodeuuid );
        System.out.println( jsonObject );
    }

    @Test
    public void FindNodeParentByNodeType() throws Exception {
        String type = "HIVE";
        String Nodeuuid = "1234567";
        JSONObject jsonObject = this.neo4jService.FindNodeParentByNodeType( type, Nodeuuid );
        System.out.println( jsonObject );
    }

    @Test
    public void FindNodeParent() throws Exception {
        String Nodeuuid = "1234567";
        JSONObject jsonObject = this.neo4jService.FindNodeParent( Nodeuuid );
        System.out.println( jsonObject );
    }

    /**
     * @return
     * @throws Exception
     */
    @Test
    public void FindAllRoot() throws Exception {
        JSONArray jsonArray = this.neo4jService.FindAllRoot();
        System.err.println( jsonArray );
    }

    public void FindAllNode() throws Exception {
        String uuid;
    }
}