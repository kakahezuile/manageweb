package com.xj.bean.welfare;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 福利订单信息类，不对应福利订单表，仅作为后台福利订单数据传输类
 * @author 王利东
 * 2015-09-16
 */
@SuppressWarnings("serial")
public class WelfareOrder implements Serializable {

	private Integer welfareId;		// 福利id
	private Integer welfareOrderId;	// 福利订单id
	private String code;			// 福利码
	private String emobId;			// 用户环信ID
	private String nickname;		// 用户昵称
	private String username;		// 用户账号
	private String avatar;			// 用户头像
	private String status;			// 订单状态
	private BigDecimal money;		// 订单金额
	private Integer bonusCoin;		// 订单帮帮币
	private String share;			// 是否分享

	public Integer getWelfareId() {
		return welfareId;
	}

	public void setWelfareId(Integer welfareId) {
		this.welfareId = welfareId;
	}

	public Integer getWelfareOrderId() {
		return welfareOrderId;
	}

	public void setWelfareOrderId(Integer welfareOrderId) {
		this.welfareOrderId = welfareOrderId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getEmobId() {
		return emobId;
	}

	public void setEmobId(String emobId) {
		this.emobId = emobId;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public Integer getBonusCoin() {
		return bonusCoin;
	}

	public void setBonusCoin(Integer bonusCoin) {
		this.bonusCoin = bonusCoin;
	}

	public String getShare() {
		return share;
	}

	public void setShare(String share) {
		this.share = share;
	}
}