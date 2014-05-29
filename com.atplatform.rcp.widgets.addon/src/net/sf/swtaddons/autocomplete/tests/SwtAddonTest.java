package net.sf.swtaddons.autocomplete.tests;

import java.util.ArrayList;
import java.util.List;

import net.sf.swtaddons.autocomplete.combo.AutocompleteComboInput;
import net.sf.swtaddons.autocomplete.combo.AutocompleteComboSelector;
import net.sf.swtaddons.autocomplete.text.AutocompleteTextInput;
import net.sf.swtaddons.autocomplete.text.AutocompleteTextSelector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class SwtAddonTest {
    private static String[] selections = new String[] { "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten" };

    public static void main(String[] args) {
        Display display = new Display();
        Shell shell = new Shell(display);
        shell.setLayout(new GridLayout());

        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            list.add(StringUtils.getRandomString(20));
        }
        selections = list.toArray(new String[] {});
        createPartControl(shell);

        shell.pack();
        shell.open();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) display.sleep();
        }
        display.dispose();
    }

    public static void createPartControl(Composite parent) {

        Composite composite = new Composite(parent, SWT.NONE);
        GridLayout gl = new GridLayout();
        gl.numColumns = 3;
        gl.makeColumnsEqualWidth = true;
        composite.setLayout(gl);
        composite.setBackground(composite.getDisplay().getSystemColor(SWT.COLOR_WHITE));

        // Autocomplete Input Example
        Label label1 = new Label(composite, SWT.NONE);
        label1.setText("Autocomplete Input:");
        label1.setBackground(composite.getDisplay().getSystemColor(SWT.COLOR_WHITE));

        final Combo combo1 = new Combo(composite, SWT.DROP_DOWN);
        combo1.setItems(selections);
        new AutocompleteComboInput(combo1);

        Text text1 = new Text(composite, SWT.SINGLE | SWT.BORDER);
        text1.setText("");
        new AutocompleteTextInput(text1, selections);

        // Autocomplete Selector Example
        Label label2 = new Label(composite, SWT.NONE);
        label2.setText("Autocomplete Selector:");
        label2.setBackground(composite.getDisplay().getSystemColor(SWT.COLOR_WHITE));

        Combo combo2 = new Combo(composite, SWT.DROP_DOWN);
        combo2.setItems(selections);
        combo2.setText("");
        new AutocompleteComboSelector(combo2);

        Text text2 = new Text(composite, SWT.SINGLE | SWT.BORDER);
        text2.setText("");
        new AutocompleteTextSelector(text2, selections);

    }

}