package net.atp.trader.client.utils.dialog.history;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;

import net.atp.trader.client.Activator;
import net.atp.trader.client.Application;
import net.atp.trader.client.log.action.MoreLogContentProvider;
import net.atp.trader.client.log.action.MoreLogLabelProvider;
import net.atp.trader.client.log.action.LogEntity;
import net.atp.trader.client.utils.SWTUtils;
import net.atp.trader.client.utils.nls.CustomString;
import net.atp.trader.client.utils.pages.IImageConstant;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolTip;
import org.eclipse.ui.internal.misc.StringMatcher;
import org.eclipse.ui.plugin.AbstractUIPlugin;

public class HistoryRecordsDialog extends Dialog {
	// part1. big title area
	private CLabel title;

	// part2. sub title area
	private Text tabST;

	// part3. tool bar area
	private DateTime dateBegin, timeBegin;
	private Combo recordsTypeC;
	private String[] recordsTypeS;
	private DateTime dateEnd, timeEnd;

	// part4. table area
	private TableViewer tabV;
	private boolean hasIdx;
	private String[] tabHeaderS;
	private List<Object> list;
	private IContentModelProvider contentModel;
	private IContentProvider contentProvider;
	private IBaseLabelProvider labelProvider;
	private TablePatternFilter tabPF;

	// part5. status bar area
	private CLabel cmdFirst, cmdPrevious, cmdNext, cmdLast, gridInfo;
	private int pageIndex = 1, pageCount, pageItems, totalItems;
	private CCombo cbPageItems, cbPageJump;

	/**
	 * 构造函数 参数： shell type：能够切换的日志类型 tabHeader：表头 contentProvider：内容提供器
	 * labelProvider：标签提供器 contentModel：内容模型，负责提供表格list hasIdx：第一列是否是显示序号
	 * */
	public HistoryRecordsDialog(Shell parentShell, String[] recordsType,
			String[] tabHeaderS, IContentProvider contentProvider,
			IBaseLabelProvider labelProvider,
			IContentModelProvider contentModel, boolean hasIdx) {
		super(parentShell);
		setShellStyle(getShellStyle() | SWT.RESIZE | SWT.MAX);
		this.recordsTypeS = recordsType;
		this.tabHeaderS = tabHeaderS;
		this.contentProvider = contentProvider;
		this.labelProvider = labelProvider;
		this.contentModel = contentModel;
		this.hasIdx = hasIdx;
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setSize(800, 1000);
		SWTUtils.setCenter(newShell);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		GridLayout pgl = new GridLayout();
		pgl.marginLeft = pgl.marginRight = 30;
		pgl.marginTop = pgl.marginBottom = 40;
		pgl.verticalSpacing = 10;
		parent.setLayout(pgl);

		createTitle(parent);
		createSubTitle(parent);
		createToolBar(parent);

		Composite comp = new Composite(parent, SWT.BORDER);
		GridLayout cgl = new GridLayout();
		cgl.verticalSpacing = 10;
		comp.setLayout(cgl);
		comp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		createTable(comp);
		createStatusBar(comp);

		return parent;
	}

	@Override
	protected void okPressed() {
		list = contentModel.getList(getBeginTime(), getEndTime(),
				getRecordsType());
		totalItems = list.size();

		pageItems = Integer.parseInt(cbPageItems.getText());
		pageCount = totalItems / pageItems
				+ (totalItems % pageItems == 0 ? 0 : 1);

		refresh();
	}

	private void refresh() {
		setStatusInfo();
		setCmdIcons();
		setPageJump();
		List<Object> input = new ArrayList<Object>();
		for (int idx = (pageIndex - 1) * pageItems, i = 0; i < pageItems
				&& idx < list.size(); ++i, ++idx) {
			input.add(list.get(idx));
		}
		tabV.setInput(input);
		if (hasIdx) {
			ItemsNumberForTable.setItemsNumber(tabV);
		}
	}

	/**
	 * 获取随机字符串
	 * */
	private static String getRandomString() {
		String s = "";
		int l = 1 + (int) (Math.random() * 10);
		for (int j = 0; j < l; ++j) {
			s += (char) ('a' + (int) (Math.random() * 26));
		}
		return s;
	}

	// title //////////////////
	/**
	 * create title composite
	 * */
	protected void createTitle(Composite parent) {
		Composite comp = new Composite(parent, SWT.NONE);
		GridData gd = new GridData(SWT.FILL, SWT.FILL, true, false);
		comp.setLayoutData(gd);
		GridLayout cgl = new GridLayout();
		cgl.marginBottom = 20;
		comp.setLayout(cgl);

		{
			title = new CLabel(comp, SWT.NONE);
			GridData tgd = new GridData(SWT.CENTER, SWT.CENTER, true, false);
			title.setLayoutData(tgd);
			title.setText(CustomString.HISTORY_RECORD);
			Font font = title.getFont();
			FontData fd = font.getFontData()[0];
			fd.setStyle(SWT.BOLD);
			fd.setHeight((int) (fd.getHeight() * 1.618));
			title.setFont(new Font(Display.getDefault(), fd));
		}
	}

	/**
	 * set title text
	 * */
	public void setTitleText(String titleText) {
		title.setText(titleText);
	}

	/**
	 * set title font
	 * */
	public void setTitleFont(Font font) {
		title.setFont(font);
	}

	// subtitle//////////////////
	private void createSubTitle(Composite parent) {
		Composite comp = new Composite(parent, SWT.NONE);
		GridData gd = new GridData(SWT.FILL, SWT.FILL, true, false);
		comp.setLayoutData(gd);
		GridLayout cgl = new GridLayout(3, true);
		comp.setLayout(cgl);

		{
			Label label = new Label(comp, SWT.NONE);
			GridData tgd = new GridData(SWT.FILL, SWT.CENTER, true, false);
			tgd.horizontalSpan = 2;
			label.setLayoutData(tgd);
			String text = DateFormat.getDateInstance(DateFormat.LONG,
					Locale.CHINA).format(new Date());
			text = "";
			label.setText(text);

			Font font = label.getFont();
			FontData fd = font.getFontData()[0];
			fd.setStyle(SWT.ITALIC);
			label.setFont(new Font(Display.getDefault(), fd));
		}
		{
			Composite tmp = new Composite(comp, SWT.NONE);
			tmp.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
			tmp.setLayout(new GridLayout(2, false));

			Label label = new Label(tmp, SWT.NONE);
			label.setImage(AbstractUIPlugin.imageDescriptorFromPlugin(
					Activator.PLUGIN_ID, IImageConstant.SEARCH).createImage());

			tabST = new Text(tmp, SWT.SINGLE | SWT.BORDER | SWT.SEARCH
					| SWT.ICON_CANCEL | SWT.ICON_SEARCH);
			if ((tabST.getStyle() & SWT.ICON_SEARCH) != 0) {
				label.setVisible(false);
			}
			tabST.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
			tabST.setMessage(CustomString.FILTER_TEXT);
			tabST.addModifyListener(new ModifyListener() {
				@Override
				public void modifyText(ModifyEvent e) {
					tabTextChanged();
				}
			});
		}
	}

	// toolbar//////////////////
	protected void createToolBar(Composite parent) {
		Composite comp = new Composite(parent, SWT.BORDER);
		GridData gd = new GridData(SWT.FILL, SWT.FILL, true, false);
		comp.setLayoutData(gd);
		GridLayout cgl = new GridLayout(7, false);
		cgl.verticalSpacing = 5;
		comp.setLayout(cgl);

		{
			Label btl = new Label(comp, SWT.LEFT);
			GridData btlgd = new GridData(SWT.LEFT, SWT.CENTER, false, false);
			btlgd.horizontalSpan = 2;
			btl.setLayoutData(btlgd);
			btl.setText(CustomString.BEGIN_TIME);

			Label etl = new Label(comp, SWT.LEFT);
			GridData etlgd = new GridData(SWT.LEFT, SWT.CENTER, false, false);
			etlgd.horizontalSpan = 2;
			etl.setLayoutData(etlgd);
			etl.setText(CustomString.END_TIME);

			Label m = new Label(comp, SWT.NONE);
			m.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

			Label rtl = new Label(comp, SWT.NONE);
			GridData rtlgd = new GridData(SWT.LEFT, SWT.CENTER, false, false);
			rtl.setLayoutData(rtlgd);
			rtl.setText(CustomString.RECORD_TYPE);

			Button no = new Button(comp, SWT.NONE);
			GridData ngd = new GridData(110, 25);
			ngd.horizontalAlignment = SWT.RIGHT;
			no.setLayoutData(ngd);
			no.setVisible(false);
		}
		{
			dateBegin = new DateTime(comp, SWT.DATE | SWT.SHADOW_IN
					| SWT.BORDER);
			timeBegin = new DateTime(comp, SWT.TIME | SWT.BORDER);

			dateEnd = new DateTime(comp, SWT.DATE | SWT.SHADOW_IN | SWT.BORDER);
			timeEnd = new DateTime(comp, SWT.TIME | SWT.BORDER);

			Label m = new Label(comp, SWT.NONE);
			m.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

			recordsTypeC = new Combo(comp, SWT.READ_ONLY);
			GridData rtcgd = new GridData(SWT.LEFT, SWT.CENTER, false, false);
			recordsTypeC.setLayoutData(rtcgd);
			recordsTypeC.setItems(recordsTypeS);
			recordsTypeC.select(0);

			Button yes = new Button(comp, SWT.NONE);
			GridData ygd = new GridData(90, 25);
			ygd.horizontalAlignment = SWT.RIGHT;
			yes.setLayoutData(ygd);
			yes.setText(CustomString.SURE);
			yes.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					okPressed();
				}
			});
		}
	}

	@SuppressWarnings("deprecation")
	public static long getTime(DateTime date, DateTime time) {
		Date dat = new Date();
		dat.setYear(date.getYear());
		dat.setMonth(date.getMonth());
		dat.setDate(date.getDay());
		dat.setHours(time.getHours());
		dat.setMinutes(time.getMinutes());
		dat.setSeconds(time.getMinutes());

		return dat.getTime();
	}

	public long getBeginTime() {
		return getTime(dateBegin, timeBegin);
	}

	public String getRecordsType() {
		return recordsTypeC.getText();
	}

	public long getEndTime() {
		return getTime(dateEnd, timeEnd);
	}

	// table//////////////////
	protected void createTable(Composite parent) {
		Composite comp = new Composite(parent, SWT.NONE);
		GridData gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		comp.setLayoutData(gd);
		comp.setLayout(new GridLayout());

		tabPF = new TablePatternFilter();

		tabV = new TableViewer(comp, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		tabV.setContentProvider(contentProvider);
		tabV.setLabelProvider(labelProvider);
		tabV.addFilter(tabPF);

		final Table table = tabV.getTable();
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		for (int idx = 0; idx < tabHeaderS.length; ++idx) {
			TableColumn tc = new TableColumn(table, SWT.LEFT);
			tc.setText(tabHeaderS[idx]);
			tc.setWidth(50);
		}
		if (hasIdx) {
			TableColumn tc = table.getColumn(0);
			tc.setResizable(false);
		}
		table.addListener(SWT.Resize, new Listener() {

			@Override
			public void handleEvent(Event event) {
				int width = table.getSize().x / table.getColumnCount() + 1;
				if (hasIdx) {
					width = (table.getSize().x - 50)
							/ (table.getColumnCount() - 1) + 1;
				}

				for (int idx = hasIdx ? 1 : 0; idx < table.getColumnCount(); ++idx) {
					table.getColumn(idx).setWidth(width);
				}
			}
		});
	}

	protected void tabTextChanged() {
		String text = tabST.getText();
		tabPF.setPattern(text);

		tabV.getControl().setRedraw(false);
		tabV.refresh(true);
		if (hasIdx) {
			ItemsNumberForTable.setItemsNumber(tabV);
		}
		tabV.getControl().setRedraw(true);
	}

	public class TablePatternFilter extends ViewerFilter {
		/**
		 * The string pattern matcher used for this pattern filter.
		 */
		@SuppressWarnings("restriction")
		private StringMatcher matcher;

		@Override
		public boolean select(Viewer viewer, Object parentElement,
				Object element) {
			return isAnyColumnVisible(viewer, element, ((TableViewer) viewer)
					.getTable().getColumnCount());
		}

		private boolean isAnyColumnVisible(Viewer viewer, Object rowElement,
				int columnCount) {
			ITableLabelProvider tableLabelProvider = ((ITableLabelProvider) ((StructuredViewer) viewer)
					.getLabelProvider());
			if (tableLabelProvider == null) {
				return false;
			}

			for (int i = 0; i < columnCount; i++) {
				String lableStringText = tableLabelProvider.getColumnText(
						rowElement, i);
				if (lableStringText != null && match(lableStringText)) {
					return true;
				}
			}

			return false;
		}

		/**
		 * The pattern string for which this filter should select elements in
		 * the viewer.
		 * 
		 * @param patternString
		 */
		@SuppressWarnings("restriction")
		public void setPattern(String patternString) {
			if (patternString == null || patternString.equals("")) { //$NON-NLS-1$
				matcher = null;
			} else {
				matcher = new StringMatcher(
						"*" + patternString + "*", true, false);//$NON-NLS-1$
			}
		}

		/**
		 * Answers whether the given String matches the pattern.
		 * 
		 * @param string
		 *            the String to test
		 * 
		 * @return whether the string matches the pattern
		 */
		@SuppressWarnings("restriction")
		private boolean match(String string) {
			if (matcher == null) {
				return true;
			}
			return matcher.match(string);
		}
	}

	// status bar//////////////////
	protected void createStatusBar(Composite parent) {
		Composite comp = new Composite(parent, SWT.NONE);
		GridData gd = new GridData(SWT.FILL, SWT.FILL, true, false);
		comp.setLayoutData(gd);
		GridLayout cgl = new GridLayout();
		cgl.marginWidth = 2;
		cgl.marginHeight = 0;
		cgl.numColumns = 15;
		comp.setLayout(cgl);

		{
			gridInfo = new CLabel(comp, SWT.LEFT);
			gridInfo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		}

		{
			{
				Label label = new Label(comp, SWT.SEPARATOR);
				GridData gridData = new GridData();
				gridData.heightHint = 14;
				label.setLayoutData(gridData);
			}

			{
				cbPageItems = new CCombo(comp, SWT.BORDER);
				GridData gridData = new GridData();
				gridData.widthHint = 50;
				cbPageItems.setLayoutData(gridData);
				cbPageItems.setItems(new String[] { "10", "20", "50", "100",
						"200", "500", "1000" });
				cbPageItems.select(1);
				cbPageItems.addModifyListener(new ModifyListener() {
					Display display = Display.getDefault();
					Shell shell = cbPageItems.getShell();
					ToolTip toolTip = new ToolTip(shell, SWT.BALLOON
							| SWT.ICON_WARNING);

					public void modifyText(ModifyEvent e) {
						if (list == null || list.size() == 0) {
							return;
						}
						String string = cbPageItems.getText();
						String message = null;
						try {
							int value = Integer.parseInt(string);
							int maximum = totalItems;
							int minimum = 1;
							if (value > maximum) {
								message = "Current input is greater than the maximum limit ("
										+ maximum + ")";
							} else if (value < minimum) {
								message = "Current input is less than the minimum limit ("
										+ minimum + ")";
							}
						} catch (Exception ex) {
							message = "Current input is not numeric";
						}
						if (message != null) {
							cbPageItems.setForeground(display
									.getSystemColor(SWT.COLOR_RED));
							Rectangle rect = cbPageItems.getBounds();
							GC gc = new GC(cbPageItems);
							Point pt = gc.textExtent(string);
							gc.dispose();
							toolTip.setLocation(display.map(cbPageItems, null,
									pt.x, rect.height));
							toolTip.setMessage(message);
							toolTip.setVisible(true);
						} else {
							toolTip.setVisible(false);
							cbPageItems.setForeground(null);
							pageItems = Integer.parseInt(cbPageItems.getText());
							pageCount = totalItems / pageItems;
							if (pageIndex > pageCount) {
								pageIndex = pageCount;
							}
							refresh();
						}
					}
				});
				cbPageItems.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						if (list == null || list.size() == 0) {
							return;
						}
						pageItems = Integer.parseInt(cbPageItems.getText());
						pageCount = totalItems / pageItems;
						if (pageIndex > pageCount) {
							pageIndex = pageCount;
						}
						refresh();
					}
				});
			}

			{
				Label label = new Label(comp, SWT.NONE);
				label.setText(CustomString.ITEM_PER_PAGE);
			}

		}

		{
			{
				Label label = new Label(comp, SWT.SEPARATOR);
				GridData gridData = new GridData();
				gridData.heightHint = 14;
				label.setLayoutData(gridData);
			}
			{
				cmdFirst = new CLabel(comp, SWT.NONE);
				cmdFirst.addMouseListener(new MouseAdapter() {
					public void mouseUp(MouseEvent e) {
						pageIndex = 1;
						refresh();
					}
				});
			}

			{
				cmdPrevious = new CLabel(comp, SWT.NONE);
				cmdPrevious.addMouseListener(new MouseAdapter() {
					public void mouseUp(MouseEvent e) {
						pageIndex--;
						if (pageIndex <= 0) {
							pageIndex = 1;
						}
						refresh();
					}
				});
			}

			{
				cmdNext = new CLabel(comp, SWT.NONE);
				cmdNext.addMouseListener(new MouseAdapter() {
					public void mouseUp(MouseEvent e) {
						pageIndex++;
						if (pageIndex >= pageCount) {
							pageIndex = pageCount;
						}
						refresh();
					}
				});
			}

			{
				cmdLast = new CLabel(comp, SWT.NONE);
				cmdLast.setImage(AbstractUIPlugin.imageDescriptorFromPlugin(
						Application.PLUGIN_ID, IImageConstant.END_DIS)
						.createImage());
				cmdLast.addMouseListener(new MouseAdapter() {
					public void mouseUp(MouseEvent e) {
						pageIndex = pageCount;
						refresh();
					}
				});
			}

			setEnable(1);
			setEnable(2);
			setEnable(3);
			setEnable(4);
		}

		{
			{
				Label label = new Label(comp, SWT.SEPARATOR);
				GridData gridData = new GridData();
				gridData.heightHint = 14;
				label.setLayoutData(gridData);
			}

			{
				Label label = new Label(comp, SWT.NONE);
				label.setText(CustomString.GOTO);
			}

			{

				cbPageJump = new CCombo(comp, SWT.READ_ONLY);
				GridData gridData = new GridData();
				gridData.widthHint = 14;
				cbPageJump.setLayoutData(gridData);
				cbPageJump.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						pageIndex = Integer.parseInt(cbPageJump.getText());
						refresh();
					}
				});
			}
			{
				Label label = new Label(comp, SWT.NONE);
				label.setText(CustomString.PAGE);
			}
		}
	}

	protected void setStatusInfo() {
		gridInfo.setText(CustomString.THE + pageIndex + CustomString.PAGE
				+ ", " + CustomString.TOTAL + pageCount + CustomString.PAGE
				+ ", " + totalItems + CustomString.ITEM);
		Logger.getLogger("global").info(gridInfo.getText());
	}

	private void setCmdIcons() {
		if (pageIndex == 1) {
			setEnable(1);
		} else {
			setAble(1);
		}
		if (pageIndex - 1 >= 1) {
			setAble(2);
		} else {
			setEnable(2);
		}
		if (pageIndex + 1 <= pageCount) {
			setAble(3);
		} else {
			setEnable(3);
		}
		if (pageIndex == pageCount) {
			setEnable(4);
		} else {
			setAble(4);
		}
	}

	private void setPageJump() {
		cbPageJump.removeAll();
		for (int i = 1; i <= pageCount; ++i) {
			cbPageJump.add(i + "");
		}
	}

	protected void setEnable(int idx) {
		if (idx == 1) {
			cmdFirst.setEnabled(false);
			cmdFirst.setImage(AbstractUIPlugin.imageDescriptorFromPlugin(
					Application.PLUGIN_ID, IImageConstant.BEGIN_DIS)
					.createImage());
		} else if (idx == 2) {
			cmdPrevious.setEnabled(false);
			cmdPrevious.setImage(AbstractUIPlugin.imageDescriptorFromPlugin(
					Application.PLUGIN_ID, IImageConstant.BACK_DIS)
					.createImage());
		} else if (idx == 3) {
			cmdNext.setEnabled(false);
			cmdNext.setImage(AbstractUIPlugin.imageDescriptorFromPlugin(
					Application.PLUGIN_ID, IImageConstant.FORWARD_DIS)
					.createImage());
		} else if (idx == 4) {
			cmdLast.setEnabled(false);
			cmdLast.setImage(AbstractUIPlugin.imageDescriptorFromPlugin(
					Application.PLUGIN_ID, IImageConstant.END_DIS)
					.createImage());
		}

	}

	protected void setAble(int idx) {
		if (idx == 1) {
			cmdFirst.setEnabled(true);
			cmdFirst.setImage(AbstractUIPlugin.imageDescriptorFromPlugin(
					Application.PLUGIN_ID, IImageConstant.BEGIN).createImage());
		} else if (idx == 2) {
			cmdPrevious.setEnabled(true);
			cmdPrevious.setImage(AbstractUIPlugin.imageDescriptorFromPlugin(
					Application.PLUGIN_ID, IImageConstant.BACK).createImage());
		} else if (idx == 3) {
			cmdNext.setEnabled(true);
			cmdNext.setImage(AbstractUIPlugin.imageDescriptorFromPlugin(
					Application.PLUGIN_ID, IImageConstant.FORWARD)
					.createImage());
		} else if (idx == 4) {
			cmdLast.setEnabled(true);
			cmdLast.setImage(AbstractUIPlugin.imageDescriptorFromPlugin(
					Application.PLUGIN_ID, IImageConstant.END).createImage());
		}

	}

	// //////////////////
}
