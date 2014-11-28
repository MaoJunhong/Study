package ui;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.wb.swt.SWTResourceManager;

public class SystemButton extends Composite {
	public static final int BTN_STATE_NORMAL = 0;
	public static final int BTN_STATE_HOVER = 1;
	public static final int BTN_STATE_CLICK = 2;
	public static final int BTN_STATE_UP = 3;
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
	public SystemButton(Composite parent, int style,String imgPath,
			int imgCount, String tooltip,MainUI backgroundDemo,Listener clickListener) {
		super(parent, style);
		this.backgroundDemo=backgroundDemo;
		image = SWTResourceManager.getImage(getClass(), imgPath);
		btnWidth=image.getBounds().width/imgCount;
		btnHeight=image.getBounds().height;
		this.setSize(btnWidth, btnHeight);
		this.setBackgroundMode(SWT.INHERIT_DEFAULT);
		this.setToolTipText(tooltip);
		state = BTN_STATE_NORMAL;
		initListener(clickListener);
	}
	
	public void toNoraml(){
		state = BTN_STATE_NORMAL;
		redraw();
	}

	private void initListener(final Listener clickListener) {
		this.addPaintListener(new PaintListener() {
			@Override
			public void paintControl(PaintEvent e) {
				e.gc.drawImage(image, btnWidth*state, 0,btnWidth,btnHeight,0,0,btnWidth,btnHeight);
			}
		});
		
		
		this.addListener(SWT.MouseUp, new Listener() {
			@Override
			public void handleEvent(Event e) {
				state = BTN_STATE_UP;
				redraw();
				if(clickListener!=null){
					clickListener.handleEvent(e);
				}
			}
		});
		this.addListener(SWT.MouseHover, new Listener() {
			@Override
			public void handleEvent(Event e) {
					state = BTN_STATE_HOVER;
					redraw();
			}
		});
		
		this.addListener(SWT.MouseExit, new Listener() {
			@Override
			public void handleEvent(Event e) {
				state = BTN_STATE_NORMAL;
				redraw();
			}
		});
		
		this.addListener(SWT.MouseDown, new Listener() {
			@Override
			public void handleEvent(Event e) {
				state = BTN_STATE_NORMAL;
				redraw();
			}
		});
		
		
		

	}

}
