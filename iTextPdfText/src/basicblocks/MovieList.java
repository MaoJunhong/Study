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
import utils.FilmFonts;
import utils.PojoFactory;
import beans.Director;
import beans.Movie;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * Writes a list of countries to a PDF file.
 */
public class MovieList {

	/** The resulting PDF file. */
	public static final String RESULT = "movie_list_1.pdf";

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
		new MovieList().createPdf(RESULT);
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
				.prepareStatement("SELECT DISTINCT mc.country_id, c.country, count(*) AS c FROM film_country c, film_movie_country mc WHERE c.id = mc.country_id GROUP BY mc.country_id, country ORDER BY c DESC");
		pre.execute();
		ResultSet rs = pre.getResultSet();
		List list = new List(List.ORDERED);
		// loop over the countries
		while (rs.next()) {
			// create a list item for the country
			ListItem item = new ListItem(String.format("%s: %d movies",
					rs.getString("country"), rs.getInt("c")),
					FilmFonts.BOLDITALIC);
			// create a movie list for each country
			List movielist = new List(List.ORDERED, List.ALPHABETICAL);
			movielist.setLowercase(List.LOWERCASE);
			for (Movie movie : PojoFactory.getMovies(conn,
					rs.getString("country_id"))) {
				ListItem movieitem = new ListItem(movie.getMovieTitle());
				List directorlist = new List(List.UNORDERED);
				for (Director director : movie.getDirectors()) {
					directorlist.add(String.format("%s, %s",
							director.getName(), director.getGivenName()));
				}
				movieitem.add(directorlist);
				movielist.add(movieitem);
			}
			item.add(movielist);
			list.add(item);
		}
		document.add(list);
		// close the statement and the database connection
		pre.close();
		conn.close();

		// step 5
		document.close();
	}
}