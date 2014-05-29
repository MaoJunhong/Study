package net.sf.swtaddons.autocomplete;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.widgets.Combo;

public class AutoCompleteUtils {
    public static void abandonListDown(final Combo combo) {
        combo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.keyCode == SWT.ARROW_DOWN) e.doit = false;
            }
        });
    }
}
