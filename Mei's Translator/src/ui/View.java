package ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

import translate.Translator;
import dir.DirectoryToken;

public class View extends ViewPart {
	public final static String ID = "Mei's Translator.View";
	private final static String lan_en[] = { "auto", "zh", "en", "jp", "kor",
			"spa", "fra", "th", "ara", "yue" };
	private final static String lan_zh[] = { "�Զ����", "����", "Ӣ��", "����", "����",
			"��������", "����", "̩��", "��������", "����" };

	private DirectoryToken token;

	private Combo srcLan, dstLan;
	private Text srcFile, dstFile;

	private Text srcTxt, dstTxt;

	private Button srcPos, dstPos;
	private Text srcPosTxt, dstPosTxt;

	private Text srcWord, dstWord;
	private Button translateWord, save;

	private Button auto, lastFile, translateTxt, nextFile;

	public View() {
		token = new DirectoryToken();
	}

	@Override
	public void createPartControl(Composite parent) {
		Composite view_parent = parent.getParent().getParent().getParent();
		StackLayout stack_layout = (StackLayout) view_parent.getLayout();
		stack_layout.marginHeight = -1;
		stack_layout.marginWidth = -1;
		view_parent.layout();

		GridLayout layout = new GridLayout();
		parent.setLayout(layout);
		parent.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		SashForm form = new SashForm(parent, SWT.HORIZONTAL | SWT.BORDER);
		form.setLayout(new GridLayout());
		form.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		createSrc(form);
		createDst(form);
		form.setWeights(new int[] { 1, 1 });

		addListener();
	}

	private void createSrc(SashForm form) {
		Composite parent = new Composite(form, SWT.NONE);
		parent.setLayout(new GridLayout());
		{
			Composite comp = new Composite(parent, SWT.NONE);
			GridLayout lo = new GridLayout(3, false);
			comp.setLayout(lo);
			comp.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

			new Label(comp, SWT.NONE).setText("Դ���ԣ�");
			srcLan = new Combo(comp, SWT.READ_ONLY);
			srcLan.setItems(lan_zh);
			srcLan.select(0);

			srcFile = new Text(comp, SWT.SINGLE | SWT.BORDER | SWT.READ_ONLY);
			srcFile.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
					false));
			srcFile.setText("Դ�ļ�����·����");
			// srcFile.setVisible(false);
		}
		{
			srcTxt = new Text(parent, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL
					| SWT.H_SCROLL);
			srcTxt.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
			srcTxt.setText("��Ҫ������ı�");
		}
		{
			Composite comp = new Composite(parent, SWT.NONE);
			GridLayout lo = new GridLayout(3, false);
			comp.setLayout(lo);
			comp.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

			srcWord = new Text(comp, SWT.SINGLE | SWT.BORDER);
			srcWord.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
					false));
			srcWord.setMessage("��Ҫ����ĵ���");

			translateWord = new Button(comp, SWT.NONE);
			translateWord.setText("���뵥��");

			dstWord = new Text(comp, SWT.SINGLE | SWT.BORDER);
			dstWord.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
					false));
		}
		{
			Composite comp = new Composite(parent, SWT.NONE);
			GridLayout lo = new GridLayout(2, false);
			comp.setLayout(lo);
			comp.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

			srcPos = new Button(comp, SWT.NONE);
			srcPos.setText("Դ�ļ���");

			srcPosTxt = new Text(comp, SWT.SINGLE | SWT.BORDER);
			srcPosTxt.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
					false));
		}
	}

	private void createDst(SashForm form) {
		Composite parent = new Composite(form, SWT.NONE);
		parent.setLayout(new GridLayout());
		{
			Composite comp = new Composite(parent, SWT.NONE);
			GridLayout lo = new GridLayout(3, false);
			comp.setLayout(lo);
			comp.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

			new Label(comp, SWT.NONE).setText("Ŀ�����ԣ�");
			dstLan = new Combo(comp, SWT.READ_ONLY);
			dstLan.setItems(lan_zh);
			dstLan.remove(0);
			dstLan.select(1);

			dstFile = new Text(comp, SWT.SINGLE | SWT.BORDER | SWT.READ_ONLY);
			dstFile.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
					false));
			dstFile.setText("Ŀ���ļ�����·����");
			dstFile.setVisible(false);
		}
		{
			dstTxt = new Text(parent, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL
					| SWT.H_SCROLL);
			dstTxt.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		}
		{
			Composite comp = new Composite(parent, SWT.NONE);
			GridLayout lo = new GridLayout(6, false);
			comp.setLayout(lo);
			comp.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

			Button bt = new Button(comp, SWT.NONE);
			bt.setVisible(false);
			bt.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

			auto = new Button(comp, SWT.RADIO);
			auto.setText("�Զ���һ��");

			lastFile = new Button(comp, SWT.NONE);
			lastFile.setText("��һ���ļ�");

			translateTxt = new Button(comp, SWT.NONE);
			translateTxt.setText("�����ı�");

			nextFile = new Button(comp, SWT.NONE);
			nextFile.setText("��һ���ļ�");

			save = new Button(comp, SWT.NONE);
			save.setText("����");
		}
		{
			Composite comp = new Composite(parent, SWT.NONE);
			GridLayout lo = new GridLayout(2, false);
			comp.setLayout(lo);
			comp.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
			// comp.setVisible(false);

			dstPos = new Button(comp, SWT.NONE);
			dstPos.setText("Ŀ���ļ���");

			dstPosTxt = new Text(comp, SWT.SINGLE | SWT.BORDER);
			dstPosTxt.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
					false));
		}
	}

	private void addListener() {
		translateWord.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String src = srcWord.getText();
				if (src.compareTo("") == 0) {
					return;
				}
				String from = lan_en[srcLan.getSelectionIndex()];
				String to = lan_en[dstLan.getSelectionIndex() + 1];
				String dst = Translator.Translate(from, to, src);
				dstWord.setText(dst);
			}
		});

		translateTxt.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String from = lan_en[srcLan.getSelectionIndex()];
				String to = lan_en[dstLan.getSelectionIndex() + 1];
				if (auto.getSelection()) {
					token.translateAll(from, to);
				} else {
					String src = srcTxt.getText();
					if (src.compareTo("") == 0) {
						return;
					}
					String dst = Translator.Translate(from, to, src);
					dstTxt.setText(dst);
				}
			}
		});

		srcPos.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				token.setSrcPos(new DirectoryDialog(Display.getCurrent()
						.getActiveShell()).open());
				srcPosTxt.setText(token.getSrcPos());
				srcTxt.setText(token.getCurrentFile());
				srcFile.setText(token.getCurrentFileName());
			}
		});

		dstPos.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				token.setDstPos(new DirectoryDialog(Display.getCurrent()
						.getActiveShell()).open());
				dstPosTxt.setText(token.getDstPos());
			}
		});

		save.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				token.save(dstTxt.getText());
			}
		});

		lastFile.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				srcTxt.setText(token.getLastFile());
			}
		});

		nextFile.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				srcTxt.setText(token.getNextFile());
			}
		});
	}

	@Override
	public void setFocus() {

	}

}
