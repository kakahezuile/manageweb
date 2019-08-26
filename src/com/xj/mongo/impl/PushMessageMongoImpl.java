package com.xj.mongo.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import com.xj.bean.Bulletin;
import com.xj.bean.PushMessage;
import com.xj.bean.PushStatistics;
import com.xj.mongo.MongoUtils;
import com.xj.mongo.PushMessageMongo;

@Repository("pushMessageMongo")
public class PushMessageMongoImpl implements PushMessageMongo{

	@Override
	public void addPushMessage(PushMessage pushMessage) throws Exception {
		MongoUtils.getCollection("PushMessage").insert((DBObject) JSON.parse(new Gson().toJson(pushMessage)));
	}

	@Override
	public boolean updatePushMessage(PushMessage pushMessage) throws Exception {
		BasicDBObject dbObject = new BasicDBObject();
		dbObject.put("emobId",pushMessage.getEmobId());
		dbObject.put("messageId", pushMessage.getMessageId());
		
		BasicDBObject set = new BasicDBObject();
		set.put("type", pushMessage.getType());
		
		BasicDBObject doc = new BasicDBObject();
		doc.put("$set", set);
		
		return MongoUtils.getCollection("PushMessage").update(dbObject, doc , false , true).getN() > 0;
	}
	
	@Override
	public List<PushMessage> getPushMessages(String emobId , Integer time) throws Exception {
		DBCursor dbCursor = null;
		try {
			BasicDBObject dbObject = new BasicDBObject();
			dbObject.put("emobId", emobId);
			dbObject.put("type", "unread");
			dbObject.put("messageId", new BasicDBObject("$gte",time*1000l));
			
			DBCollection dbCollection = MongoUtils.getCollection("PushMessage");
			dbCursor = dbCollection.find(dbObject);
			
			List<PushMessage> list = getList(dbCursor);
			
			BasicDBObject o = new BasicDBObject("$inc",new BasicDBObject("number",1));
			dbCollection.update(dbObject, o,false,true);
			
			return list;
		} finally {
			if (null != dbCursor) {
				dbCursor.close();
			}
		}
	}
	
	private List<PushMessage> getList(DBCursor dbCursor){
		List<PushMessage> list = new ArrayList<PushMessage>();
		Gson gson = new Gson();
		while(dbCursor.hasNext()){
			DBObject dbObject = dbCursor.next();
			String dbStr = JSON.serialize(dbObject);
			PushMessage pushMessage = gson.fromJson(dbStr, PushMessage.class);
			list.add(pushMessage);
		}
		return list;
	}

	@Override
	public List<PushMessage> getPushMessages() throws Exception {
		DBCursor dbCursor = null;
		try {
			DBCollection dbCollection = MongoUtils.getCollection("PushMessage");
			BasicDBObject dbObject = new BasicDBObject();
			dbObject.put("type", "unread");
			dbObject.put("CMD_CODE", 100);
			dbObject.put("timestamp", new BasicDBObject("$gte",System.currentTimeMillis()/1000l-3*24*60*60));
			dbObject.put("equipment", "android");
			
			dbCursor = dbCollection.find(dbObject);
			List<PushMessage> list = getList(dbCursor);
			
			BasicDBObject o = new BasicDBObject("$inc",new BasicDBObject("number",1));
			dbCollection.update(dbObject, o,false,true);
			
			return list;
		} finally {
			if(dbCursor != null){
				dbCursor.close();
			}
		}
	}

	@Override
	public List<PushStatistics> getPushMessageByCode(Integer cmdCode , int page , int pageSize) throws Exception {
		BasicDBObject key = new BasicDBObject("messageId",true);  
		BasicDBObject cond = new BasicDBObject("CMD_CODE",cmdCode); 
		BasicDBObject initial = new BasicDBObject("CMD_CODE",cmdCode);
		initial.append("sum", 0);
		initial.append("unsum", 0);
		String reduce = "function (doc,prev){"
									   +"if(doc.CMD_CODE == prev.CMD_CODE){" 
			                           +"prev.title=doc.title;"
			                           +"prev.content=doc.content;"
			                           +"prev.emobId=doc.emobId;"
			                           +"if(doc.type == 'unread'){prev.unsum++;}"
			                           +"if(doc.type == 'read'){prev.sum++}"
			                           +"}"
			                        +"}";
		
		BasicDBList group =  (BasicDBList) MongoUtils.getCollection("PushMessage").group(key,cond,initial,reduce); 
		return new Gson().fromJson(group.toString(), new TypeToken<List<PushStatistics>>(){}.getType());
	}
	@Override
	public List<PushStatistics> getPushMessageByCodeWithComminuty(Integer cmdCode , int page , int pageSize) throws Exception {
		BasicDBObject key = new BasicDBObject("messageId",true);  
		BasicDBObject cond = new BasicDBObject("CMD_CODE",cmdCode); 
		BasicDBObject initial = new BasicDBObject("CMD_CODE",cmdCode);
		initial.append("sum", 0);
		initial.append("unsum", 0);
		String reduce = "function (doc,prev){"
			+"if(doc.CMD_CODE == prev.CMD_CODE){"
			+"prev.title=doc.title;"
			+"prev.content=doc.content;"
			+"prev.emobId=doc.emobId;"
			+"if(doc.type == 'unread'){prev.unsum++;}"
			+"if(doc.type == 'read'){prev.sum++}"
			+"}"
			+"}";
		
		BasicDBList group =  (BasicDBList) MongoUtils.getCollection("PushMessage").group(key,cond,initial,reduce); 
		return new Gson().fromJson(group.toString(), new TypeToken<List<PushStatistics>>(){}.getType());
	}

	@Override
	public List<PushMessage> getPushMessages(Long messageId , int page , int pageSize) throws Exception {
		DBCursor dbCursor = null;
		try {
			DBCollection dbCollection = MongoUtils.getCollection("PushMessage");
			BasicDBObject dbObject = new BasicDBObject();
			dbObject.put("messageId", messageId);
			dbCursor = dbCollection.find(dbObject).skip((page-1)*pageSize+1).limit(pageSize);
			return getList(dbCursor);
		} finally {
			if (dbCursor != null) {
				dbCursor.close();
			}
		}
	}

	@Override
	public List<Bulletin> getResendPushMessage() {
		BasicDBList result = (BasicDBList)((DBObject)MongoUtils.getDB().doEval("groupResendPushMessage()").get("retval")).get("retval");
		List<Bulletin> list = new ArrayList<Bulletin>();
		for (Object object : result) {
			BasicDBObject obj = (BasicDBObject)object; 
			Bulletin bulletin = new Bulletin();
			bulletin.setMessageId(obj.getLong("messageId"));
			bulletin.setCMD_CODE(obj.getInt("CMD_CODE"));
			bulletin.setTheme(obj.getString("title"));
			bulletin.setBulletinText(obj.getString("content"));
			bulletin.setCommunityId(obj.getInt("communityId"));
			bulletin.setCreateTime(obj.getInt("timestamp"));
			bulletin.setTarget(Arrays.asList((String[]) obj.get("users")));
			list.add(bulletin);
		}
		
		return list;
	}
}