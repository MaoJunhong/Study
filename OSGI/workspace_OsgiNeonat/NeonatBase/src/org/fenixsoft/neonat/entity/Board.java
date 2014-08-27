package org.fenixsoft.neonat.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.fenixsoft.neonat.Activator;
import org.fenixsoft.neonat.service.PersistenceService;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 * BBS论坛版面实体类
 * 
 * @author IcyFenix
 */
@SuppressWarnings("serial")
public class Board implements Serializable {

	// 论坛版面名称
	private String name;

	// 论坛帖子列表
	private List<Topic> topics;
	private Map<Integer, Topic> topicsMap;

	public Board(String name) {
		this.name = name;
		this.topics = new ArrayList<Topic>();
		this.topicsMap = new HashMap<Integer, Topic>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Topic> getTopics() {
		return topics;
	}

	public void setTopics(List<Topic> topics) {
		this.topics = topics;
	}

	public Map<Integer, Topic> getTopicsMap() {
		return topicsMap;
	}

	public void setTopicsMap(Map<Integer, Topic> topicsMap) {
		this.topicsMap = topicsMap;
	}

	public int getTopicCount() {
		return topicsMap.size();
	}

	/**
	 * 为版面回复或者添加新的帖子
	 * 
	 * @param replyTopicID
	 *            回帖ID，当ID为0的时候代表新增帖子，否则代表回复指定ID的帖子
	 * @param title
	 *            帖子主题
	 * @param content
	 *            帖子内容
	 */
	public void addTopic(int replyTopicID, String title, String content) {
		Topic topic = new Topic(getTopicCount() + 1, title, content);
		if (replyTopicID == 0) {
			topics.add(topic);
		} else {
			Topic parentTopic = topicsMap.get(replyTopicID);
			if (parentTopic == null) {
				throw new IllegalArgumentException("非法的回帖ID");
			} else {
				parentTopic.getReplyTopics().add(topic);
			}
		}
		topicsMap.put(topic.getId(), topic);
		save();
	}

	/**
	 * 保存版面信息
	 */
	public void save() {
		getPersistenceService().save(this);
	}

	/**
	 * 加载版面信息
	 */
	public void load() {
		getPersistenceService().load(this);
	}

	/**
	 * 获取Neonat持久化服务
	 */
	private PersistenceService getPersistenceService() {
		BundleContext context = Activator.getContext();
		ServiceReference<PersistenceService> sr = context.getServiceReference(PersistenceService.class);
		if (sr == null) {
			throw new UnsupportedOperationException("当前OSGi容器中没有提供Neonat持久化服务，无法存取版面信息");
		} else {
			PersistenceService service = context.getService(sr);
			return service;
		}
	}

}
