package ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.wb.swt.SWTResourceManager;

import util.LayoutUtil;

/**
 * SWT放360软件管家界面
 * 
 * @author xwalker
 * 
 */
public class MainUI {

	protected Shell shell;
	private Composite winTitle;
	private Composite winToolbar;
	private Composite winStatusbar;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			MainUI window = new MainUI();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();

		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell(SWT.NO_TRIM | SWT.BORDER);
		shell.setSize(850, 560);
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));
		LayoutUtil.centerShell(Display.getCurrent(), shell);
		Composite composite = new Composite(shell, SWT.NONE);
		composite.setBackgroundImage(SWTResourceManager.getImage(getClass(),
				"/res/bg.jpg"));
		composite.setBackgroundMode(SWT.INHERIT_FORCE);
		RowLayout rowLayout = new RowLayout(SWT.HORIZONTAL);
		rowLayout.marginTop = 0;
		rowLayout.marginRight = 0;
		rowLayout.marginLeft = 0;
		rowLayout.spacing = 5;
		composite.setLayout(rowLayout);

		winTitle = createWinTitle(composite);
		winToolbar = createWinToolBar(composite);

		Composite winMainContent = new Composite(composite, SWT.NONE);
		winMainContent.setLayout(new StackLayout());
		winMainContent.setLayoutData(new RowData(850, 407));

		winStatusbar = createWinStatusBar(composite);
		addShellListener(winTitle, winToolbar, winStatusbar);
	}

	private Composite createWinTitle(Composite composite) {
		Composite winTitle = new Composite(composite, SWT.NONE);
		winTitle.setLayoutData(new RowData(850, 25));

		SystemButton menuBtn = new SystemButton(winTitle, SWT.NONE,
				"/res/sysbtn_menu.png", 4, "菜单", this, new Listener() {

					@Override
					public void handleEvent(Event e) {
						int x = e.getBounds().x;
						int y = e.getBounds().y;
						if (x < 0 || y < 0)
							return;
						((Composite) (e.widget)).getMenu().setVisible(true);
					}
				});
		menuBtn.setLocation(769, 0);
		Menu menu = new Menu(menuBtn);
		menuBtn.setMenu(menu);
		MenuItem mntmCaidan = new MenuItem(menu, SWT.NONE);
		mntmCaidan.setText("设置");

		MenuItem menuItem = new MenuItem(menu, SWT.NONE);
		menuItem.setText("\u65B0\u7248\u529F\u80FD");

		MenuItem menuItem_1 = new MenuItem(menu, SWT.NONE);
		menuItem_1.setText("\u5B98\u65B9\u5FAE\u535A");

		MenuItem menuItem_2 = new MenuItem(menu, SWT.NONE);
		menuItem_2.setText("\u95EE\u9898\u53CD\u9988\u4E0E\u5EFA\u8BAE");

		MenuItem menuItem_3 = new MenuItem(menu, SWT.NONE);
		menuItem_3.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

			}
		});
		menuItem_3.setText("\u7528\u6237\u9690\u79C1\u4FDD\u62A4\u653F\u7B56");

		MenuItem menuItem_4 = new MenuItem(menu, SWT.NONE);
		menuItem_4.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				MessageBox mb = new MessageBox(shell, SWT.ABORT);
				mb.setMessage("开源中国 @xwalker QQ:909854136");
				mb.setText("关于");
				mb.open();

			}
		});
		menuItem_4.setText("\u5173\u4E8E\u6211\u4EEC");

		SystemButton minBtn = new SystemButton(winTitle, SWT.NONE,
				"/res/sysbtn_min.png", 4, "最小化", this, new Listener() {

					@Override
					public void handleEvent(Event event) {
						shell.setMinimized(true);
					}
				});
		minBtn.setLocation(796, 0);

		SystemButton closeBtn = new SystemButton(winTitle, SWT.NONE,
				"/res/sysbtn_close.png", 4, "关闭", this, new Listener() {

					@Override
					public void handleEvent(Event event) {
						MessageBox mb = new MessageBox(shell, SWT.OK
								| SWT.CANCEL);
						mb.setMessage("确定退出？");
						mb.setText("确定");
						if (mb.open() == SWT.OK) {
							shell.close();
							shell.dispose();
						}

					}
				});
		closeBtn.setLocation(823, 0);

		CLabel lblJava = new CLabel(winTitle, SWT.NONE);
		lblJava.setFont(SWTResourceManager.getFont("微软雅黑", 9, SWT.BOLD));
		lblJava.setForeground(SWTResourceManager
				.getColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT));
		lblJava.setBounds(5, 0, 309, 23);
		lblJava.setText("Java\u684C\u9762\u7A0B\u5E8F \u4EFF360\u8F6F\u4EF6\u7BA1\u5BB6 QQ:909854136");

		return winTitle;
	}

	private Composite createWinToolBar(Composite composite) {
		Composite winToolbar = new Composite(composite, SWT.NONE);
		winToolbar.setLayoutData(new RowData(850, 88));

		TabButton button1 = new TabButton(winToolbar, SWT.NONE, true,
				"/res/tab_button_SoftCenter.png", 3, null, this);
		button1.setBounds(20, 0, 74, 82);
		TabButton button2 = new TabButton(winToolbar, SWT.NONE, false,
				"/res/tab_button_AppCenter.png", 3, null, this);
		button2.setBounds(20 + 74 + 5, 0, 74, 82);
		TabButton button3 = new TabButton(winToolbar, SWT.NONE, false,
				"/res/tab_button_GameCenter.png", 3, null, this);
		button3.setBounds(20 + 74 * 2 + 5, 0, 74, 82);
		TabButton button4 = new TabButton(winToolbar, SWT.NONE, false,
				"/res/tab_button_Update.png", 3, null, this);
		button4.setBounds(20 + 74 * 3 + 5, 0, 74, 82);
		TabButton button5 = new TabButton(winToolbar, SWT.NONE, false,
				"/res/tab_button_Uninstall.png", 3, null, this);
		button5.setBounds(20 + 74 * 4 + 5, 0, 74, 82);
		TabButton button6 = new TabButton(winToolbar, SWT.NONE, false,
				"/res/tab_button_StartupAccelerate.png", 3, null, this);
		button6.setBounds(20 + 74 * 5 + 5, 0, 74, 82);
		TabButton button7 = new TabButton(winToolbar, SWT.NONE, false,
				"/res/tab_button_MobileEssential.png", 3, null, this);
		button7.setBounds(20 + 74 * 6 + 5, 0, 74, 82);

		Label logoLabel = new Label(winToolbar, SWT.NONE);
		logoLabel.setBounds(690, 5, 140, 67);
		logoLabel.setImage(SWTResourceManager.getImage(getClass(),
				"/res/logo.png"));

		return winToolbar;
	}

	private Composite createWinStatusBar(Composite composite) {
		Composite winStatusbar = new Composite(composite, SWT.NONE);
		winStatusbar.setLayoutData(new RowData(850, 25));

		Label smlIconLabel = new Label(winStatusbar, SWT.NONE);
		smlIconLabel.setBounds(565, 7, 16, 16);
		smlIconLabel.setImage(SWTResourceManager.getImage(getClass(),
				"/res/icon_sml.png"));

		CLabel smlLabel = new CLabel(winStatusbar, SWT.NONE);
		smlLabel.setForeground(SWTResourceManager
				.getColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT));
		smlLabel.setBounds(585, 5, 67, 20);
		smlLabel.setText("软件小助手");

		Label settingIconLabel = new Label(winStatusbar, SWT.NONE);
		settingIconLabel.setBounds(655, 7, 16, 16);
		settingIconLabel.setImage(SWTResourceManager.getImage(getClass(),
				"/res/setting.png"));

		CLabel lsettingLbel = new CLabel(winStatusbar, SWT.NONE);
		lsettingLbel.setForeground(SWTResourceManager
				.getColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT));
		lsettingLbel.setBounds(668, 5, 30, 20);
		lsettingLbel.setText("设置");

		Label downloadMgrIconLabel = new Label(winStatusbar, SWT.NONE);
		downloadMgrIconLabel.setBounds(705, 5, 16, 16);
		downloadMgrIconLabel.setImage(SWTResourceManager.getImage(getClass(),
				"/res/downloadMgr.png"));

		CLabel downloadMgrLabel = new CLabel(winStatusbar, SWT.NONE);
		downloadMgrLabel.setForeground(SWTResourceManager
				.getColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT));
		downloadMgrLabel.setBounds(725, 5, 117, 20);
		downloadMgrLabel.setText("已下载软件共 10 款");

		return winStatusbar;
	}

	/**
	 * 其他导航条按钮还原
	 */
	public void otherNavBtnToNormal(Widget widget) {
		Control[] btns = winToolbar.getChildren();
		for (Control btn : btns) {
			if (btn instanceof TabButton) {
				if (!btn.equals(widget)) {
					((TabButton) btn).toNoraml();
				}
			}
		}

	}

	/**
	 * 添加窗口事件监听器
	 * 
	 * @param winTitle
	 * @param winToolbar
	 * @param winStatusbar
	 */
	private void addShellListener(Composite winTitle, Composite winToolbar,
			Composite winStatusbar) {
		Listener listener = new Listener() {
			int startX, startY;

			public void handleEvent(Event e) {
				if (e.type == SWT.MouseDown && e.button == 1) {
					startX = e.x;
					startY = e.y;
				}
				if (e.type == SWT.MouseMove && (e.stateMask & SWT.BUTTON1) != 0) {
					Point p = shell.toDisplay(e.x, e.y);
					p.x -= startX;
					p.y -= startY;

					if (p.x + startX < 0)
						return;
					if (p.y + startY < 0)
						return;
					int w = shell.getDisplay().getPrimaryMonitor().getBounds().width;
					int h = shell.getDisplay().getPrimaryMonitor().getBounds().height;
					if (p.x > w)
						return;
					if (p.y > h)
						return;

					shell.setLocation(p);
				}
			}
		};
		winTitle.addListener(SWT.MouseDown, listener);
		winTitle.addListener(SWT.MouseMove, listener);
		winToolbar.addListener(SWT.MouseDown, listener);
		winToolbar.addListener(SWT.MouseMove, listener);
		winStatusbar.addListener(SWT.MouseDown, listener);
		winStatusbar.addListener(SWT.MouseMove, listener);
		shell.addListener(SWT.KeyUp, new Listener() {

			@Override
			public void handleEvent(Event e) {
				if (e.keyCode == SWT.ESC) {
					shell.close();
					shell.dispose();
				}

			}
		});
	}
}
