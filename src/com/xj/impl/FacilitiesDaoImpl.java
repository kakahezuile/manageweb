package com.xj.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.xj.bean.Facilities;
import com.xj.bean.FacilitiesClass;
import com.xj.bean.FacilitiesList;
import com.xj.bean.Page;
import com.xj.dao.FacilitiesDao;
import com.xj.httpclient.utils.DaoUtils;
import com.xj.httpclient.vo.MyReturnKey;

@Repository("facilitiesDao")
public class FacilitiesDaoImpl extends MyBaseDaoImpl implements FacilitiesDao {
    //logger
    Logger logger = Logger.getLogger(FacilitiesDaoImpl.class);
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Facilities> findAllById(String communityId) {
        List<Facilities> facilitiesBeanList = new ArrayList<Facilities>();
        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            con = jdbcTemplate.getDataSource().getConnection();
            
            preparedStatement = con.prepareStatement("select * from facilities where community_id =?");
            preparedStatement.setString(1, communityId);
            
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Facilities facilitiesBean = new Facilities();
                facilitiesBean.setFacilityId(resultSet.getLong("facility_id"));
                facilitiesBean.setFacilityName(resultSet.getString("facility_name"));
                facilitiesBean.setPhone(resultSet.getString("phone"));
                facilitiesBean.setStatus(resultSet.getString("status"));
                facilitiesBean.setCreateTime(resultSet.getInt("create_time"));
                facilitiesBean.setFacilitiesClassId(resultSet.getInt("facilities_class_id"));
                facilitiesBean.setAddress(resultSet.getString("address"));
                facilitiesBean.setLongitude(resultSet.getFloat("longitude"));
                facilitiesBean.setLatitude(resultSet.getFloat("latitude"));
                facilitiesBean.setFacilitiesDesc(resultSet.getString("facilities_desc"));
                facilitiesBean.setLogo(resultSet.getString("logo"));
                facilitiesBeanList.add(facilitiesBean);
            }
        } catch (Exception e) {
            logger.error(this, e);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                }  catch (Exception e) {
                    logger.error(this, e);
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                }  catch (Exception e) {
                    logger.error(this, e);
                }
            }
            if (con != null) {
                try {
                    con.close();
                }  catch (Exception e) {
                    logger.error(this, e);
                }
            }
        }
        return facilitiesBeanList;
    }

 
    public List<Facilities> findAllBySort(String communityId, String sort) {
        List<Facilities> facilitiesBeanList = new ArrayList<Facilities>();
        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            con = jdbcTemplate.getDataSource().getConnection();
            
            preparedStatement = con.prepareStatement("select * from facilities where community_id =? and sort = ?");
            preparedStatement.setString(1, communityId);
            preparedStatement.setString(2, sort);
            
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Facilities facilitiesBean = new Facilities();
                facilitiesBean.setFacilityId(resultSet.getLong("facility_id"));
                facilitiesBean.setFacilityName(resultSet.getString("facility_name"));
                facilitiesBean.setPhone(resultSet.getString("phone"));
                facilitiesBean.setStatus(resultSet.getString("status"));
                facilitiesBean.setCreateTime(resultSet.getInt("create_time"));
                facilitiesBean.setFacilitiesClassId(resultSet.getInt("facilities_class_id"));
                facilitiesBean.setAddress(resultSet.getString("address"));
                facilitiesBean.setLongitude(resultSet.getFloat("longitude"));
                facilitiesBean.setLatitude(resultSet.getFloat("latitude"));
                facilitiesBean.setFacilitiesDesc(resultSet.getString("facilities_desc"));
                facilitiesBean.setLogo(resultSet.getString("logo"));
                facilitiesBeanList.add(facilitiesBean);
            }
        } catch (Exception e) {
            logger.error(this, e);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                }  catch (Exception e) {
                    logger.error(this, e);
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                }  catch (Exception e) {
                    logger.error(this, e);
                }
            }
            if (con != null) {
                try {
                    con.close();
                }  catch (Exception e) {
                    logger.error(this, e);
                }
            }
        }
        return facilitiesBeanList;
    }


	@Override
	public Integer addFacilities(Facilities facilities) throws Exception {
		MyReturnKey key = new MyReturnKey();
		this.save(facilities, key);
		return key.getId();
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean updateFacilities(Facilities facilities) throws Exception {
		String sql = " UPDATE facilities SET ";
		List<Object> list = new ArrayList<Object>();
		Object resultObject[] = DaoUtils.reflect(facilities);
		if(resultObject != null && resultObject[1] != null && ((List<Object>)resultObject[1]).size() > 0){
			list = (List<Object>)resultObject[1];
			sql += (String) resultObject[0];
		}else{
			return false;
		}
		
		sql += " WHERE facility_id = ? ";
		System.out.println(sql);
		list.add(facilities.getFacilityId());
		int result = this.updateData(sql, list, null);
		return result > 0;
	}


	@Override
	public Page<Facilities> findFacilitiesByTag(Integer communityId, String sort , Integer type , Integer pageNum , Integer pageSize , Integer nvm) throws Exception {
		String sql = "SELECT * FROM facilities ";
		Page<Facilities> page = null;
		if(type == 1){
			sql += " WHERE community_id = ? order by facility_id desc ";
			page = this.getData4Page(sql, new Integer[]{communityId}, pageNum, pageSize, nvm, Facilities.class);
		}else if(type == 2){
			sql += " WHERE community_id = ? and facilities_class_id = ?  order by facility_id desc   ";
			page = this.getData4Page(sql, new Object[]{communityId,sort}, pageNum, pageSize, nvm, Facilities.class);
		}
		return page;
	}

	@Override
	public Page<Facilities> findeFacilitiesByClassId(Integer communityId ,Integer facilitiesClassId , Integer pageNum , Integer pageSize , Integer nvm) throws Exception {
		String sql = " SELECT * FROM facilities WHERE community_id = ? and facilities_class_id =  ? ";
		return this.getData4Page(sql, new Integer[]{communityId , facilitiesClassId}, pageNum, pageSize, nvm, Facilities.class);
	}

	/**
	 * 按名字查找
	 * @throws Exception 
	 */
	@Override
	public Page<FacilitiesList> selectFacilitiesName(Integer communityId, Integer type, Integer pageNum, Integer pageSize, Integer nvm, String facilitiesName) throws Exception {
		if(facilitiesName!=null){
			facilitiesName="%"+facilitiesName+"%";
		}else{
			facilitiesName="%%";
		}
		String sql = " SELECT  f.facility_id,f.facility_name,f.facilities_desc,f.address,f.longitude,f.latitude,f.phone," +
				" fc.facilities_class_name AS type_name , f.admin_id , f.distance ," +
				"f.logo,f.status,f.facilities_class_id,f.create_time,f.community_id,f.business_start_time,f.business_end_time" +
				" FROM facilities f LEFT JOIN " +
				"facilities_class fc ON f.facilities_class_id=fc.facilities_class_id WHERE f.community_id = ? AND (f.facility_name like  ? OR fc.facilities_class_name like ? ) ";
		
		return this.getData4Page(sql, new Object[]{communityId , facilitiesName , facilitiesName}, pageNum, pageSize, nvm, FacilitiesList.class);
	}
	/**
	 * 按类别查找
	 * @throws Exception 
	 */
	@Override
	public Page<FacilitiesList> selectFacilitiesType(Integer communityId, String type, Integer pageNum, Integer pageSize, Integer nvm, String facilitiesName) throws Exception {
		if(facilitiesName!=null){
			facilitiesName="%"+facilitiesName+"%";
		}else{
			facilitiesName="%%";
		}
		String sql = " SELECT  f.facility_id,f.facility_name,f.facilities_desc,f.address,f.longitude,f.latitude,f.phone," +
		" fc.facilities_class_name AS type_name , f.admin_id , f.distance ," +
		"f.logo,f.status,f.facilities_class_id,f.create_time,f.community_id,f.business_start_time,f.business_end_time" +
		" FROM facilities f LEFT JOIN " +
		"facilities_class fc ON f.facilities_class_id=fc.facilities_class_id WHERE fc.facilities_class_name=? AND f.community_id = ? AND f.facility_name like  ? ";
		
		return this.getData4Page(sql, new Object[]{type,communityId , facilitiesName}, pageNum, pageSize, nvm, FacilitiesList.class);
	}
	
	/**
	 * 统计周边商家数量
	 */
	@Override
	public Map<String,Integer> conntFacilitiesTop(Integer communityId)
			throws Exception {
		
		Map<String,Integer> map=new HashMap<String, Integer>();
		String sql = "SELECT fc.facilities_class_name AS typeName,COUNT(f.community_id) AS typeNum FROM facilities f,facilities_class fc " +
				"WHERE f.facilities_class_id=fc.facilities_class_id AND " +
				"f.community_id = ? GROUP BY fc.facilities_class_name";
		
		Connection con = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			con = jdbcTemplate.getDataSource().getConnection();
			preparedStatement = con.prepareStatement(sql);
			preparedStatement.setInt(1, communityId);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				map.put(resultSet.getString("typeName"), resultSet.getInt("typeNum"));
			}
		} catch (Exception e) {
			logger.error(this, e);
		} finally {
			if (null != resultSet) {
				try {
					resultSet.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (null != preparedStatement) {
				try {
					preparedStatement.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (null != con) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return map;
	}


	@Override
	public Integer getNumberByAdminId(Integer adminId , Integer startTime , Integer endTime) throws Exception {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection = this.getJdbcTemplate().getDataSource().getConnection();
			String sql = " SELECT count(*) FROM facilities f WHERE admin_id = ? ";
			if(startTime != null && endTime != null){
				sql += " AND create_time >= ? AND create_time <= ? ";
				preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setInt(1, adminId);
				preparedStatement.setInt(2, startTime);
				preparedStatement.setInt(3, endTime);
			}else{
				preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setInt(1, adminId);
			}
			
			resultSet = preparedStatement.executeQuery();
			if(resultSet != null && resultSet.next()){
				return resultSet.getInt(1);
			}
			
			return 0;
		} finally {
			if (null != resultSet) {
				try {
					resultSet.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (null != preparedStatement) {
				try {
					preparedStatement.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (null != connection) {
				try {
					connection.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public List<FacilitiesClass> getFacilitiesType() throws Exception {
		return this.getList("SELECT * FROM facilities_class", new Object[]{}, FacilitiesClass.class);
	}
}
