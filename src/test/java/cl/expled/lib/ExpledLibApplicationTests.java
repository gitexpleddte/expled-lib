package cl.expled.lib;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Properties;

import org.json.JSONArray;
import org.json.XML;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.itextpdf.text.DocumentException;
//import com.sap.conn.jco.JCoException;

import cl.expled.lib.controllers.HanaController;
import cl.expled.lib.controllers.MysqlController;
import cl.expled.lib.controllers.SapController;
import cl.expled.lib.properties.ConfigProperties;
import cl.expled.lib.utils.Html2Pdf;
import cl.expled.lib.utils.Xsl2Pdf;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ExpledLibApplicationTests {
	@Test
	public void contextHana() {
		//HanaController h = new HanaController();
		//h.connectionTest();
	}
	//@Test
	public void contextLoads() throws JSONException {
		System.out.println("MysqlController");
		MysqlController m = new MysqlController()
			.setUser("root")
			.setPass("expled08*.")
			.setUrl("jdbc:mysql://10.20.1.37:3306/baika_movilidad")
			.connect();
		
		JSONObject result = new JSONObject();
		if(!m.Exeptions.isEmpty()) {
			result.put("Exepcion Text", m.Exeptions);
			System.out.println(result);
			return;
		}
		JSONObject input = new JSONObject();
		input.put("sp", "test_get_users");
		JSONObject params = new JSONObject();
		//params.put("id", "0");
		/*params
			.put("ID",0)
			.put("NOMBRE","")
			.put("TIPO","")
			//.put("MIN_WARNING","")
			//.put("MIN_DANGER","")
			.put("PLAN","");
			*/
		//input.put("p",params);
		System.out.println(m.CallSP(input));
		
	}
	//@Test
	public void contextLoads2() throws JSONException {
		System.out.println("SapController");
		/*SapController sap = new SapController()
			.setJCO_ASHOST("/H/200.54.27.10/H/192.168.10.98")
			.setJCO_CLIENT("300")
			.setJCO_PASSWD("Invertec@2019.")
			.setJCO_USER("WULTU_MOVIL")
			.setJCO_SYSNR("00")
			.setJCO_LANG("ES");
		if(!sap.connect()) {
			System.out.println();
		}
		String sJson ="{\"RFC\": \"ZMOV_40005\",\"getBase\":true}";
		JSONObject json = new JSONObject(sJson);
		System.out.println(sap.callRfc(json));*/
	}
	
	//@Test
	public void contextLoads3() throws JSONException {
		System.out.println("ConfigProperties");
		Properties p = ConfigProperties.getProperties("C:/cl.expled/lib/test.properties");
		System.out.println(p);
	}
	
	//@Test
	public void contextLoads4() {
		System.out.println("Html2Pdf");
		Html2Pdf.FromText("<html><body>test</body></html>", "C:/cl.expled/lib/html2pdf.pdf"); 
		Html2Pdf.FromFile("C:/cl.expled\\blumar\\calidad\\templates\\mail/reporte.html", "C:/cl.expled/lib/htmlfile2pdf.pdf");
		
	}
	
	//@Test
	public void contextLoads5() {
		System.out.println("Xsl2Pdf");
		Xsl2Pdf.create("C:/cl.expled/lib/Xsl2Pdf.pdf",
		"C:\\cl.expled\\lib\\20024.xml", 
		"C:\\cl.expled\\lib\\materiaPrima.xsl");
	}

}
