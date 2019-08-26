package com.xj.resource;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.google.gson.Gson;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import com.xj.bean.HomeOwner;
import com.xj.bean.ResultStatusBean;
import com.xj.service.HomeOwnerService;

@Path("/communities/{communityId}/homeOwner")
@Scope("prototype")
@Component
public class HomeOwnerResource {
	
	private Gson gson = new Gson();
	
	@Autowired
	private HomeOwnerService homeOwnerService;
	
	@GET
	public String getHomeOwners(@PathParam("communityId") Integer communityId){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		try {
			resultStatusBean.setInfo(homeOwnerService.getHomeOwner(communityId));
			resultStatusBean.setStatus("yes");
		} catch (Exception e) {
			e.printStackTrace();
			resultStatusBean.setStatus("error");
			resultStatusBean.setMessage("查询时发生错误了");
		}
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}

	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.TEXT_HTML)
	@Path("/import")
	public String importRoom(@FormDataParam("roomFile") InputStream roomFileInputStream, @FormDataParam("roomFile") FormDataContentDisposition roomFile, @PathParam("communityId") Integer communityId) {
		ResultStatusBean result = new ResultStatusBean();
		List<HomeOwner> homeOwners = null;
		if (roomFile.getFileName().endsWith("xlsx")) {
			try {
				homeOwners = getHomeOwnersFromXlsx(roomFileInputStream, String.valueOf(communityId));
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (roomFile.getFileName().endsWith("xls")) {
			try {
				homeOwners = getHomeOwnersFromXls(roomFileInputStream, String.valueOf(communityId));
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			result.setStatus("no");
			result.setMessage("请选择excel文件!");
			return gson.toJson(result).replace("\"null\"", "\"\"");
		}
		
		if (!CollectionUtils.isEmpty(homeOwners)) {
			try {
				homeOwnerService.importHomeOwners(homeOwners);
				result.setStatus("yes");
			} catch (Exception e) {
				e.printStackTrace();
				
				result.setStatus("no");
				result.setMessage("导入楼号信息时出错，" + e.getMessage());
			}
		} else {
			result.setStatus("no");
			result.setMessage("导入楼号信息时出错，未能获取到楼号信息，请检查excel文件!");
		}
		
		return gson.toJson(result).replace("\"null\"", "\"\"");
	}
	
	@SuppressWarnings("resource")//roomFileInputStream会被web容器自动关闭
	private List<HomeOwner> getHomeOwnersFromXlsx(InputStream roomFileInputStream, String communityId) throws Exception {
		XSSFSheet sheet = new XSSFWorkbook(roomFileInputStream).getSheetAt(0);
		int total = sheet.getLastRowNum() + 1;
		
		List<HomeOwner> homeOwners = new ArrayList<HomeOwner>();
		for (int i = 2; i < total; i++) {
			XSSFRow row = sheet.getRow(i);
			
			XSSFCell cell0 = row.getCell(0);
			XSSFCell cell1 = row.getCell(1);
			XSSFCell cell2 = row.getCell(2);
			
			cell0.setCellType(Cell.CELL_TYPE_STRING);
			cell1.setCellType(Cell.CELL_TYPE_STRING);
			cell2.setCellType(Cell.CELL_TYPE_STRING);
			
			String floor = cell0.getStringCellValue();//楼号
			String unit = cell1.getStringCellValue();//单元号
			String room = cell2.getStringCellValue();//房间号
			
			if (StringUtils.isBlank(floor) && StringUtils.isBlank(unit) && StringUtils.isBlank(room)) {
				break;
			}
			
			homeOwners.add(new HomeOwner(communityId, floor, unit, room));
		}
		
		return homeOwners;
	}
	
	@SuppressWarnings("resource")//roomFileInputStream会被web容器自动关闭
	private List<HomeOwner> getHomeOwnersFromXls(InputStream roomFileInputStream, String communityId) throws Exception {
		HSSFSheet sheet = new HSSFWorkbook(roomFileInputStream).getSheetAt(0);
		int total = sheet.getLastRowNum() + 1;
		
		List<HomeOwner> homeOwners = new ArrayList<HomeOwner>();
		for (int i = 2; i < total; i++) {
			HSSFRow row = sheet.getRow(i);
			
			HSSFCell cell0 = row.getCell(0);
			HSSFCell cell1 = row.getCell(1);
			HSSFCell cell2 = row.getCell(2);
			
			cell0.setCellType(Cell.CELL_TYPE_STRING);
			cell1.setCellType(Cell.CELL_TYPE_STRING);
			cell2.setCellType(Cell.CELL_TYPE_STRING);
			
			String floor = cell0.getStringCellValue();//楼号
			String unit = cell1.getStringCellValue();//单元号
			String room = cell2.getStringCellValue();//房间号
			
			if (StringUtils.isBlank(floor) && StringUtils.isBlank(unit) && StringUtils.isBlank(room)) {
				break;
			}
			
			homeOwners.add(new HomeOwner(communityId, floor, unit, room));
		}
		
		return homeOwners;
	}
	
	@GET
	@Path("/selectFloor")
	public String selectFloor(@PathParam("communityId") Integer communityId){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		List<String> floor = homeOwnerService.selectFloor(communityId);
		resultStatusBean.setInfo(floor);
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
	@GET
	@Path("/selectUnit")
	public String selectUnit(@PathParam("communityId") Integer communityId,@QueryParam("userFloor")String userFloor){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		List<String> unit = homeOwnerService.selectUnit(communityId,userFloor);
		resultStatusBean.setInfo(unit);
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
	@GET
	@Path("/selectRoom")
	public String selectRoom(@PathParam("communityId") Integer communityId,@QueryParam("userFloor")String userFloor,@QueryParam("userUnit")String userUnit){
		ResultStatusBean resultStatusBean = new ResultStatusBean();
		List<String> room = homeOwnerService.selectRoom(communityId,userFloor,userUnit);
		resultStatusBean.setInfo(room);
		return gson.toJson(resultStatusBean).replace("\"null\"", "\"\"");
	}
	
}