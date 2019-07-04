/**  
 * Project Name:springneo4j  
 * File Name:Base.java  
 * Package Name:com.dsg.springneo4j.data.domain  
 * Date:2019年1月8日下午12:57:50  
 * Copyright (c) 2019, wangyl@dsgdata.com All Rights Reserved.  
 *  
*/

package org.poem.neo4j.user.entity;

import lombok.Data;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.poem.neo4j.base.Entity;

/**
 * ClassName:Base <br/>
 * Date: 2019年1月8日 下午12:57:50 <br/>
 * 
 * @author Administrator
 * @version
 * @since JDK 1.7
 * @see
 */
@NodeEntity
@Data
public class Base extends Entity {
	private String uuid;
	private String name;
	private String type;
	private String parUuid;
	private String info;

}
