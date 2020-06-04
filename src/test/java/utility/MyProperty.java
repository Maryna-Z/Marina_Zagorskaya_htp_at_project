package utility;

import java.io.*;
import java.util.Properties;

public class MyProperty {
    private static Properties prop = new Properties();

    public static Properties getProperties(String path){
        if(null != prop){
            return prop;
        }
        try (InputStream input = new FileInputStream(path)) {
            prop.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return prop;
    }

    public static void setProperties(String path, String propName, String propValue){
        getProperties(path);
        try {
            OutputStream out = new FileOutputStream(path);
            prop.put(propName, propValue);
            prop.store(out, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readPropValue(String path, String propName) {
        String propValue = null;
        try (InputStream input = new FileInputStream(path)) {
            propValue = prop.getProperty(propName);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return propValue;
    }

}