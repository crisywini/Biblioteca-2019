package persistencia;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.*;
import modelo.*;

public class Persistencia 
{
	public static final String RUTA_BIBLIOTECA_XML = "src/resources/BibliotecaCrisi.XML";
	public static final String RUTA_BIBLIOTECA = "src/resources/BibliotecaCrisi.dat";
	public static final String RUTA_LIBROS = "src/resources/LibrosCrisi.txt";
	public static final String RUTA_PRESTAMOS = "src/resources/PrestamosBiblioteca.txt";
	public static final String RUTA_AMIGOS = "src/resources/AmigosCrisi.txt";
	
	public static void serializarBiblioteca(Object objeto) throws IOException 
	{
		FileOutputStream archivoBiblioteca = new FileOutputStream(RUTA_BIBLIOTECA_XML);
		XMLEncoder codificador = new XMLEncoder(archivoBiblioteca);
		codificador.writeObject(objeto);
		codificador.close();
		archivoBiblioteca.close();
	}
	public static Object deserializarBiblioteca() throws IOException
	{
		Object biblioteca = null;
		FileInputStream archivoBiblioteca = new FileInputStream(RUTA_BIBLIOTECA_XML);
		XMLDecoder decodificador = new XMLDecoder(archivoBiblioteca);
		biblioteca = decodificador.readObject();
		decodificador.close();
		archivoBiblioteca.close();
		return biblioteca;
	}
	public static void serializarBibliotecaB(Object objeto) throws IOException
	{
		FileOutputStream archivoBiblioteca = new FileOutputStream(RUTA_BIBLIOTECA);
		ObjectOutputStream serializador = new ObjectOutputStream(archivoBiblioteca);
		serializador.writeObject(objeto);
		serializador.close();
	}
	public static Object deserializarBibliotecaB() throws IOException, ClassNotFoundException
	{
		Object biblioteca;
		FileInputStream archivoBiblioteca = new FileInputStream(RUTA_BIBLIOTECA);
		ObjectInputStream deserializador = new ObjectInputStream(archivoBiblioteca);
		biblioteca = deserializador.readObject();
		deserializador.close();
		return biblioteca;
		
	}
	public static void guardarArchivo(String ruta, ArrayList<String> informacionArchivo) throws IOException
	{
		FileWriter archivoEscritor = new FileWriter(ruta, false);
		BufferedWriter direccionadorArchivo = new BufferedWriter(archivoEscritor);
		for (String linea : informacionArchivo) 
		{
			direccionadorArchivo.write(linea);
		}
		direccionadorArchivo.flush();
		direccionadorArchivo.close();
		archivoEscritor.close();
	}
	public static ArrayList<String> leerArchivo(String ruta) throws IOException
	{
		FileReader archivoLector = new FileReader(ruta);
		BufferedReader direccionadorArchivo = new BufferedReader(archivoLector);
		ArrayList<String> informacionArchivo = new ArrayList<>();
		String linea = "";
		while((linea = direccionadorArchivo.readLine())!=null)
		{
			informacionArchivo.add(linea+"\n");
		}
		direccionadorArchivo.close();
		archivoLector.close();
		return informacionArchivo;
	}
	public static void guardarLibrosEnArchivo(ArrayList<Libro> libros) throws IOException
	{
		ArrayList<String> informacionArchivo = new ArrayList<>();
		String linea = "";
		Libro miLibro = null;
		for (int i = 0; i < libros.size(); i++) 
		{
			miLibro = libros.get(i);
			linea = "<"+miLibro.getNombre()+"$("+miLibro.getFechaPublicacion()+")$"+miLibro.getMisTemas()+">"+"\n";
			informacionArchivo.add(linea);
		}
		guardarArchivo(RUTA_LIBROS, informacionArchivo);
	}
	public static void guardarAmigosEnArchivo(ArrayList<Amigo> amigos) throws IOException
	{
		ArrayList<String> informacionArchivo = new ArrayList<>();
		String linea = "";
		Amigo miAmigo = null;
		for (int i = 0; i < amigos.size(); i++) 
		{
			miAmigo = amigos.get(i);
			linea = "<"+miAmigo.getNombre()+">\n";
			informacionArchivo.add(linea);
		}
		guardarArchivo(RUTA_AMIGOS, informacionArchivo);
	}
	public static void guardarPrestamosEnArchivo(ArrayList<Prestamo> prestamos) throws IOException
	{
		ArrayList<String> informacionArchivo = new ArrayList<>();
		String linea = "";
		Prestamo miPrestamo = null;
		for (int i = 0; i < prestamos.size(); i++) 
		{
			miPrestamo = prestamos.get(i);
			linea = "<"+miPrestamo.getCodigo()+"$("+miPrestamo.getFechaPrestamo()+")$("+miPrestamo.getFechaDeEntrega()+")$"+miPrestamo.getMiAmigoAsociado().getNombre()+"$"+miPrestamo.getMisLibrosAsociados()+">\n";
			informacionArchivo.add(linea);
		}
		guardarArchivo(RUTA_PRESTAMOS, informacionArchivo);
	}
}