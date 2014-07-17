package utils;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.FontSelector;

public class Util {
	public static String fontName = "font/font.ttf";// or "STSong-Light"
	static String encoding = BaseFont.IDENTITY_H;// or "UniGB-UCS2-H"

	public static FontSelector getFontSelector(BaseColor color) {
		FontSelector selector = new FontSelector();
		Font f1 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12);
		Font f2 = FontFactory
				.getFont(fontName, encoding, BaseFont.EMBEDDED, 12);

		f1.setColor(color);
		f2.setColor(color);

		selector.addFont(f1);
		selector.addFont(f2);

		return selector;
	}

	public static Font getCNFontBySize(int size) {
		return FontFactory.getFont(fontName, encoding, BaseFont.EMBEDDED, size);
	}

	public static Font getCNFontBySize(int size, int style) {
		Font font = FontFactory.getFont(fontName, encoding, BaseFont.EMBEDDED,
				size);
		font.setStyle(style);

		return font;
	}

	public static Font getCNFontBySize(int size, BaseColor color) {
		Font font = FontFactory.getFont(fontName, encoding, BaseFont.EMBEDDED,
				size);
		font.setColor(color);
		return font;
	}

	public static Font getCNFontBySize(int size, BaseColor color, int style) {
		Font font = FontFactory.getFont(fontName, encoding, BaseFont.EMBEDDED,
				size);
		font.setColor(color);
		font.setStyle(style);

		return font;
	}

	public static Paragraph formatIndentParagraph(Paragraph paragraph) {
		paragraph.setFirstLineIndent(18);// 首行缩进
		paragraph.setLeading(18);// 同段内行的间距
		paragraph.setSpacingBefore(13);// 段前距离
		paragraph.setSpacingAfter(13);// 段后距离

		return paragraph;
	}

	public static Phrase getColorPhrase(String str, BaseColor color) {
		Phrase ph = getFontSelector(color).process(str);
		return ph;
	}

}
