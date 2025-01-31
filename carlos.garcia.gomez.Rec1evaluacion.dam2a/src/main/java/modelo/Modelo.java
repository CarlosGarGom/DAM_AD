package modelo;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import modelo.clasesJAXB.Album;
import modelo.clasesJAXB.AlbumesPorPaises;
import modelo.clasesJAXB.Pais;
import org.apache.poi.ss.usermodel.Row;
import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Modelo {
	
	
	public static boolean llenaTablasAlbumes() {
	    System.out.println("En llenaTablasAlbumes");
	    int numFila = 1; // Comienza en la fila 1 (después de los encabezados)
	    Row fila = ConexionFicheroExcel.getInstancia().getHoja().getRow(numFila);
	    
	    try {
	        PreparedStatement sentenciaArtista = ConexionMySQL.getInstancia().getCon()
	                .prepareStatement("INSERT IGNORE INTO bandaArtistas (nombre) VALUES (?)", PreparedStatement.RETURN_GENERATED_KEYS);
	        PreparedStatement sentenciaAlbum = ConexionMySQL.getInstancia().getCon()
	                .prepareStatement("INSERT INTO albumes (titulo, bandaArtista, pais, discografica, precio, anio) VALUES (?, ?, ?, ?, ?, ?)");

	        while (fila != null) {
	            // Leer los datos de la fila
	            String titulo = fila.getCell(1).getStringCellValue();
	            String artista = fila.getCell(2).getStringCellValue();
	            String pais = fila.getCell(3).getStringCellValue();
	            String discografica = fila.getCell(4).getStringCellValue();
	            String precio =  fila.getCell(5).getStringCellValue();
	            int anio = (int) fila.getCell(6).getNumericCellValue();


	            // Insertar en bandaArtistas y obtener el ID generado
	            sentenciaArtista.setString(1, artista);
	            sentenciaArtista.executeUpdate();
	            ResultSet generatedKeys = sentenciaArtista.getGeneratedKeys();
	            int idArtista = 0;
	            if (generatedKeys.next()) {
	                idArtista = generatedKeys.getInt(1);
	            } else {
	                // Si el artista ya existe, buscar su ID
	                PreparedStatement sentenciaBuscaArtista = ConexionMySQL.getInstancia().getCon()
	                        .prepareStatement("SELECT id_bandaArtista FROM bandaArtistas WHERE nombre = ?");
	                sentenciaBuscaArtista.setString(1, artista);
	                ResultSet resultArtista = sentenciaBuscaArtista.executeQuery();
	                if (resultArtista.next()) {
	                    idArtista = resultArtista.getInt(1);
	                }
	            }

	            // Insertar en albumes
	            sentenciaAlbum.setString(1, titulo);
	            sentenciaAlbum.setInt(2, idArtista);
	            sentenciaAlbum.setString(3, pais);
	            sentenciaAlbum.setString(4, discografica);
	            sentenciaAlbum.setString(5, precio);
	            sentenciaAlbum.setInt(6, anio);
	            System.out.println("Insertado álbum: " + sentenciaAlbum.executeUpdate());

	            // Leer la siguiente fila
	            fila = ConexionFicheroExcel.getInstancia().getHoja().getRow(++numFila);
	           
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	       return false;
	    }
		return true;
	}
	
	

	    public static boolean creaDocumentoXML_AlbumesPorPaises() {
	        AlbumesPorPaises albumesPorPaises = new AlbumesPorPaises();
	        Map<String, Pais> mapaPaises = new HashMap<>();

	        try {
	            // Consulta para obtener álbumes agrupados por países
	            PreparedStatement consulta = ConexionMySQL.getInstancia().getCon().prepareStatement(
	                    "SELECT a.titulo, a.anio, a.pais, b.nombre AS artista " +
	                            "FROM albumes a " +
	                            "JOIN bandaArtistas b ON a.bandaArtista = b.id_bandaArtista " +
	                            "ORDER BY a.pais, a.anio");

	            ResultSet rs = consulta.executeQuery();

	            while (rs.next()) {
	                String paisCodigo = rs.getString("pais");
	                String titulo = rs.getString("titulo");
	                String artista = rs.getString("artista");
	                int anio = rs.getInt("anio");

	                // Si el país no existe en el mapa, lo agregamos
	                Pais pais = mapaPaises.computeIfAbsent(paisCodigo, k -> {
	                    Pais nuevoPais = new Pais();
	                    nuevoPais.setCodigo(paisCodigo);
	                    return nuevoPais;
	                });

	                // Crear el álbum y añadirlo al país
	                Album album = new Album();
	                album.setTitulo(titulo);
	                album.setArtista(artista);
	                album.setAnio(anio);
	                pais.getAlbumes().add(album);
	            }

	            // Pasar los países al objeto raíz
	            albumesPorPaises.setPaises(new ArrayList<>(mapaPaises.values()));

	            // Crear el archivo XML con JAXB
	            JAXBContext jaxbContext = JAXBContext.newInstance(AlbumesPorPaises.class);
	            Marshaller marshaller = jaxbContext.createMarshaller();
	            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

	            // Generar archivo
	            marshaller.marshal(albumesPorPaises, new File(Utilidades.getRutadatos() + Utilidades.getDocumentoXml()));

	            System.out.println("Archivo XML generado correctamente.");
	            return true;
	        } catch (SQLException | JAXBException e) {
	            e.printStackTrace();
	            return false;
	        }
	    }
	

}
