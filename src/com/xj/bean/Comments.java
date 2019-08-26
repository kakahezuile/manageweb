package com.xj.bean;


import com.xj.annotation.NotInsertAnnotation;

/**
 * 评论表   来自A对B的评论
 */
public class Comments {
	@NotInsertAnnotation
	private Integer commentId;
    private String emobIdFrom;  // A用户环信id
    private String emobIdTo; // B用户环信id
    private int score; // 分数
    private String content; // 内容
    private int createTime; // 创建时间

    public String getEmobIdFrom() {
        return emobIdFrom;
    }

    public void setEmobIdFrom(String emobIdFrom) {
        this.emobIdFrom = emobIdFrom;
    }

    public String getEmobIdTo() {
        return emobIdTo;
    }

    public void setEmobIdTo(String emobIdTo) {
        this.emobIdTo = emobIdTo;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCreateTime() {
        return createTime;
    }

    public void setCreateTime(int createTime) {
        this.createTime = createTime;
    }

	public Integer getCommentId() {
		return commentId;
	}

	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
	}
    
}
