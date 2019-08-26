package com.xj.service;


import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.xj.httpclient.apidemo.EasemobChatGroups;

@Service("emobGroupService")
public class EmobGroupService {
	
	private final Integer MAX_COUNT = 500;
	
	public String getEmobGroup(String emobGroupId){
		String result = "no";
		ObjectNode objectNode = EasemobChatGroups.getGroupList(emobGroupId);
		Integer temp = null;
		if(objectNode.get("statusCode") != null){
			temp = objectNode.get("statusCode").asInt();
		}
		if(temp != null && temp == 200){
			ArrayNode arrayNode = (ArrayNode)objectNode.get("data");
			JsonNode jsonNode = arrayNode.get(0);
			if(jsonNode.get("affiliations_count") != null){
				Integer count = jsonNode.get("affiliations_count").intValue();
				if(count >= MAX_COUNT){
					result = "no";
				}else{
					result = "yes";
				}
			}
		}else{
			result = "error";
		}
		
		return result;
	}
	
	public static void main(String[] args) {
		new EmobGroupService().getEmobGroup("1429700422596075");
	}
	
}
