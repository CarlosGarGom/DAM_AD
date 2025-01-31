package modelo.clasesXML;

import java.util.HashSet;

public class Modulo {
	private String codigo;
	private String nombre;
	private int horas;
	private int curso;
	private HashSet<String> ciclo;
	
	public Modulo(String codigo, String nombre, int horas, int curso, HashSet<String> ciclo) {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
		this.horas = horas;
		this.curso = curso;
		this.ciclo = ciclo;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getHoras() {
		return horas;
	}

	public void setHoras(int horas) {
		this.horas = horas;
	}

	public int getCurso() {
		return curso;
	}

	public void setCurso(int curso) {
		this.curso = curso;
	}

	public HashSet<String> getCiclo() {
		return ciclo;
	}

	public void setCiclo(HashSet<String> ciclo) {
		this.ciclo = ciclo;
	}
	


}
