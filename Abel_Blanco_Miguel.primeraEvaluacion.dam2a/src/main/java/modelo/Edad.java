package modelo;

import jakarta.xml.bind.annotation.XmlElement;

public class Edad {

    private int hombres;
    private int mujeres;

    @XmlElement
    public int getHombres() {
        return hombres;
    }

    public void setHombres(int hombres) {
        this.hombres = hombres;
    }

    @XmlElement
    public int getMujeres() {
        return mujeres;
    }

    public void setMujeres(int mujeres) {
        this.mujeres = mujeres;
    }
}
