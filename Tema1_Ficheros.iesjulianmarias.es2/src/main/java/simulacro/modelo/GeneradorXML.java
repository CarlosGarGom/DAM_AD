package simulacro.modelo;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class GeneradorXML {

	 public void generarArchivoXML(Connection conexion, String rutaArchivo) {
	        try {
	            List<Provincia> provincias = obtenerDatosDesdeBD(conexion);

	            // Crear el contexto y marshaller de JAXB
	            JAXBContext context = JAXBContext.newInstance(ProvinciaWrapper.class);
	            Marshaller marshaller = context.createMarshaller();

	            // Configurar salida con formato
	            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

	            // Crear el contenedor raíz
	            ProvinciaWrapper wrapper = new ProvinciaWrapper();
	            wrapper.setProvincias(provincias);

	            // Guardar en archivo
	            marshaller.marshal(wrapper, new File(rutaArchivo));
	            System.out.println("Archivo XML generado con éxito en: " + rutaArchivo);

	        } catch (JAXBException | SQLException e) {
	            System.err.println("Error al generar el archivo XML: " + e.getMessage());
	        }
	    }
	 private List<Provincia> obtenerDatosDesdeBD(Connection conexion) throws SQLException {
	        String queryProvincias = "SELECT * FROM Provincia";
	        String queryPoblaciones = "SELECT * FROM Poblacion WHERE id_provincia = ?";
	        List<Provincia> provincias = new ArrayList<>();

	        try (PreparedStatement stmtProvincias = conexion.prepareStatement(queryProvincias);
	             PreparedStatement stmtPoblaciones = conexion.prepareStatement(queryPoblaciones)) {
	            ResultSet rsProvincias = stmtProvincias.executeQuery();

	            while (rsProvincias.next()) {
	                String nombreProvincia = rsProvincias.getString("nombre");
	                int idProvincia = rsProvincias.getInt("id");

	                // Obtener poblaciones de esta provincia
	                stmtPoblaciones.setInt(1, idProvincia);
	                ResultSet rsPoblaciones = stmtPoblaciones.executeQuery();

	                List<Poblacion> poblaciones = new ArrayList<>();
	                while (rsPoblaciones.next()) {
	                    String nombrePoblacion = rsPoblaciones.getString("nombre");
	                    String fecha = rsPoblaciones.getDate("fecha").toString();

	                    poblaciones.add(new Poblacion(nombrePoblacion, fecha));
	                }

	                provincias.add(new Provincia(nombreProvincia, poblaciones));
	            }
	        }

	        return provincias;
	    }
}
