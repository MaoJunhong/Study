package com.gface.controls.samples;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.gface.date.DatePickerCombo;
import com.gface.date.DateSelectedEvent;
import com.gface.date.DateSelectionListener;

public class DatePickerComboSnippet {

	public static void main(String[] args) {

		Display display = Display.getDefault();
		Shell shell = new Shell(display);
		shell.setLayout(new FillLayout());
		// create a date picker combo component
		DatePickerCombo dateCombo = new DatePickerCombo(shell, SWT.NONE);
		dateCombo.addDateSelectionListener(new DateSelectionListener(){

			public void dateSelected(DateSelectedEvent e) {
				System.out.println(e.date);
			}
			
		});
		
		// become younger in ten years ;-)
		Calendar calendar = Calendar.getInstance();
		calendar.roll(Calendar.YEAR, -10);
		Date d = calendar.getTime();
		dateCombo.setDate(d);
		
		// set the date format
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		dateCombo.setDateFormat(sdf);
//		DateFormat df = DateFormat.getDateInstance(DateFormat.LONG);
//		dateCombo.setDateFormat(df);
		shell.pack();

		// on GTK, text widgets are by default very narrow
		shell.setSize(150, shell.getSize().y);
		
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
}
