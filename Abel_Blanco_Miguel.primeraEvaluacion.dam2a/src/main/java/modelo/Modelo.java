package modelo;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import utilidades.Utilidades;

public class Modelo {

	private static String FILE_EXCEL = "IngresosIctus.xlsx";
	private static String SHEET_WORKBENCH = "Sheet";
	private static ConexionExcel conexionExcel;
	private static Workbook wb;



	//Funcion para realizar la inserccion a la BDD desde un Excel
	private static void insertarBDD() {
		//Debemos de obtener las filas del excel
		conexionExcel = ConexionExcel.getInstance(Utilidades.RUTA + FILE_EXCEL);
		wb = conexionExcel.getWorkbook();

		Sheet hoja = wb.getSheetAt(0);
		if (hoja == null) {
			System.out.println("La hoja " + SHEET_WORKBENCH + " no existe.");
			return;
		}

		int numFila = 1	; 
		Row fila = hoja.getRow(numFila);

		//Consulta
		String sql = "INSERT INTO `ictussacyl`.`ictus` (`fecha_ingreso`, `hospital`, `edad`, `sexo`) VALUES (?,?,?,?);";
		Connection con = ConexionBDD.getInstance().getCon();
		PreparedStatement sentencia;
		try {
			sentencia = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			while(fila!=null) {
				Cell celdaFechaIngreso = fila.getCell(0);
				Cell celdaHospital = fila.getCell(1);
				Cell celdaProvincia = fila.getCell(2);
				Cell celdaEdad = fila.getCell(3);
				Cell celdaSexo = fila.getCell(4);


				sentencia.setDate(1, fechaFormateada(celdaFechaIngreso));
				sentencia.setInt(2, conversorHospitales(celdaHospital.toString()));
				sentencia.setString(3, celdaEdad.toString());
				sentencia.setString(4, celdaSexo.toString());
				sentencia.execute();

				numFila++; 
				fila = hoja.getRow(numFila);

			}
			sentencia.close();
			wb.close();
			System.out.println("Datos registrados");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}


	//Funcion para formatear la fecha como nos pide el enunciado
	private static Date fechaFormateada(Cell fecha) {
		if (fecha!=null) {
			java.util.Date fechaU;
			try {
				fechaU = new SimpleDateFormat("yyyy-MM-dd").parse(fecha.getStringCellValue());
				java.sql.Date fechaSQL = new java.sql.Date(fechaU.getYear(), fechaU.getMonth(), fechaU.getDay());
				return fechaSQL;
			} catch (java.text.ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return null;
	}



	//Funcion para obtener todos los hospitales sin repetir con id y nombre
	private static Set<Hospitales> obtenerHospitalesSinRepetir() {
		try {
			PreparedStatement sentencia = ConexionBDD.getInstance().getCon().prepareStatement("SELECT id_hospital, nombre FROM hospitales");
			ResultSet rS = sentencia.executeQuery();
			Set<Hospitales> hospitales = new HashSet<Hospitales>();
			while(rS.next()) {
				hospitales.add(new Hospitales(rS.getInt(1), rS.getString(2)));
			}
			return hospitales;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}


	//Funcion para convertir el String del excel recibido en un id
	private static int conversorHospitales(String HospitalName) {
		int idHospital=0;
		Set<Hospitales> hospitales = obtenerHospitalesSinRepetir();
		for(Hospitales hospital: hospitales) {
			if(HospitalName.equals(hospital.getNombre())) {
				idHospital = hospital.getId_hospital();
			}
		}

		return idHospital;
	}


	//Funcion para cargar los datos para cuando se inicie la vista
	//Si hay registros en la bdd no se carga de nuevo el excel
	public static void cargarDatos() {
		PreparedStatement sentencia1;
		try {
			sentencia1 = ConexionBDD.getInstance().getCon().prepareStatement("SELECT count(*) FROM ictus");
			ResultSet rs = sentencia1.executeQuery();
			int count = 0;
			while (rs.next()) {
				count = rs.getInt("COUNT(*)");
			}

			if (count > 0) {
				System.out.println("No tenemos registros en la tabla Ictus");
			}else {
				insertarBDD();
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	//Funcion para obtener los años de los ictus
	public static Set<Integer> obtenerAniosIctus() {
	    try {
	        //Obtenemos la fecha completa y tenemos que modificarla
	        PreparedStatement sentencia = ConexionBDD.getInstance().getCon().prepareStatement("SELECT DATE_FORMAT(fecha_ingreso, '%Y') AS anio FROM ictus");
	        ResultSet rS = sentencia.executeQuery();
	        Set<Integer> anos = new HashSet<>();
	        while (rS.next()) {
	            String valor = rS.getString(1);
	            int anio = Integer.parseInt(valor);
	            System.out.println(anio);
	            anos.add(anio);
	        }

	        // Convert the set to a list
	        List<Integer> anosList = new ArrayList<>(anos);

	        return anos;

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return null;
	}


	//Obtenemos los datos mediante una funcion de count y posteriormente lo insertaremos en el textFieldCasosS
	public static int obtenerDatosContador(String anio, String edadMenor, String edadMayor, String sexo) {
		String sql = "SELECT COUNT(*) FROM ictussacyl.ictus WHERE sexo = ? and DATE_FORMAT(fecha_ingreso, '%Y') = ? and edad between ? and ?;";

		try {
			PreparedStatement ps;
			ps = ConexionBDD.getInstance().getCon().prepareStatement(sql);
			ps.setString(1, sexo);
			ps.setString(2, anio);
			ps.setInt(3, Integer.parseInt(edadMenor));
			ps.setInt(4, Integer.parseInt(edadMayor));

			ResultSet rs = ps.executeQuery();

			int count = 0;
			while (rs.next()) {
				count = rs.getInt("COUNT(*)");
			}
			System.out.println(count);
			return count;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return 0;
	}


	//Funcion que se encarga de obtener los periodos para posteriormente generar el xml mediante una consulta compleja
	private static List<Periodo> obtenerPeriodosXML() throws SQLException {
		List<Periodo> periodos = new ArrayList<>();
		String sql = "SELECT YEAR(fecha_ingreso) AS anio, edad, sexo, COUNT(*)" +
				"FROM ictus " +
				"GROUP BY anio, edad, sexo " +
				"ORDER BY anio, edad, sexo";

		try {
			PreparedStatement sentencia = ConexionBDD.getInstance().getCon().prepareStatement(sql);
			ResultSet rs = sentencia.executeQuery();
			int currentYear = -1;
			Periodo currentPeriodo = null;
			Edad currentEdad = null;
			while (rs.next()) {
				int anio = rs.getInt("anio");
				int edad = rs.getInt("edad");
				String sexo = rs.getString("sexo");
				int totalCasos = rs.getInt("total_casos");

				if (anio != currentYear) {
					if (currentPeriodo != null) {
						periodos.add(currentPeriodo);
					}
					currentYear = anio;
					currentPeriodo = new Periodo();
					currentPeriodo.setAnio(anio);
					currentPeriodo.setTotalCasos(totalCasos);
					currentEdad = new Edad();
				}

				if ("Masculino".equals(sexo)) {
					currentEdad.setHombres(totalCasos);
				} else if ("Femenino".equals(sexo)) {
					currentEdad.setMujeres(totalCasos);
				}

				currentPeriodo.setEdad(currentEdad);
			}
			if (currentPeriodo != null) {
				periodos.add(currentPeriodo);
			}

			sentencia.close();

		}catch (Exception e) {
			// TODO: handle exception
		}

		return periodos;
	}

	
	//Funcion que hace llamada a la consulta de los datos y posteriormente genera el xml
	public static void generarPeriodosXML() {
		try {
			List<Periodo> periodos = obtenerPeriodosXML();
			Ictus2 ictus = new Ictus2();
			ictus.setPeriodos(periodos);

			JAXBContext context;
			try {
				context = JAXBContext.newInstance(Ictus.class, Periodo.class, Edad.class);
				Marshaller marshaller = context.createMarshaller();
				marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

	            String filePath = "src/main/resources/ictus.xml";
	            File xmlFile = new File(filePath);
	            File parentDir = xmlFile.getParentFile();
	            if (!parentDir.exists()) {
	                try {
	                    Files.createDirectories(Paths.get(parentDir.toURI()));
	                } catch (IOException e) {
	                    e.printStackTrace();
	                    return;
	                }
	            }

	            marshaller.marshal(ictus, xmlFile);

	            System.out.println("XML guardado en: " + xmlFile.getAbsolutePath());
				
			} catch (JAXBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	//******MAIN DE PRUEBAS
	public static void main(String[] args) {
	}

}
