import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Principal {

	public static ArrayList<Autor> listaAutor = new ArrayList<Autor>();
	public static ArrayList<Libro> listaLibro = new ArrayList<Libro>();
	
	public static void main(String[] args) throws IOException, ParserConfigurationException, TransformerException, SQLException {
		// TODO creacion de un programa que lea y escriba ficheros de texto
		leerCSV();
		generarXML();
		insertarBBDD();

	}

	private static void insertarBBDD() throws SQLException {
		// TODO realizamos un insert en una bbdd
		Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/eval1", "root", "");
		
		PreparedStatement psAutor = conexion.prepareStatement("INSERT INTO autor (codigo,nombre,pais) VALUES (?,?,?)");
		for(int i = 0; i < listaAutor.size(); i++) {
			psAutor.setInt(1, listaAutor.get(i).getCodigo());
			psAutor.setString(2, listaAutor.get(i).getNombre());
			psAutor.setString(3, listaAutor.get(i).getPais());
			psAutor.executeUpdate();
		}
		
		PreparedStatement psLibro = conexion.prepareStatement("INSERT INTO libro (isnb,titulo,editorial,codAutor) VALUES (?,?,?,?)");
		for(int i = 0; i < listaLibro.size(); i++) {
			psLibro.setString(1, listaLibro.get(i).getIsbn());
			psLibro.setString(2, listaLibro.get(i).getTitulo());
			psLibro.setString(3, listaLibro.get(i).getEditorial());
			psLibro.setInt(4, listaLibro.get(i).getCodigoAutor());
			psLibro.executeUpdate();
		}
		
	}

	private static void generarXML() throws ParserConfigurationException, TransformerException {
		// TODO a partir de los datos obtenidos antes, generar un archivo XML
		DocumentBuilderFactory factoria =DocumentBuilderFactory.newInstance();
		DocumentBuilder db = factoria.newDocumentBuilder();
		Document documento = db.newDocument();
		
		// SE EMPIEZA PLANTEANDO LA ESTRUCTURA
		Element biblioteca = documento.createElement("biblioteca");
		documento.appendChild(biblioteca);
		
		//	BUCLE QUE ITERA SOBRE LA PRIMERA LISTA
		
		Element autores = documento.createElement("autores");
		biblioteca.appendChild(autores);
		
		for(int i =0; i<listaAutor.size();i++) {
			Element autor = documento.createElement("autor");
			autores.appendChild(autor);
			
			Element codigo = documento.createElement("codigo");
			codigo.setTextContent(String.valueOf(listaAutor.get(i).getCodigo()));
			autor.appendChild(codigo);
			
			Element nombre = documento.createElement("nombre");
			nombre.setTextContent(listaAutor.get(i).getNombre());
			autor.appendChild(nombre);
			
			Element pais = documento.createElement("pais");
			pais.setTextContent(listaAutor.get(i).getPais());
			autor.appendChild(pais);
		}
		
		//	BUCLE QUE ITERA SOBRE LA SEGUNDA LISTA
		
		Element libros = documento.createElement("libros");
		biblioteca.appendChild(libros);
		
		for(int i =0; i<listaLibro.size();i++) {
			Element libro = documento.createElement("libro");
			libros.appendChild(libro);
			
			Element isbn = documento.createElement("isbn");
			isbn.setTextContent(listaLibro.get(i).getIsbn());
			libro.appendChild(isbn);
			
			Element titulo = documento.createElement("titulo");
			titulo.setTextContent(listaLibro.get(i).getTitulo());
			libro.appendChild(titulo);
			
			Element editorial = documento.createElement("editorial");
			editorial.setTextContent(listaLibro.get(i).getEditorial());
			libro.appendChild(editorial);
			
			Element codigoAutor= documento.createElement("codigoAutor");
			codigoAutor.setTextContent(String.valueOf(listaLibro.get(i).getCodigoAutor()));
			libro.appendChild(codigoAutor);
		}
		
		//CREACION DEL DOCUMENTO
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer trans = tf.newTransformer();
		
		DOMSource dom = new DOMSource(documento);
		
		StreamResult sr = new StreamResult("C:\\PRUEBAS\\PRACTICA5\\biblioteca.xml");
		
		trans.transform(dom, sr);
		
		
	}

	private static void leerCSV() throws IOException {
		// TODO LECTURA DE DOS FICHEROS DE TEXTO CSV
		Path ficheroAutor = Paths.get("C:\\PRUEBAS\\PRACTICA5\\Autor.csv");
		Path ficheroLibro = Paths.get("C:\\PRUEBAS\\PRACTICA5\\Libros.csv");
		
		//	LECTURA FICHERO AUTOR´
		BufferedReader brF = Files.newBufferedReader(ficheroAutor);
		String linea;
		String[] valores;
		while ((linea = brF.readLine()) != null) {
			valores = linea.split(",");
			Autor autor1 = new Autor(Integer.parseInt(valores[0]), valores[1], valores[2]);
			listaAutor.add(autor1);
			System.out.println(linea);
		}
		
		//	LECTURA FICHERO LIBRO
		BufferedReader brL = Files.newBufferedReader(ficheroLibro);
		while((linea = brL.readLine()) != null) {
			valores = linea.split(",");
			Libro libro1 = new Libro(valores[0], valores[1], valores[2], Integer.parseInt(valores[3]));
			listaLibro.add(libro1);
			System.out.println(linea);
		}
	}

}
