package MVC_Ejercicio18.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import MVC_Ejercicio18.modelo.Departamento;
import MVC_Ejercicio18.modelo.Modelo;
import MVC_Ejercicio18.vista.Vista;

public class Controlador {
	Modelo modelo;
	Vista vista;
	public Controlador(Modelo modelo, Vista vista) {
		super();
		this.modelo = modelo;
		this.vista = vista;
		
		vista.btnGuardar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				modelo.anadirDpto(vista.textFieldNombre.getText(), vista.textFieldLocalidad.getText());
				limpiaControles();
			}
		});
		
		vista.btnListar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//modelo.listarDptos();
				rellenaTabla();
				
				
			}
		});
		
		vista.btnNuevo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				limpiaControles();
			}
		});
		
		vista.tableResultados.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				int filas = vista.tableResultados.getSelectedRow();
				vista.textFieldNombre.setText((vista.modeloTBbl.getValueAt(filas,1).toString()));
			}
		});
		
		vista.btnBorrar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int filas=vista.tableResultados.getSelectedRow();
				modelo.borrarDpto(Integer.valueOf(vista.modeloTBbl.getValueAt(filas, 0).toString()));
				rellenaTabla();
				limpiaControles();
			}
		});
		vista.btnModificar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int filas=vista.tableResultados.getSelectedRow();
				modelo.modificarDpto(Integer.valueOf(vista.modeloTBbl.getValueAt(filas, 0).toString()),
						vista.textFieldNombre.getText(),vista.textFieldLocalidad.getText());
				rellenaTabla();
				limpiaControles();
			}
		});
	}
	
	

	protected void rellenaTabla() {
		// TODO Auto-generated method stub
		vista.modeloTBbl.setNumRows(0);
		ArrayList<Departamento> dptoListado = modelo.listarDptos();
		for(Departamento dpto: dptoListado) {
			Object[] fila = {dpto.getDepNum(),dpto.getDepNombre(),dpto.getDepLocalidad()};
			vista.modeloTBbl.addRow(fila);
		}
	}

	protected void limpiaControles() {
		vista.textFieldNombre.setText(null);
		vista.textFieldLocalidad.setText(null);
	}
	
	
	
	
	

}
