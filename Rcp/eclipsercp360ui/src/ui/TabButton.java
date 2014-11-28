package ui;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.wb.swt.SWTResourceManager;

public class TabButton extends Composite {
	public static final int BTN_STATE_NORMAL = 0;
	public static final int BTN_STATE_HOVER = 1;
	public static final int BTN_STATE_CLICK = 2;
	private Image image;
	private int state;
	private MainUI backgroundDemo;
	private int btnWidth;
	private int btnHeight;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 * @param Imgcount 
	 */
	public TabButton(Composite parent, int style, boolean active, String imgPath,
			int imgCount, String tooltip,MainUI backgroundDemo) {
		super(parent, style);
		this.backgroundDemo=backgroundDemo;
		image = SWTResourceManager.getImage(getClass(), imgPath);
		btnWidth=image.getBounds().width/imgCount;
		btnHeight=image.getBounds().height;
		this.setSize(btnWidth, btnHeight);
		this.setBackgroundMode(SWT.INHERIT_DEFAULT);
		this.setToolTipText(tooltip);
		if (active) {
			state = BTN_STATE_CLICK;
		} else {
			state = BTN_STATE_NORMAL;
		}
		initListener();
	}
	
	public void toNoraml(){
		state = BTN_STATE_NORMAL;
		redraw();
	}

	private void initListener() {
		this.addPaintListener(new PaintListener() {
			@Override
			public void paintControl(PaintEvent e) {
				e.gc.drawImage(image, btnWidth*state, 0,btnWidth,btnHeight,0,0,btnWidth,btnHeight);
			}
		});
		this.addListener(SWT.MouseHover, new Listener() {
			@Override
			public void handleEvent(Event e) {
				if (state == BTN_STATE_NORMAL) {
					state = BTN_STATE_HOVER;
					redraw();
				}
			}
		});
		this.addListener(SWT.MouseDown, new Listener() {
			@Override
			public void handleEvent(Event e) {
				state = BTN_STATE_CLICK;
				redraw();
				backgroundDemo.otherNavBtnToNormal(e.widget);
			}
		});
		this.addListener(SWT.MouseExit, new Listener() {
			@Override
			public void handleEvent(Event e) {
				if (state == BTN_STATE_HOVER) {
					state = BTN_STATE_NORMAL;
					redraw();
				}
			}
		});
		

	}

}
