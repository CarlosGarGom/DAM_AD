package modelo;

public class Hospitales {

	private int id_hospital;
	private String nombre;
	private int id_provincia;
	
	public Hospitales() {
		
	}

	public Hospitales(int id_hospital, String nombre, int id_provincia) {
		super();
		this.id_hospital = id_hospital;
		this.nombre = nombre;
		this.id_provincia = id_provincia;
	}
	
	public Hospitales(int id_hospital, String nombre) {
		super();
		this.id_hospital = id_hospital;
		this.nombre = nombre;

	}

	public int getId_hospital() {
		return id_hospital;
	}

	public void setId_hospital(int id_hospital) {
		this.id_hospital = id_hospital;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getId_provincia() {
		return id_provincia;
	}

	public void setId_provincia(int id_provincia) {
		this.id_provincia = id_provincia;
	}
	
	
	
	
}
