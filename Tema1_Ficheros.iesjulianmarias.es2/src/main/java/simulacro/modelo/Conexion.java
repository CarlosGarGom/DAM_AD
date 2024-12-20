package simulacro.modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private static final String URL = "jdbc:mysql://localhost:3306/ictusSaCyL";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public static Connection establecerConexion() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
