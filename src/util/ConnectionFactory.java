package util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class ConnectionFactory {

    public static Connection getConnection() {
        try (InputStream input = ConnectionFactory.class.getResourceAsStream("/db.properties")) {
            if (input == null) {
                throw new RuntimeException("No se encontró el archivo db.properties en resources");
            }

            Properties props = new Properties();
            props.load(input);

            String url = props.getProperty("url");
            String user = props.getProperty("user");
            String password = props.getProperty("password");
            String driver = props.getProperty("driver");

            Class.forName(driver);

            return DriverManager.getConnection(url, user, password);

        } catch (Exception e) {
            throw new RuntimeException("Error cargando conexión: " + e.getMessage(), e);
        }
    }
}
