package simulacro.modelo;

import java.util.List;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "provincia")
public class Provincia {
	private int id;
    private String nombre;
    private List<Poblacion> poblaciones;
    public Provincia(String nombre) {
        this.nombre = nombre;
    }

    public Provincia(String nombre, List<Poblacion> poblaciones) {
		// TODO Auto-generated constructor stub
    	this.nombre = nombre;
    	this.poblaciones = poblaciones;
	}

	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    @XmlElement(name = "nombre")
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
