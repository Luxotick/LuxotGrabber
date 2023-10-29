package io.github.luxotick.utils;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

public class DriverUtil {
    public static java.sql.Driver getDriver(){
        try {
            File driverFile = new File(System.getProperty("java.io.tmpdir") + File.separator + "sqlite-jdbc-3.42.0.0.jar");
            URL url = new URL("https://search.maven.org/remotecontent?filepath=org/xerial/sqlite-jdbc/3.42.0.0/sqlite-jdbc-3.42.0.0.jar");
            if (!driverFile.exists()) {
                FileUtils.copyURLToFile(url, driverFile);
            }
            driverFile.deleteOnExit();
            ClassLoader classLoader = new URLClassLoader(new URL[] { driverFile.toURI().toURL() });
            Class clazz = Class.forName("org.sqlite.JDBC", true, classLoader);
            Object driverInstance = clazz.newInstance();
            return (java.sql.Driver) driverInstance;
        } catch (Exception e) {
        }
        return null;
    }
}
