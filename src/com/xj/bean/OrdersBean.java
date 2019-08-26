package com.xj.bean;

import java.util.List;

/**
 * Created by maxwell on 14/12/11.
 */
public class OrdersBean {
    private Integer orderId;
    private String serial;
    private String emobIdUser;
    private String emobIdShop;
    private Integer communityId;
    private String orderPrice;
    private String action;
    private String online = "yes";
    private String status;
    
    private UserBonus userBonus;
    
    
    
    private List<OrderDetailBean> orderDetailBeanList;
    private String orderAddress;

    


    public UserBonus getUserBonus() {
		return userBonus;
	}

	public void setUserBonus(UserBonus userBonus) {
		this.userBonus = userBonus;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOrderAddress() {
		return orderAddress;
	}

	public void setOrderAddress(String orderAddress) {
		this.orderAddress = orderAddress;
	}

	public String getOnline() {
		return online;
	}

	public void setOnline(String online) {
		this.online = online;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(String orderPrice) {
		this.orderPrice = orderPrice;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getCommunityId() {
		return communityId;
	}

	public void setCommunityId(Integer communityId) {
		this.communityId = communityId;
	}

	public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getEmobIdUser() {
        return emobIdUser;
    }

    public void setEmobIdUser(String emobIdUser) {
        this.emobIdUser = emobIdUser;
    }

    public String getEmobIdShop() {
        return emobIdShop;
    }

    public void setEmobIdShop(String emobIdShop) {
        this.emobIdShop = emobIdShop;
    }

    
    public List<OrderDetailBean> getOrderDetailBeanList() {
        return orderDetailBeanList;
    }

    public void setOrderDetailBeanList(List<OrderDetailBean> orderDetailBeanList) {
        this.orderDetailBeanList = orderDetailBeanList;
    }
}
