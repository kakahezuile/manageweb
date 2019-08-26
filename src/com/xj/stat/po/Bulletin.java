package com.xj.stat.po;

public class Bulletin {
    private Integer id;

    private String bulletinText;

    private Integer createTime;

    private Integer communityId;

    private Integer adminId;

    private String theme;

    private String senderObject;

    private Long messageId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBulletinText() {
        return bulletinText;
    }

    public void setBulletinText(String bulletinText) {
        this.bulletinText = bulletinText;
    }

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    public Integer getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Integer communityId) {
        this.communityId = communityId;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getSenderObject() {
        return senderObject;
    }

    public void setSenderObject(String senderObject) {
        this.senderObject = senderObject;
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }
}