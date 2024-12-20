package examen.modelo;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class Ictus {
	private Date fecha_ingreso;
	

	public Ictus() {
		// TODO Auto-generated constructor stub
	}

	public static Set<String> getYears() {
	    try {
	        Conexion.getInstance();
			PreparedStatement sentencia = Conexion.getCon().prepareStatement("Select  (year(fecha_ingreso)) as year from ictus order by year;");
	        ResultSet rS = sentencia.executeQuery();
	        Set<String> years = new HashSet<String>();
	        while (rS.next()) {
	            
	            years.add(rS.getString(1));
	        }
	        return years;
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return null;
	}

}
