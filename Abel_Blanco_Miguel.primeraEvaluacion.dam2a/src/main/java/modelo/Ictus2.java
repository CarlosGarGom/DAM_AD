package modelo;

import java.util.List;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ictus")
public class Ictus2 {

	 private List<Periodo> periodos;

	    @XmlElementWrapper(name = "periodos")
	    @XmlElement(name = "periodo")
	    public List<Periodo> getPeriodos() {
	        return periodos;
	    }

	    public void setPeriodos(List<Periodo> periodos) {
	        this.periodos = periodos;
	    }

	
}


