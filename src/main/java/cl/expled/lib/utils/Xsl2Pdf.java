package cl.expled.lib.utils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.springframework.beans.factory.annotation.Autowired;

public class Xsl2Pdf{

	public Xsl2Pdf(){}
	@Autowired
	public static int create(String pdfFilePath,String xmlFilePath,String xslFilePath) {
		try {
			File xmlfile = new File(xmlFilePath);
        	File xslfile = new File(xslFilePath);
        	File pdfFile = new File(pdfFilePath);
        	//System.out.println(pdfFile.getAbsolutePath());
        	final FopFactory fopFactory = FopFactory.newInstance(new File(".").toURI());
        	
        	FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
        	OutputStream out = new FileOutputStream(pdfFile);
        	out = new java.io.BufferedOutputStream(out);
    		Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);
    		TransformerFactory factory = TransformerFactory.newInstance();
    		Transformer transformer = factory.newTransformer(new StreamSource(xslfile));
    		Source src = new StreamSource(xmlfile);
    		Result res = new SAXResult(fop.getDefaultHandler());
    		transformer.transform(src, res);
        	out.close();
        } catch(IOException ex) {
        	ex.printStackTrace();
        	return 3;
        } catch (FOPException | TransformerException ex) {
    		ex.printStackTrace();
    		return 2;
		} catch(Exception ex) {
        	ex.printStackTrace();
        	return 1;
        }
		return 0;
	}

}
