package modelo;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

import excepciones.*;

public class Biblioteca implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Amigo> misAmigos;
	private ArrayList<Libro> misLibros;
	private ArrayList<Prestamo> misPrestamos;

	public Biblioteca() 
	{
		this.misAmigos = new ArrayList<>();
		this.misLibros = new ArrayList<>();
		this.misPrestamos = new ArrayList<>();
	}

	public ArrayList<Amigo> getMisAmigos() 
	{
		return misAmigos;
	}

	public void setMisAmigos(ArrayList<Amigo> misAmigos) 
	{
		this.misAmigos = misAmigos;
	}

	public ArrayList<Libro> getMisLibros() 
	{
		return misLibros;
	}

	public void setMisLibros(ArrayList<Libro> misLibros) 
	{
		this.misLibros = misLibros;
	}

	public ArrayList<Prestamo> getMisPrestamos() 
	{
		return misPrestamos;
	}

	public void setMisPrestamos(ArrayList<Prestamo> misPrestamos) 
	{
		this.misPrestamos = misPrestamos;
	}

	public int obtenerPosLibro(String nombre, String nombreAutor) 
	{
		String nombreAux = "";
		int pos = -1;
		boolean centinela = false;
		Libro miLibro = null;
		for (int i = 0; i < getMisLibros().size() && !centinela; i++) {
			miLibro = getMisLibros().get(i);
			nombreAux = miLibro.getNombre();
			if (nombre.equals(nombreAux)&&miLibro.getNombreAutor().equals(nombreAutor)) {
				pos = i;
				centinela = true;
			}
		}
		return pos;
	}

	public Libro obtenerLibro(String nombre, String nombreAutor) throws LibroNullException 
	{
		Libro aux = null;
		int pos = obtenerPosLibro(nombre, nombreAutor);
		if (pos == -1) {
			throw new LibroNullException("El libro: " + nombre + " no se encuentra la Biblioteca: Crisi");
		} else {
			aux = getMisLibros().get(pos);
		}
		return aux;
	}

	public void eliminarLibro(String nombre, String nombreAutor) throws LibroNullException 
	{
		int pos = obtenerPosLibro(nombre, nombreAutor);
		if (pos == -1) {
			throw new LibroNullException("El libro: " + nombre + " no se encuentra en la Biblioteca: Crisi");
		} else {
			getMisLibros().remove(pos);
		}
	}

	public void agregarLibro(String nombre, String nombreAutor,LocalDate fechaPublicacion, ArrayList<Tema> misTemas)
			throws LibroRepeatException {
		int pos = obtenerPosLibro(nombre, nombreAutor);
		if (pos == -1) {
			Libro miLibro = new Libro(nombre, nombreAutor,fechaPublicacion);
			miLibro.setMiBibliotecaAsociada(this);
			miLibro.setMisTemas(misTemas);
			getMisLibros().add(miLibro);
		} else {
			throw new LibroRepeatException("El libro: " + nombre + " ya se encuentra en la Biblioteca: Crisi");
		}
	}

	public int obtenerPosAmigo(String nombre) 
	{
		int pos = -1;
		Amigo miAmigo = null;
		boolean centinela = false;
		String nombreAux = null;
		for (int i = 0; i < getMisAmigos().size() && !centinela; i++) {
			miAmigo = getMisAmigos().get(i);
			nombreAux = miAmigo.getNombre();
			if (nombreAux.equals(nombre)) {
				pos = i;
				centinela = true;
			}
		}
		return pos;
	}

	public Amigo obtenerAmigo(String nombre) throws AmigoNullException 
	{
		int pos = obtenerPosAmigo(nombre);
		Amigo miAmigo = null;
		if (pos == -1) {
			throw new AmigoNullException("El amigo: " + nombre + " no se encuentra en la Biblioteca: Crisi");
		} else {
			miAmigo = getMisAmigos().get(pos);
		}
		return miAmigo;
	}

	public void eliminarAmigo(String nombre) throws AmigoNullException 
	{
		int pos = obtenerPosAmigo(nombre);
		if (pos != -1) {
			getMisAmigos().remove(pos);
		} else {
			throw new AmigoNullException("El amigo: " + nombre + " no se encuentra en la Biblioteca: Crisi");
		}
	}

	public void agregarAmigo(String nombre) throws AmigoRepeatException 
	{
		int pos = obtenerPosAmigo(nombre);
		if (pos == -1) {
			Amigo miAmigo = new Amigo(nombre);
			miAmigo.setMiBibliotecaAsociada(this);
			getMisAmigos().add(miAmigo);
		} else {
			throw new AmigoRepeatException("El amigo: " + nombre + " ya se encuentra en la Biblioteca: Crisi");
		}
	}

	public int obtenerPosPrestamo(String codigo) 
	{
		int pos = -1;
		boolean centinela = false;
		Prestamo miPrestamo = null;
		String codigoAux = null;
		for (int i = 0; i < getMisPrestamos().size() && !centinela; i++) {
			miPrestamo = getMisPrestamos().get(i);
			codigoAux = miPrestamo.getCodigo();
			if (codigoAux.equals(codigo)) {
				pos = i;
				centinela = true;
			}
		}
		return pos;
	}

	public Prestamo obtenerPrestamo(String codigo) throws PrestamoNullException 
	{
		int pos = obtenerPosPrestamo(codigo);
		Prestamo miPrestamo = null;
		if (pos == -1) {
			throw new PrestamoNullException("El prestamo: " + codigo + " no existe en la Biblioteca: Crisi");
		} else {
			miPrestamo = getMisPrestamos().get(pos);
		}
		return miPrestamo;
	}

	public void eliminarPrestamo(String codigo) throws PrestamoNullException 
	{
		int pos = obtenerPosPrestamo(codigo);
		if (pos == -1) {
			throw new PrestamoNullException("El prestamo: " + codigo + " no se encuentra en la Biblioteca: Crisi");
		} else {
			getMisPrestamos().remove(pos);
		}
	}

	public void agregarPrestamo(LocalDate fechaPrestamo, LocalDate fechaDeEntrega, String codigo, Amigo miAmigoAsociado) throws PrestamoRepeatException 
	{
		int pos = obtenerPosPrestamo(codigo);
		if (pos == -1) {
			Prestamo miPrestamo = new Prestamo(fechaPrestamo, fechaDeEntrega, codigo, miAmigoAsociado);
			miAmigoAsociado.agregarPrestamo(miPrestamo.getFechaPrestamo(), miPrestamo.getFechaDeEntrega(), miPrestamo.getCodigo());
			miPrestamo.setMiBibliotecaAsociada(this);
			getMisPrestamos().add(miPrestamo);
		} else {
			throw new PrestamoRepeatException("El prestamo: " + codigo + " ya se encuentra en la Biblioteca: Crisi");
		}
	}

	/*
	public int contarCantidadPrestamosLibro(String nombreLibro) throws LibroNullException {
		int cont = 0;
		Prestamo miPrestamo = null;
		int pos = obtenerPosLibro(nombreLibro);
		if (pos == -1) {
			throw new LibroNullException("El libro: " + nombreLibro + " no se encuentra en la Biblioteca: Crisi");
		} else {
			for (int i = 0; i < getMisPrestamos().size(); i++) {
				miPrestamo = getMisPrestamos().get(i);
				if (miPrestamo.obtenerPosLibro(nombreLibro) != -1)
					cont++;
			}
		}
		return cont;
	}*/

	public Libro obtenerLibroMasPrestado() throws LibroNullException
	{
		Libro miLibro = null;
		int mayor = 0;
		int pos = -1;
		int aux = 0;
		for (int i = 0; i < getMisLibros().size(); i++) 
		{
			miLibro = getMisLibros().get(i);
			aux = miLibro.getMisPrestamos().size();
			if(aux>mayor)
			{
				mayor = aux;
				pos = i;
			}
		}
		if(pos == -1)
		{
			throw new LibroNullException("Error que nunca se puede llegar a dar a menos de que no existan libros en la Biblioteca: Crisi");
		}
		else
		{
			miLibro = getMisLibros().get(pos);
		}
		return miLibro;
	}
	public Amigo obtenerAmigoConMasPrestamos()throws AmigoNullException
	{
		int pos = -1;
		Amigo miAmigo = null;
		int aux = 0;
		int mayor = 0;
		for (int i = 0; i < getMisAmigos().size(); i++) 
		{
			miAmigo = getMisAmigos().get(i);
			aux = miAmigo.getMisPrestamos().size();
			if(aux > mayor)
			{
				mayor = aux;
				pos = i;
			}
		}
		if(pos == -1)
		{
			throw new AmigoNullException("Error que no puede llegar a darse excepto si no existen amigos en la Biblioteca");
		}
		else
		{
			miAmigo = getMisAmigos().get(pos);
		}
		return miAmigo;
	}
	/**
	 * Metodo recursivo que organiza los libros mas prestados
	 * @param misLibrosMasPrestados
	 * @param i
	 * @param j
	 * @param miLibro
	 * @return
	 */
	public ArrayList<Libro> obtenerLibrosMasPrestadosR(ArrayList<Libro> misLibrosMasPrestados, int i, int j, Libro miLibro)
	{
		if(i == misLibrosMasPrestados.size()-1)
		{
			return misLibrosMasPrestados;
		}
		if(j==misLibrosMasPrestados.size()-1)
		{
			j = 0;
			i = i+1;
		}
		miLibro = misLibrosMasPrestados.get(j);
		if(miLibro.getMisPrestamos().size()<misLibrosMasPrestados.get(j+1).getMisPrestamos().size())
		{
			misLibrosMasPrestados.get(j+1).replace(misLibrosMasPrestados.get(j+1), miLibro);
			misLibrosMasPrestados.get(j).replace(miLibro, misLibrosMasPrestados.get(j+1));
		}
		return obtenerLibrosMasPrestadosR(misLibrosMasPrestados, i, j+1, miLibro);
	}
	/**
	 * Metodo que obtiene la lista de Libros mas prestados organizados de mayor a menor
	 * @return una lista de Libros organizada de menor a mayor
	 */
	public ArrayList<Libro> obtenerLibrosMasPrestados()
	{
		ArrayList<Libro> misLibrosMasPrestados = new ArrayList<>();
		for (int i = 0; i < getMisLibros().size(); i++) 
		{
			misLibrosMasPrestados.add(getMisLibros().get(i));
		}
		Libro miLibro = null;
		misLibrosMasPrestados = obtenerLibrosMasPrestadosR(misLibrosMasPrestados, 0, 0, miLibro);
		return misLibrosMasPrestados;
	}
}