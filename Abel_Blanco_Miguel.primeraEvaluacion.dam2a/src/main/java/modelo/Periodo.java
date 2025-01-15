package modelo;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;

public class Periodo {

    private int anio;
    private int totalCasos;
    private Edad edad;

    @XmlAttribute(name = "anio")
    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    @XmlElement(name = "total_casos")
    public int getTotalCasos() {
        return totalCasos;
    }

    public void setTotalCasos(int totalCasos) {
        this.totalCasos = totalCasos;
    }

    @XmlElement
    public Edad getEdad() {
        return edad;
    }

    public void setEdad(Edad edad) {
        this.edad = edad;
    }
}

