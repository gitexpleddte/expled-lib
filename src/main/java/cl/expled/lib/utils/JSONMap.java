package cl.expled.lib.utils;

import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONObject;

public class JSONMap {
	public static Object getObject(JSONObject input, String map) {
		
		String[] arrMap = map.split("/"); 
		if(arrMap.length==1) {
			return input.get(arrMap[0]);
		}
		for(String m: arrMap) {
			if (m.contains("[")) {
				String[] arrM = m.split("\\["); 
				String newKey = arrM[0];
				String newIndex = arrM[1];
				int index = Integer.parseInt(newIndex.replaceAll("\\D+",""));
				JSONArray arr = input.getJSONArray(newKey);
				String newMap = String.join("/",Arrays.copyOfRange(arrMap, 1, arrMap.length));
			
				if(arr.optJSONArray(index)!=null) {
					JSONArray newInput = arr.getJSONArray(index);
					return getObject( newInput, newMap);
				}else {
					JSONObject newImput= arr.getJSONObject(index);
					return getObject( newImput, newMap);
				}
			}else {
				if(input.has(m)) {
					String newMap = String.join("/",Arrays.copyOfRange(arrMap, 1, arrMap.length));
					if(input.optJSONObject(m)!=null) {
						JSONObject newImput=input.getJSONObject(m);
						return getObject( newImput, newMap);
					}else {
						JSONArray newImput=input.getJSONArray(m);
						return getObject( newImput, newMap);
					}
				}
			}
		}
		return null;
	}
	
	public static Object getObject(JSONArray input, String map) {
		String[] arrMap = map.split("/"); 
		if(arrMap.length==1) {
			String[] arrM = arrMap[0].split("\\["); 
			String newIndex = arrM[0];
			int index = Integer.parseInt(newIndex.replaceAll("\\D+",""));
			return input.get(index);
		}
		for(String m: arrMap) {
			if (m.contains("[")) {
				System.out.println(m);
				String[] arrM = m.split("\\["); 
				String newM = arrM[1];
				int index = Integer.parseInt(newM.replaceAll("\\D+",""));
				String newMap = String.join("/",Arrays.copyOfRange(arrMap, 1, arrMap.length));
				if(input.optJSONArray(index)!=null) {
					JSONArray newInput = input.getJSONArray(index);
					return getObject( newInput, newMap);
				}else {
					JSONObject newImput= input.getJSONObject(index);
					return getObject( newImput, newMap);
				}
			}else {
				return input;
			}
		}
		return null;
	}
}
