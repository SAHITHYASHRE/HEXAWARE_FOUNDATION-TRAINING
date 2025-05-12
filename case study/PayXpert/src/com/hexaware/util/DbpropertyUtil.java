package com.hexaware.util;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public class DbpropertyUtil {
	
	private static final String File_path = "resource/db.properties";
	private static Properties properties = new Properties();
	
	static {
		
		try {
			
			FileInputStream fis = new FileInputStream(File_path);
			properties.load(fis);
			
		}
		catch(IOException e) {
			e.printStackTrace();
			
		}
		
		
	}
	public static String get(String key) {
		return properties.getProperty(key);
		
	}
	


}
