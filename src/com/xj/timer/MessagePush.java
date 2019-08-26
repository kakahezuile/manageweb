package com.xj.timer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import com.google.gson.Gson;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import com.xj.getui.pushtoList;
import com.xj.mongo.MongoUtils;

/**
 * 定时重推
 */
public class MessagePush {

	private Gson gson = new Gson();
	
	@SuppressWarnings("unchecked")
	public void execute() {
		BasicDBList dBList = (BasicDBList)((DBObject)MongoUtils.getDB().doEval("groupResendPushMessage()").get("retval")).get("retval");
		for (Object object : dBList) {
			BasicDBObject obj = (BasicDBObject)object;
			Map<String,Object> node = new HashMap<String,Object>();
			node.put("messageId", obj.getLong("messageId"));
			node.put("CMD_CODE", obj.getInt("CMD_CODE"));
			node.put("title", obj.getString("title"));
			node.put("content", obj.getString("content"));
			node.put("timestamp", obj.getInt("timestamp"));
			List<String> list = (List<String>)obj.get("users");
			if(CollectionUtils.isEmpty(list)){
				continue;
			}
			DBObject q = BasicDBObjectBuilder.start("messageId", obj.getLong("messageId")).add("CMD_CODE", obj.getInt("CMD_CODE")).add("type", "unread").add("equipment", "android").add("timestamp",obj.getInt("timestamp")).get();
			BasicDBObject o = new BasicDBObject("$inc",new BasicDBObject("number",1));
			MongoUtils.getCollection("PushMessage").updateMulti(q, o);
			pushtoList.pushtoTransmissionList1000(list,gson.toJson(node), obj.getString("content"));
		}
	}
}