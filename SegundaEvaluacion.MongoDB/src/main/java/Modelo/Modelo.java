package Modelo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;

public class Modelo {

	private static MongoClient cliente;
	private static MongoDatabase db;

	public static void main(String[] args) {
		
		conexionLocal();
//		conexionRemota();
		ArrayList<String> personajes = new ArrayList<String>(Arrays.asList("Sancho Panza","Don Quijote"));
		
//		 Listar todos
		 mostrarLibros();
//		// Mostrar libros por género
//		 System.out.println("MOSTRAR POR GENERO");
//		 mostrarLibros("ciencia ficción");
//	       // Mostrar libros por autor
//	        mostrarLibrosPorAutor("Miguel Cervantes");
//
//	        // Eliminar un libro por nombre
//	        eliminarLibro("Don Quijote");
//
//	        // Eliminar libros por género
//	        eliminarLibroGenero("novela");
//
//	        // Modificar género de un libro
//	        modificarLibroGenero("Don Quijote", "literatura");
//
//	        // Añadir campo precio
//	        ponerPrecios(20.0);
//
//	        // Actualizar precios con un porcentaje
//	        actualizarPrecios(10); // Incrementar 10%
		cliente.close();
	}

	private static void anadirLibroAutorReferenciado(String titulo, String genero, String nombreAutor, String nacionalidadAutor, int nacimientoAutor, ArrayList<String> personajes) {
		Document docAutor = null;
		ObjectId id;
		docAutor = db.getCollection("autores").find(Filters.and(
				Filters.eq("nombre", nombreAutor), 
				Filters.eq("nacionalidad", nacionalidadAutor), 
				Filters.eq("nacimiento", nacimientoAutor))).first();
		
		// Si no existe el autor lo añade en la colección autores
		if (docAutor==null) {
			docAutor = new Document()
					.append("nacimiento", nacimientoAutor)
					.append("nacionalidad", nacionalidadAutor)
					.append("nombre", nombreAutor);
			db.getCollection("autores").insertOne(docAutor);
		}
		id = docAutor.getObjectId("_id");
		
		
		Document docLibro = new Document()
				.append("titulo", titulo)
				.append("genero", genero)
				.append("autor", id) 
				.append("personajes", personajes);
		db.getCollection("libros").insertOne(docLibro);
	}
	
	private static void mostrarLibros() {
		FindIterable<Document> fitL = db.getCollection("libros").find();
		Iterator<Document> itL = fitL.iterator();
		while(itL.hasNext()) {
			System.out.println(itL.next().toString());
		}
	}
	
	private static void mostrarLibros(String genero) {
		FindIterable<Document> fitL = db.getCollection("libros").find(Filters.eq("genero", genero));
		Iterator<Document> itL = fitL.iterator();
		while(itL.hasNext()) {
			System.out.println(itL.next().toString());
		}
	}
	
	private static void mostrarLibros(String titulo, String nombreAutor) {
		HashSet<ObjectId> idsAutor = new HashSet<ObjectId>();
		
		//Obtengo el ObjectId del autor
		FindIterable<Document> fitA = db.getCollection("autores").find(Filters.eq("nombre", nombreAutor)); 
		Iterator<Document> itA = fitA.iterator();
		while(itA.hasNext()) {
			idsAutor.add(itA.next().getObjectId("_id"));
		}
		// Faltarían dos opciones más: autor nombre directamente y autro id 
		FindIterable<Document> fitL = db.getCollection("libros").find(
				Filters.or(
					Filters.and(
						Filters.eq("titulo", titulo), 
						Filters.eq("autor.nombre", nombreAutor)
					),
					Filters.and(
						Filters.eq("titulo", titulo), 
						Filters.in("autor", idsAutor)
					)
				));
		Iterator<Document> itL = fitL.iterator();
		while(itL.hasNext()) {
			System.out.println(itL.next().toString());
		}
	}
	







    // Método eliminarLibro
    private static void eliminarLibro(String titulo) {
        MongoCollection<Document> libros = db.getCollection("libros");
        libros.deleteOne(Filters.eq("titulo", titulo));
        System.out.println("Libro eliminado: " + titulo);
    }

    // Método eliminarLibroGenero
    private static void eliminarLibroGenero(String genero) {
        MongoCollection<Document> libros = db.getCollection("libros");
        libros.deleteMany(Filters.eq("genero", genero));
        System.out.println("Todos los libros del género '" + genero + "' han sido eliminados.");
    }

    // Método modificarLibroGenero
    private static void modificarLibroGenero(String titulo, String nuevoGenero) {
        MongoCollection<Document> libros = db.getCollection("libros");
        UpdateResult result = libros.updateOne(Filters.eq("titulo", titulo), Updates.set("genero", nuevoGenero));
        System.out.println("Género actualizado para el libro: " + titulo + " (" + result.getModifiedCount() + " documentos modificados)");
    }

    // Método ponerPrecios
    private static void ponerPrecios(double precioDefault) {
        MongoCollection<Document> libros = db.getCollection("libros");
        UpdateResult result = libros.updateMany(Filters.exists("precio", false), Updates.set("precio", precioDefault));
        System.out.println(result.getModifiedCount() + " libros actualizados con el precio por defecto de " + precioDefault);
    }

    // Método actualizarPrecios
    private static void actualizarPrecios(int porcentaje) {
        MongoCollection<Document> libros = db.getCollection("libros");
        for (Document libro : libros.find(Filters.exists("precio"))) {
            double precioActual = libro.getDouble("precio");
            double nuevoPrecio = precioActual + (precioActual * porcentaje / 100);
            libros.updateOne(Filters.eq("_id", libro.get("_id")), Updates.set("precio", nuevoPrecio));
        }
        System.out.println("Precios actualizados en un " + porcentaje + "%.");
    }

	private static void conexionRemota() {

		 String connectionString = "mongodb+srv://carlosgarciagomez:Xdrj2BJOWoVM7cVw@cluster.gt6rq.mongodb.net/biblioteca?retryWrites=true&w=majority\r\n"
		 		+ "";
	        ServerApi serverApi = ServerApi.builder()
	                .version(ServerApiVersion.V1)
	                .build();
	        MongoClientSettings settings = MongoClientSettings.builder()
	                .applyConnectionString(new ConnectionString(connectionString))
	                .serverApi(serverApi)
	                .build();
	        // Create a new client and connect to the server
	        cliente = MongoClients.create(settings);
	        db = cliente.getDatabase("biblioteca");
		
	}

	private static void conexionLocal() {
		// TODO Auto-generated method stub
		try {
			cliente = MongoClients.create();
			db= cliente.getDatabase("biblioteca");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
