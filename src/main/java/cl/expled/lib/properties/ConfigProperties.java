package cl.expled.lib.properties;
/*
 *
Clase para el uso de configuraciones de la app

parametros espeficios
winDisk 
	Disco del archivo de propiedades (para Windows) 
	NombreDelDisco:
	ej: C:
propertiesPath
	Directorio de los archivos de propiedades
	/ruta/del/archivo/propiedades/
 	ej: /cl.expled/
propertiesFilePath
	(Opcional)
	Ruta del archivo de propiedades 
	/ruta/del/archivo/propiedades/archivo.properties
 	ej: /cl.expled/
el uso de estos parametros esta enfocado en la ayuda 
para el desarrollo de centralizacion de configuraciones en ambientes de  microservicios 	
 * */
import java.io.FileInputStream;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

public class ConfigProperties {
	public static String OS = System.getProperty("os.name").toLowerCase();
	public static boolean IsWindows = System.getProperty("os.name").toLowerCase().contains("win")?true:false;
	/*public static String getProperty(String value) {
		try {
			Properties pro = new Properties();
			String absPath;
			absPath = IsWindows? 
				getLocalProperty("winDisk") + getLocalProperty("propertiesFile"):
				getLocalProperty("propertiesFile");
			pro.load(new FileInputStream(absPath));
			return pro.getProperty(value);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "";
	}*/
	
	public static String getLocalProperty(String value) {
		ResourceBundle config = ResourceBundle.getBundle("application", Locale.ENGLISH);
		return config.getString(value);
	}
	
	public static String getLocalProperty(String value,String resourcename) {
		ResourceBundle config = ResourceBundle.getBundle(resourcename, Locale.ENGLISH);
		return config.getString(value);
	}

	/*public static String getPropertyByFile(String value, String pFile) {
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
		return null;
	}*/

	/*local properties application name y default*/
	public static Properties getLocalProperties() {
		ResourceBundle resource = ResourceBundle.getBundle("application", Locale.ENGLISH);
		Properties properties = new Properties();
        Enumeration<String> keys = resource.getKeys();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            properties.put(key, resource.getString(key));
        }
        return properties;
	}
	/*resource name*/
	public static Properties getLocalProperties(String propertyresource) {
		ResourceBundle resource = ResourceBundle.getBundle(propertyresource, Locale.ENGLISH);
		Properties properties = new Properties();
        Enumeration<String> keys = resource.getKeys();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            properties.put(key, resource.getString(key));
        }
        return properties;
	}
	/*specific properties file on server whit path file */
	public static Properties getProperties(String pFile) {
		Properties pro = new Properties();
		try {
			pro.load(new FileInputStream(pFile));
			return pro;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return pro;
	}
	/*public static Properties getPropertiesByFile(String pFile) {
		try {
			Properties pro = new Properties();
			String absPath;
			absPath = (OS.contains("win")) ? 
				(getLocalProperty("winDisk") + getLocalProperty("propertiesPath")+pFile+".properties")
				: getLocalProperty("propertiesPath")+pFile+".properties";
			pro.load(new FileInputStream(absPath));
			return pro;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return new Properties();
	}*/

}
