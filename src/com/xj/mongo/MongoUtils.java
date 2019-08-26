package com.xj.mongo;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.MongoOptions;
import com.xj.utils.SystemProperties;

public class MongoUtils {

	private MongoUtils() {
	}

	private static Mongo mongo = null;//A database connection with internal connection pooling.

	public static DBCollection getCollection(String collection) {
		return getDB().getCollection(collection);
	}

	public static DB getDB() {
		return getDB(SystemProperties.getInstance().get("mongo.database"));
	}

	public static DB getDB(String dbName) {
		if (mongo == null) {
			init();
		}
		
		DB db = mongo.getDB(dbName);
		if (!db.isAuthenticated()) {
			synchronized (MongoUtils.class) {
				if (!db.isAuthenticated()) {
					SystemProperties properties = SystemProperties.getInstance();
					db.authenticate(properties.get("mongo.username"), properties.get("mongo.password").toCharArray());
				}
			}
		}
		
		return db;
	}

	private static void init() {
		SystemProperties properties = SystemProperties.getInstance();
		
		try {
			mongo = new Mongo(properties.get("mongo.server"), Integer.valueOf(properties.get("mongo.port")));
			
			MongoOptions opt = mongo.getMongoOptions();
			opt.connectionsPerHost = Integer.valueOf(properties.get("mongo.pool.size"));
			opt.threadsAllowedToBlockForConnectionMultiplier = Integer.valueOf(properties.get("mongo.block.size"));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}