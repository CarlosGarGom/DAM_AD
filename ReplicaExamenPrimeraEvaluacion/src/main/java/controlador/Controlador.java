package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import modelo.Modelo;
import modelo.Sexo;
import vista.Vista;


public class Controlador {

	private Modelo modelo;
	private Vista vista;
	
public Controlador(Modelo modelo, Vista vista) {
		
		this.modelo = modelo;
		this.vista = vista;
		
		cargaDatosVista();
		
		vista.btnConsultaCasos.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				 
				
				int valor = modelo.calculaCasos(Integer.valueOf(vista.comboBoxAnio.getSelectedItem().toString()), Integer.valueOf(vista.textFieldEdadMinima.getText()),
						Integer.valueOf(vista.textFieldEdadMaxima.getText()), vista.comboBoxSexo.getSelectedItem().toString());
				vista.textFieldCasos.setText(String.valueOf(valor));   
			}
			
		});
		
//		vista.btnGeneraXml.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				//TODO controlar valor devuelto por el método y deshabilitar en ese caso
//				modelo.creaDocumentoXML_Ictus();
//				vista.btnGeneraXml.setEnabled(false);
//			}
//			
//		});
	}

	private void cargaDatosVista() {
		
		for(Integer anio: modelo.obtenerAños()) {
			vista.comboBoxAnio.addItem(anio);
		}
		for(Sexo sexo: Sexo.values()) {
			vista.comboBoxSexo.addItem(sexo.toString());
		}
	}
}
