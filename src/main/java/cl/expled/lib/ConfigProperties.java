package cl.expled.lib;

import java.io.FileInputStream;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

public class ConfigProperties {
	public static String OS = System.getProperty("os.name").toLowerCase();

	public static String getProperty(String value) {
		try {
			Properties pro = new Properties();
			String absPath;
			absPath = (OS.contains("win")) ? getLocalProperty("winDisk") + getLocalProperty("propertiesFile")
					: getLocalProperty("propertiesFile");
			pro.load(new FileInputStream(absPath));
			return pro.getProperty(value);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "";
	}
	
	public static Properties getPropertiesByServerFile(String pFile) {
		Properties pro = new Properties();
		try {
			pro.load(new FileInputStream(pFile));
			return pro;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return pro;
	}


	public static String getLocalProperty(String value) {
		ResourceBundle config = ResourceBundle.getBundle("application", Locale.ENGLISH);
		return config.getString(value);
	}

	public static String getPropertyByFile(String value, String pFile) {
		try {
			Properties pro = new Properties();
			String absPath;
			absPath = (OS.contains("win")) ? (getLocalProperty("winDisk") + getLocalProperty("propertiesPath")+pFile+".properties")
					: getLocalProperty("propertiesPath")+pFile+".properties";
			pro.load(new FileInputStream(absPath));
			return pro.getProperty(value);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "";
	}

}
