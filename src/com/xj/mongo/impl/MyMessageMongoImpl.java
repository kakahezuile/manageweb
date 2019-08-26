package com.xj.mongo.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import com.xj.bean.Page;
import com.xj.bean.PushMessage;
import com.xj.bean.PushStatistics;
import com.xj.mongo.MongoUtils;
import com.xj.mongo.MyMessageMongo;

@Repository
public class MyMessageMongoImpl implements MyMessageMongo {

	@Override
	public List<PushStatistics> statMessageByCommunityAndCode(Integer communityId,Integer cmdCode) {
		BasicDBObject key = new BasicDBObject("messageId",true);  
		BasicDBObject cond = new BasicDBObject("CMD_CODE",cmdCode); 
		cond.put("communityId", communityId);
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
	public List<PushStatistics> getConsumeTimeByMessageId(Long messageId,Integer  cmdCode) {
		BasicDBObject key = new BasicDBObject("messageId",true);  
		BasicDBObject cond = new BasicDBObject("messageId",messageId); 
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
	public Map<String,Object> getBulletinsDetailsByMessageId(Integer communityId,Long messageId, int start, Integer pageSize) {
		Map<String, Object> map = new HashMap<String, Object>();
		DBCursor dbCursor = null;
		try {
			DBCollection dbCollection = MongoUtils.getCollection("PushMessage");
			BasicDBObject dbObject = new BasicDBObject();
			dbObject.put("communityId", communityId);
			dbObject.put("messageId",messageId );
			dbCursor = dbCollection.find(dbObject).skip(start).limit(pageSize);
			int count = dbCollection.find(dbObject).count();
			while(dbCursor.hasNext()){
				DBObject next = dbCursor.next();
				Object object = next.get("emobId");
				Object object2 = next.get("type");
				Object object3 = next.get("number");
				Object object4 = next.get("timestamp");
				Object object5 = next.get("equipment");
				if(object4 instanceof Integer){
					object4 = Long.parseLong(object4+"");
				}
				
				if(object instanceof String  && object2 instanceof String){
					map.put((String)object,new Object[]{object2,object3,object4,object5});
				}
			}
			map.put("count", count+"");
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(dbCursor != null){
				dbCursor.close();
			}
		}
		
		return map;
	}

	@Override
	public List<PushStatistics> getUsersByCommunityId(Integer communityId, int page, int pageSize,Integer  cmdCode) {
		DBCollection dbCollection = MongoUtils.getCollection("PushMessage");
		BasicDBObject key = new BasicDBObject("emobId",true);  
		BasicDBObject cond = new BasicDBObject("communityId",communityId); 
		BasicDBObject initial = new BasicDBObject("CMD_CODE",cmdCode);
		initial.append("sum", 0);
		initial.append("unsum", 0);
		
		String reduce = "function (doc,prev){"
			+"if(doc.CMD_CODE == prev.CMD_CODE){"
			+"prev.title=doc.title;"
			+"prev.content=doc.content;"
			+"if(doc.type == 'unread'){prev.unsum++;}"
			+"if(doc.type == 'read'){prev.sum++}"
			+"}"
			+"}";
		
		BasicDBList group =  (BasicDBList) dbCollection.group(key,cond,initial,reduce);
		return new Gson().fromJson(group.toString(), new TypeToken<List<PushStatistics>>(){}.getType());
	}


	@Override
	public Page<PushMessage> getBulletinsDetailsEmobId(Integer communityId, String emobId, int page, int pageSize,Integer  cmdCode) {
		Page<PushMessage> pageBean = null;
		DBCursor dbCursor = null;
		try {
			DBCollection dbCollection = MongoUtils.getCollection("PushMessage");
			BasicDBObject dbObject = new BasicDBObject();
			dbObject.put("communityId", communityId);
			dbObject.put("emobId",emobId );
			dbObject.put("CMD_CODE",cmdCode );
			int count = dbCollection.find(dbObject).count();
			pageBean = new Page<PushMessage>(page, count, pageSize, 10);
			
			dbCursor = dbCollection.find(dbObject).skip(pageBean.getStartRow()).limit(pageSize).sort(new BasicDBObject("timestamp",-1));
			List<PushMessage> list = getList(dbCursor);
			
			for (PushMessage pushMessage : list) {
				pushMessage.setNickname(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(pushMessage.getTimestamp()*1000)));
			}
			
			pageBean.setPageData(list);
		} catch (Exception e) {
			e.printStackTrace();
		
		}finally{
			if(dbCursor != null){
				dbCursor.close();
			}
		}
		
		return pageBean;
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
}