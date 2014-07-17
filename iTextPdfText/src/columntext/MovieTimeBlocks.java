/*
 * This class is part of the book "iText in Action - 2nd Edition"
 * written by Bruno Lowagie (ISBN: 9781935182610)
 * For more info, go to: http://itextpdf.com/examples/
 * This example only works with the AGPL version of iText.
 */

package columntext;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.List;

import utils.ConnectionFactory;
import utils.PojoFactory;
import beans.Screening;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.html.WebColors;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * Draws a time table to the direct content using lines and simple shapes,
 * adding blocks representing a movies.
 */
public class MovieTimeBlocks extends MovieTimeTable {

	/** The resulting PDF. */
	public static final String RESULT = "time_blocks.pdf";

	/**
	 * Creates a PDF document.
	 * 
	 * @param filename
	 *            the path to the new PDF document
	 * @throws Exception
	 */
	public void createPdf(String filename) throws Exception {
		// step 1
		Document document = new Document(PageSize.A4.rotate());
		// step 2
		PdfWriter writer = PdfWriter.getInstance(document,
				new FileOutputStream(filename));
		// step 3
		document.open();
		// step 4
		PdfContentByte over = writer.getDirectContent();
		PdfContentByte under = writer.getDirectContentUnder();
		try {
			Connection connection = ConnectionFactory.getConnection();
			locations = PojoFactory.getLocations(connection);
			List<Date> days = PojoFactory.getDays(connection);
			List<Screening> screenings;
			for (Date day : days) {
				drawTimeTable(under);
				drawTimeSlots(over);
				screenings = PojoFactory.getScreenings(connection, day);
				for (Screening screening : screenings) {
					drawBlock(screening, under, over);
				}
				document.newPage();
			}
			connection.close();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			document.add(new Paragraph("Database error: " + sqle.getMessage()));
		}
		// step 5
		document.close();
	}

	/**
	 * Draws a colored block on the time table, corresponding with the screening
	 * of a specific movie.
	 * 
	 * @param screening
	 *            a screening POJO, contains a movie and a category
	 * @param under
	 *            the canvas to which the block is drawn
	 */
	protected void drawBlock(Screening screening, PdfContentByte under,
			PdfContentByte over) {
		under.saveState();
		BaseColor color = WebColors.getRGBColor("#"
				+ screening.getMovie().getEntry().getCategory().getColor());
		under.setColorFill(color);
		Rectangle rect = getPosition(screening);
		under.rectangle(rect.getLeft(), rect.getBottom(), rect.getWidth(),
				rect.getHeight());
		under.fill();
		over.rectangle(rect.getLeft(), rect.getBottom(), rect.getWidth(),
				rect.getHeight());
		over.stroke();
		under.restoreState();
	}

	/** The "offset time" for our calendar sheets. */
	public static final long TIME930 = Time.valueOf("09:30:00").getTime();

	/** The width of one minute. */
	public static final float MINUTE = WIDTH_TIMESLOT / 30f;

	/** A list containing all the locations. */
	protected List<String> locations;

	/**
	 * Calculates the position of a rectangle corresponding with a screening.
	 * 
	 * @param screening
	 *            a screening POJO, contains a movie
	 * @return a Rectangle
	 */
	protected Rectangle getPosition(Screening screening) {
		float llx, lly, urx, ury;
		long minutesAfter930 = (screening.getTime().getTime() - TIME930) / 60000l;
		llx = OFFSET_LEFT + (MINUTE * minutesAfter930);
		int location = locations.indexOf(screening.getLocation()) + 1;
		lly = OFFSET_BOTTOM + (LOCATIONS - location) * HEIGHT_LOCATION;
		urx = llx + MINUTE * screening.getMovie().getDuration();
		ury = lly + HEIGHT_LOCATION;
		Rectangle rect = new Rectangle(llx, lly, urx, ury);
		return rect;
	}

	/**
	 * Main method creating the PDF.
	 * 
	 * @param args
	 *            no arguments needed
	 * @throws DocumentException
	 * @throws IOException
	 */
	public static void main(String[] args) throws Exception {
		new MovieTimeBlocks().createPdf(RESULT);
	}
}