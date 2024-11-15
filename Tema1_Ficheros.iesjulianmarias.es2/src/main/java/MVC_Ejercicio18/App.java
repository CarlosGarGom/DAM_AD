package MVC_Ejercicio18;

import MVC_Ejercicio18.controlador.Controlador;
import MVC_Ejercicio18.modelo.Modelo;
import MVC_Ejercicio18.vista.Vista;

public class App {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Controlador controlador = new Controlador(new Modelo(), new Vista());
	}

}
