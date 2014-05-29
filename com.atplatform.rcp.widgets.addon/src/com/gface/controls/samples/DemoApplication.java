package com.gface.controls.samples;

import java.text.DateFormat;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.ColorDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;

import com.gface.custom.SearchBox;
import com.gface.date.DatePicker;
import com.gface.date.DateSelectedEvent;
import com.gface.date.DateSelectionListener;
import com.gface.date.HourSelectionCombo;

public class DemoApplication {

	private static final String FOREGROUND_COLOR_STRING = "Foreground Color:";
	private static final String BACKGROUND_COLOR_STR = "Background Color:";
	private Display display;
	private Text bgText;
	private DatePicker datePicker;
	private Text fgText;
	private Text selectionBackgroundText;
	private Text selectionForegroundText;
	private Text headerBackgroundText;
	private Text headerForegroundText;
	private HourSelectionCombo hourSelectionCombo;

	public DemoApplication() {
	};

	public void initialize() {
		display = Display.getDefault();
		Shell shell = new Shell(display);
		shell.setSize(600, 450);
		shell.setText("GFace Control Set");
		shell.setLayout(new FillLayout());

		TabFolder tabFolder = new TabFolder(shell, SWT.NONE);
		TabItem dpItem = new TabItem(tabFolder, SWT.NONE);
		dpItem.setText("Date Picker");
		dpItem.setControl(createDatePickerSection(tabFolder));
		TabItem hourSelection = new TabItem(tabFolder, SWT.NONE);
		hourSelection.setText("Hour Selection");
		hourSelection.setControl(createHourSelectionSection(tabFolder));

		TabItem searchBoxItem = new TabItem(tabFolder, SWT.NONE);
		searchBoxItem.setControl(createSearchBoxSection(tabFolder));
		searchBoxItem.setText("Search Box");

		shell.pack();
		shell.open();
		shell.setFocus();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}

	private Control createSearchBoxSection(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		composite.setLayout(layout);
		new Label(composite, SWT.NONE)
				.setText("Type in the search box to see how it works");
		SearchBox sb = new SearchBox(composite, SWT.BORDER);
		String items[] = { "Lions", "Tigers", "Bears", "Alpha", "Bravo",
				"Charlie", "Delta", "Echo", "Foxtrot", "Golf", "Hotel",
				"India", "Juliet", "Kilo", "Lima", "Mike", "November", "Oscar",
				"Papa", "Quebec", "Romeo", "Sierra", "Tango", "Uniform",
				"Victor", "Whiskey", "X-Ray", "Yankee", "Zulu" };
		sb.setItems(items);
		return composite;
	}

	private Control createHourSelectionSection(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		composite.setLayout(layout);
		hourSelectionCombo = new HourSelectionCombo(composite, SWT.BORDER);
		GridData gd = new GridData(GridData.VERTICAL_ALIGN_BEGINNING);
		hourSelectionCombo.setLayoutData(gd);
		Composite c = createHourSelectionComboProperties(composite);
		gd = new GridData(GridData.GRAB_HORIZONTAL | GridData.FILL_BOTH);
		c.setLayoutData(gd);
		return composite;

	}

	private Composite createHourSelectionComboProperties(final Composite parent) {
		Composite composite = new Composite(parent, SWT.BORDER);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		composite.setLayout(layout);
		GridData gd = new GridData(GridData.GRAB_HORIZONTAL
				| GridData.FILL_HORIZONTAL);
		new Label(composite, SWT.NONE).setText("Minute interval:");
		final CCombo minutesCombo = new CCombo(composite, SWT.BORDER);
		minutesCombo.setLayoutData(gd);
		minutesCombo.setEditable(false);
		minutesCombo.setText("30");
		String[] intervals = { "5", "10", "15", "30", "60" };
		for (int i = 0; i < intervals.length; i++) {
			minutesCombo.add(intervals[i]);
		}
		minutesCombo.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {
				String s = minutesCombo.getItem(minutesCombo
						.getSelectionIndex());
				hourSelectionCombo.setMinuteInterval(Integer.parseInt(s));
			}

			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

		new Label(composite, SWT.NONE).setText("Min Hour:");
		final Spinner minSpinner = new Spinner(composite, SWT.BORDER);
		minSpinner.setMinimum(0);
		minSpinner.setMaximum(23);
		minSpinner.setLayoutData(gd);

		new Label(composite, SWT.NONE).setText("Max Hour:");
		final Spinner maxSpinner = new Spinner(composite, SWT.BORDER);
		maxSpinner.setMinimum(0);
		maxSpinner.setMaximum(23);
		maxSpinner.setSelection(23);
		maxSpinner.setLayoutData(gd);

		ModifyListener ml = new ModifyListener() {

			public void modifyText(ModifyEvent e) {
				int min = minSpinner.getSelection();
				int max = maxSpinner.getSelection();
				if (max < min) {
					maxSpinner.setSelection(min);
					minSpinner.setSelection(max);
				}
				hourSelectionCombo.setHourRange(minSpinner.getSelection(),
						maxSpinner.getSelection());
			}

		};

		minSpinner.addModifyListener(ml);
		maxSpinner.addModifyListener(ml);

		Composite colorProps = new Composite(composite, SWT.BORDER);
		gd = new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL);
		gd.horizontalSpan = 2;
		colorProps.setLayoutData(gd);
		GridLayout propLayout = new GridLayout();
		colorProps.setLayout(propLayout);
		propLayout.numColumns = 3;
		new Label(colorProps, SWT.NONE).setText(BACKGROUND_COLOR_STR);
		final Text bgText = new Text(colorProps, SWT.BORDER);
		final Button bgButton = new Button(colorProps, SWT.PUSH);
		bgButton.setText("...");
		bgButton.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {
				Color c = chooseColor(parent.getShell());
				if (c != null) {
					hourSelectionCombo.setBackground(c);
					bgText.setBackground(c);
				}
			}

		});
		new Label(colorProps, SWT.NONE).setText(FOREGROUND_COLOR_STRING);
		final Text fgText = new Text(colorProps, SWT.BORDER);
		Button fgButton = new Button(colorProps, SWT.PUSH);
		fgButton.setText("...");
		fgButton.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {
				Color c = chooseColor(parent.getShell());
				if (c != null) {
					hourSelectionCombo.setForeground(c);
					fgText.setBackground(c);
				}
			}

		});

		return composite;
	}

	private Composite createDatePickerSection(final Composite parent) {

		Composite composite = new Composite(parent, SWT.NONE);
		RowLayout layout = new RowLayout();
		composite.setLayout(layout);

		// first row
		datePicker = new DatePicker(composite, SWT.BORDER | SWT.LINE_SOLID);
		// second row
		createPickerProperties(composite);
		return composite;

	}

	private void createPickerProperties(final Composite parent) {
		Composite pickerProperties = new Composite(parent, SWT.BORDER);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		pickerProperties.setLayout(layout);

		GridData gd = new GridData(GridData.GRAB_HORIZONTAL
				| GridData.FILL_HORIZONTAL);
		Label label = new Label(pickerProperties, SWT.NONE);
		label.setText("Selected Date:");
		final Text dateText = new Text(pickerProperties, SWT.BORDER);
		dateText.setEditable(false);
		datePicker.addDateSelectionListener(new DateSelectionListener() {
			public void dateSelected(DateSelectedEvent e) {
				DateFormat df = DateFormat.getDateInstance(DateFormat.LONG,
						datePicker.getLocale());
				dateText.setText(df.format(e.date));
			}
		});
		dateText.setLayoutData(gd);

		createGeneralGroup(pickerProperties);
		createSelectionGroup(pickerProperties);
		createHeaderGroup(pickerProperties);
	}

	private void createHeaderGroup(final Composite parent) {
		Button button;
		Group group = new Group(parent, SWT.NONE);
		group.setText("Header Properties:");
		GridLayout grid = new GridLayout();
		grid.numColumns = 3;
		group.setLayout(grid);
		new Label(group, SWT.NONE).setText(BACKGROUND_COLOR_STR);
		headerBackgroundText = new Text(group, SWT.BORDER);
		headerBackgroundText.setEditable(false);
		button = new Button(group, SWT.PUSH);
		button.setText("...");
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				Color color = chooseColor(parent.getShell());
				if (color != null)
					handleHeaderBackgroundColorChanged(color);
			}
		});

		new Label(group, SWT.NONE).setText(FOREGROUND_COLOR_STRING);
		headerForegroundText = new Text(group, SWT.BORDER);
		headerForegroundText.setEditable(false);
		button = new Button(group, SWT.PUSH);
		button.setText("...");
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				Color color = chooseColor(parent.getShell());
				if (color != null)
					handleHeaderForegroundColorChanged(color);
			}
		});

		GridData gd = new GridData(GridData.FILL_HORIZONTAL
				| GridData.GRAB_HORIZONTAL);
		gd.horizontalSpan = 2;
		group.setLayoutData(gd);
	}

	private void createSelectionGroup(final Composite parent) {
		Button button;
		Group group = new Group(parent, SWT.NONE);
		group.setText("Selection Properties:");
		GridLayout grid = new GridLayout();
		grid.numColumns = 3;
		group.setLayout(grid);
		new Label(group, SWT.NONE).setText(BACKGROUND_COLOR_STR);
		selectionBackgroundText = new Text(group, SWT.BORDER);
		selectionBackgroundText.setEditable(false);
		button = new Button(group, SWT.PUSH);
		button.setText("...");
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				Color color = chooseColor(parent.getShell());
				if (color != null)
					handleSelectionColorChanged(color);
			}
		});

		new Label(group, SWT.NONE).setText(FOREGROUND_COLOR_STRING);
		selectionForegroundText = new Text(group, SWT.BORDER);
		selectionForegroundText.setEditable(false);
		button = new Button(group, SWT.PUSH);
		button.setText("...");
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				Color color = chooseColor(parent.getShell());
				if (color != null)
					handleSelectionForegroundColorChanged(color);
			}
		});
		GridData gd = new GridData(GridData.FILL_HORIZONTAL
				| GridData.GRAB_HORIZONTAL);
		gd.horizontalSpan = 2;
		group.setLayoutData(gd);

	}

	private void createGeneralGroup(final Composite parent) {
		Group group = new Group(parent, SWT.NONE);
		GridLayout grid = new GridLayout();
		grid.numColumns = 3;
		group.setLayout(grid);
		group.setText("General:");
		new Label(group, SWT.NONE).setText(BACKGROUND_COLOR_STR);
		bgText = new Text(group, SWT.BORDER);
		bgText.setEditable(false);
		Button button = new Button(group, SWT.PUSH);
		button.setText("...");
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				Color color = chooseColor(parent.getShell());
				if (color != null)
					handleBackgroundColorChanged(color);
			}
		});

		new Label(group, SWT.NONE).setText(FOREGROUND_COLOR_STRING);
		fgText = new Text(group, SWT.BORDER);
		fgText.setEditable(false);
		button = new Button(group, SWT.PUSH);
		button.setText("...");
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				Color color = chooseColor(parent.getShell());
				if (color != null)
					handleForegroundColorChanged(color);
			}
		});
		GridData gd = new GridData(GridData.FILL_HORIZONTAL
				| GridData.GRAB_HORIZONTAL);
		gd.horizontalSpan = 2;
		group.setLayoutData(gd);

	}

	protected void handleHeaderForegroundColorChanged(Color color) {
		datePicker.setHeaderForeground(color);
		headerForegroundText.setBackground(color);

	}

	protected void handleHeaderBackgroundColorChanged(Color color) {
		datePicker.setHeaderBackground(color);
		// selectionBackgroundText.setText(color.toString());
		headerBackgroundText.setBackground(color);

	}

	protected void handleSelectionForegroundColorChanged(Color color) {
		datePicker.setSelectedDateForeground(color);
		// selectionBackgroundText.setText(color.toString());
		selectionForegroundText.setBackground(color);

	}

	protected void handleSelectionColorChanged(Color color) {
		datePicker.setSelectedDateBackgroud(color);
		// selectionBackgroundText.setText(color.toString());
		selectionBackgroundText.setBackground(color);

	}

	protected void handleForegroundColorChanged(Color color) {
		datePicker.setForeground(color);
		// fgText.setText(color.toString());
		fgText.setBackground(color);
	}

	protected void handleBackgroundColorChanged(Color color) {
		datePicker.setBackground(color);
		// bgText.setText (color.toString());
		bgText.setBackground(color);

	}

	protected Color chooseColor(Shell shell) {
		Color c = null;
		ColorDialog cd = new ColorDialog(shell);
		cd.open();
		RGB rgb = cd.getRGB();
		if (rgb != null)
			c = new Color(display, rgb);
		return c;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new DemoApplication().initialize();
	}
}
