package com.xj.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.xj.bean.BlackList;
import com.xj.bean.CommunityId;
import com.xj.bean.IdCount;
import com.xj.bean.LifeCircle;
import com.xj.bean.LifeCircleDetailList;
import com.xj.bean.LifeCircleGet;
import com.xj.bean.LifeCircleGetAll;
import com.xj.bean.LifeCircleNumer;
import com.xj.bean.LifeCircleSelect;
import com.xj.bean.LifeCircleTop;
import com.xj.bean.LifeCircleVO;
import com.xj.bean.LifePhoto;
import com.xj.bean.LifeSensitive;
import com.xj.bean.Page;
import com.xj.bean.ScanningTime;
import com.xj.bean.life.Favorite;
import com.xj.dao.LifeCircleDao;
import com.xj.httpclient.utils.DaoUtils;
import com.xj.httpclient.vo.MyReturnKey;

@Repository("lifeCircleDao")
public class LifeCircleDaoImpl extends MyBaseDaoImpl implements LifeCircleDao {

	@Override
	public Integer addLifeCircle(LifeCircle lifeCircle) throws Exception {
		MyReturnKey key = new MyReturnKey();
		lifeCircle.setPraiseSum(0);
		lifeCircle.setContentSum(0);
		if (lifeCircle.getType() == null) {
			lifeCircle.setType(0);
		}
		this.save(lifeCircle, key);
		return key.getId();
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean updateLifeCircle(LifeCircle lifeCircle) throws Exception {
		String sql = "UPDATE life_circle SET ";
		List<Object> list = new ArrayList<Object>();
		Object resultObject[] = DaoUtils.reflect(lifeCircle);
		if (resultObject != null && resultObject[1] != null
				&& ((List<Object>) resultObject[1]).size() > 0) {
			list = (List<Object>) resultObject[1];
			sql += (String) resultObject[0];
		} else {
			return false;
		}
		sql += " WHERE life_circle_id = ? ";
		list.add(lifeCircle.getLifeCircleId());
		return this.updateData(sql, list, null) > 0;
	}

	@Override
	public Page<LifeCircle> getLifeCircles(Integer communityId, String emobId,
			Integer pageNum, Integer pageSize, Integer nvm) throws Exception {
		String sql = " SELECT l.community_id , l.type , l.emob_id , l.life_circle_id , l.life_content , l.praise_sum , l.create_time "
				+ " ,l.emob_group_id , u.character_values , u.avatar , u.nickname , ifnull(a.activity_title,'') as activity_title FROM life_circle l LEFT JOIN "
				+ " users u ON l.emob_id = u.emob_id LEFT JOIN activities a ON a.emob_group_id = l.emob_group_id WHERE l.community_id = ? AND "
				+ " l.emob_id not in (SELECT emob_id_to FROM black_list WHERE emob_id_from = ? AND status = 'circle') AND l.status = 'ongoing' "
				+ " ORDER BY l.create_time DESC ";
		return this.getData4Page(sql, new Object[] { communityId, emobId },
				pageNum, pageSize, nvm, LifeCircle.class);
	}

	@Override
	public Page<LifeCircle> getLifeCircles(Integer communityId, String emobId,
			Integer pageNum, Integer pageSize, Integer nvm, int appVersion)
			throws Exception {
		String sql = " SELECT l.community_id , l.type , l.emob_id , l.life_circle_id , l.life_content , l.praise_sum , l.create_time "
				+ " ,l.emob_group_id , u.character_values , u.avatar , u.nickname , ifnull(a.activity_title,'') as activity_title FROM life_circle l LEFT JOIN "
				+ " users u ON l.emob_id = u.emob_id LEFT JOIN activities a ON a.emob_group_id = l.emob_group_id WHERE l.type = 0 and l.community_id = ? AND "
				+ " l.emob_id not in (SELECT emob_id_to FROM black_list WHERE emob_id_from = ? AND status = 'circle') AND l.status = 'ongoing' "
				+ " ORDER BY l.create_time DESC ";
		return this.getData4Page(sql, new Object[] { communityId, emobId },
				pageNum, pageSize, nvm, LifeCircle.class);
	}

	@Override
	public boolean updateLifeCircle(Integer lifeCircleId) throws Exception {
		String sql = "UPDATE life_circle SET praise_sum = praise_sum + 1 , update_time = unix_timestamp() WHERE life_circle_id = ?  ";
		List<Object> list = new ArrayList<Object>();
		list.add(lifeCircleId);
		int result = this.updateData(sql, list, null);
		return result > 0;
	}

	@Override
	public Page<LifeCircleVO> getLifeCircleVos(String emobId, Integer pageNum,
			Integer pageSize, Integer navNum) throws Exception {
		String sql = " SELECT FROM_UNIXTIME(create_time, '%Y-%m-%d') as timstamp , life_content as content , praise_sum , create_time , content_sum , life_circle_id FROM life_circle WHERE emob_id = ? AND status = 'ongoing' ORDER BY create_time DESC ";
		return this.getData4Page(sql, new Object[] { emobId }, pageNum,
				pageSize, navNum, LifeCircleVO.class);
	}

	@Override
	public boolean updateContentSum(Integer lifeCircleId) throws Exception {
		String sql = "UPDATE life_circle SET content_sum = content_sum + 1 , update_time = unix_timestamp() WHERE life_circle_id = ?  ";
		List<Object> list = new ArrayList<Object>();
		list.add(lifeCircleId);
		int result = this.updateData(sql, list, null);
		return result > 0;
	}

	@Override
	public LifeCircle getLifeCircle(Integer lifeCircleId) throws Exception {
		String sql = " SELECT l.community_id , l.type ,  l.emob_id , l.life_circle_id , l.life_content , l.praise_sum , l.create_time "
				+ " , l.emob_group_id , u.character_values , u.avatar , u.nickname , a.activity_title FROM life_circle l LEFT JOIN "
				+ " users u ON l.emob_id = u.emob_id LEFT JOIN activities a ON a.emob_group_id = l.emob_group_id WHERE l.life_circle_id = ? ";
		List<LifeCircle> list = this.getList(sql,
				new Object[] { lifeCircleId }, LifeCircle.class);
		return list != null && list.size() > 0 ? list.get(0) : null;
	}

	@Override
	public LifeCircleNumer getLifeCircleNumber(Integer communityId,
			Integer createTime) throws Exception {
		String sql = " SELECT count(*) as sum , max(create_time) as create_time FROM life_circle WHERE create_time > ? AND community_id = ?";
		List<LifeCircleNumer> list = this.getList(sql, new Object[] {
				createTime, communityId }, LifeCircleNumer.class);
		return list != null && list.size() > 0 ? list.get(0) : null;
	}

	@Override
	public LifeCircleNumer getLifeCircleNumber(String emobId) throws Exception {
		String sql = " SELECT count(*) as sum , max(create_time) as create_time FROM life_circle WHERE emob_id = ? AND status = 'ongoing'";
		List<LifeCircleNumer> list = this.getList(sql, new Object[] { emobId },
				LifeCircleNumer.class);
		return list != null && list.size() > 0 ? list.get(0) : null;
	}

	@Override
	public LifeCircleTop getLifeCircleTop(Integer communityId,
			Integer startTimeInt, Integer endTimeInt) throws Exception {
		String sql = "SELECT "
				+ "  t3.emob_id,"
				+ "  t3.create_time,"
				+ " SUM(case when  t.id IS  NOT NULL   then t3.life_circle_id else 0 end ) as test_dynamic_state,"
				+ " SUM(case when  t.id IS  NOT NULL   then t3.emob_num else 0 end ) as test_user_num,"
				+ " SUM(case when  t.id IS  NOT NULL   then t3.emob_group_id else 0 end ) as test_group_chat,"
				+ " SUM(case when  t.id IS  NOT NULL   then t3.group_emob else 0 end ) as test_group_user,"
				+ " SUM(case when  t.id IS  NOT NULL   then t1.life_praise_id else 0 end ) as test_praise,"
				+ " SUM(case when  t.id IS  NOT NULL   then t1.emob_id_from2 else 0 end ) as test_praise_user,"
				+ " SUM(case when  t.id IS  NOT NULL   then t2.life_circle_detail_id  else 0 end ) as test_comment,"
				+ " SUM(case when  t.id IS  NOT NULL   then t2.emob_id_from3 else 0 end ) as test_comment_user,"
				+ " SUM(case when  t.id IS   NULL   then t3.life_circle_id else 0 end ) as dynamic_state,"
				+ " SUM(case when  t.id IS   NULL   then t3.emob_num else 0 end ) as user_num,"
				+ " SUM(case when  t.id IS   NULL   then t3.emob_group_id else 0 end ) as group_chat,"
				+ " SUM(case when  t.id IS   NULL   then t3.group_emob else 0 end ) as group_user,"
				+ " SUM(case when  t.id IS   NULL   then t1.life_praise_id else 0 end ) as praise,"
				+ " SUM(case when  t.id IS   NULL   then t1.emob_id_from2 else 0 end ) as praise_user,"
				+ " SUM(case when  t.id IS   NULL   then t2.life_circle_detail_id else 0 end ) as comment,"
				+ " SUM(case when  t.id IS   NULL   then t2.emob_id_from3 else 0 end ) as comment_user"
				+ " FROM ( "
				+ " SELECT "
				+ " u.emob_id "
				+ " FROM "
				+ " users u "
				+ " WHERE "
				+ " u.community_id = ? AND u.role='owner' ) t4 LEFT JOIN "
				+ " ( SELECT  l.create_time,l.emob_id, "
				+ " COUNT(l.life_circle_id) AS life_circle_id, COUNT("
				+ " DISTINCT l.emob_id,  l.emob_id  ) AS emob_num,"
				+ " COUNT(  CASE "
				+ " WHEN l.emob_group_id IS NOT NULL THEN "
				+ " l.emob_group_id  ELSE  NULL  END "
				+ " ) AS emob_group_id,  COUNT(  DISTINCT CASE "
				+ " WHEN l.emob_group_id IS NOT NULL THEN  l.emob_id "
				+ " ELSE  NULL  END  ) AS group_emob "
				+ " FROM  life_circle l  WHERE "
				+ " l.community_id = ? "
				+ " AND l.create_time>? AND l.create_time<?  GROUP BY "
				+ " l.emob_id ) t3 ON t4.emob_id=t3.emob_id  LEFT JOIN (  SELECT "
				+ " lp.emob_id_from, "
				+ " COUNT(lp.life_praise_id) AS life_praise_id,"
				+ " COUNT(DISTINCT lp.emob_id_from) AS emob_id_from2 "
				+ " FROM  life_praise lp WHERE  lp.create_time>? AND lp.create_time<?  GROUP BY "
				+ " lp.emob_id_from "
				+ " ) t1 ON t4.emob_id = t1.emob_id_from  LEFT JOIN ( "
				+ " SELECT  lc.emob_id_from, "
				+ " COUNT(lc.life_circle_detail_id) AS life_circle_detail_id, "
				+ " COUNT(DISTINCT lc.emob_id_from) AS emob_id_from3 "
				+ " FROM  life_circle_detail lc WHERE  lc.create_time>? AND lc.create_time<? GROUP BY lc.emob_id_from "
				+ "   ) t2 ON t4.emob_id = t2.emob_id_from LEFT JOIN try_out t ON t4.emob_id=t.emob_id ";
		List<LifeCircleTop> list = this.getList(sql, new Object[] {
				communityId, communityId, startTimeInt, endTimeInt,
				startTimeInt, endTimeInt, startTimeInt, endTimeInt },
				LifeCircleTop.class);
		return list != null && list.size() > 0 ? list.get(0) : null;
	}

	@Override
	public List<LifeCircleTop> getLifeCircleList(Integer communityId,
			Integer startTimeInt, Integer endTimeInt, List<Integer> sqlTime)
			throws Exception {
		List<LifeCircleTop> listTop = new ArrayList<LifeCircleTop>();
		for (int i = 0; i < sqlTime.size(); i++) {
			String sql = "SELECT "
					+ "  t3.emob_id,"
					+ "  ? as create_time,"
					+ " SUM(case when  t.id IS  NOT NULL   then t3.life_circle_id else 0 end ) as test_dynamic_state,"
					+ " SUM(case when  t.id IS  NOT NULL   then t3.emob_num else 0 end ) as test_user_num,"
					+ " SUM(case when  t.id IS  NOT NULL   then t3.emob_group_id else 0 end ) as test_group_chat,"
					+ " SUM(case when  t.id IS  NOT NULL   then t3.group_emob else 0 end ) as test_group_user,"
					+ " SUM(case when  t.id IS  NOT NULL   then t1.life_praise_id else 0 end ) as test_praise,"
					+ " SUM(case when  t.id IS  NOT NULL   then t1.emob_id_from2 else 0 end ) as test_praise_user,"
					+ " SUM(case when  t.id IS  NOT NULL   then t2.life_circle_detail_id  else 0 end ) as test_comment,"
					+ " SUM(case when  t.id IS  NOT NULL   then t2.emob_id_from3 else 0 end ) as test_comment_user,"
					+ " SUM(case when  t.id IS   NULL   then t3.life_circle_id else 0 end ) as dynamic_state,"
					+ " SUM(case when  t.id IS   NULL   then t3.emob_num else 0 end ) as user_num,"
					+ " SUM(case when  t.id IS   NULL   then t3.emob_group_id else 0 end ) as group_chat,"
					+ " SUM(case when  t.id IS   NULL   then t3.group_emob else 0 end ) as group_user,"
					+ " SUM(case when  t.id IS   NULL   then t1.life_praise_id else 0 end ) as praise,"
					+ " SUM(case when  t.id IS   NULL   then t1.emob_id_from2 else 0 end ) as praise_user,"
					+ " SUM(case when  t.id IS   NULL   then t2.life_circle_detail_id else 0 end ) as comment,"
					+ " SUM(case when  t.id IS   NULL   then t2.emob_id_from3 else 0 end ) as comment_user"
					+ " FROM ( "
					+ " SELECT "
					+ " u.emob_id "
					+ " FROM "
					+ " users u "
					+ " WHERE "
					+ " u.community_id = ? AND u.role='owner' ) t4 LEFT JOIN "
					+ " ( SELECT  l.create_time,l.emob_id, "
					+ " COUNT(l.life_circle_id) AS life_circle_id, COUNT("
					+ " DISTINCT l.emob_id,  l.emob_id  ) AS emob_num,"
					+ " COUNT(  CASE "
					+ " WHEN l.emob_group_id IS NOT NULL THEN "
					+ " l.emob_group_id  ELSE  NULL  END "
					+ " ) AS emob_group_id,  COUNT(  DISTINCT CASE "
					+ " WHEN l.emob_group_id IS NOT NULL THEN  l.emob_id "
					+ " ELSE  NULL  END  ) AS group_emob "
					+ " FROM  life_circle l  WHERE "
					+ " l.community_id = ? "
					+ " AND  l.create_time>? AND l.create_time<? GROUP BY "
					+ " l.emob_id ) t3 ON t4.emob_id=t3.emob_id  LEFT JOIN (  SELECT "
					+ " lp.emob_id_from, "
					+ " COUNT(lp.life_praise_id) AS life_praise_id,"
					+ " COUNT(DISTINCT lp.emob_id_from) AS emob_id_from2 "
					+ " FROM  life_praise lp WHERE  lp.create_time>? AND lp.create_time<?   GROUP BY "
					+ " lp.emob_id_from "
					+ " ) t1 ON t4.emob_id = t1.emob_id_from  LEFT JOIN ( "
					+ " SELECT  lc.emob_id_from, "
					+ " COUNT(lc.life_circle_detail_id) AS life_circle_detail_id, "
					+ " COUNT(DISTINCT lc.emob_id_from) AS emob_id_from3 "
					+ " FROM  life_circle_detail lc WHERE lc.create_time>? AND lc.create_time<?  GROUP BY lc.emob_id_from "
					+ " ) t2 ON t4.emob_id = t2.emob_id_from LEFT JOIN try_out t ON t4.emob_id=t.emob_id ";
			List<LifeCircleTop> list = this.getList(sql,
					new Object[] { sqlTime.get(i), communityId, communityId,
							sqlTime.get(i), sqlTime.get(i) + (24 * 60 * 60),
							sqlTime.get(i), sqlTime.get(i) + (24 * 60 * 60),
							sqlTime.get(i), sqlTime.get(i) + (24 * 60 * 60) },
					LifeCircleTop.class);
			listTop.add(list.get(0));
		}

		return listTop;
	}

	public List<LifeCircleTop> getLifeCircleList2(Integer communityId,
			Integer startTimeInt, Integer endTimeInt) throws Exception {
		String sql = "SELECT  "
				+ "  t3.emob_id,"
				+ "  t3.create_time,"
				+ " SUM(case when  t.id IS  NOT NULL   then t3.life_circle_id else 0 end ) as test_dynamic_state,"
				+ " SUM(case when  t.id IS  NOT NULL   then t3.emob_num else 0 end ) as test_user_num,"
				+ " SUM(case when  t.id IS  NOT NULL   then t3.emob_group_id else 0 end ) as test_group_chat,"
				+ " SUM(case when  t.id IS  NOT NULL   then t3.group_emob else 0 end ) as test_group_user,"
				+ " SUM(case when  t.id IS  NOT NULL   then t1.life_praise_id else 0 end ) as test_praise,"
				+ " SUM(case when  t.id IS  NOT NULL   then t1.emob_id_from2 else 0 end ) as test_praise_user,"
				+ " SUM(case when  t.id IS  NOT NULL   then t2.life_circle_detail_id else 0 end ) as test_comment,"
				+ " SUM(case when  t.id IS  NOT NULL   then t2.emob_id_from3 else 0 end ) as test_comment_user,"
				+ " SUM(case when  t.id IS   NULL   then t3.life_circle_id else 0 end ) as dynamic_state,"
				+ " SUM(case when  t.id IS   NULL   then t3.emob_num else 0 end ) as user_num,"
				+ " SUM(case when  t.id IS   NULL   then t3.emob_group_id else 0 end ) as group_chat,"
				+ " SUM(case when  t.id IS   NULL   then t3.group_emob else 0 end ) as group_user,"
				+ " SUM(case when  t.id IS   NULL   then t1.life_praise_id else 0 end ) as praise,"
				+ " SUM(case when  t.id IS   NULL   then t1.emob_id_from2 else 0 end ) as praise_user,"
				+ " SUM(case when  t.id IS   NULL   then t2.life_circle_detail_id else 0 end ) as comment,"
				+ " SUM(case when  t.id IS   NULL   then t2.emob_id_from3 else 0 end ) as comment_user"
				+ " FROM (SELECT "
				+ " u.emob_id "
				+ " FROM "
				+ " users u "
				+ " WHERE "
				+ " u.community_id = ? AND u.role='owner'	) t4 LEFT JOIN ( "
				+ " SELECT  l.create_time,DAY(from_unixtime(l.create_time)) AS time, "
				+ " l.emob_id, "
				+ " COUNT(l.life_circle_id) AS life_circle_id,  COUNT( "
				+ " DISTINCT l.emob_id,  l.emob_id  ) AS emob_num, "
				+ " COUNT(  CASE  WHEN l.emob_group_id IS NOT NULL THEN "
				+ " l.emob_group_id  ELSE  NULL  END "
				+ " ) AS emob_group_id,  COUNT(  DISTINCT CASE "
				+ " WHEN l.emob_group_id IS NOT NULL THEN  l.emob_id "
				+ " ELSE  NULL  END  ) AS group_emob "
				+ " FROM  life_circle l  WHERE "
				+ " l.community_id = ?  AND l.create_time>? AND  l.create_time<? GROUP BY "
				+ " l.emob_id,DAY(from_unixtime(l.create_time))  ) t3  ON t4.emob_id=t3.emob_id "
				+ " LEFT JOIN (  SELECT  lp.emob_id_from, "
				+ " COUNT(lp.life_praise_id) AS life_praise_id, "
				+ " COUNT(DISTINCT lp.emob_id_from) AS emob_id_from2 "
				+ " FROM  life_praise lp  WHERE  lp.create_time>? AND lp.create_time<?  GROUP BY  lp.emob_id_from "
				+ " ) t1 ON t4.emob_id = t1.emob_id_from  LEFT JOIN ( "
				+ " SELECT  lc.emob_id_from, "
				+ " COUNT(lc.life_circle_detail_id) AS life_circle_detail_id, "
				+ " COUNT(DISTINCT lc.emob_id_from) AS emob_id_from3 "
				+ " FROM  life_circle_detail lc  WHERE  lc.create_time>? AND lc.create_time<? GROUP BY lc.emob_id_from "
				+ " ) t2 ON t4.emob_id = t2.emob_id_from "
				+ " LEFT JOIN try_out t ON t4.emob_id=t.emob_id "
				+ " GROUP BY t3.time ";
		List<LifeCircleTop> list = this.getList(sql, new Object[] {
				communityId, communityId, startTimeInt, endTimeInt,
				startTimeInt, endTimeInt, startTimeInt, endTimeInt },
				LifeCircleTop.class);
		return list;
	}

	@Override
	public List<LifeCircleSelect> selectLifeCircleList(Integer communityId, Integer startTimeInt, Integer endTimeInt, String type) throws Exception {
		StringBuilder builder = new StringBuilder( "SELECT u.emob_id,lc.life_circle_id,lc.create_time,lc.life_content,lc.praise_sum,lc.content_sum,u.avatar,u.nickname,u.user_unit,u.room,u.user_floor,lc.photoes FROM life_circle lc LEFT JOIN users u ON lc.emob_id =u.emob_id WHERE lc.community_id=? AND lc.create_time>? AND lc.create_time<? ");
		if (type.equals("time")) {
			builder.append(" ORDER BY lc.create_time DESC");
		} else if (type.equals("praise")) {
			builder.append(" ORDER BY lc.praise_sum DESC");
		} else if (type.equals("content")) {
			builder.append(" ORDER BY lc.content_sum DESC");
		}

		List<LifeCircleSelect> list = this.getList(builder.toString(), new Object[] { communityId, startTimeInt, endTimeInt }, LifeCircleSelect.class);

		for (LifeCircleSelect lifeCircleSelect : list) {
			String str = lifeCircleSelect.getPhotoes();
			if(StringUtils.isBlank(str)){
				continue;
			}
			String[] photoes = str.split(",");
			List<LifePhoto> LifePhotoes = new ArrayList<LifePhoto>(photoes.length);
			for (String string : photoes) {
				LifePhotoes.add(new LifePhoto(string));
			}
			lifeCircleSelect.setList(LifePhotoes);
		}

		return list;
	}

	@Override
	public List<Integer> getLifeCircleId(Integer communityId, String text,
			List<BlackList> blacklist) throws Exception {
		String sql = " SELECT l.community_id , l.type , l.emob_id , l.life_circle_id , l.life_content , l.praise_sum , l.create_time "
				+ " , l.emob_group_id , u.character_values , u.avatar , u.nickname , a.activity_title FROM life_circle l LEFT JOIN "
				+ " users u ON l.emob_id = u.emob_id LEFT JOIN activities a ON a.emob_group_id = l.emob_group_id WHERE l.community_id = ? AND l.life_content like '%"
				+ text + "%' ";
		List<LifeCircle> list = this.getList(sql, new Object[] { communityId },
				LifeCircle.class);
		List<Integer> result = new ArrayList<Integer>();
		StringBuffer sb = new StringBuffer("");
		if (blacklist != null && blacklist.size() > 0) {
			Iterator<BlackList> iterator = blacklist.iterator();
			BlackList blackList2 = null;
			while (iterator.hasNext()) {
				blackList2 = iterator.next();
				if ("".equals(sb.toString())) {
					sb.append(blackList2.getEmobIdTo());
				} else {
					sb.append(",").append(blackList2.getEmobIdTo());
				}
			}
		}

		if (list != null && list.size() > 0) {
			Iterator<LifeCircle> iterator = list.iterator();
			LifeCircle lifeCircle = null;
			while (iterator.hasNext()) {
				lifeCircle = iterator.next();
				if (lifeCircle != null) {
					if (sb.toString().indexOf(lifeCircle.getEmobId()) < 0) {
						result.add(lifeCircle.getLifeCircleId());
					}
				}

			}
		}
		sb.setLength(0);
		return result;
	}

	@Override
	public Page<LifeCircle> getLifeCircleByText(String text, Integer pageNum,
			Integer pageSize, Integer nvm) throws Exception {
		if ("".equals(text.trim())) {
			text = "0";
		}
		String sql = " SELECT l.community_id , l.emob_id , l.type , l.life_circle_id , l.life_content , l.praise_sum , l.create_time "
				+ " , l.emob_group_id , u.character_values , u.avatar , u.nickname , a.activity_title FROM life_circle l LEFT JOIN "
				+ " users u ON l.emob_id = u.emob_id LEFT JOIN activities a ON a.emob_group_id = l.emob_group_id WHERE l.life_circle_id in ( "
				+ text + ") order by l.create_time desc";
		return this.getData4Page(sql, new Object[] {}, pageNum, pageSize, nvm,
				LifeCircle.class);
	}

	@Override
	public LifeCircleSelect getLifeCireDelit(String life_circle_id) throws Exception {
		String sql = "SELECT u.emob_id,lc.life_circle_id,lc.create_time,lc.life_content,  lc.praise_sum,  lc.content_sum,u.avatar,u.nickname,u.user_unit,u.room,u.user_floor,lc.photoes  FROM life_circle lc left join users u on lc.emob_id =u.emob_id WHERE lc.life_circle_id=?  ";
		List<LifeCircleSelect> list = this.getList(sql, new Object[] { life_circle_id }, LifeCircleSelect.class);
		
		if(CollectionUtils.isEmpty(list)){
			return null;
		}
		LifeCircleSelect lifeCircle = list.get(0);
		
		String str = lifeCircle.getPhotoes();
		if(StringUtils.isNotBlank(str)){
			String[] split = str.split(",");
			List<LifePhoto>  photoes = new ArrayList<LifePhoto>(split.length);  
			for (String photo : split) {
				photoes.add(new LifePhoto(photo));
			}
			lifeCircle.setList(photoes);
		}
		
		return lifeCircle;
	}

	@Override
	public List<LifeCircleDetailList> getLifeCircleDetail(String emob_id_to)
			throws Exception {
		String sql = " SELECT u.nickname as nickname_from,l.emob_id_from as nickid_from,uu.nickname as nickname_to,l.detail_content ,l.life_circle_detail_id as lifecircledetail_id,praise_sum  FROM  life_circle_detail l   LEFT JOIN users u ON l.emob_id_from=u.emob_id LEFT JOIN users uu ON   l.emob_id_to = uu.emob_id  " + "WHERE l.life_circle_id=?";
		return this.getList(sql, new Object[] { emob_id_to }, LifeCircleDetailList.class);
	}

	@Override
	public ScanningTime selectLifeCirrleDetailId() throws Exception {
		List<ScanningTime> list = this.getList( " SELECT * FROM  scanning_time ", new Object[] {}, ScanningTime.class);
		if (list.size() > 0) {
			return list.get((list.size() - 1));
		}
		return null;
	}

	@Override
	public List<LifeSensitive> selectLifeCircleListTime(
			Integer lifeCirrleDetailId) throws Exception {
		String sql = " SELECT 0 as id,'new' as status,0 as create_time,0 as update_time,"
				+ " lc.life_circle_detail_id,lc.life_circle_id,l.community_id,lc.detail_content "
				+ " FROM life_circle_detail lc LEFT JOIN life_circle l ON l.life_circle_id = lc.life_circle_id "
				+ " WHERE lc.life_circle_detail_id > ?";

		List<LifeSensitive> list = this.getList(sql,
				new Object[] { lifeCirrleDetailId }, LifeSensitive.class);
		if (list.size() > 0) {
			ScanningTime st = new ScanningTime();
			st.setLifeCirrleDetailId(list.get((list.size() - 1))
					.getLifeCircleDetailId());
			st.setScanningTime((int) (System.currentTimeMillis() / 1000));

			this.save(st, new MyReturnKey());
		}

		return list;
	}

	@Override
	public Integer addLifeSensitive(LifeSensitive lifeSensitive)
			throws Exception {
		lifeSensitive.setCreateTime((int) (System.currentTimeMillis() / 1000));

		MyReturnKey key = new MyReturnKey();
		this.save(lifeSensitive, key);
		return key.getId();
	}

	@Override
	public Page<LifeSensitive> getSensitive(Integer communityId,
			Integer pageNum, Integer pageSize) throws Exception {
		String sql = " SELECT * FROM life_sensitive ls WHERE ls.community_id=? ORDER BY ls.id DESC ";
		return this.getData4Page(sql, new Object[] { communityId }, pageNum,
				pageSize, 10, LifeSensitive.class);
	}

	@Override
	public boolean pingbi(Integer lifeCircleId) throws Exception {
		String sql = "UPDATE life_circle SET status = 'block'  WHERE life_circle_id = ?  ";
		List<Object> list = new ArrayList<Object>();
		list.add(lifeCircleId);
		int result = this.updateData(sql, list, null);
		return result > 0;
	}

	@Override
	public boolean upStatus(Integer id, String status) throws Exception {
		List<Object> list = new ArrayList<Object>(2);
		list.add(status);
		list.add(id);

		return this
				.updateData(
						"UPDATE life_sensitive SET status = ? WHERE id = ?",
						list, null) > 0;
	}

	@Override
	public Integer addLifePhoto(LifePhoto lifePhoto) throws Exception {
		lifePhoto.setCreateTime((int) (System.currentTimeMillis() / 1000));

		MyReturnKey key = new MyReturnKey();
		this.save(lifePhoto, key);
		return key.getId();
	}

	@Override
	public Page<LifeCircleGetAll> getLifeCircleList(Integer communityId,
			Integer pageNum, Integer pageSize) throws Exception {
		String sql = "SELECT lc.life_circle_id,lc.create_time,lc.life_content,"
				+ " lc.praise_sum,"
				+ " lc.content_sum,u.avatar,u.nickname,u.user_unit,u.room,u.user_floor"
				+ " FROM life_circle lc left join users u on lc.emob_id =u.emob_id WHERE lc.community_id=? "
				+ " order by lc.create_time desc";

		return this.getData4Page(sql, new Object[] { communityId }, pageNum,
				pageSize, 10, LifeCircleGetAll.class);
	}

	@Override
	public Integer addFavorite(Favorite favorite) throws Exception {
		MyReturnKey key = new MyReturnKey();
		this.save(favorite, key);
		return key.getId();
	}

	@Override
	public Integer delFavorite(Integer favorite_id) throws Exception {
		List<Favorite> list = this.getList(
				"SELECT * FROM favorite WHERE favorite_id=?",
				new Object[] { favorite_id }, Favorite.class);
		if (list.size() != 0) {
			return this.delete(list.get(0));
		}
		return 0;
	}

	@Override
	public Page<LifeCircleGet> getFavoriteLifeCire(Integer pageNum,
			Integer pageSize) throws Exception {
		String sql = "SELECT "
				+ " f.favorite_id,"
				+ " lc.life_circle_id,"
				+ " lc.create_time,"
				+ " lc.life_content,"
				+ " lc.praise_sum,"
				+ " lc.content_sum,"
				+ " u.avatar,"
				+ " u.nickname,"
				+ " u.user_unit,"
				+ " u.room,"
				+ " u.user_floor"
				+ " FROM "
				+ " life_circle lc "
				+ " LEFT JOIN users u ON lc.emob_id = u.emob_id "
				+ " LEFT JOIN favorite f ON lc.life_circle_id = f.life_circle_id "
				+ " WHERE " + " f.favorite_id is not null" + " ORDER BY "
				+ " lc.create_time DESC";

		return this.getData4Page(sql, new Object[] {}, pageNum, pageSize, 10,
				LifeCircleGet.class);
	}

	@Override
	public Integer delectLifePhoto(Integer life_photo_id) throws Exception {
		List<LifePhoto> list = this.getList(
				"SELECT * FROM life_photo WHERE life_photo_id=?",
				new Object[] { life_photo_id }, LifePhoto.class);
		if (list.size() != 0) {
			return this.delete(list.get(0));
		}
		return 0;
	}

	@Override
	public List<CommunityId> getExisting(Integer lifeCircleId) throws Exception {
		String sql = "SELECT DISTINCT community_id FROM life_circle "
				+ "WHERE life_content="
				+ "(SELECT lc.life_content FROM life_circle lc WHERE lc.life_circle_id = ?)";
		return this.getList(sql, new Object[] { lifeCircleId },
				CommunityId.class);
	}

	@Override
	public List<IdCount> getLifeCircleUpdateCount(Integer communityId,
			Integer lastQuitTime) throws Exception {
		String sql = "SELECT lc.life_circle_id AS id,count(1) AS count"
				+ " FROM life_circle_detail lcd LEFT JOIN life_circle lc ON lcd.life_circle_id=lc.life_circle_id"
				+ " WHERE lcd.emob_id_from NOT IN (SELECT emob_id FROM try_out)"
				+ " AND lc.life_circle_id IS NOT NULL"
				+ " AND lc.community_id=?"
				+ " AND lcd.update_time>?"
				+ " GROUP BY lc.life_circle_id";

		return this.getList(sql, new Object[] { communityId,lastQuitTime },
				IdCount.class);
	}

}