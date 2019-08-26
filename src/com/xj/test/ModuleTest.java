package com.xj.test;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.junit.Test;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.xj.mongo.MongoUtils;
import com.xj.utils.DateUtils;

public class ModuleTest {
	
	/*1450022400-1450627199
	1449417600-1450022399
	1448812800-1449417599
	1448208000-1448812799*/

//模块点击用户
//	public static void main(String[] args) throws IOException {
//		ApplicationContext ac = new  ClassPathXmlApplicationContext("applicationContext.xml");
//		
//		DBCollection collection = MongoUtils.getCollection("hourly_events");
//		DBObject fields = BasicDBObjectBuilder.start().append("emob_id", 1).append("click", 1).append("_id", 0).get();
//		DBObject query = BasicDBObjectBuilder.start().append("service_id", "22").append("hour", BasicDBObjectBuilder.start().append("$lte", 1453175081).append("$gte", 1448899200).get()).get();
//		DBCursor find = collection.find(query, fields);
////		List<DBObject> array = find.toArray();
//		Set<String> user = new HashSet<String>();
//		Set<String> user2 = new HashSet<String>();
//		TryOutDao tryOutDao = (TryOutDao)ac.getBean("tryOutDaoImpl");
//		List<String> selectTryOut = tryOutDao.selectTryOut(2);
//		PrintWriter pw = new PrintWriter(new FileWriter(new File("C:\\Users\\Administrator\\Desktop\\点击过邀请的用户.txt"), true));
//		int userClick = 0 ;
//		int testClick = 0 ;
//		while(find.hasNext()){
//			DBObject next = find.next();
//			String str = (String)next.get("emob_id");
//			Integer clickcount = Integer.valueOf((String)next.get("click"));
//			if(!selectTryOut.contains(str)){
//				user.add(str);
//				userClick += clickcount;
//			}else{
//				user2.add(str);
//				testClick+=clickcount;
//			}
//		}
//		
//		System.out.println("点击用户:"+user.size());
//		System.out.println("点击水军用户:"+user2.size());
//		System.out.println("用户点击次数："+userClick);
//		System.out.println("水军点击次数："+testClick);
//		Iterator<String> iterator = user.iterator();
//		while(iterator.hasNext()){
//			pw.println("'"+iterator.next()+"',");
//		}
//		pw.close();
//	}
	
//	//调取各个模块的点击数据
//	public static void main(String[] args) {
//		Gson json = new Gson();
//		ApplicationContext ac = new  ClassPathXmlApplicationContext("applicationContext.xml");
//		ClickService clickService = (ClickService)ac.getBean("clickServiceImpl");
//		List<PeriodModuleClick> list = clickService.getDayModuleStatistic(22, 1446393600, 1448331477,20);
//		System.out.println(json.toJson(list));
//	}
	
	
//	public static void main(String[] args) throws IOException {
//		ApplicationContext ac = new  ClassPathXmlApplicationContext("applicationContext.xml");
//		DBCollection collection = MongoUtils.getDB().getCollection("hourly_events");
//		DBObject fields = BasicDBObjectBuilder.start().append("emob_id", 1).append("_id", 0).get();
//		DBObject query = BasicDBObjectBuilder.start().append("communityId", "2").append("appVersion", "1.3.6").get();
//		DBCursor find = collection.find(query, fields);
//		Set<String> user = new HashSet<String>();
//		Set<String> user2 = new HashSet<String>();
//		TryOutDao tryOutDao = (TryOutDao)ac.getBean("tryOutDaoImpl");
//		List<String> selectTryOut = tryOutDao.selectTryOut(2);
//		PrintWriter pw = new PrintWriter(new FileWriter(new File("C:\\Users\\Administrator\\Desktop\\首邑溪谷小区1.3.6版本用户.txt"), true));
//		while(find.hasNext()){
//			DBObject next = find.next();
//			String str = (String)next.get("emob_id");
//			if(!selectTryOut.contains(str)){
//				user.add(str);
//			
//			}else{
//				user2.add(str);
//			}
//		}
//		System.out.println("点击用户:"+user.size());
//		System.out.println("点击水军用户:"+user2.size());
//		Iterator<String> iterator = user.iterator();
//		while(iterator.hasNext()){
//			pw.println("'"+iterator.next()+"',");
//		}
//		pw.close();
//	}
//	public static void main(String[] args) {
//		ApplicationContext ac = new  ClassPathXmlApplicationContext("applicationContext.xml");
//		ClickServiceImpl bean = ac.getBean(ClickServiceImpl.class);
//		PeriodModuleClick statistic = bean.getWeekModuleStatistic(2, 1448208001,19);
//		System.out.println(statistic);
//	}
	
	

//	public static void main(String[] args) throws IOException {
//		DBCollection collection = MongoUtils.getCollection("daily_users");
//		DBCollection collection2 = MongoUtils.getCollection("hourly_events");
//		DBObject query =BasicDBObjectBuilder.start().append("appVersion","1.3.5").get();
//		DBObject filed = BasicDBObjectBuilder.start().add("emob_id", 1).append("_id", 0).get();
//		DBCursor cursor = collection.find(query, filed);
//		Set<String> set = new HashSet<String>();
//		while(cursor.hasNext()){
//			DBObject next = cursor.next();
//			set.add(next.get("emob_id").toString());
//		};
//		
//		query =BasicDBObjectBuilder.start().append("appVersion","1.3.5").get();
//		cursor = collection2.find(query, filed);
//		while(cursor.hasNext()){
//			DBObject next = cursor.next();
//			set.add(next.get("emob_id").toString());
//		};
//		cursor.close();
//		PrintWriter pw = new PrintWriter(new FileWriter(new File("C:\\Users\\Administrator\\Desktop\\1.3.5版本用户.txt"), true));
//		Iterator<String> iterator = set.iterator();
//		while(iterator.hasNext()){
//			pw.println("'"+iterator.next()+"',");
//		}
//		pw.close();
//		System.out.println(set);
//	}
	
	@Test
	public void test(){
		Date start = DateUtils.getMonthBegin(DateUtils.getOffsetDate(-3, DateUtils.OFFSET_MONTH));
		List<String> list = DateUtils.getBetweenDates(start, new Date());
		DBCollection collection = MongoUtils.getCollection("clicks_statistic");
		int i = 0;
		for (String string : list) {
			Random random = new Random();
			int random1 = random.nextInt(100);
			int random2 = random.nextInt(100);
			int random3 = random.nextInt(300);
			int random4 = random.nextInt(500);
			int random5 = random.nextInt(30003921);
			int random6 = random.nextInt(100);
			
			DBObject dbObject = BasicDBObjectBuilder.start("regist",random1).add("install", random1+random6).add("activeRegister", random2).add("activeInstall", random2+random.nextInt(100)).add("registerStart", random3).add("installStart", random3+random.nextInt(300)).add("registerClick", random3+random4).add("installClick", random3+random4+random.nextInt(500)).add("registerTime", random5).add("installTime", random5+random.nextInt(20003921)).append("community_id", 1).add("form", "month").add("type", "active").add("begin", DateUtils.formatDate(DateUtils.getMonthBegin(string))).add("end", string).add("totalInstall", i+random1+random6).add("totalRegist", i+random1).get();
			collection.save(dbObject);
			i = i+random1;
		}
	}
	
	@Test
	public void test02(){
		Date start = DateUtils.getMonthBegin(DateUtils.getOffsetDate(-3, DateUtils.OFFSET_MONTH));
		List<String> list = DateUtils.getBetweenDates(start, new Date());
		DBCollection collection = MongoUtils.getCollection("clicks_statistic");
		int i = 0;
		for (String string : list) {
			Random random = new Random();
			int random1 = random.nextInt(100);
			int random2 = random.nextInt(70);
			DBObject dbObject = BasicDBObjectBuilder.start("installCount",i+random1+random2).add("registCount", i+random1).add("disposable", random1+random2 - random.nextInt(100)).add("type", "survey").add("begin", string).add("community_id", 1).get();
			collection.save(dbObject);
			i+=random1;
		}
		
	}
	
	
	@Test
	public void test03(){
		BasicDBList list = (BasicDBList)((DBObject)MongoUtils.getDB().doEval("groupService22()").get("retval")).get("retval");
		int clickcount = 0  ;
		for (Object object : list) {
			BasicDBObject obj = (BasicDBObject)object;
			clickcount+=obj.getInt("click");
		}
		System.out.println("用户人数："+list.size()+"  点击次数："+clickcount);
	}
	
}
