package cl.expled.lib.controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.JSONArray;
import org.json.JSONObject;

public class HanaController {
	static String jdbcUrl ;//= "jdbc:mysql://10.60.1.220:3306/blumarCalidad";
	static String username ;//= "root";
	static String password ;//= "expled08";
	static String driver ;//= "com.mysql.jdbc.Driver";
	
	public static Connection conn = null;
	public HanaController() {
		
	}
	public void connectionTest() {
		Connection connection = null;
		try {
			Class.forName("com.sap.db.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			//connection = DriverManager.getConnection("jdbc:sap://hostname:30015/?databaseName=mydb&user=myuser&password=mypassword");
			//jdbc:sap://localhost:30013/?databaseName=tdb1&user=SYSTEM&password=manager
			//"jdbc:sap://vadbhh00900.stl1.od.sap.biz:30059/", 
			connection = DriverManager.getConnection(
				"jdbc:sap://localhost:30015/?databaseName=A05&user=DEV_AROOFW14POX4EHN021B5SCABO&password=Expled08&instanceNumber=00"
					//"jdbc:sap://10.20.1.121:30313/?databaseName=SYSTEMDB&user=MCARRASCO&password=Ddc#2018&&instanceNumber=03"
					);
		} catch (SQLException e) {
			System.err.println("Connection Failed. User/Passwd Error? Message: " + e.getMessage());
			return;
		}
		if (connection != null) {
			try {
				System.out.println("Connection to HANA successful!");
				Statement stmt = connection.createStatement();
				ResultSet resultSet = stmt.executeQuery("select 'hello world' from dummy");
				resultSet.next();
				String hello = resultSet.getString(1);
				System.out.println(hello);
			} catch (SQLException e) {
				System.err.println("Query failed!");
			}
		}
	}
	public HanaController(String jdbcUrl,String username,String password) {
        try {
            conn = DriverManager.getConnection(jdbcUrl, username, password);
        }  catch (Exception e) {
            System.out.println("Error Conexion: " + e.getMessage());
        }
    }
	public HanaController(String jdbcUrl,String username,String password ,String driver) {
        try {
        	if(driver!=null)Class.forName(driver);
            conn = DriverManager.getConnection(jdbcUrl, username, password);
        }  catch (Exception e) {
            System.out.println("Error Conexion: " + e.getMessage());
        }
    }
	public JSONObject ExecSP(JSONObject data){
		JSONObject res= new JSONObject();
		res.put("error", "1");
		res.put("message", "Parametros ExecSP incorrectos");
		String sql = "";
		Statement stmt =null;
		try {
			String sp = data.getString("SP");
			String sFilter=" ";
			System.out.println(data.getJSONArray("FILTERS"));
			if(data.has("FILTERS")){
				sFilter="( ";
				for(Object oFilter: data.getJSONArray("FILTERS")){
					JSONObject filter = (JSONObject) oFilter;
					if(filter.has("type")&&!filter.isNull("type")&&filter.getString("type").equals("number")){
						sFilter += filter.isNull("value")?"null,":filter.get("value")+",";
					}else{
						sFilter += filter.isNull("value")?"null,":"'"+filter.get("value")+"',";
					}
				} 
				sFilter = sFilter.substring(0, sFilter.length() - 1);
				sFilter+=")";
			}
			
		    System.out.println(sFilter);
		    JSONArray dataArr = new JSONArray();
			sql = "CALL "+sp+" "+sFilter;
			System.out.println(sql);
		    stmt = (Statement) conn.createStatement();
		    ResultSet rs = stmt.executeQuery(sql);
		    ResultSetMetaData rsmd = (ResultSetMetaData) rs.getMetaData();
		    int columnCount = rsmd.getColumnCount();
		    
		    while(rs.next()) {
		    	JSONObject row = new JSONObject();
			    for (int i = 1; i <= columnCount; i++ ) {
			      String key = rsmd.getColumnLabel(i);
			      row.put(key,rs.getString(key));
		    	}
		    	dataArr.put(row);
		    }
		    res.put("data", dataArr);
		    res.put("error", 0);
		    res.put("message", "OK");
		    stmt.close();
		} catch (SQLException e) {
	    	res.put("error", 1);
			res.put("message", e.getMessage());
			res.put("printStackTrace", ""+e.getStackTrace()[0].getLineNumber()+"");
			res.put("e", "SQLException");
			res.put("sql", sql);
			System.out.println(res);
	    }catch (Exception e) {
			res.put("error", "1");
			res.put("message", e.getMessage()+" null "+e.getLocalizedMessage());
			res.put("printStackTrace", ""+e.getStackTrace()[1].getLineNumber()+"");
			res.put("e", "Exception");
			System.out.println(res);
			
		}finally {}
		return res;
	}
	
	 public void close() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println("Error cerrar conexion: " + e.getMessage());
        }
    }
	 
	 public  JSONObject sqlCommand(String sql, String command) {
        System.out.println("sqlCommand");
        JSONObject res = new JSONObject();
        res.put("error", 1);
        res.put("message", "Instruccion no ejecutada");
        try {
        	//HanaController db = new HanaController();
            JSONArray dataArr = new JSONArray();
            Statement stmt = (Statement) conn.createStatement();
            
            if(command=="execute") {
            	 stmt.execute(sql);
            }else {
            	ResultSet rs = stmt.executeQuery(sql);
                ResultSetMetaData rsmd = (ResultSetMetaData) rs.getMetaData();
                int columnCount = rsmd.getColumnCount();

                while (rs.next()) {
                    JSONObject row = new JSONObject();
                    for (int i = 1; i <= columnCount; i++) {
                        String key = rsmd.getColumnName(i);
                        row.put(key, rs.getString(key));
                    }
                    dataArr.put(row);
                    //System.out.println(row);
                }
            }
            res.put("data", dataArr);
            res.put("error", 0);
            res.put("message", "OK");
            stmt.close();
            //conn.close();
        } catch (SQLException e) {
            res.put("error", 1);
            res.put("message", e.getMessage());
            res.put("printStackTrace", "" + e.getStackTrace()[1].getLineNumber() + "");
            res.put("e", "SQLException");
        } catch (Exception e) {
            res.put("error", "1");
            res.put("message", e.getMessage());
            res.put("printStackTrace", "" + e.getStackTrace()[1].getLineNumber() + "");
            res.put("e", "Exception");
            res.put("sql", sql);
        } finally {}
        
        return res;
    }
}
