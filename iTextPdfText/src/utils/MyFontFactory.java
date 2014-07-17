package utils;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;

public class MyFontFactory {
	public static Font font = new Font(FontFamily.HELVETICA, 6, Font.BOLD,
			BaseColor.WHITE);
	/** A font that will be used in our PDF. */
	public static final Font BOLD_UNDERLINED = new Font(FontFamily.TIMES_ROMAN,
			12, Font.BOLD | Font.UNDERLINE);
	/** A font that will be used in our PDF. */
	public static final Font NORMAL = new Font(FontFamily.TIMES_ROMAN, 12);
}
