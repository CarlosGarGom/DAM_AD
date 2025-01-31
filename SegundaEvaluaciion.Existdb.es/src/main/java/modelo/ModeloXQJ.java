package modelo;

import javax.xml.xquery.*;

import modelo.clasesXML.Modulo;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import net.xqj.exist.ExistXQDataSource;


public class ModeloXQJ {

	private  String user;
	private  String password;


	public ModeloXQJ(String user, String password) {
		super();
		this.user = user;
		this.password = password;
	}
	public static XQConnection getCon() {
		XQDataSource xqs = new ExistXQDataSource();
		XQConnection conn = null;
		try {
			xqs.setProperty("serverName", "localhost");
			xqs.setProperty("port", "8080");
			xqs.setProperty("user","admin");
			xqs.setProperty("password","admin");

			conn = xqs.getConnection();
			
		} catch (XQException e) {
			
			e.printStackTrace();
			
		}
		return conn;
	}
	public static void main(String[] args) throws XQException
	{


	}
	public void metaInformacion(XQConnection cnn) {
		try {
			XQMetaData xqmd = cnn.getMetaData();
			System.out.println("Nombre de usuario: " + xqmd.getUserName() +
					"\nSoporte de transacciones: " + xqmd.isTransactionSupported() +
					"\nSoporte de XQuery: " + xqmd.isXQueryXSupported() +
					"\nSolo lectura: " + xqmd.isReadOnly());
		} catch (XQException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Mostramos el nombre y los módulos de un ciclo cuyas siglas se pasan como parámetro
	 * @param siglas
	 */
	public void muestraModulosCiclo(XQConnection cnn, String siglas) {
		
		String consultaNombreCiclo = "doc('/db/ejercicio_clase/ciclos.xml')/fp/ciclos/ciclo[@siglas='" + siglas + "']/nombre";
		String consultaModulosCiclo = "for $m in doc('/db/ejercicio_clase/ciclos.xml')/fp/modulos/modulo" +
				" where $m/ciclos/ciclo/@siglas='" + siglas + "' return $m/nombre";
		System.out.println("Nombre del ciclo: ");
		muestraResultados(cnn, consultaNombreCiclo);
		System.out.println("Módulos: ");
		muestraResultados(cnn, consultaModulosCiclo);
	}

	private void muestraResultados(XQConnection cnn, String consultaNombreCiclo) {
		try {
			XQExpression xqe = cnn.createExpression();
			XQResultSequence xqrs = xqe.executeQuery(consultaNombreCiclo);
			while(xqrs.next()) {
				XMLStreamReader  xsr = xqrs.getItemAsStream();
				while(xsr.hasNext()) {
					if (xsr.getEventType()==XMLStreamConstants.CHARACTERS) {
						System.out.println("\t" + xsr.getText());
					}
//					if (xsr.getEventType()==XMLStreamConstants.START_ELEMENT) {
//						System.out.println("Comienzo de elemento");
//					}
					xsr.next();
				}
			}
		} catch (XQException | XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void anadirModulo(XQConnection cnn, Modulo datos) {
		
		try {
			XQExpression xqe = cnn.createExpression();
			String elemento = String.format(
					"update insert <modulo codigo='%s'>" +
					        "<nombre>%s</nombre>" +
					        "<duracion unidad='horas'>%d</duracion>" +
					        "<curso>%d</curso><ciclos>", datos.getCodigo(), datos.getNombre(), datos.getHoras(), datos.getCurso());
			StringBuilder elementoSB = new StringBuilder(elemento);
			for(String ciclo: datos.getCiclo()) {
				elementoSB = elementoSB.append("<ciclo siglas='" + ciclo + "'/>");
			}
			elementoSB.append("</ciclos></modulo> into doc('/db/ejercicio_clase/ciclos.xml')/fp/modulos").toString();
			xqe.executeCommand(elementoSB.toString());
		} catch (XQException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
