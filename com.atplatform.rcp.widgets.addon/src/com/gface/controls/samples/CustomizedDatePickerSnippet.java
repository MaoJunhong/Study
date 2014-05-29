package com.gface.controls.samples;

import java.util.Locale;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import com.gface.date.DatePicker;
import com.gface.date.DatePickerStyle;

public class CustomizedDatePickerSnippet {
	public static void main(String[] args) {

		Display display = Display.getDefault();
		Shell shell = new Shell(display);

		shell.setLayout(new FillLayout());

		int dpStyle = DatePickerStyle.BUTTONS_ON_BOTTOM
				| DatePickerStyle.HIDE_WHEN_NOT_IN_FOCUS |
				// DatePickerStyle.DISABLE_MONTH_BUTTONS |
				// DatePickerStyle.NO_TODAY_BUTTON |
				DatePickerStyle.SINGLE_CLICK_SELECTION
				| DatePickerStyle.TEN_YEARS_BUTTONS
				| DatePickerStyle.WEEKS_STARTS_ON_MONDAY
				| DatePickerStyle.YEAR_BUTTONS | 0;
		// create a date picker component
		DatePicker picker = new DatePicker(shell, SWT.FLAT | SWT.BORDER,
				dpStyle);
		picker.setLocale(Locale.US);
		// picker.setSelectedDateBackgroud(display.getSystemColor(SWT.COLOR_BLACK))
		// ;
		// picker.setSelectedDateForeground(display.getSystemColor(SWT.COLOR_GREEN));
		// picker.setBackground(display.getSystemColor(SWT.COLOR_GREEN));
		// picker.setForeground(display.getSystemColor(SWT.COLOR_BLACK));
		shell.pack();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();

	}
}
