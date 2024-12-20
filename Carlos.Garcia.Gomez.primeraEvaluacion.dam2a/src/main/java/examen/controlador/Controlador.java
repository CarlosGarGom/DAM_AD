package examen.controlador;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

import examen.modelo.Ictus;
import examen.modelo.Modelo;
import examen.vista.Vista_manana_paraAlumnos;
import examen.modelo.Sexo;

public class Controlador {

	private Modelo modelo;
	private Vista_manana_paraAlumnos vista;
	public Controlador(Modelo modelo, Vista_manana_paraAlumnos vista) {
		super();
		this.modelo = modelo;
		this.vista = vista;
		

		//rellenarComboBox();
		
		/*vista.btnConsultaCasos.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});*/
		

	}

	protected void rellenarComboBox() {
		for(Sexo sexo: Sexo.values()) {
			vista.comboBoxSexo.addItem(sexo);
		}
		Set<String> years = Ictus.getYears();
		for(String year:years) {
			vista.comboBoxAnio.addItem(year.toString());
	}
	}

}
