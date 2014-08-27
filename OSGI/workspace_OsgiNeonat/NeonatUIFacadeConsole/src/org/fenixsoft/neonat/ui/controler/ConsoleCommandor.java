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
 * Neonat BBS控制台命令解释器
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
	 * 将命令行输入的内容分派到对应的命令实现方法之中
	 */
	public void doCommand(String cmd) {
		// 这句过滤掉putty第一条命令附带的Header
		cmd = cmd.charAt(0) == 65533 ? cmd.substring(14) : cmd;
		StringTokenizer st = new StringTokenizer(cmd);
		try {
			ConsoleCommandor.class.getDeclaredMethod(st.nextToken().toLowerCase() + "Action", StringTokenizer.class)
					.invoke(this, st);
		} catch (Exception e) {
			if (e instanceof NoSuchMethodException) {
				session.getWriter().println("非法命令：" + cmd);
			} else {
				e.printStackTrace(session.getWriter());
			}
		}
		prompt();
	}

	/**
	 * 命令行提示符
	 */
	private void prompt() {
		session.getWriter().print(TempleteRepository.PROMPT);
		session.getWriter().flush();
	}

	/************************************
	 * ACTIONS LIST
	 ************************************/

	/**
	 * 显示帮助界面
	 */
	void helpAction(StringTokenizer st) {
		session.getWriter().println(TempleteRepository.HELP_PAGE);
	}

	void debugAction(StringTokenizer st) {
		board.addTopic(0, "byte和char怎么这么麻烦？", "xxx");
		board.addTopic(0, "java里如何调用超类的构造函数", "xxx");
		board.addTopic(0, "关于数据移位", "xxx");
		board.addTopic(0, "Java中的无穷运算", "xxx");
		board.addTopic(0, "关于“动态绑定”的问题", "xxx");
		board.addTopic(0, "引用，指针的一些感想", "xxx");
		board.addTopic(3, "Re:关于数据移位", "xxx");
		board.addTopic(3, "Re:关于数据移位", "xxx");
		board.addTopic(8, "Re:Re:关于数据移位", "xxx");
		board.addTopic(8, "Re:Re:关于数据移位", "xxx");
		board.addTopic(5, "Re:关于“动态绑定”的问题", "xxx");
	}

	/**
	 * 显示欢迎界面
	 */
	void welcomeAction(StringTokenizer st) {
		session.getWriter().println(TempleteRepository.WELCOME_PAGE);
	}

	/**
	 * 显示帖子列表
	 */
	void topicsAction(StringTokenizer st) {
		session.getWriter().println(
				MessageFormat.format(TempleteRepository.BOARD_PAGE, board.getName(), board.getTopicCount()));
		printTopic(board.getTopics(), 1);
	}

	/**
	 * 查看帖子
	 */
	void viewAction(StringTokenizer st) {
		int id = Integer.parseInt(st.nextToken());
		Topic topic = board.getTopicsMap().get(id);
		if (topic == null) {
			throw new IllegalArgumentException("无效的帖子ID");
		}
		session.getWriter().println(
				MessageFormat.format(TempleteRepository.TOPIC_PAGE, topic.getTitle(), topic.getDate(),
						topic.getContent()));
	}

	/**
	 * 回帖动作
	 */
	void replyAction(StringTokenizer st) {
		int id = Integer.parseInt(st.nextToken());
		String title = st.nextToken();
		String content = st.nextToken();
		board.addTopic(id, title, content);
	}

	/**
	 * 发帖动作
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
			session.getWriter().print(" ├ ");
			session.getWriter().println("[" + topic.getId() + "] " + topic.getTitle());
			printTopic(topic.getReplyTopics(), level + 1);
		}
	}
}
