package com.xj.bean;



/**
 * 帮帮币历史记录
 * @author Administrator
 *
 */
public class BonuscoinHistory {
    private Integer bonuscoinId;

    private Integer createTime; // 创建时间

    private Integer bonuscoinCount; // 数量

    private String bonuscoinSource; // 来源

    private String emobId; // 环信id
    
    
    
    

    public BonuscoinHistory( Integer createTime,
			Integer bonuscoinCount, String bonuscoinSource, String emobId) {
		super();
		this.createTime = createTime;
		this.bonuscoinCount = bonuscoinCount;
		this.bonuscoinSource = bonuscoinSource;
		this.emobId = emobId;
	}

	public BonuscoinHistory() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getBonuscoinId() {
        return bonuscoinId;
    }

    public void setBonuscoinId(Integer bonuscoinId) {
        this.bonuscoinId = bonuscoinId;
    }
    

    public Integer getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Integer createTime) {
		this.createTime = createTime;
	}

	public Integer getBonuscoinCount() {
        return bonuscoinCount;
    }

    public void setBonuscoinCount(Integer bonuscoinCount) {
        this.bonuscoinCount = bonuscoinCount;
    }

    public String getBonuscoinSource() {
        return bonuscoinSource;
    }

    public void setBonuscoinSource(String bonuscoinSource) {
        this.bonuscoinSource = bonuscoinSource;
    }

    public String getEmobId() {
        return emobId;
    }

    public void setEmobId(String emobId) {
        this.emobId = emobId;
    }
}