package org.fenixsoft.neonat.ui.controler;

import java.text.MessageFormat;
import java.util.List;
import java.util.StringTokenizer;

import org.fenixsoft.neonat.entity.Board;
import org.fenixsoft.neonat.entity.Topic;
import org.fenixsoft.neonat.service.NeonatModelService;
import org.fenixsoft.neonat.ui.Activator;
import org.fenixsoft.neonat.ui.view.TempleteRepository;
import org.osgi.framework.BundleContext;

/**
 * Neonat BBS����̨���������
 * 
 * @author IcyFenix
 */
public class ConsoleCommandor {

	private ConsoleSession session;
	private Board board;

	public ConsoleCommandor(ConsoleSession session) {
		this.session = session;
		BundleContext context = Activator.getContext();
		NeonatModelService ms = context.getService(context.getServiceReference(NeonatModelService.class));
		this.board = ms.getBoard();
	}

	/**
	 * ����������������ݷ��ɵ���Ӧ������ʵ�ַ���֮��
	 */
	public void doCommand(String cmd) {
		// �����˵�putty��һ���������Header
		cmd = cmd.charAt(0) == 65533 ? cmd.substring(14) : cmd;
		StringTokenizer st = new StringTokenizer(cmd);
		try {
			ConsoleCommandor.class.getDeclaredMethod(st.nextToken().toLowerCase() + "Action", StringTokenizer.class)
					.invoke(this, st);
		} catch (Exception e) {
			if (e instanceof NoSuchMethodException) {
				session.getWriter().println("�Ƿ����" + cmd);
			} else {
				e.printStackTrace(session.getWriter());
			}
		}
		prompt();
	}

	/**
	 * ��������ʾ��
	 */
	private void prompt() {
		session.getWriter().print(TempleteRepository.PROMPT);
		session.getWriter().flush();
	}

	/************************************
	 * ACTIONS LIST
	 ************************************/

	/**
	 * ��ʾ��������
	 */
	void helpAction(StringTokenizer st) {
		session.getWriter().println(TempleteRepository.HELP_PAGE);
	}

	void debugAction(StringTokenizer st) {
		board.addTopic(0, "byte��char��ô��ô�鷳��", "xxx");
		board.addTopic(0, "java����ε��ó���Ĺ��캯��", "xxx");
		board.addTopic(0, "����������λ", "xxx");
		board.addTopic(0, "Java�е���������", "xxx");
		board.addTopic(0, "���ڡ���̬�󶨡�������", "xxx");
		board.addTopic(0, "���ã�ָ���һЩ����", "xxx");
		board.addTopic(3, "Re:����������λ", "xxx");
		board.addTopic(3, "Re:����������λ", "xxx");
		board.addTopic(8, "Re:Re:����������λ", "xxx");
		board.addTopic(8, "Re:Re:����������λ", "xxx");
		board.addTopic(5, "Re:���ڡ���̬�󶨡�������", "xxx");
	}

	/**
	 * ��ʾ��ӭ����
	 */
	void welcomeAction(StringTokenizer st) {
		session.getWriter().println(TempleteRepository.WELCOME_PAGE);
	}

	/**
	 * ��ʾ�����б�
	 */
	void topicsAction(StringTokenizer st) {
		session.getWriter().println(
				MessageFormat.format(TempleteRepository.BOARD_PAGE, board.getName(), board.getTopicCount()));
		printTopic(board.getTopics(), 1);
	}

	/**
	 * �鿴����
	 */
	void viewAction(StringTokenizer st) {
		int id = Integer.parseInt(st.nextToken());
		Topic topic = board.getTopicsMap().get(id);
		if (topic == null) {
			throw new IllegalArgumentException("��Ч������ID");
		}
		session.getWriter().println(
				MessageFormat.format(TempleteRepository.TOPIC_PAGE, topic.getTitle(), topic.getDate(),
						topic.getContent()));
	}

	/**
	 * ��������
	 */
	void replyAction(StringTokenizer st) {
		int id = Integer.parseInt(st.nextToken());
		String title = st.nextToken();
		String content = st.nextToken();
		board.addTopic(id, title, content);
	}

	/**
	 * ��������
	 */
	void newAction(StringTokenizer st) {
		String title = st.nextToken();
		String content = st.nextToken();
		board.addTopic(0, title, content);
	}

	/************************************
	 * ACTIONS PRIVATE METHODS
	 ************************************/

	private void printTopic(List<Topic> topics, int level) {
		for (Topic topic : topics) {
			for (int i = 0; i < level - 1; i++)
				session.getWriter().print("    ");
			session.getWriter().print(" �� ");
			session.getWriter().println("[" + topic.getId() + "] " + topic.getTitle());
			printTopic(topic.getReplyTopics(), level + 1);
		}
	}
}
