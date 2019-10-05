package modelo;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

import excepciones.PrestamoNullException;
import excepciones.PrestamoRepeatException;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Amigo implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nombre;
	private ArrayList<Prestamo> misPrestamos;
	private Biblioteca miBibliotecaAsociada;

	public Amigo() 
	{
		this("");
	}
	public Amigo(String nombre)
	{
		this.nombre = nombre;
		this.misPrestamos = new ArrayList<>();
	}
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public StringProperty nombreProperty()
	{
		StringProperty nombreProperty = new SimpleStringProperty(this.nombre);
		return nombreProperty;
	}
	public ArrayList<Prestamo> getMisPrestamos() {
		return misPrestamos;
	}
	public void setMisPrestamos(ArrayList<Prestamo> misPrestamos) {
		this.misPrestamos = misPrestamos;
	}
	public Biblioteca getMiBibliotecaAsociada() {
		return miBibliotecaAsociada;
	}
	public void setMiBibliotecaAsociada(Biblioteca miBibliotecaAsociada) {
		this.miBibliotecaAsociada = miBibliotecaAsociada;
	}
	public int obtenerPosPrestamo(String codigoPrestamo)
	{
		int pos = -1;
		Prestamo miPrestamo = null;
		String codigoAux = null;
		boolean centinela = false;
		for (int i = 0; i < getMisPrestamos().size()&&!centinela; i++) 
		{
			miPrestamo = getMisPrestamos().get(i);
			codigoAux = miPrestamo.getCodigo();
			if(codigoAux.equals(codigoPrestamo))
			{
				pos = i;
				centinela = true;
			}
		}
		return pos;
	}
	public Prestamo obtenerPrestamo(String codigoPrestamo)
	{
		Prestamo miPrestamo = null;
		int pos = obtenerPosPrestamo(codigoPrestamo);
		if(pos == -1)
		{
			throw new PrestamoNullException("El prestamo: "+ codigoPrestamo+" no se encuentra en la lista de prestamos de: "+getNombre());
		}
		else
		{
			miPrestamo = getMisPrestamos().get(pos);
		}
		return miPrestamo;
	}
	public void agregarPrestamo(LocalDate fechaPrestamo, LocalDate fechaDeEntrega, String codigo) throws PrestamoRepeatException
	{
		int pos = obtenerPosPrestamo(codigo);
		if(pos == -1)
		{
			Prestamo miPrestamo = new Prestamo(fechaPrestamo, fechaDeEntrega, codigo, this);
			getMisPrestamos().add(miPrestamo);
		}
		else
		{
			throw new PrestamoRepeatException("El prestamo: "+ codigo+ " ya se encuentra en la lista de prestamos de: "+getNombre());
		}
	}
	public void eliminarPrestamo(String codigoPrestamo)throws PrestamoNullException
	{
		int pos = obtenerPosPrestamo(codigoPrestamo);
		if(pos==-1)
		{
			throw new PrestamoNullException("El prestamo: "+codigoPrestamo+ " no se encuentra en la lista de prestamos de: "+ getNombre());
		}
		else
		{
			getMisPrestamos().remove(pos);
		}
	}
	public int obtenerPosLibroEnLista(Libro miLibro, ArrayList<Libro> misLibros)
	{
		int pos = -1;
		boolean centinela = false;
		Libro aux = null;
		for (int i = 0; i < misLibros.size()&&!centinela; i++) 
		{
			aux = misLibros.get(i);
			if(aux.equals(miLibro))
			{
				pos = i;
				centinela = true;
			}
		}
		return pos;
	}
	public void agregarLibrosAListaDeLibros(ArrayList<Libro> misLibros, ArrayList<Libro> misLibrosAux)
	{
		int pos = -1;
		Libro miLibro = null;
		for (int i = 0; i < misLibrosAux.size(); i++) 
		{
			miLibro = misLibros.get(i);
			pos = obtenerPosLibroEnLista(miLibro, misLibros);
			if(pos == -1)
			{
				misLibros.add(miLibro);
			}
		}	
	}
	public void agregarLibroAListaDeLibros(Libro miLibro, ArrayList<Libro> misLibros)
	{
		int pos = obtenerPosLibroEnLista(miLibro, misLibros);
		if(pos==-1)
		{
			misLibros.add(miLibro);
		}
	}
	
	public ArrayList<Libro> obtenerListaLibrosAmigo()
	{
		Prestamo miPrestamoAux = null;
		ArrayList<Libro> misLibros = new ArrayList<>();
		ArrayList<Libro> aux = null;
		for (int i = 0; i < getMisPrestamos().size(); i++) 
		{
			miPrestamoAux = getMisPrestamos().get(i);
			aux = miPrestamoAux.getMisLibrosAsociados();
			agregarLibrosAListaDeLibros(misLibros, aux);
		}
		return misLibros;
	}
	
	public int contarLibroEnPrestamos(Libro miLibro)
	{
		int cont = 0;
		Prestamo miPrestamo = null;
		for (int i = 0; i < getMisPrestamos().size(); i++) 
		{
			miPrestamo = getMisPrestamos().get(i);
			if(miPrestamo.getMisLibrosAsociados().contains(miLibro))
				cont++;
		}
		return cont;
	}
	public ArrayList<Libro> obtenerLibrosMasPrestados()
	{
		ArrayList<Libro> librosMasPrestados = new ArrayList<>();
		ArrayList<Libro> misLibrosAux = obtenerListaLibrosAmigo();
		Libro aux = null;
		for (int i = 0; i < misLibrosAux.size(); i++) 
		{
			aux = misLibrosAux.get(i);
			if(contarLibroEnPrestamos(aux)>0)
				agregarLibroAListaDeLibros(aux, librosMasPrestados);
		}
		return librosMasPrestados;
	}
	public int obtenerCantidadPrestamosSegunFecha(LocalDate fecha)
	{
		int cantidad = 0;
		Prestamo miPrestamo = null;
		LocalDate fechaAux = LocalDate.now();
		for (int i = 0; i < getMisPrestamos().size(); i++) 
		{
			miPrestamo = getMisPrestamos().get(i);
			fechaAux = miPrestamo.getFechaPrestamo();
			if(fechaAux.equals(fecha))
				cantidad++;
		}
		return cantidad;
	}
	@Override
	public String toString() 
	{
		String informacion = ""+getNombre();
		return informacion;
	}
}