package controlador;

import java.io.File;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import excepciones.AmigoNullException;
import excepciones.AmigoRepeatException;
import excepciones.LibroNullException;
import excepciones.LibroRepeatException;
import excepciones.PrestamoNullException;
import excepciones.PrestamoRepeatException;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import modelo.*;
import persistencia.Persistencia;

public class Principal extends Application implements IControlBiblioteca
{

	private Biblioteca miBiblioteca;
	private static final ObservableList<Libro> booksData = FXCollections.observableArrayList();
	private static final ObservableList<Amigo> amigosData = FXCollections.observableArrayList();
	private static final ObservableList<Prestamo> prestamosData = FXCollections.observableArrayList();
	@Override
	public void start(Stage primaryStage) 
	{
		cargarDatos();
		if(miBiblioteca == null)
		{
			miBiblioteca = new Biblioteca();
		}
		initVentanaPrincipal(primaryStage);
	}

	public static void main(String[] args) {
		launch(args);
	}
	public void initVentanaPrincipal(Stage primaryStage)
	{
		try 
		{
			FXMLLoader cargador = new FXMLLoader();
			cargador.setLocation(Principal.class.getResource("../vista/VentanaPrincipal.fxml"));
			
			AnchorPane panelPrincipal = (AnchorPane)cargador.load();
			Scene scene = new Scene(panelPrincipal);
			VentanaPrincipalController controlador = cargador.getController();
			controlador.setPrimaryStage(primaryStage);
			controlador.setMiPrincipal(this);
			primaryStage.setScene(scene);
			primaryStage.getIcons().add(new Image("file:src/imagenes/Biblioteca-setup-icon.png"));
			primaryStage.show();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	public void cargarDatos()
	{
		Biblioteca miBiblioteca;
		File archivoBiblioteca = new File(Persistencia.RUTA_BIBLIOTECA_XML);
		if(archivoBiblioteca.exists())
		{
			try 
			{
				miBiblioteca = (Biblioteca)Persistencia.deserializarBibliotecaB();
				this.miBiblioteca = miBiblioteca;
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			} 
			catch (ClassNotFoundException e) 
			{
				
				e.printStackTrace();
			}
		}
		if(this.miBiblioteca!=null)
			cargarColecciones();
			
	}
	public void cargarColecciones()
	{
		getAmigosdata().setAll(getMisAmigos());
		getBooksdata().setAll(getMisLibros());
		getPrestamosdata().setAll(getMisPrestamos());
	}
	public void guardarDatos()
	{
		try 
		{
			Persistencia.serializarBibliotecaB(miBiblioteca);
			Persistencia.guardarAmigosEnArchivo(getMisAmigos());
			Persistencia.guardarLibrosEnArchivo(getMisLibros());
			Persistencia.guardarPrestamosEnArchivo(getMisPrestamos());
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

	public Biblioteca getMiBiblioteca() {
		return miBiblioteca;
	}

	//ColeccionesFX
	public void setMiBiblioteca(Biblioteca miBiblioteca) {
		this.miBiblioteca = miBiblioteca;
	}

	public static ObservableList<Libro> getBooksdata() {
		return booksData;
	}

	public static ObservableList<Amigo> getAmigosdata() {
		return amigosData;
	}

	public static ObservableList<Prestamo> getPrestamosdata() {
		return prestamosData;
	}

	//Services
	@Override
	public ArrayList<Libro> getMisLibros() 
	{	
		return this.miBiblioteca.getMisLibros();
	}

	@Override
	public void setMisLibros(ArrayList<Libro> misLibros) 
	{
		this.miBiblioteca.setMisLibros(misLibros);
	}

	@Override
	public int obtenerPosLibro(String nombre, String nombreAutor) 
	{
		return this.miBiblioteca.obtenerPosLibro(nombre, nombreAutor);
	}

	@Override
	public Libro obtenerLibro(String nombre, String nombreAutor) throws LibroNullException 
	{
		return this.miBiblioteca.obtenerLibro(nombre, nombreAutor);
	}

	@Override
	public void eliminarLibro(String nombre, String nombreAutor) throws LibroNullException 
	{
		this.miBiblioteca.eliminarLibro(nombre, nombreAutor);
	}

	@Override
	public void agregarLibro(String nombre, String nombreAutor, LocalDate fechaPublicacion, ArrayList<Tema> misTemas)
			throws LibroRepeatException 
	{
		this.miBiblioteca.agregarLibro(nombre, nombreAutor, fechaPublicacion, misTemas);
	}

	@Override
	public ArrayList<Amigo> getMisAmigos() 
	{
		return this.miBiblioteca.getMisAmigos();
	}

	@Override
	public void setMisAmigos(ArrayList<Amigo> misAmigos) 
	{
		this.miBiblioteca.setMisAmigos(misAmigos);
	}

	@Override
	public int obtenerPosAmigo(String nombre) 
	{
		return this.miBiblioteca.obtenerPosAmigo(nombre);
	}

	@Override
	public Amigo obtenerAmigo(String nombre) throws AmigoNullException 
	{
		return this.miBiblioteca.obtenerAmigo(nombre);
	}

	@Override
	public void eliminarAmigo(String nombre) throws AmigoNullException 
	{
		this.miBiblioteca.eliminarAmigo(nombre);
	}

	@Override
	public void agregarAmigo(String nombre) throws AmigoRepeatException 
	{
		this.miBiblioteca.agregarAmigo(nombre);
	}

	@Override
	public ArrayList<Prestamo> getMisPrestamos() 
	{
		return this.miBiblioteca.getMisPrestamos();
	}

	@Override
	public void setMisPrestamos(ArrayList<Prestamo> misPrestamos) 
	{
		this.miBiblioteca.setMisPrestamos(misPrestamos);
	}

	@Override
	public int obtenerPosPrestamo(String codigo) 
	{
		return this.miBiblioteca.obtenerPosPrestamo(codigo);
	}

	@Override
	public Prestamo obtenerPrestamo(String codigo) throws PrestamoNullException 
	{
		return this.miBiblioteca.obtenerPrestamo(codigo);
	}

	@Override
	public void eliminarPrestamo(String codigo) throws PrestamoNullException 
	{
		this.miBiblioteca.eliminarPrestamo(codigo);
	}

	@Override
	public void agregarPrestamo(LocalDate fechaPrestamo, LocalDate fechaDeEntrega, String codigo, Amigo miAmigoAsociado) throws PrestamoRepeatException 
	{
		this.miBiblioteca.agregarPrestamo(fechaPrestamo, fechaDeEntrega, codigo, miAmigoAsociado);
	}

	@Override
	public Libro obtenerLibroMasPrestado() throws LibroNullException 
	{
		return this.miBiblioteca.obtenerLibroMasPrestado();
	}

	@Override
	public Amigo obtenerAmigoConMasPrestamos() throws AmigoNullException 
	{
		return this.miBiblioteca.obtenerAmigoConMasPrestamos();
	}

	@Override
	public ArrayList<Libro> obtenerLibrosMasPrestadosR(ArrayList<Libro> misLibrosMasPrestados, int i, int j,
			Libro miLibro) 
	{
		return this.miBiblioteca.obtenerLibrosMasPrestadosR(misLibrosMasPrestados, i, j, miLibro);
	}

	@Override
	public ArrayList<Libro> obtenerLibrosMasPrestados() 
	{
		return this.miBiblioteca.obtenerLibrosMasPrestados();
	}	
}