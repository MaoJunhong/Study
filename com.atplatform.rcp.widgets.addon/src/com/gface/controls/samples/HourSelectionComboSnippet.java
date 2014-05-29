package com.gface.controls.samples;

import java.text.SimpleDateFormat;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import com.gface.date.DateSelectedEvent;
import com.gface.date.DateSelectionListener;
import com.gface.date.HourSelectionCombo;

public class HourSelectionComboSnippet {

	/**
	 * @param args
	 */
public static void main(String[] args) {
		Display display = Display.getDefault();
		Shell shell = new Shell(display);
		shell.setLayout(new FillLayout(SWT.VERTICAL));
		HourSelectionCombo hsc = new HourSelectionCombo(shell, SWT.FLAT
				| SWT.BORDER);
//		hsc.setMinuteInterval(30);
//		hsc.setHourRange(9,10);
		hsc.setBackground(display.getSystemColor(SWT.COLOR_RED));
		hsc.setForeground(display.getSystemColor(SWT.COLOR_BLUE));
		final Label l = new Label(shell, SWT.BORDER);
		l.setText(hsc.getTime().toString());
		hsc.addDateSelectionListener(new DateSelectionListener() {

			public void dateSelected(DateSelectedEvent e) {
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
				l.setText(sdf.format(e.date)) ;

			}

		});
		hsc.pack();
		shell.pack();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();

	}
}
