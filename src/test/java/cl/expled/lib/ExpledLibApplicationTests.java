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
import cl.expled.lib.properties.ConfigProperties;
import cl.expled.lib.utils.Html2Pdf;
import cl.expled.lib.utils.JSONMap;
import cl.expled.lib.utils.Xsl2Pdf;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ExpledLibApplicationTests {
	@Test
	public void contextHana() {
		//HanaController h = new HanaController();
		//h.connectionTest();
	}
	
	@Test
	public void contextCamilo() {
		MysqlController m = new MysqlController()
				.setUser("root")
				.setPass("expled08")
				.setUrl("jdbc:mysql://10.20.1.41:3306/baika_movilidad")
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
		
		String sinput2 = "{\"sp\":\"planificaciones\",\"p\":{\"_cmd\":\"getPlanificacion\"}}";
		JSONObject input2 = new JSONObject(sinput2);
		System.out.println(m.CallSP(input2));
	}
	//@Test
	public void contextLoads() throws JSONException {
		System.out.println("MysqlController");
		MysqlController m = new MysqlController()
			.setUser("root")
			.setPass("expled08")
			.setUrl("jdbc:mysql://10.99.90.122:3306/FRUSAN")
			.connect();
		
		JSONObject result = new JSONObject();
		if(!m.Exeptions.isEmpty()) {
			result.put("Exepcion Text", m.Exeptions);
			System.out.println(result);
			return;
		}
		String sinput2 = "{\"sp\":\"Login\",\"p\":{\"user_p\":\"CVALENZUELA\",\"pass_p\":\"Cv@l2019\"}}";
		JSONObject input2 = new JSONObject(sinput2);
		System.out.println(m.CallSP(input2));
		
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
	
	//@Test
	public void contextLoads6() {
		System.out.println("JSONMap");
	    JSONObject ob = new JSONObject("{\"data\":{\"BAPI_GOODSMVT_CANCEL\":{\"INPUT\":{\"MATERIALDOCUMENT\":\"\",\"MATDOCUMENTYEAR\":\"0000\",\"DOCUMENTHEADER_TEXT\":\"\",\"GOODSMVT_PSTNG_DATE\":\"2020-01-17\",\"GOODSMVT_PR_UNAME\":\"\"},\"OUTPUT\":{\"GOODSMVT_HEADRET\":{\"DOC_YEAR\":\"0000\",\"MAT_DOC\":\"\"}},\"TABLES\":{\"RETURN\":{\"item\":{\"SYSTEM\":\"SCPCLNT300\",\"NUMBER\":\"063\",\"FIELD\":\"\",\"MESSAGE_V2\":\"\",\"MESSAGE\":\"El documento  no existe\",\"MESSAGE_V3\":\"\",\"MESSAGE_V4\":\"\",\"LOG_NO\":\"\",\"MESSAGE_V1\":\"\",\"ID\":\"M7\",\"ROW\":0,\"TYPE\":\"E\",\"LOG_MSG_NO\":\"000000\",\"PARAMETER\":\"GOODSMVT_HEADER\"}},\"GOODSMVT_MATDOCITEM\":\"\"}}},\"commit\":{\"BAPI_TRANSACTION_COMMIT\":{\"INPUT\":{\"WAIT\":\"X\"},\"OUTPUT\":{\"RETURN\":{\"SYSTEM\":\"\",\"NUMBER\":\"000\",\"FIELD\":\"\",\"MESSAGE_V2\":\"\",\"MESSAGE\":\"\",\"MESSAGE_V3\":\"\",\"MESSAGE_V4\":\"\",\"LOG_NO\":\"\",\"MESSAGE_V1\":\"\",\"ID\":\"\",\"ROW\":0,\"TYPE\":\"\",\"LOG_MSG_NO\":\"000000\",\"PARAMETER\":\"\"}}}},\"error\":0,\"message\":\"OK\"}");
	    JSONObject ob2 = new JSONObject("{\"data\":{\"BAPI_GOODSMVT_CREATE\":{\"INPUT\":{\"GOODSMVT_PRINT_CTRL\":{\"PR_PRINT\":0},\"GOODSMVT_CODE\":{\"GM_CODE\":\"03\"},\"GOODSMVT_HEADER\":{\"HEADER_TXT\":\"\",\"BILL_OF_LADING_LONG\":\"\",\"REF_DOC_NO_LONG\":\"\",\"PR_UNAME\":\"\",\"REF_DOC_NO\":\"\",\"PSTNG_DATE\":\"2020-01-17\",\"BILL_OF_LADING\":\"\",\"BAR_CODE\":\"\",\"VER_GR_GI_SLIPX\":\"\",\"GR_GI_SLIP_NO\":\"\",\"VER_GR_GI_SLIP\":\"\",\"EXT_WMS\":\"\",\"DOC_DATE\":\"2020-01-17\"},\"GOODSMVT_REF_EWM\":{\"LOGSYS\":\"\",\"GTS_SCRAP_NO\":\"\",\"REF_DOC_EWM\":\"\"},\"TESTRUN\":\"\"},\"OUTPUT\":{\"MATERIALDOCUMENT\":\"\",\"MATDOCUMENTYEAR\":\"0000\",\"GOODSMVT_HEADRET\":{\"DOC_YEAR\":\"0000\",\"MAT_DOC\":\"\"}},\"TABLES\":{\"GOODSMVT_SERIALNUMBER\":\"\",\"RETURN\":{\"item\":{\"SYSTEM\":\"SCPCLNT300\",\"NUMBER\":300,\"FIELD\":\"\",\"MESSAGE_V2\":\"\",\"MESSAGE\":\"No se han transferido posiciones\",\"MESSAGE_V3\":\"\",\"MESSAGE_V4\":\"\",\"LOG_NO\":\"\",\"MESSAGE_V1\":\"\",\"ID\":\"M7\",\"ROW\":0,\"TYPE\":\"E\",\"LOG_MSG_NO\":\"000000\",\"PARAMETER\":\"GOODSMVT_HEADER\"}},\"GOODSMVT_ITEM_CWM\":\"\",\"GOODSMVT_SERV_PART_DATA\":\"\",\"GOODSMVT_ITEM\":\"\",\"EXTENSIONIN\":\"\"}}},\"commit\":{\"BAPI_TRANSACTION_COMMIT\":{\"INPUT\":{\"WAIT\":\"X\"},\"OUTPUT\":{\"RETURN\":{\"SYSTEM\":\"\",\"NUMBER\":\"000\",\"FIELD\":\"\",\"MESSAGE_V2\":\"\",\"MESSAGE\":\"\",\"MESSAGE_V3\":\"\",\"MESSAGE_V4\":\"\",\"LOG_NO\":\"\",\"MESSAGE_V1\":\"\",\"ID\":\"\",\"ROW\":0,\"TYPE\":\"\",\"LOG_MSG_NO\":\"000000\",\"PARAMETER\":\"\"}}}},\"error\":0,\"message\":\"OK\"}");
	    
	    String map ="data/BAPI_GOODSMVT_CANCEL/OUTPUT/GOODSMVT_HEADRET/DOC_YEAR";
	    String map2 ="data/BAPI_GOODSMVT_CREATE/TABLES/RETURN/item/MESSAGE";
	    Object o = JSONMap.getObject(ob2, map2);
	    // Object oa = JSONMap.getObject(arr2, map4);
	    System.out.println(o);
	    //System.out.println(oa);
			
			
	}
	
	

}
