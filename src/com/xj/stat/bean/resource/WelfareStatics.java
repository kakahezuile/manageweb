package com.xj.stat.bean.resource;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WelfareStatics {
	private String buyNum;				//购买量
	private Integer welfareId;			//主键
	private String  title;  			//福利标题
	private Integer startTime;			//开始时间
	private Integer endTime;			//结束时间
	private BigDecimal price;			//福利价
	private Integer clickNum;			//点击量
	private BigDecimal totalPrice;		//总共价格
	private Integer totalBonusCoinCount;//帮帮比数量
	private String  status ;			//福利状态
	private String nickName;			//购买用户昵称
	private String phone;				//购买用户电话
	private String buyTime;				//购买时间
	private String emobId;
	private String createTime;
	private String adress;	          //用户地址
	private String start;
	private String end;
	private String billChannel ;
	private String tradeType ;
	private String tradeNo ;
	private String code ;
	
	
	public String getBillChannel() {
		return billChannel;
	}



	public void setBillChannel(String billChannel) {
		this.billChannel = billChannel;
	}



	public String getTradeType() {
		return tradeType;
	}



	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}



	public String getTradeNo() {
		return tradeNo;
	}



	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}



	public String getCode() {
		return code;
	}



	public void setCode(String code) {
		this.code = code;
	}



	public String getAdress() {
		return adress;
	}



	public void setAdress(String adress) {
		this.adress = adress;
	}



	public String getEmobId() {
		return emobId;
	}

	
	
	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		SimpleDateFormat sf = new SimpleDateFormat("HH:mm:ss");
		this.createTime = sf.format(new Date(Integer.parseInt(createTime)*1000l));
	}



	public void setEmobId(String emobId) {
		this.emobId = emobId;
	}


	public String getNickName() {
		return nickName;
	}


	public void setNickName(String nickName) {
		this.nickName = nickName;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public String getBuyTime() {
		return buyTime;
	}


	public void setBuyTime(String buyTime) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		this.buyTime = sf.format(new Date(Integer.parseInt(buyTime)*1000l));
	}


	public String getStart() {
		return start;
	}


	public String getEnd() {
		return end;
	}

	public void setStart(String start) {
		this.start = start;
	}


	public void setEnd(String end) {
		this.end = end;
	}


	public Integer getWelfareId() {
		return welfareId;
	}

	public void setWelfareId(Integer welfareId) {
		this.welfareId = welfareId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getStartTime() {
		return startTime;
	}

	public void setStartTime(Integer startTime) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		this.start = sf.format(new Date(startTime*1000l));
		this.startTime = startTime;
	}

	public Integer getEndTime() {
		return endTime;
	}

	public void setEndTime(Integer endTime) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		this.end = sf.format(new Date(endTime*1000l));
		this.endTime = endTime;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Integer getClickNum() {
		return clickNum;
	}

	public void setClickNum(Integer clickNum) {
		this.clickNum = clickNum;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		if(totalPrice==null){
			this.totalPrice = new BigDecimal(0);
		}else{
			
			this.totalPrice = totalPrice;
		}
	}

	public Integer getTotalBonusCoinCount() {
		return totalBonusCoinCount;
	}

	public void setTotalBonusCoinCount(Integer totalBonusCoinCount) {
		this.totalBonusCoinCount = totalBonusCoinCount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getBuyNum() {
		return buyNum;
	}

	public void setBuyNum(String buyNum) {
		this.buyNum = buyNum;
	}
	
}
