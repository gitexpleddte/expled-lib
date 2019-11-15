package cl.expled.lib.utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;


import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

public class Html2Pdf {

	public static void FromText(String html,String path)  {
		try {
			Document doc = new Document(PageSize.A4);
			PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(path));
			doc.open();
			InputStream is = new ByteArrayInputStream(html.getBytes());
			// read it with BufferedReader
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			/*String line;
			while ((line = br.readLine()) != null) {
				System.out.println(line);
			}*/
			br.close();
			//this.createPdfItext(path, is);
	        XMLWorkerHelper.getInstance().parseXHtml(writer, doc, new StringReader(html));
	        doc.close();
		}catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
			throw new RuntimeException(e.getMessage(),e.getCause());
		}
	}
	
	public static void FromFile(String htmlPath,String path)  {
		try {
			String html = new String(Files.readAllBytes(Paths.get(htmlPath)), "UTF-8");
			Document doc = new Document(PageSize.A4);
			PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(path));
			doc.open();
			InputStream is = new ByteArrayInputStream(html.getBytes());
			// read it with BufferedReader
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			/*String line;
			while ((line = br.readLine()) != null) {
				//System.out.println(line);
			}*/
			br.close();
			//this.createPdfItext(path, is);
	        XMLWorkerHelper.getInstance().parseXHtml(writer, doc, new StringReader(html));
	        doc.close();
		}catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
			throw new RuntimeException(e.getMessage(),e.getCause());
		}
	}
}
