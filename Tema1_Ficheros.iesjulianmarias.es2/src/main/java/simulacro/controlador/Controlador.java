package simulacro.controlador;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import simulacro.modelo.Conexion;
import simulacro.modelo.GeneradorXML;
import simulacro.modelo.InsertarDatosDAO;
import simulacro.modelo.Poblacion;
import simulacro.modelo.Provincia;
import simulacro.vista.Consola;
import utilidades.Utilidades;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

public class Controlador {

    private static final String EXCEL_FILE = "IngresosIctus.xlsx";

    public void iniciar() {
        try{
        	Connection con = Conexion.establecerConexion();
            FileInputStream fis = new FileInputStream(new File(Utilidades.RUTA+ EXCEL_FILE));
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
//            GeneradorXML generador = new GeneradorXML();
//            generador.generarArchivoXML(con, Utilidades.RUTA+"datos.xml");
            
          Sheet sheet = workbook.getSheetAt(0);
           int numFila = 1;
           Row fila = sheet.getRow(numFila);

            InsertarDatosDAO dao = new InsertarDatosDAO();
           Map<String, Provincia> provinciaMap = new HashMap<>();

          while (fila != null) {
                String nombrePoblacion = fila.getCell(0).getStringCellValue().trim();
                String nombreProvincia = fila.getCell(1).getStringCellValue().trim();
                Date fecha = new Date(fila.getCell(2).getDateCellValue().getTime());

              Provincia provincia = provinciaMap.computeIfAbsent(nombreProvincia, prov -> {
                   try {
                       int id = dao.insertarProvincia(new Provincia(prov), con);
                        return new Provincia(prov) {{ setId(id); }};
                    } catch (Exception e) {
                        Consola.mostrarError("Error al insertar provincia: " + e.getMessage());
                        return null;
                    }
             });

               if (provincia != null) {
                  dao.insertarPoblacion(new Poblacion(nombrePoblacion, provincia, fecha), con);
               }

               fila = sheet.getRow(++numFila);
           }

           Consola.mostrarMensaje("Datos insertados correctamente.");
        		

        } catch (Exception e) {
            Consola.mostrarError("Error durante la ejecución: " + e.getMessage());
        }
    }
}
