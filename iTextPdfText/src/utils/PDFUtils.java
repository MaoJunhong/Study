package utils;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.draw.LineSeparator;

public class PDFUtils {
	public static void addBlankLine(Document document) throws DocumentException {
		document.add(new Paragraph("\n"));
	}
	
	public static void addLineSeparator(Document document) throws DocumentException {
		Paragraph para = new Paragraph();
		LineSeparator line = new LineSeparator(1, 100, null,
				Element.ALIGN_CENTER, 10);
		para.add(line);
		para.add("\n");
		document.add(para);
	}
	
	public static void addTitleBySize(Document document, String title, int size) throws DocumentException {
		Paragraph para = new Paragraph(title, Util.getCNFontBySize(size));
		para.setAlignment(Element.ALIGN_CENTER);
		document.add(para);
	}
	
	public static void addStringBySize(Document document, String title, int size) throws DocumentException {
		Paragraph para = new Paragraph(title, Util.getCNFontBySize(size));
		para.setAlignment(Element.ALIGN_LEFT);
		document.add(para);
	}

	public static void addBlankPage(Document document) throws DocumentException {
		addBlankPage(document, "This page left blank on purpose");
	}

	public static void addBlankPage(Document document, String hint)
			throws DocumentException {
		document.newPage();
		document.add(new Paragraph(hint));
		document.newPage();
	}
}
