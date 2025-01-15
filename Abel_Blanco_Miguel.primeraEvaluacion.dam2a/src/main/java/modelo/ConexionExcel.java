package modelo;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ConexionExcel {
    private static ConexionExcel instance;
    private Workbook workbook;
    private String filePath;

    // Constructor privado para prevenir instanciación externa
    private ConexionExcel(String filePath) {
        this.filePath = filePath;
        this.workbook = cargarWorkbook(filePath);
    }

    // Método para obtener la instancia única
    public static ConexionExcel getInstance(String filePath) {
        if (instance == null) {
            synchronized (ConexionExcel.class) {
                if (instance == null) {
                    instance = new ConexionExcel(filePath);
                }
            }
        }
        return instance;
    }

    // Método para cargar el Workbook
    private Workbook cargarWorkbook(String filePath) {
        try (FileInputStream fis = new FileInputStream(new File(filePath))) {
            return new XSSFWorkbook(fis);
        } catch (IOException e) {
            System.err.println("Error al cargar el archivo Excel: " + e.getMessage());
            throw new RuntimeException("No se pudo cargar el archivo Excel", e);
        }
    }

    // Método para obtener el Workbook
    public Workbook getWorkbook() {
        if (workbook == null) {
            workbook = cargarWorkbook(filePath);
        }
        return workbook;
    }

    // Método para cerrar el Workbook
    public void cerrarConexion() {
        if (workbook != null) {
            try {
                workbook.close();
                workbook = null;
                instance = null; 
            } catch (IOException e) {
                System.err.println("Error al cerrar el archivo Excel: " + e.getMessage());
            }
        }
    }
}
