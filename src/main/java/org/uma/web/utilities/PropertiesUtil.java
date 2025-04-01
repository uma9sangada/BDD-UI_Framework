package org.uma.web.utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PropertiesUtil {

	// read properties file!!

	public static String readProperty(String propertyName) {
		System.out.println(System.getProperty("user.dir"));
		String relativePath = "src/main/resources/Configuration.properties";

        // Create the File object using the relative path.
        File propFile = new File(System.getProperty("user.dir"), relativePath);

		FileReader fileReader = null;
		Properties properties = new Properties();
		try {
			fileReader = new FileReader(propFile);
			properties.load(fileReader);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		catch (IOException e) {
			e.printStackTrace();
		}
		String value = properties.getProperty(propertyName.toUpperCase());
		return value;
	}

}
