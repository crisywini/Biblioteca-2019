package controlador;

import java.time.LocalDate;
import java.util.ArrayList;

import excepciones.LibroNullException;
import excepciones.LibroRepeatException;
import modelo.Libro;
import modelo.Tema;

public interface IControlLibros 
{
	public ArrayList<Libro> getMisLibros();
	public void setMisLibros(ArrayList<Libro> misLibros);
	public int obtenerPosLibro(String nombre, String nombreAutor);
	public Libro obtenerLibro(String nombre, String nombreAutor) throws LibroNullException;
	public void eliminarLibro(String nombre, String nombreAutor) throws LibroNullException;
	public void agregarLibro(String nombre, String nombreAutor,LocalDate fechaPublicacion, ArrayList<Tema> misTemas)throws LibroRepeatException;
}
