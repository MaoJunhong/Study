package com.gface.controls.samples;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.gface.custom.GLabel;

public class GLabelSnippet {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Display display = Display.getDefault();
		Shell shell = new Shell(display);
		Color gray = display.getSystemColor(SWT.COLOR_GRAY);
		Color blue = display.getSystemColor(SWT.COLOR_BLUE);
		// insert code here
		GridLayout layout = new GridLayout ();
		layout.numColumns = 1 ;
		GridData gd = new GridData (GridData.FILL_BOTH | GridData.GRAB_HORIZONTAL );
		shell.setLayout(layout);
		GLabel label = new GLabel(shell, SWT.NONE);
		label.setText("No style");
		label.setLayoutData(gd);
		label.setBackground(gray);
		label = new GLabel(shell, SWT.RIGHT | SWT.TOP) ;
		label.setText("Top Right");
		label.setLayoutData(gd);
		label.setBackground(blue);
		label = new GLabel(shell, SWT.LEFT | SWT.BOTTOM);
		label.setText("Buttom left");
		label.setLayoutData(gd);
		label.setBackground(gray);
		label = new GLabel(shell, SWT.CENTER | SWT.BOTTOM);
		label.setText("Buttom center");
		label.setLayoutData(gd);
		label.setBackground(blue);
		label = new GLabel(shell, SWT.CENTER | SWT.TOP);
		label.setText("Top center");
		label.setLayoutData(gd);
		label.setBackground(gray);
		label = new GLabel(shell, SWT.CENTER | SWT.HORIZONTAL | SWT.VERTICAL);
		label.setText("Center horizontal and vertical");
		label.setLayoutData(gd);
		label.setBackground(blue);
		
		
		
		shell.setSize(800,600);
//		shell.pack();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		gray.dispose() ;
		blue.dispose() ;
		display.dispose();
	}

}
