/*
 * This class is part of the book "iText in Action - 2nd Edition"
 * written by Bruno Lowagie (ISBN: 9781935182610)
 * For more info, go to: http://itextpdf.com/examples/
 * This example only works with the AGPL version of iText.
 */

package basicblocks;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import utils.ConnectionFactory;
import utils.MyFontFactory;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * Writes a list of countries to a PDF file.
 */
public class CountryChunks {

	/** The resulting PDF file. */
	public static final String RESULT = "country_chunks.pdf";

	/**
	 * Main method.
	 * 
	 * @param args
	 *            no arguments needed
	 * @throws DocumentException
	 * @throws IOException
	 * @throws SQLException
	 */
	public static void main(String[] args) throws Exception {
		new CountryChunks().createPdf(RESULT);
	}

	/**
	 * Creates a PDF document.
	 * 
	 * @param filename
	 *            the path to the new PDF document
	 * @throws DocumentException
	 * @throws IOException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public void createPdf(String filename) throws Exception {
		// step 1
		Document document = new Document();
		// step 2
		PdfWriter.getInstance(document, new FileOutputStream(filename))
				.setInitialLeading(16);
		// step 3
		document.open();
		// step 4

		// database connection and statement
		Connection conn = ConnectionFactory.getConnection();
		PreparedStatement pre = conn
				.prepareStatement("SELECT COUNTRY, ID FROM `FILM_COUNTRY` ORDER BY COUNTRY");
		pre.execute();
		ResultSet rs = pre.getResultSet();
		while (rs.next()) {
			// add a country to the document as a Chunk
			Chunk country = new Chunk(rs.getString("country"));
			country.setBackground(BaseColor.CYAN, 1f, 0.5f, 1f, 1.5f);
			document.add(country);
			document.add(new Chunk(" "));
			Chunk id = new Chunk(rs.getString("id"), MyFontFactory.font);
			// with a background color
			id.setBackground(BaseColor.BLACK, 1f, 0.5f, 1f, 1.5f);
			// and a text rise
			id.setTextRise(6);
			document.add(id);
			document.add(Chunk.NEWLINE);
		}
		pre.close();
		conn.close();

		// step 5
		document.close();
	}
}