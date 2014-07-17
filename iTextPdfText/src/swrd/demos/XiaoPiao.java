/*
 * This class is part of the book "iText in Action - 2nd Edition"
 * written by Bruno Lowagie (ISBN: 9781935182610)
 * For more info, go to: http://itextpdf.com/examples/
 * This example only works with the AGPL version of iText.
 */

package swrd.demos;

import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * First iText example: Hello World.
 */
public class XiaoPiao {

	/** Path to the resulting PDF file. */
	public static final String RESULT = "xiaopiao.pdf";

	/**
	 * Creates a PDF file: hello.pdf
	 * 
	 * @param args
	 *            no arguments needed
	 */
	public static void main(String[] args) throws DocumentException,
			IOException {
		new XiaoPiao().createPdf(RESULT);
	}

	/**
	 * Creates a PDF document.
	 * 
	 * @param filename
	 *            the path to the new PDF document
	 * @throws DocumentException
	 * @throws IOException
	 */
	public void createPdf(String filename) throws DocumentException,
			IOException {
		// step 1
		Rectangle pagesize = new Rectangle(216f, 720f);
		Document document = new Document(pagesize, 36f, 36f, 36f, 36f);
		// step 2
		PdfWriter.getInstance(document, new FileOutputStream(filename));
		// step 3
		document.open();
		// step 4
		Font bf_12 = FontFactory.getFont("font/font.ttf", BaseFont.IDENTITY_H,
				BaseFont.EMBEDDED, 12);
		Font bf_8 = FontFactory.getFont("font/font.ttf", BaseFont.IDENTITY_H,
				BaseFont.EMBEDDED, 8);
		PdfPTable table = new PdfPTable(new float[] { 4, 3, 2, 2 });
		table.setWidthPercentage(100f);

		PdfPCell cell = new PdfPCell(new Phrase("人人乐超市", bf_12));
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		cell.setBorderWidth(0);
		cell.setColspan(4);
		cell.setRowspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase("欢迎光临", bf_8));
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		cell.setBorderWidth(0);
		cell.setColspan(4);
		table.addCell(cell);

		table.addCell(getLineCell());

		cell = new PdfPCell(new Phrase("销售单号:", bf_8));
		cell.setBorderWidth(0);
		cell.setColspan(1);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase("XS090110020001", bf_8));
		cell.setBorderWidth(0);
		cell.setColspan(3);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase("顾客名称:", bf_8));
		cell.setBorderWidth(0);
		cell.setColspan(1);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase("普通顾客", bf_8));
		cell.setBorderWidth(0);
		cell.setColspan(3);
		table.addCell(cell);

		table.addCell(getLineCell());

		cell = new PdfPCell(new Phrase("商品名称", bf_8));
		cell.setBorderWidth(0);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase("折后价", bf_8));
		cell.setBorderWidth(0);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase("数量", bf_8));
		cell.setBorderWidth(0);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase("金额", bf_8));
		cell.setBorderWidth(0);
		table.addCell(cell);

		table.addCell(getLineCell());

		for (int i = 0; i < 15; ++i) {
			cell = new PdfPCell(new Phrase("4984658 统一方便面", bf_8));
			cell.setBorderWidth(0);
			cell.setColspan(4);
			table.addCell(cell);
			cell = new PdfPCell(new Phrase("6468431258", bf_8));
			cell.setBorderWidth(0);
			table.addCell(cell);
			cell = new PdfPCell(new Phrase("13.00", bf_8));
			cell.setBorderWidth(0);
			table.addCell(cell);
			cell = new PdfPCell(new Phrase("1", bf_8));
			cell.setBorderWidth(0);
			table.addCell(cell);
			cell = new PdfPCell(new Phrase("13.00", bf_8));
			cell.setBorderWidth(0);
			table.addCell(cell);
		}

		table.addCell(getLineCell());

		cell = new PdfPCell(new Phrase("消费10项, 折后合计130.0元", bf_8));
		cell.setColspan(4);
		cell.setBorderWidth(0);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase("原价合计: 130.0元, 为您节省: 0.0元", bf_8));
		cell.setColspan(4);
		cell.setBorderWidth(0);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase("现金付款: 130.0元, 找零: 0.00", bf_8));
		cell.setColspan(4);
		cell.setBorderWidth(0);
		table.addCell(cell);

		table.addCell(getLineCell());

		cell = new PdfPCell(new Phrase("收银员:", bf_8));
		cell.setBorderWidth(0);
		cell.setColspan(1);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase("明万历", bf_8));
		cell.setBorderWidth(0);
		cell.setColspan(3);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase("销售时间:", bf_8));
		cell.setBorderWidth(0);
		cell.setColspan(1);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase("2014-07-09 11:03:42", bf_8));
		cell.setBorderWidth(0);
		cell.setColspan(3);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase("凭小票兑换发票", bf_8));
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		cell.setBorderWidth(0);
		cell.setColspan(4);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase("客服电话 079656849512", bf_8));
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		cell.setBorderWidth(0);
		cell.setColspan(4);
		table.addCell(cell);

		document.add(table);

		// step 5
		document.close();
	}

	private PdfPCell getLineCell() {
		PdfPCell cell = new PdfPCell(new Phrase(
				"-----------------------------------"));
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		cell.setVerticalAlignment(PdfPCell.ALIGN_CENTER);
		cell.setBorderWidth(0);
		cell.setColspan(4);
		cell.setPadding(0f);
		cell.setUseAscender(true);
		cell.setUseDescender(false);
		//cell.setLeading(4, 0);
		return cell;
	}
}