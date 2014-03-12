package rcp;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

import ui.View;


public class Perspective implements IPerspectiveFactory {

	public void createInitialLayout(IPageLayout layout) {
		layout.setEditorAreaVisible(false);
		layout.addStandaloneView(View.ID, false, IPageLayout.BOTTOM, 0.05f,
				layout.getEditorArea());
	}
}
