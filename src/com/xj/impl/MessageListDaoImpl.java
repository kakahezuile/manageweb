package com.xj.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.xj.bean.MessageList;
import com.xj.dao.MessageListDao;
import com.xj.httpclient.vo.MyReturnKey;

@Repository("messageListDao")
public class MessageListDaoImpl extends MyBaseDaoImpl implements MessageListDao{

	@Override
	public Integer addMessageList(MessageList messageList) throws Exception {
		// TODO Auto-generated method stub
		MyReturnKey key = new MyReturnKey();
		this.save(messageList, key);
		return key.getId();
	}

	@Override
	public List<MessageList> getMessageList(String emobIdTo) throws Exception {
		// TODO Auto-generated method stub
//		String sql = "SELECT m.emob_id_from , m.emob_id_to , u.nickname , m.create_time , m.message_list_id , u.avatar FROM message_list m left join users u on m.emob_id_from = u.emob_id where m.emob_id_to = ? group by m.emob_id_from , m.emob_id_to order by m.create_time desc " ;
		String sql = "SELECT m.emob_id_from , m.emob_id_to ,(case when u.role = 'owner' then u.nickname else s.shop_name end ) as nickname , m.create_time , m.message_list_id , " +
					 
					 " (case when u.role = 'owner' then u.avatar else s.logo end ) as avatar FROM message_list m left join users u on m.emob_id_from = u.emob_id " +

					 " LEFT JOIN shops s on m.emob_id_from = s.emob_id "+

					 " where m.emob_id_to = ? group by m.emob_id_from , m.emob_id_to order by m.create_time desc ";
		List<MessageList> list = this.getList(sql, new Object[]{emobIdTo}, MessageList.class);
		return list;
	}

}
