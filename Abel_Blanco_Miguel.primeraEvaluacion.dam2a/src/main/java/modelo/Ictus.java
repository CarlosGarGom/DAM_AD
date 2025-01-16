package modelo;

import java.util.Date;



public class Ictus {

	private int id_ictus;
	private Date fecha_ingreso;
	private int hospital;
	private int edad;
	private int sexo;
	private int anio;
	
	
	public Ictus() {
		
	}
	
	
	public Ictus(int anio) {
		this.anio = anio;
	}
	
	public Ictus(Date fecha_ingreso) {
		this.fecha_ingreso = fecha_ingreso;
	}


	public Ictus(int id_ictus, Date fecha_ingreso, int hospital, int edad, int sexo) {
		super();
		this.id_ictus = id_ictus;
		this.fecha_ingreso = fecha_ingreso;
		this.hospital = hospital;
		this.edad = edad;
		this.sexo = sexo;
	}


	public int getId_ictus() {
		return id_ictus;
	}
	
	public int get_anioIctus() {
		return anio;
	}


	public void setId_ictus(int id_ictus) {
		this.id_ictus = id_ictus;
	}


	public Date getFecha_ingreso() {
		return fecha_ingreso;
	}


	public void setFecha_ingreso(Date fecha_ingreso) {
		this.fecha_ingreso = fecha_ingreso;
	}


	public int getHospital() {
		return hospital;
	}


	public void setHospital(int hospital) {
		this.hospital = hospital;
	}


	public int getEdad() {
		return edad;
	}


	public void setEdad(int edad) {
		this.edad = edad;
	}


	public int getSexo() {
		return sexo;
	}


	public void setSexo(int sexo) {
		this.sexo = sexo;
	}
	
	public static int obtenerAnio(Date fecha_ingreso) {
		return fecha_ingreso.getYear();
	}
	
	
}
