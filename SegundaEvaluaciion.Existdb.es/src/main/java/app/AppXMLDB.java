package app;

import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.XMLDBException;

import modelo.ModeloXMLDB;

public class AppXMLDB {

	private static String user = "admin";
	private static String password ="admin";
	private static String URICol= "xmldb:exist://localhost:8080/exist/xmlrpc/db/";
	
	public static void main(String[] args) {
	
		
		
		try {
			DatabaseManager dbM = ModeloXMLDB.getCon();
//			ModeloXMLDB.listarContenido(dbM.getCollection(URICol+"ejercicio_clase",user,password));
//			ModeloXMLDB.creaBorraColeccion(dbM.getCollection(URICol+"ejercicio_clase",user,password), "datosHacienda",'c');
//			ModeloXMLDB.creaBorraColeccion(dbM.getCollection(URICol+"ejercicio_clase",user,password), "coleccion2",'b');
//			ModeloXMLDB.creaCreaDocumentoString(dbM.getCollection(URICol+"ejercicio_clase/datosHacienda",user,password), "mascotas.xml","<mascotas><mascota tipo='perro' nombre='lolo'/></mascotas>");
//			ModeloXMLDB.descargarDocumento(dbM.getCollection(URICol+"ejercicio_clase/datosHacienda",user,password), "mascotas.xml","C:\\Users\\admin\\Documents\\docs");
			ModeloXMLDB.subirDocumento(dbM.getCollection(URICol+"ejercicio_clase/datosHacienda",user,password),"C:\\Users\\admin\\Documents\\docs\\mascotas.xml");
		} catch (XMLDBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
