package com.xj.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xj.bean.CommunityHome;
import com.xj.bean.CommunityHome.FloorInRoom;
import com.xj.bean.FloorAndUnit;
import com.xj.bean.HomeOwner;
import com.xj.dao.HomeOwnerDao;

@Service("homeOwnerService")
public class HomeOwnerService {
	
	@Autowired
	private HomeOwnerDao homeOwnerDao;
	
	public List<CommunityHome> getHomeOwner(Integer communityId) throws Exception{
		List<CommunityHome> list = new ArrayList<CommunityHome>();
		List<FloorAndUnit> floorUnits = homeOwnerDao.getCommunityHome(communityId);
		List<HomeOwner> homeOwners = homeOwnerDao.getHomeOwners(communityId);
		int len = floorUnits.size();
		HashSet<String> set = new HashSet<String>();
		for(int i = 0 ; i < len ; i++){
			set.add(floorUnits.get(i).getUserFloor());
		}
		CommunityHome communityHome = null;
		Iterator<String> iterator = set.iterator();
		while(iterator.hasNext()){
			String userFloor = iterator.next();
			communityHome = new CommunityHome();
			communityHome.setUserFloor(userFloor);
			list.add(communityHome);
		}
	
		len = list.size();
		for(int i = 0 ; i < len ; i++){
			communityHome = list.get(i);
			int length = floorUnits.size();
			List<FloorInRoom> floorInRooms = new ArrayList<CommunityHome.FloorInRoom>();
			FloorInRoom floorInRoom = null;
			for(int j = 0 ; j < length ; j++){
				if(communityHome.getUserFloor().equals(floorUnits.get(j).getUserFloor())){
					floorInRoom = communityHome.new FloorInRoom();
					floorInRoom.setUserUnit(floorUnits.get(j).getUserUnit());
					floorInRoom.setUserRooms(new ArrayList<String>());
					floorInRooms.add(floorInRoom);
				}
			}
			if(floorInRooms != null && floorInRooms.size() > 0){
				list.get(i).setList(floorInRooms);
			}
			
		}
		
		int length = homeOwners.size();
		for(int i = 0 ; i < len ; i++){
			communityHome = list.get(i);
			List<FloorInRoom> floorInRooms = communityHome.getList();
			for(int j = 0 ; j < length ; j++){
				HomeOwner homeOwner = homeOwners.get(j);
				if(homeOwner.getUserFloor().equals(communityHome.getUserFloor())){
					int temp = floorInRooms.size();
					for(int z = 0 ; z < temp ; z++){
						if(homeOwner.getUserUnit().equals(floorInRooms.get(z).getUserUnit())){
							floorInRooms.get(z).getUserRooms().add(homeOwner.getUserRoom());
						}
					}
				}
			}
		}
		return list;
	}
	
	public List<CommunityHome> getHomeOwners(Integer communityId) throws Exception{
		List<CommunityHome> list = new ArrayList<CommunityHome>();
		List<HomeOwner> homeOwners = homeOwnerDao.getHomeOwners(communityId);
		Iterator<HomeOwner> iterator = homeOwners.iterator();
		HashSet<String> set = new HashSet<String>();
		HomeOwner homeOwner = null;
		while(iterator.hasNext()){ // 不重复楼号
			homeOwner = iterator.next();
			set.add(homeOwner.getUserFloor());
		}
		Iterator<String> it = set.iterator();
		String key = "";
		CommunityHome communityHome = null;
		while(it.hasNext()){
			key = it.next();
			iterator = homeOwners.iterator();
			communityHome = new CommunityHome();
			communityHome.setUserFloor(key);
			List<FloorInRoom> floorInRooms = new ArrayList<CommunityHome.FloorInRoom>();
			while(iterator.hasNext()){
				FloorInRoom floorInRoom = null;
				homeOwner = iterator.next();
				if(key.equals(homeOwner.getUserFloor())){
					floorInRoom = communityHome.new FloorInRoom();
					floorInRoom.setUserUnit(homeOwner.getUserUnit());
					floorInRooms.add(floorInRoom);
				}
			}
			communityHome.setList(floorInRooms);
			list.add(communityHome);
		}
		return list;
	}
	
	public void importHomeOwners(List<HomeOwner> homeOwners) throws Exception {
		homeOwnerDao.importHomeOwners(homeOwners);
	}

	/**
	 * 根据小区id获取楼号信息
	 * @param communityId
	 * @return
	 */
	public List<String> selectFloor(Integer communityId) {
		return homeOwnerDao.getFloorsByCommunityId(communityId);
	}

	/**
	 * 根据小区和楼号，获取单元号信息
	 * @param communityId
	 * @param floor
	 * @return
	 */
	public List<String> selectUnit(Integer communityId, String floor) {
		return homeOwnerDao.selectUnitByCommunityIdAndFloor(communityId,floor);
	}

	/**
	 * 获取用户房间号
	 * @param communityId
	 * @param floor
	 * @param unit
	 * @return
	 */
	public List<String> selectRoom(Integer communityId, String floor,
			String unit) {
		return homeOwnerDao.selectRoom(communityId,floor,unit);
	}

}