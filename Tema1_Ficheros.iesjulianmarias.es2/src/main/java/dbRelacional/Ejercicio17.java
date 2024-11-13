package dbRelacional;
 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import utilidades.Utilidades;
 
public class Ejercicio17 {
 
	private static Connection con;
	private final static String DB= "dbEmpresaSQLite.dat";
	public Ejercicio17() {
		// TODO Auto-generated constructor stub
	}
 
	public static void main(String[] args) {
		//establecerConexion_MySQL();
		establecerConexion_MySQL(); 		//listarDepartamentos();
		//System.out.println(anadirDepartamento("Alimentos","Pamplona")!=0?"El departamento se añadio con exito":"El deparamento NO se añadio");
		//System.out.println(anadirDepartamento("LOGISTICA","Pamplona")!=0?"El departamento se añadio con exito":"El deparamento NO se añadio");
		listarDepartamentos();
		//System.out.println(borrarDepartamento(44)!=0?"El departamento se ELIMINÓ con exito":"El deparamento NO se ELIMINO");
		//System.out.println("------------------");
		//System.out.println(modificarDepartamento(43,"ONG","Barcelona")!=0?"El departamento se MODIFICÓ con exito":"El deparamento NO se MODIFICÓ");
		//System.out.println("------------------");
		//listarDepartamentos();
		anadirEmpleado("Manuel","Garcia","Lopez","Ventas");
		contarEmpleados();
		cerrarConexion();
	}
 
	private static void contarEmpleados() {
		String sentenciaSql = "SELECT COUNT(*) FROM empleados";
		PreparedStatement sentencia = null;
		ResultSet resultado = null;
		try {
			sentencia = con.prepareStatement(sentenciaSql);
			resultado = sentencia.executeQuery();
			resultado.next();
			System.out.println("Cantidad de empleados: "+ resultado.getInt(1));
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			if (sentencia != null)
				try {
					sentencia.close();
					resultado.close();
				} catch (SQLException sqle) {
					sqle.printStackTrace();
				}
		}
	}
 
	private static void anadirEmpleado(String nombre, String apellido1, String apellido2, String dpto) {
 
	    String sentencia = "SELECT dept_no FROM departamentos WHERE dnombre=?";
	    ResultSet resultado = null;
	    int idDepartamento = -1;
	    try {
	    	//	Obtenemos id del departamento
	        PreparedStatement sentenciaidDepartamento = con.prepareStatement(sentencia);
	        sentenciaidDepartamento.setString(1, dpto);
	        resultado = sentenciaidDepartamento.executeQuery();
 
	        if (resultado.next()) {
	            idDepartamento = resultado.getInt(1); 
	            System.out.println("ID del departamento " + dpto + ": " + idDepartamento);
	        } else {
	            System.out.println("No se encontró el departamento: " + dpto);
	            return; 
	        }
 
	        // Añadimos el empleado con el ID del departamento
	        String sentenciaInsertEmpleado = "INSERT INTO empleados (nombre, apellido1, apellido2, departamento) VALUES (?, ?, ?, ?)";
	        PreparedStatement sentenciaInsert = con.prepareStatement(sentenciaInsertEmpleado);
	        sentenciaInsert.setString(1, nombre);
	        sentenciaInsert.setString(2, apellido1);
	        sentenciaInsert.setString(3, apellido2);
	        sentenciaInsert.setInt(4, idDepartamento); 
	        int filasInsertadas = sentenciaInsert.executeUpdate();
	        if (filasInsertadas > 0) {
	            System.out.println("Empleado añadido correctamente.");
	        } else {
	            System.out.println("No se pudo añadir el empleado.");
	        }
 
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
 
	private static int anadirDepartamento(String dnombre,String loc) {
		// TODO Auto-generated method stub
		String sentenciaSql = "INSERT INTO departamentos (dnombre, loc) VALUES (?, ?)";
		PreparedStatement sentencia = null;
		try {
			sentencia = con.prepareStatement(sentenciaSql);
			sentencia.setString(1, dnombre);
			sentencia.setString(2, loc);
			return sentencia.executeUpdate();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} 	
		return 0;
	}
	private static int borrarDepartamento(int id) {
		// TODO Auto-generated method stub
		String sentenciaSql = "DELETE FROM departamentos WHERE (dept_no=?);";
		PreparedStatement sentencia = null;
		try {
			sentencia = con.prepareStatement(sentenciaSql);
			sentencia.setInt(1, id);
			return sentencia.executeUpdate();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} 	
		return 0;
	}
	private static int modificarDepartamento(int id, String dnombre,String loc) {
		// TODO Auto-generated method stub
		String sentenciaSql = "UPDATE departamentos set dnombre= ?, loc = ? WHERE dept_no = ?";
		PreparedStatement sentencia = null;
		try {
			sentencia = con.prepareStatement(sentenciaSql);
			sentencia.setString(1, dnombre);
			sentencia.setString(2, loc);
			sentencia.setInt(3, id);
 
			return sentencia.executeUpdate();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} 	
		return 0;
	}
	//INSERT INTO departamentos (dnombre, loc) VALUES ("prueba", "Valladolid");
	private static void listarDepartamentos() {
		// TODO Auto-generated method stub
		try {
			PreparedStatement sentencia = con.prepareStatement("select * from departamentos;");
			ResultSet resultado = sentencia.executeQuery();
			while(resultado.next()) {
				System.out.println(resultado.getInt(1) + " " + resultado.getString(2) + " " + resultado.getString(3));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
 
	private static void cerrarConexion() {
		// TODO Auto-generated method stub
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private static void establecerConexion_SQLite() {
		try {
			Class.forName("org.sqlite.JDBC");
			con = DriverManager.getConnection("jdbc:sqlite:"+Utilidades.RUTA+DB);
			PreparedStatement sentencia = con.prepareStatement(
					"CREATE TABLE IF NOT EXISTS departamentos (dept_no INTEGER PRIMARY KEY AUTOINCREMENT , dnombre VARCHAR(15), loc VARCHAR(15));" );
			sentencia.executeUpdate();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	private static void establecerConexion_MySQL() {
		// TODO Auto-generated method stub
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/empresa","root","root");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private static void anadirEmpleado2(String nombre, String apellido1, String apellido2, String dpto) {
	    Scanner scanner = new Scanner(System.in);
	    String obtenerIdDepto = "SELECT dept_no FROM departamentos WHERE dnombre = ?";
	    String insertarEmpleado = "INSERT INTO empleados (nombre, apellido1, apellido2, departamento) VALUES (?, ?, ?, ?)";
	    String contarEmpleadosDepto = "SELECT COUNT(*) FROM empleados WHERE departamento = ?";
	    
	    try {
	        con.setAutoCommit(false); // Inicia la transacción

	        // Paso 1: Obtener ID del departamento
	        PreparedStatement stmtObtenerIdDepto = con.prepareStatement(obtenerIdDepto);
	        stmtObtenerIdDepto.setString(1, dpto);
	        ResultSet resultado = stmtObtenerIdDepto.executeQuery();

	        int idDepartamento;
	        if (resultado.next()) {
	            idDepartamento = resultado.getInt(1);
	            System.out.println("ID del departamento encontrado: " + idDepartamento);

	            // Insertar el empleado en el departamento existente
	            PreparedStatement stmtInsertarEmpleado = con.prepareStatement(insertarEmpleado);
	            stmtInsertarEmpleado.setString(1, nombre);
	            stmtInsertarEmpleado.setString(2, apellido1);
	            stmtInsertarEmpleado.setString(3, apellido2);
	            stmtInsertarEmpleado.setInt(4, idDepartamento);
	            stmtInsertarEmpleado.executeUpdate();

	            // Contar empleados en el departamento
	            PreparedStatement stmtContarEmpleados = con.prepareStatement(contarEmpleadosDepto);
	            stmtContarEmpleados.setInt(1, idDepartamento);
	            ResultSet rsContarEmpleados = stmtContarEmpleados.executeQuery();
	            if (rsContarEmpleados.next()) {
	                System.out.println("Número de empleados en el departamento: " + rsContarEmpleados.getInt(1));
	            }
	            
	            con.commit(); // Confirmar transacción si todo va bien

	        } else {
	            System.out.println("No se encontró el departamento: " + dpto);
	            listarDepartamentos(); // Mostrar lista de departamentos

	            System.out.println("Seleccione una opción:");
	            System.out.println("1. Añadir un nuevo departamento");
	            System.out.println("2. Asignar a un departamento existente");
	            System.out.println("3. Cancelar operación");
	            int opcion = scanner.nextInt();
	            scanner.nextLine(); // Limpiar el buffer

	            switch (opcion) {
	                case 1:
	                    System.out.print("Ingrese el nombre del nuevo departamento: ");
	                    String nuevoDpto = scanner.nextLine();
	                    System.out.print("Ingrese la localización del nuevo departamento: ");
	                    String localizacion = scanner.nextLine();
	                    
	                    // Añadir el nuevo departamento
	                    idDepartamento = anadirDepartamento(nuevoDpto, localizacion);
	                    if (idDepartamento > 0) {
	                        PreparedStatement stmtInsertarEmpleado = con.prepareStatement(insertarEmpleado);
	                        stmtInsertarEmpleado.setString(1, nombre);
	                        stmtInsertarEmpleado.setString(2, apellido1);
	                        stmtInsertarEmpleado.setString(3, apellido2);
	                        stmtInsertarEmpleado.setInt(4, idDepartamento);
	                        stmtInsertarEmpleado.executeUpdate();

	                        System.out.println("Empleado añadido al nuevo departamento.");
	                        con.commit();
	                    } else {
	                        System.out.println("Error al añadir el nuevo departamento.");
	                        con.rollback();
	                    }
	                    break;

	                case 2:
	                    System.out.print("Ingrese el nombre de un departamento existente: ");
	                    String deptoExistente = scanner.nextLine();
	                    
	                    // Obtener ID del departamento existente
	                    stmtObtenerIdDepto.setString(1, deptoExistente);
	                    resultado = stmtObtenerIdDepto.executeQuery();

	                    if (resultado.next()) {
	                        idDepartamento = resultado.getInt(1);

	                        // Insertar el empleado
	                        PreparedStatement stmtInsertarEmpleado = con.prepareStatement(insertarEmpleado);
	                        stmtInsertarEmpleado.setString(1, nombre);
	                        stmtInsertarEmpleado.setString(2, apellido1);
	                        stmtInsertarEmpleado.setString(3, apellido2);
	                        stmtInsertarEmpleado.setInt(4, idDepartamento);
	                        stmtInsertarEmpleado.executeUpdate();

	                        // Contar empleados en el departamento
	                        PreparedStatement stmtContarEmpleados = con.prepareStatement(contarEmpleadosDepto);
	                        stmtContarEmpleados.setInt(1, idDepartamento);
	                        ResultSet rsContarEmpleados = stmtContarEmpleados.executeQuery();
	                        if (rsContarEmpleados.next()) {
	                            System.out.println("Número de empleados en el departamento: " + rsContarEmpleados.getInt(1));
	                        }

	                        con.commit();
	                    } else {
	                        System.out.println("Departamento no encontrado. Operación cancelada.");
	                        con.rollback();
	                    }
	                    break;

	                case 3:
	                    System.out.println("Operación cancelada.");
	                    con.rollback();
	                    break;

	                default:
	                    System.out.println("Opción no válida. Operación cancelada.");
	                    con.rollback();
	                    break;
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        try {
	            con.rollback(); // Revertir transacción en caso de error
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }
	    } finally {
	        try {
	            con.setAutoCommit(true); // Restaurar el modo de confirmación automática
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}
}