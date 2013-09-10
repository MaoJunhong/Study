// use JFaceResources
package net.atp.trader.client;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

/**
 * color utils
 * 
 * get common color or get rgb color
 * 
 * @author swrd
 * 
 */
public class ColorUtils {

	public enum CommonColor {
		BLACK, RED, GREEN, BLUE, WHITE
	};

	private static Map<CommonColor, Color> cc; /* common color set */
	private static Map<RGB, Color> rgbc; /* rgb color set */

	static {
		cc = new HashMap<CommonColor, Color>();
		cc.put(CommonColor.BLACK, new Color(Display.getDefault(), new RGB(0, 0,
				0)));
		cc.put(CommonColor.RED, new Color(Display.getDefault(), new RGB(255, 0,
				0)));
		cc.put(CommonColor.GREEN, new Color(Display.getDefault(), new RGB(0,
				255, 0)));
		cc.put(CommonColor.BLUE, new Color(Display.getDefault(), new RGB(0, 0,
				255)));
		cc.put(CommonColor.WHITE, new Color(Display.getDefault(), new RGB(255,
				255, 255)));
		// TODO: init map with more common color

		rgbc = new HashMap<RGB, Color>();
	}

	/**
	 * get common color
	 * 
	 * @param key
	 * @return
	 */
	public static Color getColor(RGB key) {
		if (key == null) {
			return getColor(CommonColor.WHITE);
		}
		if (!rgbc.containsKey(key)) {
			rgbc.put(key, new Color(Display.getDefault(), key));
		}
		return rgbc.get(key);
	}

	/**
	 * get rgb color
	 * 
	 * @param key
	 * @return
	 */
	public static Color getColor(CommonColor key) {
		return cc.get(key);
	}

	public static void main(String[] args) {
		Color cc = ColorUtils.getColor(CommonColor.RED);
		System.out.println(cc);
		cc = ColorUtils.getColor(new RGB(0, 0, 0));
		System.out.println(cc);
	}
}
