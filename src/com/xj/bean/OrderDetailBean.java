package com.xj.bean;

/**
 * Created by maxwell on 14/12/16.
 */
public class OrderDetailBean {
    //order detail
    private int serviceId;
    
    private String serviceName;
    
    private String price;
    
    private int count;
    
    private Integer attrId = 0;
    
    private String attrName = "";

    public Integer getAttrId() {
		return attrId;
	}

	public void setAttrId(Integer attrId) {
		this.attrId = attrId;
	}

	public String getAttrName() {
		return attrName;
	}

	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}

	public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

  

    public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
