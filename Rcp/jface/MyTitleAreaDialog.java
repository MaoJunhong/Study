package net.atp.trader.client.test.Dialog;

import org.eclipse.jface.dialogs.DialogTray;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class MyTitleAreaDialog extends TitleAreaDialog {

	public MyTitleAreaDialog(Shell parentShell) {
		super(parentShell);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.dialogs.TitleAreaDialog#createDialogArea(org.eclipse
	 * .swt.widgets.Composite)
	 */
	protected Control createDialogArea(Composite parent) {
		// TitleArea中的Title
		setTitle("My TitleAreaDialog");
		// TitleArea中的Message
		setMessage("This is a simple TitleAreaDialog example.");
		// TitleArea中的Image
		setTitleImage(Display.getDefault().getSystemImage(SWT.ICON_INFORMATION));

		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		container.setLayout(new FillLayout());

		final Button openTrayButton = new Button(container, SWT.NONE);
		openTrayButton.setText("Open Tray");
		final Button closeTrayButton = new Button(container, SWT.NONE);
		closeTrayButton.setText("Close Tray");
		closeTrayButton.setEnabled(false);
		openTrayButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				// this method is from TrayDialog
				openTray(new MyDialogTray());
				openTrayButton.setEnabled(false);
				closeTrayButton.setEnabled(true);
			}
		});
		closeTrayButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				// this method is from TrayDialog
				closeTray();
				openTrayButton.setEnabled(true);
				closeTrayButton.setEnabled(false);
			}
		});

		return area;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.dialogs.Dialog#createButtonsForButtonBar(org.eclipse
	 * .swt.widgets.Composite)
	 */
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.dialogs.TitleAreaDialog#getInitialSize()
	 */
	protected Point getInitialSize() {
		return new Point(800, 375);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets
	 * .Shell)
	 */
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		// Dialog Title
		newShell.setText("Test TitleAreaDialog Title");
		// Dialog Icon
		newShell.setImage(Display.getDefault().getSystemImage(
				SWT.ICON_INFORMATION));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.dialogs.TrayDialog#createButtonBar(org.eclipse.swt.
	 * widgets.Composite)
	 */
	protected Control createButtonBar(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 0;
		layout.marginHeight = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_MARGIN);
		layout.marginWidth = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_MARGIN);
		layout.verticalSpacing = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_SPACING);
		layout.horizontalSpacing = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_SPACING);
		composite.setLayout(layout);
		composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		if (isHelpAvailable()) {
			createHelpControl(composite);
		}
		createButton(composite, 1, "Import", false).addSelectionListener(
				new SelectionAdapter() {
					public void widgetSelected(SelectionEvent e) {
						MessageDialog.openInformation(Display.getDefault()
								.getActiveShell(), "Information",
								"\"Import\" button has not been implemented.");
					}
				});
		createButton(composite, 2, "Export", false).addSelectionListener(
				new SelectionAdapter() {
					public void widgetSelected(SelectionEvent e) {
						MessageDialog.openInformation(Display.getDefault()
								.getActiveShell(), "Information",
								"\"Export\" button has not been implemented.");
					}
				});
		createButton(composite, 3, "Other", false).addSelectionListener(
				new SelectionAdapter() {
					public void widgetSelected(SelectionEvent e) {
						MessageDialog.openInformation(Display.getDefault()
								.getActiveShell(), "Information",
								"\"Other\" button has not been implemented.");
					}
				});
		Label filler = new Label(composite, SWT.NONE);
		filler.setLayoutData(new GridData(GridData.FILL_HORIZONTAL
				| GridData.GRAB_HORIZONTAL));
		layout.numColumns++;
		super.createButtonsForButtonBar(composite);
		return composite;
	}

	@Override
	public boolean isHelpAvailable() {
		return true;
	}

	public static void main(String[] args) {
		new MyTitleAreaDialog(new Shell()).open();
	}

	class MyDialogTray extends DialogTray {
		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.eclipse.jface.dialogs.DialogTray#createContents(org.eclipse.swt
		 * .widgets .Composite)
		 */
		protected Control createContents(Composite parent) {
			Composite container = new Composite(parent, SWT.NONE);
			final GridLayout gridLayout = new GridLayout();
			gridLayout.numColumns = 2;
			container.setLayout(gridLayout);
			final Label label = new Label(container, SWT.NONE);
			label.setText("Name:");
			final Text text = new Text(container, SWT.BORDER);
			text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

			return container;
		}
	}
}
