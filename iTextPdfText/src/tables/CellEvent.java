package tables;

import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPCellEvent;
import com.itextpdf.text.pdf.PdfPTable;

public class CellEvent implements PdfPCellEvent {

	private int duration;

	public CellEvent(int duration) {
		this.duration = duration;
	}

	@Override
	public void cellLayout(PdfPCell cell, Rectangle rect,
			PdfContentByte[] canvas) {
		PdfContentByte cb = canvas[PdfPTable.BACKGROUNDCANVAS];
		cb.saveState();
		if (duration < 90) {
			cb.setRGBColorFill(0x7c, 0xfc, 0x00);
		} else if (duration > 120) {
			cb.setRGBColorFill(0x8b, 0x15, 0x00);
		} else {
			cb.setRGBColorFill(0x9d, 0xae, 0x00);
		}
		cb.rectangle(rect.getLeft(), rect.getBottom(), rect.getWidth()
				* duration / 240, rect.getHeight());
		cb.fill();
		cb.restoreState();
	}

}
