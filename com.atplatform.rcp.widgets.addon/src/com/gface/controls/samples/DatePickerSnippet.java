package com.gface.controls.samples;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.gface.date.DatePicker;

public class DatePickerSnippet {

	public static void main(String[] args) {

		Display display = Display.getDefault();
		Shell shell = new Shell(display);

		shell.setLayout(new FillLayout());

		// create a date picker component
		new DatePicker(shell, SWT.FLAT | SWT.BORDER);
		// picker.setLocale(Locale.US);
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
