package Modelo;

import java.util.ArrayList;
import java.util.Arrays;

import org.bson.Document;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class Modelo {

	private static MongoClient cliente;
	private static MongoDatabase db;

	public static void main(String[] args) {
		
//		conexionLocal();
		conexionRemota();
		ArrayList<String> personajes = new ArrayList<String>(Arrays.asList("Sancho Panza","Don Quijote"));
		anadirLibro("Don Quijote", "novela","Miguel Cervantes","Español",1946,personajes);
		cliente.close();
	}

	private static void anadirLibro(String titulo, String genero, String nombreAutor, String nacionalidadAutor, int nacimientoAutor, ArrayList<String> personajes) {
		// TODO Auto-generated method stub
		Document doc = new Document()
				.append("titulo",titulo)
				.append("genero",genero)
				.append("autor", nombreAutor)
				.append("personajes", personajes);
		
		db.getCollection("libros").insertOne(doc);
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
