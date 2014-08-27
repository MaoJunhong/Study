package org.fenixsoft.neonat.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * BBS论坛帖子实体类
 * 
 * @author IcyFenix
 */
@SuppressWarnings("serial")
public class Topic implements Serializable {

	// 唯一主键
	private int id;
	// 帖子标题
	private String title;
	// 帖子内容
	private String content;
	// 发帖日期
	private Date date;
	// 回帖内容
	private List<Topic> replyTopics;

	public Topic(int id, String title, String content) {
		this.title = title;
		this.content = content;
		this.replyTopics = new ArrayList<Topic>();
		this.date = new Date();
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<Topic> getReplyTopics() {
		return replyTopics;
	}

	public void setReplyTopics(List<Topic> replyTopics) {
		this.replyTopics = replyTopics;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
