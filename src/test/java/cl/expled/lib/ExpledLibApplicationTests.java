package cl.expled.lib;


import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ExpledLibApplicationTests {

	@Test
	public void contextLoads() throws JSONException {
		MysqlController m = new MysqlController()
			.setUser("root")
			.setPass("expled08")
			.setUrl("jdbc:mysql://10.99.99.122/PROCESOS_SCLEM?useSSL=false&useUnicode=yes&characterEncoding=UTF-8")
			.connect();
		
		JSONObject result = new JSONObject();
		if(!m.Exeptions.isEmpty()) {
			//System.out.println("Expetion test");
			result.put("Exepcion Text", m.Exeptions);
			System.out.println(result);
			return;
		}
		
		JSONObject input = new JSONObject();
		input.put("sp", "DEMO_CALIDAD_ITEMS");
		JSONObject params = new JSONObject();
		//JSONObject outputmap = new JSONObject();
		params
			.put("ID",0)
			.put("NOMBRE","")
			.put("TIPO","")
			//.put("MIN_WARNING","")
			//.put("MIN_DANGER","")
			.put("PLAN","");
		input.put("p",params);
		System.out.println(m.CallSP(input));
		
	}
	//@Test
	public void contextLoads2() throws JSONException {
		
	}

}
