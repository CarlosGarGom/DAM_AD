package simulacro.modelo;

import java.sql.Date;

import jakarta.xml.bind.annotation.XmlElement;

public class Poblacion {
    private String nombre;
    private Provincia provincia;
    private Date fecha;
    private String fecha2;

    public Poblacion(String nombre, Provincia provincia, Date fecha) {
        this.nombre = nombre;
        this.provincia = provincia;
        this.fecha = fecha;
    }

    public Poblacion(String nombre, String fecha2) {
		// TODO Auto-generated constructor stub
    	  this.nombre = nombre;
          this.fecha2 = fecha2;

	}

	@XmlElement(name = "nombre")
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Provincia getProvincia() {
        return provincia;
    }

    public void setProvincia(Provincia provincia) {
        this.provincia = provincia;
    }

    @XmlElement(name = "fecha")
    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}