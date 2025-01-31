package app;

import java.util.HashSet;

import javax.xml.xquery.XQConnection;
import javax.xml.xquery.XQException;

import modelo.ModeloXQJ;
import modelo.clasesXML.Modulo;

public class App {

	public App() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		ModeloXQJ modeloxqj= new ModeloXQJ("admin", "admin");
		XQConnection con = modeloxqj.getCon();
		modeloxqj.metaInformacion(con);
		
		modeloxqj.muestraModulosCiclo(con, "DAM");
		
		HashSet<String> ciclos = new HashSet<String>();
		ciclos.add("DAM");
		
		Modulo modulo = new  Modulo("0003", "Acceso a Datos", 180, 2, ciclos);
		
		modeloxqj.anadirModulo(con, modulo);
		try {
			con.close();
		} catch (XQException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
