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
 * BBS��̳����ʵ����
 * 
 * @author IcyFenix
 */
@SuppressWarnings("serial")
public class Board implements Serializable {

	// ��̳��������
	private String name;

	// ��̳�����б�
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
	 * Ϊ����ظ���������µ�����
	 * 
	 * @param replyTopicID
	 *            ����ID����IDΪ0��ʱ������������ӣ��������ظ�ָ��ID������
	 * @param title
	 *            ��������
	 * @param content
	 *            ��������
	 */
	public void addTopic(int replyTopicID, String title, String content) {
		Topic topic = new Topic(getTopicCount() + 1, title, content);
		if (replyTopicID == 0) {
			topics.add(topic);
		} else {
			Topic parentTopic = topicsMap.get(replyTopicID);
			if (parentTopic == null) {
				throw new IllegalArgumentException("�Ƿ��Ļ���ID");
			} else {
				parentTopic.getReplyTopics().add(topic);
			}
		}
		topicsMap.put(topic.getId(), topic);
		save();
	}

	/**
	 * ���������Ϣ
	 */
	public void save() {
		getPersistenceService().save(this);
	}

	/**
	 * ���ذ�����Ϣ
	 */
	public void load() {
		getPersistenceService().load(this);
	}

	/**
	 * ��ȡNeonat�־û�����
	 */
	private PersistenceService getPersistenceService() {
		BundleContext context = Activator.getContext();
		ServiceReference<PersistenceService> sr = context.getServiceReference(PersistenceService.class);
		if (sr == null) {
			throw new UnsupportedOperationException("��ǰOSGi������û���ṩNeonat�־û������޷���ȡ������Ϣ");
		} else {
			PersistenceService service = context.getService(sr);
			return service;
		}
	}

}
