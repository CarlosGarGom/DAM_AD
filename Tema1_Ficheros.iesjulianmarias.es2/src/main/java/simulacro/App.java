package simulacro;

import java.sql.Connection;
import java.util.List;

import simulacro.controlador.Controlador;
import simulacro.modelo.Conexion;
import simulacro.modelo.InsertarDatosDAO;
import simulacro.modelo.Poblacion;

public class App {

	public static void main(String[] args) {
        Controlador controlador = new Controlador();
        controlador.iniciar();


	}
}
