package org.fenixsoft.neonat.persistence;

import java.util.Date;

import org.fenixsoft.neonat.entity.Board;
import org.fenixsoft.neonat.entity.Topic;
import org.fenixsoft.neonat.service.PersistenceService;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.prefs.BackingStoreException;
import org.osgi.service.prefs.Preferences;
import org.osgi.service.prefs.PreferencesService;

/**
 * 通过Preferences服务来实现的持久化服务
 * 
 * @author IcyFenix
 */
public class PreferencesPersistenceService implements PersistenceService {

	private static final String DATE = "TOPIC.DATE";
	private static final String CONTENT = "TOPIC.CONTENT";
	private static final String TITLE = "TOPIC.TITLE";

	private Preferences getRootNode(String name) {
		BundleContext context = Activator.getContext();
		ServiceReference<PreferencesService> sr = context.getServiceReference(PreferencesService.class);
		if (sr == null) {
			throw new UnsupportedOperationException("当前OSGi容器中没有提供Preferences服务");
		} else {
			PreferencesService service = context.getService(sr);
			Preferences rootNode = service.getUserPreferences(name);
			return rootNode;
		}
	}

	@Override
	public void save(Board board) {
		Preferences root = getRootNode(board.getName());
		for (Topic topic : board.getTopics()) {
			saveTopic(root, topic);
		}
		try {
			root.flush();
		} catch (BackingStoreException e) {
			e.printStackTrace();
		}
	}

	private void saveTopic(Preferences root, Topic topic) {
		Preferences node = root.node(String.valueOf(topic.getId()));
		node.put(TITLE, topic.getTitle());
		node.put(CONTENT, topic.getContent());
		node.put(DATE, String.valueOf(topic.getDate()));
		for (Topic replyTopic : topic.getReplyTopics()) {
			saveTopic(node, replyTopic);
		}
	}

	@Override
	public void load(Board board) {
		Preferences root = getRootNode(board.getName());
		try {
			for (String childNodeName : root.childrenNames()) {
				Topic topic = loadTopic(board, root.node(childNodeName));
				board.getTopics().add(topic);
				board.getTopicsMap().put(topic.getId(), topic);
			}
		} catch (BackingStoreException e) {
			e.printStackTrace();
		}
	}

	private Topic loadTopic(Board board, Preferences node) throws BackingStoreException {
		Topic topic = new Topic(Integer.parseInt(node.name()), node.get(TITLE, ""), node.get(CONTENT, ""));
		topic.setDate(new Date(node.getLong(TITLE, 0)));
		for (String childNodeName : node.childrenNames()) {
			Topic replyTopic = loadTopic(board, node.node(childNodeName));
			topic.getReplyTopics().add(replyTopic);
			board.getTopicsMap().put(replyTopic.getId(), replyTopic);
		}
		return topic;
	}

	public static void main(String[] args) {
		System.out.println("A=" + ((int) 'A'));
		System.out.println("a=" + ((int) 'a'));
	}
}
