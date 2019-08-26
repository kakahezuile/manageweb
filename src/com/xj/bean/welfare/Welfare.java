package com.xj.bean.welfare;

import java.io.Serializable;
import java.math.BigDecimal;

import com.xj.annotation.MyAnnotation;
import com.xj.annotation.NotInsertAnnotation;
import com.xj.annotation.NotUpdataAnnotation;

/**
 * 福利表
 * 2015-09-16
 */
@SuppressWarnings("serial")
public class Welfare implements Serializable {

	@NotInsertAnnotation
	private Integer welfareId;			//主键
	private String title;				//福利标题
	private String poster;				//福利海报
	private String content;				//详细内容(以英文逗号分隔的图片)
	private Integer charactervalues;	//参与福利要求的最低人品值
	private Integer total;				//福利总数
	private Integer remain;				//库存
	private String rule;				//规则说明
	private String phone;				//物业联系电话
	private Integer communityId;		//小区id
	private String status;				//福利状态
	private Integer startTime;			//开始时间
	private Integer endTime;			//结束时间
	private BigDecimal price;			//福利价
	private String provideInstruction;	//发放说明
	private Integer createTime;			//创建时间
	private Integer modifyTime;			//修改时间
	private String reason;				//失败原因
	
	@NotInsertAnnotation
	@NotUpdataAnnotation
	@MyAnnotation
	private Integer orderUnpaid = 0;	//未支付的订单数量
	@NotInsertAnnotation
	@NotUpdataAnnotation
	@MyAnnotation
	private Integer orderPaid = 0;		//支付成功的订单数量
	@NotInsertAnnotation
	@NotUpdataAnnotation
	@MyAnnotation
	private Integer orderRefunded = 0;	//已退款的订单数量

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

	public String getPoster() {
		return poster;
	}

	public void setPoster(String poster) {
		this.poster = poster;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getCharactervalues() {
		return charactervalues;
	}

	public void setCharactervalues(Integer charactervalues) {
		this.charactervalues = charactervalues;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getRemain() {
		return remain;
	}

	public void setRemain(Integer remain) {
		this.remain = remain;
	}

	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getCommunityId() {
		return communityId;
	}

	public void setCommunityId(Integer communityId) {
		this.communityId = communityId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getStartTime() {
		return startTime;
	}

	public void setStartTime(Integer startTime) {
		this.startTime = startTime;
	}

	public Integer getEndTime() {
		return endTime;
	}

	public void setEndTime(Integer endTime) {
		this.endTime = endTime;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getProvideInstruction() {
		return provideInstruction;
	}

	public void setProvideInstruction(String provideInstruction) {
		this.provideInstruction = provideInstruction;
	}

	public Integer getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Integer createTime) {
		this.createTime = createTime;
	}

	public Integer getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Integer modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Integer getOrderUnpaid() {
		return orderUnpaid;
	}

	public void setOrderUnpaid(Integer orderUnpaid) {
		this.orderUnpaid = orderUnpaid;
	}

	public Integer getOrderPaid() {
		return orderPaid;
	}

	public void setOrderPaid(Integer orderPaid) {
		this.orderPaid = orderPaid;
	}

	public Integer getOrderRefunded() {
		return orderRefunded;
	}

	public void setOrderRefunded(Integer orderRefunded) {
		this.orderRefunded = orderRefunded;
	}
}