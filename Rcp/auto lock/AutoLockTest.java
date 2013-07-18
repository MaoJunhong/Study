package net.atp.trader.client.test.autolock;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

// 修改1: 监听全部系统事件
// 修改2: 监听指定系统事件
// 检测1: 在派发完系统事件后进行一次检测
// 检测2: 每间隔一定时间后后进行一次检测（会触发系统事件）

// 修改1+检测1: 延迟比较大（delayed: 8582），因为display.sleep（）不确定什么时候醒来	
// 修改1+检测2: 不能工作，因为检测2会触发系统事件
// 修改2+检测2: 延迟比较小（delayed: 218），会小于间隔时间
// 修改2+检测1: 延迟比较大（delayed: 9196），理由同1

public class AutoLockTest {
	private static long idleThresholdSecs = 6;
	private static long lastActivityAt = System.currentTimeMillis();
	private static Display display = Display.getDefault();
	private static Shell shell = new Shell(display);
	private static Button bt = new Button(shell, SWT.NONE);

	public static void main(String[] args) {
		shell.setLayout(new FillLayout());
		bt.setText("You can touch me now!");
		lock();
		shell.setSize(300, 150);
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}

	private static void lock() {
		lockAL(); // +++++++++++修改2
		checkTime(); // +++++++++++检测2
		display.asyncExec(new Runnable() {
			@Override
			public void run() {
				while (!shell.isDisposed()) {
					System.out.println(System.currentTimeMillis());

					while (display.readAndDispatch()) { // 能读取系统事件
						updateTime("update 1"); // +++++++++++修改1
					}

					checkLock("check 1"); // +++++++++++检测1

					if (!display.isDisposed()) {
						display.sleep(); // display没有系统事件了，睡觉
					}
				}
			}
		});
	}

	private static void updateTime(String name) {
		lastActivityAt = System.currentTimeMillis(); // 更新最后的操作时间
		System.out.println("lastActivityAt changed by " + name + " at "
				+ lastActivityAt);
	}

	private static boolean checkLock(String name) {
		if (System.currentTimeMillis() - lastActivityAt > idleThresholdSecs * 1000) {
			bt.setText("You cannot touch me now!"); // 改变文本
			bt.setEnabled(false); // 设置按钮不可点击
			System.out
					.println("locked at "
							+ System.currentTimeMillis()
							+ "\nby "
							+ name
							+ "\ndelayed "
							+ (System.currentTimeMillis() - lastActivityAt - idleThresholdSecs * 1000));
			return true;
		}
		return false;
	}

	private static void lockAL() {
		final Runnable handler = new Runnable() {
			@Override
			public void run() {
				updateTime("update 2");
			}
		};
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				display.syncExec(handler);
			}
		};
		display.addFilter(SWT.MouseMove, listener); // 给Display添加监听器
		display.addFilter(SWT.KeyUp, listener);
		// more events...
	}

	private static void checkTime() {
		LockTimer refresh = new LockTimer(Display.getDefault(), 1000);
		refresh.addRunnableListener(new Runnable() {
			boolean locked = false;

			@Override
			public void run() {
				if (locked == false && checkLock("check 2") == true) {
					locked = true;
				}
			}

		});
		refresh.start();
	}

	public static class LockTimer extends TimerTask {
		private Display display;
		private Timer timer;
		private long delayTime;

		private List<Runnable> reDrawLists;

		public LockTimer(Display display, long delayTime) {
			this.display = display;
			timer = new Timer();
			this.delayTime = delayTime;
			reDrawLists = new ArrayList<Runnable>();
		}

		public void start() {
			timer.schedule(this, delayTime, delayTime);
		}

		public void addRunnableListener(Runnable redraw) {
			reDrawLists.add(redraw);
		}

		@Override
		public void run() {

			int size = reDrawLists.size();
			for (int i = 0; i < size; i++) {
				display.asyncExec(reDrawLists.get(i));
			}
		}

		public void stop() {
			timer.cancel();
		}

	}

}