package controlador;

import java.util.ArrayList;

import excepciones.AmigoNullException;
import excepciones.LibroNullException;
import modelo.Amigo;
import modelo.Libro;

public interface IControlBiblioteca extends IControlLibros, IControlAmigos, IControlPrestamos
{
	public Libro obtenerLibroMasPrestado() throws LibroNullException;
	public Amigo obtenerAmigoConMasPrestamos()throws AmigoNullException;
	public ArrayList<Libro> obtenerLibrosMasPrestadosR(ArrayList<Libro> misLibrosMasPrestados, int i, int j, Libro miLibro);
	public ArrayList<Libro> obtenerLibrosMasPrestados();
}
