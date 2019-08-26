package com.xj.stat.utils;

import org.apache.log4j.Logger;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
/**
 * properties文件读取（即时）
 * @author public
 *
 */
public class PropertyTool {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(PropertyTool.class);

	
	public synchronized static Properties getPropertites(String path) {
		java.net.URL u = PropertyTool.class.getResource(path);
//		String url = System.getProperty("user.dir");
//		InputStream infile = PropertyTool.class.getClassLoader().getResourceAsStream("/configure.peoperties");
		u.getPath();
		Properties p = new Properties();
		InputStream infile= null;
		try {
			File file = new File(u.getPath());
			if(file.exists()){
				
			}else{
				
			}
			infile= new BufferedInputStream(new FileInputStream(file)); 
			p.load(infile);
		} catch (Exception e) {
			logger.error("getPropertites(String)", e);  
		} finally {
			try {
				infile.close();
			} catch (IOException e) {
				logger.error("getPropertites(String)", e);  
			}
		}
		return p;
	}

	/**
	 * 设置属�?值（同步�?
	 * @param property
	 *            the property you want to put data into
	 * @param path
	 *            the path of this property
	 * @param key
	 *            the name of the parameter
	 * @param value
	 *            the value
	 */
	public synchronized static void setPropertites(Properties property,
			String path, String key, String value) {
		OutputStream fos = null;
		try {
			
			fos = new FileOutputStream(path);
			property.setProperty(key, value);
			property.store(fos, "Update " + key + " value");

		} catch (Exception e) {
			logger.error("setPropertites(Properties, String, String, String)", e);  

		} finally {
			if (fos != null) {
				try {
					fos.flush();
					fos.close();
				} catch (Exception e) {
					logger.error("setPropertites(Properties, String, String, String)", e);  

				}
			}
		}
	}
	


}
