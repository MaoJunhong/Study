package net.atp.trader.client.test.popupDialog;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.PopupDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class TestPopupDialog {
	private static Display display = Display.getDefault();
	private static Shell shell = new Shell(display);
	private static Button button = new Button(shell, SWT.NONE);

	public static void main(String[] args) {
		shell.setLayout(new FillLayout());

		new InputDialog(shell, "标题", "提示信息", "默认值", null).open();

		{
			IStatus[] status = new IStatus[4];
			status[0] = new Status(IStatus.INFO, "OpenErrorDialog", IStatus.OK,
					"hava a error", new ClassNotFoundException());
			status[1] = new Status(IStatus.ERROR, "OpenErrorDialog",
					IStatus.OK, "hava a error", new ClassNotFoundException());
			status[2] = new Status(IStatus.WARNING, "OpenErrorDialog",
					IStatus.OK, "hava a error", new ClassNotFoundException());
			status[3] = new Status(IStatus.INFO, "OpenErrorDialog", IStatus.OK,
					"hava a error", new ClassNotFoundException());
			MultiStatus multiStatus = new MultiStatus("MultiStatus",
					IStatus.OK, status, "原因描述", new Exception());

			ErrorDialog.openError(null, "errorTitle", "error describe",
					multiStatus);
		}
		{
			ProgressMonitorDialog progress = new ProgressMonitorDialog(null);
			try {
				progress.run(true, true, new IRunnableWithProgress() {

					@Override
					public void run(IProgressMonitor monitor)
							throws InvocationTargetException,
							InterruptedException {
						// monitor.beginTask("正在进行垃圾清理，请等待...",IProgressMonitor.UNKNOWN);//不知道什么时候结束时使用
						monitor.beginTask("正在进行垃圾清理，请等待...", 1000000);
						// 逻辑处理部分
						for (int i = 0; i < 1000000 && !monitor.isCanceled(); i++) {
							System.out.println(i);
							monitor.worked(1);
							monitor.subTask("已完成" + i);
						}
						monitor.done();
						if (monitor.isCanceled())
							throw new InterruptedException("cancel");
					}

				});
				MessageDialog.openInformation(shell, "垃圾清理", "清理成功");
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
				MessageDialog.openInformation(shell, "异常", "异常中断");
			}

		}

		{
			button.setText("I am the Button");
			button.addMouseMoveListener(new MouseMoveListener() {

				@Override
				public void mouseMove(MouseEvent e) {
					new PopupDialog(button.getShell(),
							PopupDialog.HOVER_SHELLSTYLE, true, false, false,
							false, false, null, null) {
						private static final int CURSOR_SIZE = 15;

						protected Point getInitialLocation(Point initialSize) { // 弹出窗口的初始位置，此处为鼠标的位置
							// show popup relative to cursor
							Display display = getShell().getDisplay();
							Point location = display.getCursorLocation();
							location.x += CURSOR_SIZE;
							location.y += CURSOR_SIZE;
							return location;
						}

						protected Control createDialogArea(Composite parent) { // 创建弹出窗口里的内容
							Label label = new Label(parent, SWT.WRAP);
							label.setText("Only for test");
							label.addFocusListener(new FocusAdapter() {
								public void focusLost(FocusEvent event) {
									close();
								}
							});
							// Use the compact margins employed by PopupDialog.
							GridData gd = new GridData(GridData.BEGINNING
									| GridData.FILL_BOTH);
							gd.horizontalIndent = PopupDialog.POPUP_HORIZONTALSPACING;
							gd.verticalIndent = PopupDialog.POPUP_VERTICALSPACING;
							label.setLayoutData(gd);
							return label;
						}
					}.open();// 打开对话框
				}
			});
		}
		shell.setSize(300, 150);
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
}
