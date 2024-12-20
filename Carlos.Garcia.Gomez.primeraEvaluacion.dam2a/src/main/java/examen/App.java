package examen;

import examen.controlador.Controlador;
import examen.modelo.Modelo;
import examen.vista.Vista_manana_paraAlumnos;

public class App {

	public static void main(String[] args) {
		
		Controlador controlador = new Controlador(new Modelo(), new Vista_manana_paraAlumnos());
		
	}
	

}
