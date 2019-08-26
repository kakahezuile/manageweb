package com.xj.timer;

import it.sauronsoftware.jave.AudioAttributes;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncodingAttributes;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.xj.bean.Messages;
import com.xj.httpclient.apidemo.EasemobChatMessage;
import com.xj.httpclient.apidemo.EasemobFiles;
import com.xj.httpclient.utils.QiniuFileSystemUtil;
import com.xj.mongo.MessageMongo;
import com.xj.service.SensitiveWordsHistoryService;

/**
 * 消息转存
 */
public class MessageTransfer {
	
	private static Logger logger = Logger.getLogger(MessageTransfer.class);
	
	@Autowired
	private MessageMongo messageMongo;
	
	@Autowired
	private SensitiveWordsHistoryService sensitiveWordsHistoryService;
	
	public void execute() {
		logger.info("消息记录转存任务开始---------" + System.currentTimeMillis());
		try {
			Messages messages = messageMongo.getMessageInMaxTime();
			Long startTime = 0l;
			if (messages != null) {
				startTime = messages.getTimestamp();
			}
			
			save(startTime, System.currentTimeMillis(), messageMongo, sensitiveWordsHistoryService);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("消息记录转存任务结束");
	}
	
	public void save(long timestampMin, long timestampMax, MessageMongo messageMongo, SensitiveWordsHistoryService sensitiveWordsHistoryService) {
		long temp = 0l;
		String fileSeparator = System.getProperty("file.separator");
		while (timestampMax > temp) {
			try {
				Thread.sleep(300);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			ObjectNode resonpseNode = EasemobChatMessage.getChatMessages(timestampMin);
			JsonNode countNode = resonpseNode.get("count");
			if (countNode != null && countNode.asInt() < 1) {
				break;
			}
			
			List<Messages> messages = getUserMessageBean(resonpseNode);
			if (messages.size() > 0) {
				timestampMin += 300;
			}
			
			Iterator<Messages> it = messages.iterator();
			while (it.hasNext()) {
				try {
					Messages ms = it.next();
					temp = ms.getTimestamp();
					timestampMin = temp;
					if (temp > timestampMax) {
						return;
					}
					
					if (StringUtils.isNotBlank(ms.getUrl()) && ("img".equals(ms.getMsgType()) || "audio".equals(ms.getMsgType()))) {
						String directory = fileSeparator + "qiniuTemp";
						File f = new File(directory);
						if (!f.exists()) {
							f.mkdir();
						}
						
						String filename = ms.getFilename();
						final String oldPath = directory + fileSeparator + filename;
						final String newFileName = UUID.randomUUID().toString().replaceAll("-", "");
						
						EasemobFiles.mediaDownload(ms.getUrl().split("/xiaojiantech/bangbang/chatfiles/")[1], ms.getSecret(), new File(oldPath), false);
						
						if ("audio".equals(ms.getMsgType())) {
							final String newPath = directory + fileSeparator + newFileName;
							
							changeToMp3(oldPath, newPath);//changeToMp3中调用了文件转码,这个过程是异步的,导致MP3文件并未立即生成
							
							ms.setFilename(newFileName);
							ms.setUrl(QiniuFileSystemUtil.myProject + newFileName);
							
							new Thread(new Runnable() {
								@Override
								public void run() {
									try {
										File file = getFile();
										QiniuFileSystemUtil.upload(file, newFileName);
										file.delete();
										
										new File(oldPath).delete();
									} catch (Exception e) {
										e.printStackTrace();
									}
								}
								
								private File getFile() {
									File file = new File(newPath);
									if (file.exists()) {
										return file;
									}
									
									try {
										Thread.sleep(1000L);
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
									
									return getFile();
								}
							}).start();
						} else {
							File file = new File(oldPath);
							QiniuFileSystemUtil.upload(file, newFileName);
							file.delete();
							
							ms.setUrl(QiniuFileSystemUtil.myProject + newFileName);
						}
					}
					
					try {
						sensitiveWordsHistoryService.addSensitiveWordsHistory(ms);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					messageMongo.insertMessage(ms);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private static void changeToMp3(String sourcePath, String targetPath) {
		AudioAttributes audio = new AudioAttributes();
		audio.setCodec("libmp3lame");
		
		EncodingAttributes attrs = new EncodingAttributes();
		attrs.setFormat("mp3");
		attrs.setAudioAttributes(audio);
		
		try {
			new Encoder().encode(new File(sourcePath), new File(targetPath), attrs);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 从聊天记录对象中获取数据
	private static List<Messages> getUserMessageBean(ObjectNode resultObjectNode) {
		List<Messages> list = new ArrayList<Messages>();
		if (null == resultObjectNode) {
			return list;
		}
		
		String statusCode = null;
		JsonNode array = resultObjectNode.get("entities");
		if (resultObjectNode.get("statusCode") != null) {
			statusCode = resultObjectNode.get("statusCode").asText();
		}
		if (!"200".equals(statusCode) || resultObjectNode.get("count") == null || resultObjectNode.get("count").asInt() <= 0 || array == null) {
			return list;
		}
		
		Iterator<JsonNode> it = array.iterator();
		while (it.hasNext()) {
			try {
				Messages bean = new Messages();
				list.add(bean);
				
				JsonNode jsonNode = it.next();
				bean.setUuid((String) getNodeString(jsonNode, "uuid", ""));
				bean.setChatType((String) getNodeString(jsonNode, "chat_type", ""));
				bean.setType((String) getNodeString(jsonNode, "type", ""));
				bean.setMessageFrom((String) getNodeString(jsonNode, "from", ""));
				bean.setMessageTo((String) getNodeString(jsonNode, "to", ""));
				bean.setMsgId((String) getNodeString(jsonNode, "msg_id", ""));
				bean.setTimestamp((Long) getNodeString(jsonNode, "timestamp", 0l));
				bean.setCreated((Long) getNodeString(jsonNode, "created", 0l));
				bean.setModified((Long) getNodeString(jsonNode, "modified", 0l));
				bean.setDirection((String) getNodeString(jsonNode, "direction", ""));
				
				
				JsonNode payloadNode = jsonNode.get("payload");
				if (null == payloadNode || null == payloadNode.get("bodies")) {
					continue;
				}
				
				Iterator<JsonNode> it2 = payloadNode.get("bodies").iterator();
				if (!payloadNode.get("bodies").iterator().hasNext()) {
					continue;
				}
				
				JsonNode payLoadBean = it2.next();
				if (payLoadBean.get("type") != null && payLoadBean.get("type").asText().equals("img")) {
					JsonNode jsonNode3 = payLoadBean.get("size");
					if (jsonNode3.get("width") != null && jsonNode3.get("height") != null) {
						bean.setWidth(jsonNode3.get("width").intValue());
						bean.setHeight(jsonNode3.get("height").intValue());
					}
				}
				if (payloadNode.get("ext") != null) {
					JsonNode jsonNode3 = payloadNode.get("ext");
					if (jsonNode3.get("nickname") != null) {
						bean.setNickname(jsonNode3.get("nickname").asText());
					}
					if (jsonNode3.get("avatar") != null) {
						bean.setAvatar(jsonNode3.get("avatar").asText());
					}
					if (jsonNode3.get("CMD_CODE") != null) {
						bean.setCMD_CODE(jsonNode3.get("CMD_CODE").asText());
						if ("10086".equals(bean.getCMD_CODE())
							|| "100".equals(bean.getCMD_CODE())
							|| "101".equals(bean.getCMD_CODE())
							|| "102".equals(bean.getCMD_CODE())
							|| "103".equals(bean.getCMD_CODE())
							|| "104".equals(bean.getCMD_CODE())
							|| "106".equals(bean.getCMD_CODE())
							|| "105".equals(bean.getCMD_CODE())
							|| "110".equals(bean.getCMD_CODE())
							|| "111".equals(bean.getCMD_CODE())) {
							continue;
						}
					}
					
					try {
						if (jsonNode3.get("CMD_CODE").asText() != null) {
							bean.setCMD_CODE(jsonNode3.get("CMD_CODE").asText());
						}
					} catch (Exception e) {
					}
					if (jsonNode3.get("CMD_DETAIL") != null) {
						bean.setCMD_DETAIL(jsonNode3.get("CMD_DETAIL").toString());
					}
					if (jsonNode3.get("serial") != null) {
						bean.setSerial(jsonNode3.get("serial").asText());
					}
					if (jsonNode3.get("action") != null) {
						bean.setAction(jsonNode3.get("action").asText());
					}
					if (jsonNode3.get("content") != null) {
						bean.setContent(jsonNode3.get("content").asText());
					}
					if (jsonNode3.get("title") != null) {
						bean.setTitle(jsonNode3.get("title").asText());
					}
				}
				bean.setAddr((String) getNodeString(payLoadBean, "addr", ""));
				bean.setFilename((String) getNodeString(payLoadBean, "filename", ""));
				bean.setLat((String) getNodeString(payLoadBean, "lat", ""));
				bean.setVideoLength((Integer) getNodeString(payLoadBean, "length", 0));
				bean.setLng((String) getNodeString(payLoadBean, "lng", ""));
				bean.setMsg((String) getNodeString(payLoadBean, "msg", ""));
				bean.setSecret((String) getNodeString(payLoadBean, "secret", ""));
				bean.setMsgType((String) getNodeString(payLoadBean, "type", ""));
				bean.setUrl((String) getNodeString(payLoadBean, "url", ""));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return list;
	}
	
	private static Object getNodeString(JsonNode objectNode, String key, Object object) {
		if (object instanceof String) {
			return objectNode.get(key) != null ? objectNode.get(key).asText() : "";
		} else if (object instanceof Long) {
			return objectNode.get(key) != null ? objectNode.get(key).asLong() : 0l;
		} else if (object instanceof Integer) {
			return objectNode.get(key) != null ? objectNode.get(key).asInt() : 0;
		} else {
			return null;
		}
	}
}