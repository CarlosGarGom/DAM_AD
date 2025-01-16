package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

import modelo.Hospitales;
import modelo.Ictus;
import modelo.Modelo;
import vista.Vista;

public class Controlador {

	private Modelo modelo;
	private Vista vista;


	public Controlador(modelo.Modelo modelo, vista.Vista vista) {
		super();
		this.modelo = modelo;
		this.vista = vista;

		vista.btnConsultaCasos.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//Introducimos manualmente el año porque no termina de funcionar la carga en el combobox
				String anio = vista.comboBoxAnio.getSelectedItem().toString();;
				String edadMenor  = vista.textFieldEdadMinima.getText();
				String edadMayor  = vista.textFieldEdadMaxima.getText();
				String sexo = vista.comboBoxSexo.getSelectedItem().toString();
				if(!anio.isEmpty() & !edadMenor.isEmpty() & !edadMayor.isEmpty() & !sexo.isEmpty()){
					vista.textFieldCasos.setText(String.valueOf(modelo.obtenerDatosContador(anio, edadMenor, edadMayor, sexo)));
				}

			}

		});
		
		
		vista.btnGeneraXml.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				modelo.generarPeriodosXML();
			}
			
		});

	}


}
