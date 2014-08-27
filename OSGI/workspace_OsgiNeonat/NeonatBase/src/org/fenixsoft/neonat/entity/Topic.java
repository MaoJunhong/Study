package org.fenixsoft.neonat.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * BBS��̳����ʵ����
 * 
 * @author IcyFenix
 */
@SuppressWarnings("serial")
public class Topic implements Serializable {

	// Ψһ����
	private int id;
	// ���ӱ���
	private String title;
	// ��������
	private String content;
	// ��������
	private Date date;
	// ��������
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
