package com.xj.mongo.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.gson.Gson;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import com.xj.bean.Messages;
import com.xj.bean.UsersApp;
import com.xj.dao.MyUserDao;
import com.xj.mongo.MessageMongo;
import com.xj.mongo.MongoUtils;

@Repository("messageMongo")
public class MessageMongoImpl implements MessageMongo {
	
	@Autowired
	private MyUserDao myUserDao;

	@Override
	public void insertMessage(Messages messages) throws Exception {
		MongoUtils.getCollection("huanXinMassage").insert((DBObject) JSON.parse(new Gson().toJson(messages)));
	}

	@Override
	public List<Messages> getListMessage(String from, String to, Long start, Long end) throws Exception {
		DBCursor dbCursor = null;
		try {
			DBCollection dbCollection = MongoUtils.getCollection("huanXinMassage");
			BasicDBObject dbObject = new BasicDBObject();
			
			if(from != null && !"".equals(from) && to != null && !"".equals(to)){
				if(start != null && end != null){
					dbObject.put("messageFrom", from);
					dbObject.put("messageTo", to);
					dbObject.put("timestamp", new BasicDBObject("$gte",start));
					dbObject.put("timestamp", new BasicDBObject("$lte",end));
					dbCursor = dbCollection.find(dbObject); // 时间 与 来自谁 向谁
				}else{
					dbObject.put("messageFrom", from);
					dbObject.put("messageTo", to);
					dbCursor = dbCollection.find(dbObject); // 来自谁向谁
				}
			}else{
				if(from == null && to == null){
					if(start == null && end == null){
						dbCursor = dbCollection.find(); // 查询全部
					}else{
						dbObject.put("timestamp", new BasicDBObject("$gte",start));
						dbObject.put("timestamp", new BasicDBObject("$lte",end));
						dbCursor = dbCollection.find(dbObject); // 根据时间轴进行查询 start  end
					}
				}
			}
			
			return getList(dbCursor);
		} finally {
			if(dbCursor != null){
				dbCursor.close();
			}
		}
	}
	
	private List<Messages> getList(DBCursor dbCursor) throws Exception{
		Map<String, String> map = new HashMap<String, String>();
		String fromName = "";
		String toName = "";
		Gson gson = new Gson();
		List<Messages> list = new ArrayList<Messages>();
		while (dbCursor.hasNext()) {
			DBObject dbObject2 = dbCursor.next();
			String dbStr = JSON.serialize(dbObject2);
			Messages messages = gson.fromJson(dbStr, Messages.class);
			if(map.get(messages.getMessageFrom()) == null){
			
				UsersApp user1 = myUserDao.getUserByEmobId(messages.getMessageFrom());
				if(user1 != null){
					fromName = user1.getNickname();
				}
				map.put(messages.getMessageFrom(), fromName);
			}else{
				fromName = map.get(messages.getMessageFrom());
			}
			if(map.get(messages.getMessageTo()) == null){
				UsersApp user2 = myUserDao.getUserByEmobId(messages.getMessageTo());
				if(user2 != null){
					toName = user2.getNickname();
				}
				map.put(messages.getMessageTo(), toName);
			}else{
				toName = map.get(messages.getMessageTo());
			}
			messages.setFromName(fromName);
			messages.setToName(toName);
			list.add(messages);
		}
		map.clear();
		return list;
	}
	
	@Override
	public List<Messages> getListByText(Long start, Long end , String text) throws Exception {
		DBCursor dbCursor = null;
		try {
			DBCollection dbCollection = MongoUtils.getCollection("huanXinMassage");
			BasicDBObject dbObject = new BasicDBObject();
			dbObject.put("timestamp", new BasicDBObject("$gte",start));
			dbObject.put("timestamp", new BasicDBObject("$lte",end));
			Pattern pattern = Pattern.compile("^.*" + text+ ".*$",Pattern.CASE_INSENSITIVE);
			dbObject.put("msg", new BasicDBObject("$regex",pattern));
			dbCursor = dbCollection.find(dbObject); 
			return getList(dbCursor);
		} finally {
			if(dbCursor != null){
				dbCursor.close();
			}
		}
	}

	@Override
	public Messages getMessageByUuid(String uuid) throws Exception {
		Messages messages = null;
		DBCursor dbCursor = null;
		try {
			BasicDBObject dbObject = new BasicDBObject();
			dbObject.put("uuid", uuid);
			dbCursor = MongoUtils.getCollection("huanXinMassage").find(dbObject);
			List<Messages> list = getList(dbCursor);
			if(list != null && list.size() > 0){
				messages = list.get(0);
			}
		} finally {
			if(dbCursor != null){
				dbCursor.close();
			}
		}
		
		return messages;
	}

	@Override
	public List<Messages> getMessageByMsgId(String... msgId) throws Exception {
		if (msgId.length == 0) {
			return null;
		}
		
		DBCursor dbCursor = null;
		try {
			DBCollection dbCollection = MongoUtils.getCollection("huanXinMassage");
			BasicDBObject dbObject = new BasicDBObject();
			if (msgId.length == 1) {
				dbObject.put("msgId", msgId);
			} else {
				BasicDBList values = new BasicDBList();
				for (int i = 0; i < msgId.length; i++) {
					values.add(msgId[i]);
				}
				dbObject.put("msgId", new BasicDBObject("$in", values));
			}
			dbCursor = dbCollection.find(dbObject);
			return getList(dbCursor);
		} finally {
			if(dbCursor != null){
				dbCursor.close();
			}
		}
	}

	@Override
	public List<Messages> getListMessage(String to, Long time, boolean isMoreThan) throws Exception {
		DBCursor dbCursor = null;
		try {
			DBCollection dbCollection = MongoUtils.getCollection("huanXinMassage");
			BasicDBObject dbObject = new BasicDBObject();
			dbObject.put("messageTo", to);
			if(isMoreThan){ // 大于这个时间  此时间之后的内容
				dbObject.put("timestamp", new BasicDBObject("$gt",time));
				dbCursor = dbCollection.find(dbObject).limit(10);
			}else{ // 小于这个时间 此时间之前的内容
				dbObject.put("timestamp", new BasicDBObject("$lt",time));
				dbCursor = dbCollection.find(dbObject).sort(new BasicDBObject("timestamp",-1)).limit(10);
			}
			return getList(dbCursor);
		} finally {
			if(dbCursor != null){
				dbCursor.close();
			}
		}
	}

	@Override
	public List<Messages> getListMessage(String from, String to, Long time) throws Exception {
		DBCursor dbCursor = null;
		try {
			DBCollection dbCollection = MongoUtils.getCollection("huanXinMassage");
			BasicDBObject dbObject = new BasicDBObject();
			dbObject.put("messageFrom", from);
			dbObject.put("messageTo", to);
			dbObject.put("timestamp", new BasicDBObject("$lt",time));
			dbCursor = dbCollection.find().sort(new BasicDBObject("timestamp",-1)).limit(10);
			return getList(dbCursor);
		} finally {
			if(dbCursor != null){
				dbCursor.close();
			}
		}
	}

	@Override
	public List<Messages> getListMessage(String from, String to, Long time, boolean isMoreThan) throws Exception {
		DBCursor dbCursor = null;
		try {
			DBCollection dbCollection = MongoUtils.getCollection("huanXinMassage");
			BasicDBObject dbObject = new BasicDBObject();
			dbObject.put("messageTo", to);
			dbObject.put("messageFrom", from);
			if(isMoreThan){ // 大于这个时间  此时间之后的内容
				dbObject.put("timestamp", new BasicDBObject("$gt",time));
				dbCursor = dbCollection.find(dbObject).limit(10);
			}else{ // 小于这个时间 此时间之前的内容
				dbObject.put("timestamp", new BasicDBObject("$lt",time));
				dbCursor = dbCollection.find(dbObject).sort(new BasicDBObject("timestamp",-1)).limit(10);
			}
			return getList(dbCursor);
		} finally {
			if(dbCursor != null){
				dbCursor.close();
			}
		}
	}

	@Override
	public List<Messages> getListMessage(String from, String to) throws Exception {
		DBCursor dbCursor = null;
		try {
			BasicDBObject dbObject = new BasicDBObject();
			dbObject.put("messageTo", to);
			dbObject.put("messageFrom", from);
			
			dbCursor = MongoUtils.getCollection("huanXinMassage").find(dbObject);
			
			return getList(dbCursor);
		} finally {
			if(dbCursor != null){
				dbCursor.close();
			}
		}
	}

	@Override
	public List<Messages> getListMessageNext(String from, String to , Long time) throws Exception {
		DBCursor dbCursor = null;
		try {
			DBCollection dbCollection = MongoUtils.getCollection("huanXinMassage");
			BasicDBObject dbObject = new BasicDBObject();
			dbObject.put("messageTo", to);
			dbObject.put("messageFrom", from);
			dbObject.put("timestamp", new BasicDBObject("$gt",time));
			dbCursor = dbCollection.find(dbObject);
			return getList(dbCursor);
		} finally {
			if(dbCursor != null){
				dbCursor.close();
			}
		}
	}

	@Override
	public Messages getMessageInMaxTime() throws Exception {
		DBCursor dbCursor = null;
		try {
			BasicDBObject dbObject = new BasicDBObject();
			dbCursor = MongoUtils.getCollection("huanXinMassage").find(dbObject).sort(new BasicDBObject("timestamp",-1)).limit(1);
			if (dbCursor.hasNext()) {
				return new Gson().fromJson(JSON.serialize(dbCursor.next()), Messages.class);
			}
			
			return null;
		} finally {
			if(dbCursor != null){
				dbCursor.close();
			}
		}
	}

	@Override
	public List<Messages> getListmessage(String to) throws Exception {
		DBCursor dbCursor = null;
		try {
			BasicDBObject dbObject = new BasicDBObject();
			dbObject.put("messageTo", to);
			dbCursor = MongoUtils.getCollection("huanXinMassage").find(dbObject);
			return getList(dbCursor);
		} finally {
			if(dbCursor != null){
				dbCursor.close();
			}
		}
	}

	@Override
	public List<Messages> getListMessages(String type) throws Exception {
		DBCursor dbCursor = null;
		try {
			BasicDBObject dbObject = new BasicDBObject();
			dbObject.put("chatType", type);
			dbCursor = MongoUtils.getCollection("huanXinMassage").find(dbObject); 
			return getList(dbCursor);
		} finally {
			if(dbCursor != null){
				dbCursor.close();
			}
		}
	}
}