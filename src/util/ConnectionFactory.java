package util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.IOException;

public class ConnectionFactory {

    public static Connection getConnection() throws SQLException {

        Properties prop = new Properties();


        try(InputStream input = ConnectionFactory.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (input == null) {
                throw new RuntimeException("No se encontró el archivo db.properties en el classpath");
            }
            prop.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Error al leer el archivo db.properties",e);
        }

        String url = prop.getProperty("url");
        String user = prop.getProperty("user");
        String password = prop.getProperty("password");

        return DriverManager.getConnection(url,user,password);

    }
}