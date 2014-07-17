/*
 * This class is part of the book "iText in Action - 2nd Edition"
 * written by Bruno Lowagie (ISBN: 9781935182610)
 * For more info, go to: http://itextpdf.com/examples/
 * This example only works with the AGPL version of iText.
 */

package basicblocks;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import utils.ConnectionFactory;
import utils.MyFontFactory;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * Writes a list of countries to a PDF file.
 */
public class DirectorPhrases {

	/** The resulting PDF file. */
	public static final String RESULT = "director_phrases.pdf";

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
		new DirectorPhrases().createPdf(RESULT);
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
		PdfWriter.getInstance(document, new FileOutputStream(filename));
				//.setInitialLeading(16);
		// step 3
		document.open();
		// step 4

		// database connection and statement
		Connection conn = ConnectionFactory.getConnection();
		PreparedStatement pre = conn
				.prepareStatement("SELECT name, given_name FROM film_director ORDER BY name, given_name");
		pre.execute();
		ResultSet rs = pre.getResultSet();
		while (rs.next()) {
			document.add(createDirectorPhrase(rs));
			//document.add(Chunk.NEWLINE);
		}
		pre.close();
		conn.close();

		// When no parameters are passed, the default leading = 16
		Phrase phrase0 = new Phrase();
		Phrase phrase1 = new Phrase("this is a phrase");
		// In this example the leading is passed as a parameter
		Phrase phrase2 = new Phrase(160, "this is a phrase with leading 160");
		// When a Font is passed (explicitly or embedded in a chunk), the
		// default leading = 1.5 * size of the font
		Phrase phrase3 = new Phrase(
				"this is a phrase with a red, normal font Courier, size 12",
				FontFactory.getFont(FontFactory.COURIER, 12, Font.NORMAL));
		Phrase phrase4 = new Phrase(new Chunk("this is a phrase"));
		Phrase phrase5 = new Phrase(18, new Chunk("this is a phrase",
				FontFactory.getFont(FontFactory.HELVETICA, 16, Font.BOLD)));
		document.add(phrase0);
		document.add(Chunk.NEWLINE);
		document.add(phrase1);
		document.add(Chunk.NEWLINE);
		document.add(phrase2);
		document.add(Chunk.NEWLINE);
		document.add(phrase3);
		document.add(Chunk.NEWLINE);
		document.add(phrase4);
		document.add(Chunk.NEWLINE);
		document.add(phrase5);

		// step 5
		document.close();
	}

	public Phrase createDirectorPhrase(ResultSet rs)
			throws UnsupportedEncodingException, SQLException {
		Phrase director = new Phrase();
		director.add(new Chunk(new String(rs.getBytes("name"), "UTF-8"),
				MyFontFactory.BOLD_UNDERLINED));
		director.add(new Chunk(",", MyFontFactory.BOLD_UNDERLINED));
		director.add(new Chunk(" ", MyFontFactory.NORMAL));
		director.add(new Chunk(new String(rs.getBytes("given_name"), "UTF-8"),
				MyFontFactory.NORMAL));
		director.add(Chunk.NEWLINE);
		return director;
	}

}