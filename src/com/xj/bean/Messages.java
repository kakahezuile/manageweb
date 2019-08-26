package com.xj.bean;

import com.xj.annotation.MyAnnotation;
import com.xj.annotation.NotInsertAnnotation;
import com.xj.annotation.NotUpdataAnnotation;

public class Messages {

	@NotInsertAnnotation
	private Long messageId;
	
	private String messageFrom;
	
	@NotInsertAnnotation
	@NotUpdataAnnotation
	@MyAnnotation
	private String fromName;
	
	@NotInsertAnnotation
	@NotUpdataAnnotation
	@MyAnnotation
	private String toName;
	
	private String type; // chatmessage - 单聊    chatgroup - 群聊
	
	private String messageTo;
	
	private Long timestamp; //消息发送时间
	
	private String uuid;
	
	private String direction;
	
	private Long created;
	
	private Long modified;
	
	private String msgId; //消息id
	
	private String chatType; //用来判断单聊还是群聊。chat:单聊，groupchat:群聊
	
	private String msg; //消息内容
	
	private String msgType; //消息类型。txt:文本消息, img:图片, loc：位置, audio：语音
	
	private Integer videoLength; //语音时长，单位为秒，这个属性只有语音消息有
	
	private String url; //图片语音等文件的网络url，图片和语音消息有这个属性
	
	private String filename; //文件名字，图片和语音消息有这个属性
	
	private String secret; //获取文件的secret，图片和语音消息有这个属性
	
	private String lat; //发送的位置的纬度，只有位置消息有这个属性
	
	private String lng; //位置经度，只有位置消息有这个属性
	
	private String addr; //位置消息详细地址，只有位置消息有这个属性
	
	private Integer fileLength;
	
	private Integer width;
	
	private Integer height;

	private String serial;
	
	 private String title;  //通知标题
	    
	    private String content; //通知内容
	//    @NotInsertAnnotation
	    private String action = "100"; //electricity,电费,gas,燃气费,water,水费
	//    @NotInsertAnnotation
		private String CMD_CODE = "300";
		
		private String nickname; // 昵称
		
		private String avatar; // 头像
		
		
		private String CMD_DETAIL;

	public Integer getFileLength() {
		return fileLength;
	}

	public void setFileLength(Integer fileLength) {
		this.fileLength = fileLength;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getCMD_CODE() {
		return CMD_CODE;
	}

	public void setCMD_CODE(String cMD_CODE) {
		CMD_CODE = cMD_CODE;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}



	/**
	 * Mongodb会自动生成ObjectId
	 * @author fhp
	 *
	 */
	public class Oid{
		String $oid;
		public String get$oid() {
			return $oid;
		}
 
		public void set$oid(String $oid) {
			this.$oid = $oid;
		}
		
	}
	

	
	public String getFromName() {
		return fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	public String getToName() {
		return toName;
	}

	public void setToName(String toName) {
		this.toName = toName;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public Long getCreated() {
		return created;
	}

	public void setCreated(Long created) {
		this.created = created;
	}

	public Long getModified() {
		return modified;
	}

	public void setModified(Long modified) {
		this.modified = modified;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public Long getMessageId() {
		return messageId;
	}

	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public String getChatType() {
		return chatType;
	}

	public void setChatType(String chatType) {
		this.chatType = chatType;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	
	
	public String getMessageFrom() {
		return messageFrom;
	}

	public void setMessageFrom(String messageFrom) {
		this.messageFrom = messageFrom;
	}

	public String getMessageTo() {
		return messageTo;
	}

	public void setMessageTo(String messageTo) {
		this.messageTo = messageTo;
	}

	

	public Integer getVideoLength() {
		return videoLength;
	}

	public void setVideoLength(Integer videoLength) {
		this.videoLength = videoLength;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getCMD_DETAIL() {
		return CMD_DETAIL;
	}

	public void setCMD_DETAIL(String cMD_DETAIL) {
		
		CMD_DETAIL = cMD_DETAIL;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}
}
