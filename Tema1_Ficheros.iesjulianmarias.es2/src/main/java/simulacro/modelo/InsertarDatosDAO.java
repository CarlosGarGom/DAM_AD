package simulacro.modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InsertarDatosDAO {

    public int insertarProvincia(Provincia provincia, Connection con) throws SQLException {
        String query = "INSERT INTO Provincia (nombre) VALUES (?) ON DUPLICATE KEY UPDATE id=LAST_INSERT_ID(id)";
        try (PreparedStatement stmt = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, provincia.getNombre());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return -1;
    }

    public void insertarPoblacion(Poblacion poblacion, Connection con) throws SQLException {
        String query = "INSERT INTO Poblacion (nombre, id_provincia, fecha) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, poblacion.getNombre());
            stmt.setInt(2, poblacion.getProvincia().getId());
            stmt.setDate(3, poblacion.getFecha());
            stmt.executeUpdate();
        }
    }
}
