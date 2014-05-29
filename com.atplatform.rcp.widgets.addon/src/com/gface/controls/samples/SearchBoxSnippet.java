package com.gface.controls.samples;

import java.util.ArrayList;
import java.util.List;

import net.sf.swtaddons.autocomplete.tests.StringUtils;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import com.gface.custom.SearchBox;

public class SearchBoxSnippet {
    static String items[] = { "Lions", "Tigers", "Bears", "Alpha", "Bravo", "Charlie", "Delta", "Echo", "Foxtrot", "Golf", "Hotel", "India", "Juliet", "Kilo",
            "Lima", "Mike", "November", "Oscar", "Papa", "Quebec", "Romeo", "Sierra", "Tango", "Uniform", "Victor", "Whiskey", "X-Ray", "Yankee", "Zulu" };

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            list.add(StringUtils.getRandomString(20));
        }
        items = list.toArray(new String[] {});
        Display display = Display.getDefault();
        Shell shell1 = new Shell(display);
        shell1.setLayout(new GridLayout());
        shell1.setText("SearchBox");
        shell1.setLocation(400, 150);
        Label l = new Label(shell1, SWT.NORMAL);
        l.setText("Click any key to open list ...");
        SearchBox sb = new SearchBox(shell1, SWT.BORDER);
        sb.setItems(items);
        shell1.pack();
        shell1.open();
        while (!shell1.isDisposed()) {
            if (!display.readAndDispatch()) display.sleep();
        }
        display.dispose();
    }
}
