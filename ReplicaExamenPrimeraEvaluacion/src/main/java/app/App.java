package app;

import controlador.Controlador;
import modelo.Modelo;
import vista.Vista;

public class App {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Controlador controlador = new Controlador(new Modelo(), new Vista());
	}

}