package swrd.demos;

import java.io.FileOutputStream;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * Creates a document with outlines (bookmarks) using the Chapter and Section
 * object.
 * 
 * @author blowagie
 */

public class ChapterSection {

	/**
	 * Creates a document with outlines.
	 * 
	 * @param args
	 *            no arguments needed
	 */
	public static void main(String[] args) {

		System.out.println("Chapters and Sections");

		// step 1: creation of a document-object
		Document document = new Document(PageSize.A4, 50, 50, 50, 50);
		try {
			// step 2: we create a writer that listens to the document
			PdfWriter writer = PdfWriter.getInstance(document,
					new FileOutputStream("ChapterSection.pdf"));
			// step 3: we open the document
			writer.setViewerPreferences(PdfWriter.PageModeUseOutlines);
			document.open();
			// step 4: we add content to the document
			// we define some fonts
			Font chapterFont = FontFactory.getFont(FontFactory.HELVETICA, 24,
					Font.NORMAL, BaseColor.RED);
			Font sectionFont = FontFactory.getFont(FontFactory.HELVETICA, 20,
					Font.NORMAL, BaseColor.BLUE);
			Font subsectionFont = FontFactory.getFont(FontFactory.HELVETICA,
					18, Font.BOLD, BaseColor.MAGENTA);
			// we create some paragraphs
			Paragraph blahblah = new Paragraph(
					"blah blah blah blah blah blah blaah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah");
			Paragraph blahblahblah = new Paragraph(
					"blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blaah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah");
			// this loop will create 7 chapters
			for (int i = 1; i < 8; i++) {
				Paragraph cTitle = new Paragraph("This is chapter " + i,
						chapterFont);
				Chapter chapter = new Chapter(cTitle, i);
				// in chapter 4 we change the alignment to ALIGN_JUSTIFIED
				if (i == 4) {
					blahblahblah.setAlignment(Element.ALIGN_JUSTIFIED);
					blahblah.setAlignment(Element.ALIGN_JUSTIFIED);
					chapter.add(blahblah);
				}
				// in chapter 5, the alignment is changed again
				if (i == 5) {
					blahblahblah.setAlignment(Element.ALIGN_CENTER);
					blahblah.setAlignment(Element.ALIGN_RIGHT);
					chapter.add(blahblah);
				}
				// the alignment is changed to ALIGN_JUSTIFIED again
				if (i == 6) {
					blahblahblah.setAlignment(Element.ALIGN_JUSTIFIED);
					blahblah.setAlignment(Element.ALIGN_JUSTIFIED);
				}
				// in every chapter 3 sections will be added
				for (int j = 1; j < 4; j++) {
					Paragraph sTitle = new Paragraph("This is section " + j
							+ " in chapter " + i, sectionFont);
					Section section = chapter.addSection(sTitle, 1);
					// for chapters > 2, the outine isn't open by default
					if (i > 2)
						section.setBookmarkOpen(false);
					// in all chapters except the 1st one, some extra text is
					// added to section 3
					if (j == 3 && i > 1) {
						section.setIndentationLeft(72);
						section.add(blahblah);
						section.add(new Paragraph("test"));
					}
					// in every section 3 subsections are added
					for (int k = 1; k < 4; k++) {
						Paragraph subTitle = new Paragraph(
								"This is subsection " + k + " of section " + j,
								subsectionFont);
						Section subsection = section.addSection(subTitle, 3);
						// in the first subsection of section 3, extra text is
						// added
						if (k == 1 && j == 3) {
							subsection.add(blahblahblah);
						}
						subsection.add(blahblah);
					}
					// in the section section of every chapter > 2 extra text is
					// added
					if (j == 2 && i > 2) {
						section.add(blahblahblah);
					}
					// a new page is added after the second section in Chapter 1
					if (j == 2 && i == 1) {
						section.add(Chunk.NEXTPAGE);
					}
				}
				document.add(chapter);
			}
		} catch (Exception de) {
			de.printStackTrace();
		}
		// step 5: we close the document
		document.close();
	}
}
