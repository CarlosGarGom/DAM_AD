package modelo.clasesJAXB;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "albumesPorPaises")
public class AlbumesPorPaises {
    private List<Pais> paises;

    public AlbumesPorPaises() {
        this.paises = new ArrayList<>();
    }

    @XmlElement(name = "pais")
    public List<Pais> getPaises() {
        return paises;
    }

    public void setPaises(List<Pais> paises) {
        this.paises = paises;
    }
}
