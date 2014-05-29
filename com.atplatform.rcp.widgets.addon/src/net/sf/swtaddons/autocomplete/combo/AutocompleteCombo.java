package net.sf.swtaddons.autocomplete.combo;

import java.util.HashMap;
import java.util.Map;

import net.sf.swtaddons.autocomplete.AutocompleteWidget;

import org.eclipse.jface.fieldassist.ComboContentAdapter;
import org.eclipse.jface.fieldassist.ContentProposalAdapter;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.widgets.Combo;

public abstract class AutocompleteCombo extends AutocompleteWidget {
    private Map<String, Integer> selectionMap;

    private final class ProposalUpdateFocusListener implements FocusListener {
        public void focusGained(FocusEvent e) {
            provider.setProposals(combo.getItems());

            if (selectionMap == null) {
                selectionMap = new HashMap<>();
                String[] items = combo.getItems();
                for (int idx = 0; items != null && idx < items.length; ++idx)
                    selectionMap.put(items[idx], idx);
                
                combo.addDisposeListener(new DisposeListener() {
                    @Override
                    public void widgetDisposed(DisposeEvent e) {
                        selectionMap = null;
                    }
                });
            }
        }

        public void focusLost(FocusEvent e) {
            if (combo != null && !combo.isDisposed() && selectionMap != null) {
                String text = combo.getText();
                Integer selection = selectionMap.get(text);
                if (selection != null && selection >= 0) combo.select(selection);
                System.err.println(combo.getText() + "," + combo.getSelectionIndex());
            }
        }
    }

    protected Combo combo = null;

    public AutocompleteCombo(Combo aCombo) {
        this.combo = aCombo;

        if (combo != null) {
            this.combo.addFocusListener(new ProposalUpdateFocusListener());

            provider = getContentProposalProvider(combo.getItems());
            adapter = new ContentProposalAdapter(combo, new ComboContentAdapter(), provider, getActivationKeystroke(), getAutoactivationChars());
            adapter.setProposalAcceptanceStyle(ContentProposalAdapter.PROPOSAL_REPLACE);
        }
    }

}
