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

        /*
         * Meter datos del excel ictus a la tabla SQL
         * Cargar datos comboboxaño con fecha_ingreso
         * Cargar combobox ssexo con enumeracion
         * 
         * boton consultar casos que dice el total de casos del año y del sexo
         * 
         * Boton generar XML con JAXB
         */
	}
}
