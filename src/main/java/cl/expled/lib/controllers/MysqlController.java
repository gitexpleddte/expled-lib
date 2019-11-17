package cl.expled.lib.controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;

import org.json.JSONArray;
import org.json.JSONObject;


public class MysqlController {
	private Connection conn;
	private String driver;
	private String user;
	private String url;
	private String pass;
	public ArrayList<Exception> Exeptions = new ArrayList<Exception>();
	public boolean isSuccess;
	
	public MysqlController() {
		
	}
	public MysqlController connect(){
		try {
			if(driver!=null)Class.forName(driver);
			conn = DriverManager.getConnection(url,user, pass);
		} catch (ClassNotFoundException e1) {
			Exeptions.add(e1);
			e1.printStackTrace();
		}catch (Exception e) {
			Exeptions.add(e);
			e.printStackTrace();
		}
		return this;
	}
	public MysqlController setDriver(String s){driver=s;return this;}
	public MysqlController setUrl(String s){url=s;return this;}
	public MysqlController setUser(String s){user=s;return this;}
	public MysqlController setPass(String s){pass=s;return this;}

	public MysqlController close() {
		if (conn != null) {
			try {
				conn.close();
			} catch (Exception e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
				Exeptions.add(e);
			}
		}
		return this;
	}

	public JSONObject sqlExecute(String sql)  {
		System.out.println("sqlCommand");
		JSONObject res = new JSONObject();
		res.put("error", 1);
		res.put("message", "Instruccion no ejecutada");
		try {
			Statement stmt = (Statement) conn.createStatement();
			stmt.execute(sql);
			res.put("error", 0);
			res.put("message", "OK");
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			Exeptions.add(e);
			res.put("error", 1);
			res.put("message", e.getMessage());
			res.put("printStackTrace", "" + e.getStackTrace()[1].getLineNumber() + "");
			res.put("e", "SQLException");
		} finally {

		}
		return res;
	}

	public JSONObject sqlExecuteQuery(String sql)  {
		System.out.println("sqlExecuteQuery");
		JSONObject res = new JSONObject();
		res.put("error", 1);
		res.put("message", "Instruccion no ejecutada");
		try {
			JSONArray dataArr = new JSONArray();
			Statement stmt = (Statement) conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			ResultSetMetaData rsmd = (ResultSetMetaData) rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			while (rs.next()) {
				JSONObject row = new JSONObject();
				for (int i = 1; i <= columnCount; i++) {
					String key = rsmd.getColumnLabel(i);
					row.put(key, rs.getString(key));
				}
				dataArr.put(row);
			}
			res.put("data", dataArr);
			res.put("message", "OK");
			res.put("error", 0);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			Exeptions.add(e);
			res.put("error", 1);
			res.put("message", e.getMessage());
			res.put("printStackTrace", e.getStackTrace()[0].getLineNumber());
			res.put("e", "SQLException");
			res.put("sql", sql);
		} catch (Exception e) {
			e.printStackTrace();
			Exeptions.add(e);
			res.put("error", 1);
			res.put("message", e.getMessage());
			res.put("printStackTrace",  e.getStackTrace()[1].getLineNumber());
			res.put("e", "Exception");
		}finally {

		}
		return res;
	}
	public JSONObject CallSP2(JSONObject input) {
		//String sqlSP="SELECT param_list FROM mysql.proc WHERE name='"+input.getString("SP")+"'";
		//JSONObject params = sqlExecuteQuery(sqlSP);
		return new JSONObject();
	}
	public JSONObject CallSP(JSONObject data)  {
		System.out.println("CallSP");
		JSONObject res = new JSONObject();
		res.put("error", 1);
		res.put("message", "Parametros ExecSP incorrectos");
		String sql="";
		Statement stmt = null;
		try {
			String sp = data.getString("sp");
			JSONObject params = data.has("p")?
					data.getJSONObject("p"):
					null;
			//buscar todos los parametros del sp y setear los que vienen desde el consumo 
			LinkedHashMap<String, String> map = getSpParamsMap( sp, params);
			System.out.println(map);
			String sParams = "( ";
			if (map != null) {
				for (String key : map.keySet()) {
					sParams += map.get(key)==null ? "null," : "'" + map.get(key) + "',";
				}
				sParams = (sParams.substring(0, sParams.length() - 1))+ ")";
			}
			System.out.println(sParams);
			JSONArray dataArr = new JSONArray();
			sql = "CALL " + sp + " " + sParams;
			stmt = (Statement) conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			ResultSetMetaData rsmd = (ResultSetMetaData) rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			while (rs.next()) {
				JSONObject row = new JSONObject();
				for (int i = 1; i <= columnCount; i++) {
					String key = rsmd.getColumnLabel(i);
					String dataTypeName = rsmd.getColumnTypeName(i);
					switch(dataTypeName) {
						case "INTEGER":row.put(key, rs.getInt(key));break;
						case "FLOAT":
						case "DOUBLE":row.put(key, rs.getDouble(key));break;
						case "VARCHAR":row.put(key, rs.getString(key));break;
						case "DATE":row.put(key, rs.getDate(key));break;
						case "TIME":row.put(key, rs.getTime(key));break;
						case "OTHER":row.put(key, rs.getString(key));break;
						default: row.put(key, rs.getString(key));break;
					}
					
				}
				dataArr.put(row);
			}
			res.put("data", dataArr);
			res.put("message", "OK");
			res.put("error", 0);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			Exeptions.add(e);
			res.put("error", 1);
			res.put("message", e.getMessage());
			res.put("printStackTrace", e.getStackTrace()[0].getLineNumber());
			res.put("e", "SQLException");
			res.put("sql", sql);
		} catch (Exception e) {
			e.printStackTrace();
			Exeptions.add(e);
			res.put("error", 1);
			res.put("message", e.getMessage());
			res.put("printStackTrace",  e.getStackTrace()[1].getLineNumber());
			res.put("e", "Exception");
		} finally {
			
		}
		return res;
	}
	
	public JSONObject CallSPCustom(JSONObject data,LinkedHashMap<String, String> map)  {
		System.out.println("CallSP");
		JSONObject res = new JSONObject();
		res.put("error", 1);
		res.put("message", "Parametros ExecSP incorrectos");
		String sql="";
		Statement stmt = null;
		try {
			String sp = data.getString("sp");
			String sParams = "( ";
			//System.out.println(map);
			if (map != null) {
				for (String key : map.keySet()) {
					sParams += map.get(key)==null ? "null," : "'" + map.get(key) + "',";
				}
				sParams = (sParams.substring(0, sParams.length() - 1))+ ")";
			}
			System.out.println(sParams);
			JSONArray dataArr = new JSONArray();
			sql = "CALL " + sp + " " + sParams;
			stmt = (Statement) conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			ResultSetMetaData rsmd = (ResultSetMetaData) rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			while (rs.next()) {
				JSONObject row = new JSONObject();
				if(data.has("odt")) {//mapeo de tipos de dato de la salida  OUTPUT DATA TYPES
					JSONObject odt = data.getJSONObject("odt");
					for (int i = 1; i <= columnCount; i++) {
						String key = rsmd.getColumnLabel(i);
						if(odt.has(key) && odt.getString(key).equals("number")) {
							row.put(key, rs.getDouble(key));
						}else if(odt.has(key) && odt.getString(key).equals("string")) {
							row.put(key, rs.getString(key));
						}else if(odt.has(key) && odt.getString(key).equals("date")) {
							row.put(key, rs.getDate(key));
						}else if(odt.has(key) && odt.getString(key).equals("time")) {
							row.put(key, rs.getTime(key));
						}else {
							row.put(key, rs.getString(key)==null?JSONObject.NULL:rs.getString(key));
						}
					}
				}else {
					for (int i = 1; i <= columnCount; i++) {
						String key = rsmd.getColumnLabel(i);
						row.put(key, rs.getString(key));
					}
				}
				dataArr.put(row);
			}
			res.put("data", dataArr);
			res.put("message", "OK");
			res.put("error", 0);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			res.put("error", 1);
			res.put("message", e.getMessage());
			res.put("printStackTrace", e.getStackTrace()[0].getLineNumber());
			res.put("e", "SQLException");
			res.put("sql", sql);
		} catch (Exception e) {
			e.printStackTrace();
			res.put("error", 1);
			res.put("message", e.getMessage());
			res.put("printStackTrace",  e.getStackTrace()[1].getLineNumber());
			res.put("e", "Exception");
		} finally {
			
		}
		return res;
	}
	
	
	private LinkedHashMap<String, String> getSpParamsMap(String sp,JSONObject params)  {
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		if(params==null)return map;
		JSONObject j = new JSONObject();
		JSONObject r = this.sqlExecuteQuery("SELECT param_list FROM mysql.proc WHERE name='"+sp+"' ");
		if(r.has("data") && r.getJSONArray("data").length()>0) {
			JSONObject item = r.getJSONArray("data").getJSONObject(0);
			String[] arrP = item.getString("param_list").split(",");
			for (int i = 0; i < arrP.length; i++) { 
				arrP[i]=arrP[i].replaceAll("\\n", "").replaceAll("\\t", "").trim();
				String[] arrItem = arrP[i].split(" ");
				if(arrItem.length>2)arrItem = Arrays.copyOfRange(arrItem, 1, arrItem.length);
				if(!arrItem[0].equals("")) {
					j.put(arrItem[0], "");
					map.put(arrItem[0],params.has(arrItem[0])?params.get(arrItem[0])+"":null);
				}
				//map.put(arrItem[0],arrItem[1]);
			}
		}
		//System.out.println(j+"");
		System.out.println(map);
		return map;
	}

}
