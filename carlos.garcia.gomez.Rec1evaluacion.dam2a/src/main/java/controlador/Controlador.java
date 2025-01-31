package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import modelo.Modelo;
import vista.Vista;

public class Controlador {
	
	private Modelo modelo;
	private Vista vista;
	public Controlador(Modelo modelo, Vista vista) {
		this.modelo = modelo;
		this.vista = vista;
		
		vista.btnDatosDB.addActionListener(new ActionListener(){
			

			@Override
			public void actionPerformed(ActionEvent e) {
				 Boolean valor = modelo.llenaTablasAlbumes();
				 if (valor==true) {
					 disableBtnDatosDB();
				 }
				
			}
		});
		
		vista.btnGeneraXml.addActionListener(new ActionListener(){
			
			
			@Override
			public void actionPerformed(ActionEvent e) {
				 Boolean valor = modelo.creaDocumentoXML_AlbumesPorPaises();
				 if (valor==true) {
					 disableBtnGeneraXml();
				 }
				
				
			}
		});
		
	}
	public void disableBtnDatosDB() {
        vista.btnDatosDB.setEnabled(false);
    }
	public void disableBtnGeneraXml() {
        vista.btnGeneraXml.setEnabled(false);
    }

}
