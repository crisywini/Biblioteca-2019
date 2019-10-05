package modelo;

import java.io.Serializable;

import java.time.LocalDate;
import java.util.ArrayList;

import excepciones.PrestamoNullException;
import excepciones.TemaNullException;
import excepciones.TemaRepeatException;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Libro implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nombre;
	private String nombreAutor;
	private LocalDate fechaPublicacion;
	private ArrayList<Tema> misTemas;
	private ArrayList<Prestamo> misPrestamos;
	private Biblioteca miBibliotecaAsociada;
	private EstadoLibro miEstadoLibro;
	
	public Libro()
	{
		this("", "",LocalDate.now());//Se debe poner asi el constructor porque puede generar una InstatiationException
	}
	public Libro(String nombre, String nombreAutor ,LocalDate fechaPublicacion) 
	{
		this.nombre = nombre;
		this.setNombreAutor(nombreAutor);
		this.fechaPublicacion = fechaPublicacion;
		this.misTemas = new ArrayList<>();
		this.misPrestamos = new ArrayList<>();
		this.setMiEstadoLibro(EstadoLibro.DISPONIBLE);
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
	public LocalDate getFechaPublicacion() {
		return fechaPublicacion;
	}
	public void setFechaPublicacion(LocalDate fechaPublicacion) {
		this.fechaPublicacion = fechaPublicacion;
	}
	public ArrayList<Tema> getMisTemas() {
		return misTemas;
	}
	public void setMisTemas(ArrayList<Tema> misTemas) {
		this.misTemas = misTemas;
	}
	public StringProperty temasProperty()
	{
		StringProperty temasProperty = new SimpleStringProperty(this.misTemas.toString());
		return temasProperty;
	}
	public Biblioteca getMiBibliotecaAsociada() {
		return miBibliotecaAsociada;
	}
	public void setMiBibliotecaAsociada(Biblioteca miBibliotecaAsociada) {
		this.miBibliotecaAsociada = miBibliotecaAsociada;
	}
	public ArrayList<Prestamo> getMisPrestamos() {
		return misPrestamos;
	}
	public void setMisPrestamos(ArrayList<Prestamo> misPrestamos) {
		this.misPrestamos = misPrestamos;
	}
	public StringProperty nombreAutorProperty()
	{
		StringProperty nombreProperty = new SimpleStringProperty(this.nombreAutor);
		return nombreProperty;
	}
	public String getNombreAutor() {
		return nombreAutor;
	}
	public void setNombreAutor(String nombreAutor) {
		this.nombreAutor = nombreAutor;
	}
	public EstadoLibro getMiEstadoLibro() {
		return miEstadoLibro;
	}
	public void setMiEstadoLibro(EstadoLibro miEstadoLibro) {
		this.miEstadoLibro = miEstadoLibro;
	}
	public StringProperty estadoProperty()
	{
		String estado = "";
		if(this.miEstadoLibro==EstadoLibro.DISPONIBLE)
		{
			estado = "Disponible";
		}
		else
		{
			estado = "Prestado";
		}
		StringProperty estadoProperty = new SimpleStringProperty(estado);
		return estadoProperty;
	}
	public StringProperty cantidadPrestamosProperty()
	{
		StringProperty cantidadPrestamosProperty = new SimpleStringProperty(this.getMisPrestamos().size()+"");
		return cantidadPrestamosProperty;
	}
	public StringProperty fechaPublicacionProperty()
	{
		StringProperty fechaPublicacionProperty = new SimpleStringProperty(this.fechaPublicacion+"");
		return fechaPublicacionProperty;
	}
	public int obtenerPosTema(String nombre)
	{
		int pos = -1;
		boolean centinela = false;
		Tema miTema = null;
		for (int i = 0; i < misTemas.size()&&!centinela; i++) 
		{
			miTema = misTemas.get(i);
			if(miTema.getNombre().equals(nombre))
			{
				pos = i;
				centinela = true;
			}
		}
		return pos;
	}
	public Tema obtenerTema(String nombre)throws TemaNullException
	{
		int pos = obtenerPosTema(nombre);
		Tema miTema = null;
		if(pos == -1)
		{
			throw new TemaNullException("El Tema: "+nombre+" no se encuentra en este libro: "+ getNombre());
		}
		else
		{
			miTema = getMisTemas().get(pos);
		}
		return miTema;
	}
	public void guardarTema(String nombre) throws TemaRepeatException
	{
		int pos = obtenerPosTema(nombre);
		if(pos==-1)
		{
			getMisTemas().add(new Tema(nombre, this));
		}
		else
		{
			throw new TemaRepeatException("El tema: "+ nombre+"ya se encuentra en el libro: "+ getNombre());
		}
	}
	public void eliminarTema(String nombre)
	{
		int pos = obtenerPosTema(nombre);
		if(pos == -1)
		{
			throw new TemaNullException("El tema: "+nombre+" no se encuentra en el libro: "+ getNombre());
		}
		else
		{
			getMisTemas().remove(pos);
		}
	}
	public void guardarTemas(ArrayList<Tema> temas) throws TemaRepeatException
	{
		for (int i = 0; i < temas.size(); i++) 
		{
			guardarTema(temas.get(i).getNombre());
		}
	}
	public int obtenerPosPrestamo(String codigo) {
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

	public Prestamo obtenerPrestamo(String codigo) throws PrestamoNullException {
		int pos = obtenerPosPrestamo(codigo);
		Prestamo miPrestamo = null;
		if (pos == -1) {
			throw new PrestamoNullException("El prestamo: " + codigo + " no existe en la lista de prestamos del libro: "+getNombre());
		} else {
			miPrestamo = getMisPrestamos().get(pos);
		}
		return miPrestamo;
	}

	public void eliminarPrestamo(String codigo) throws PrestamoNullException {
		int pos = obtenerPosPrestamo(codigo);
		if (pos == -1) {
			throw new PrestamoNullException("El prestamo: " + codigo + " no se encuentra en la lista de prestamos del libro: "+getNombre());
		} else {
			getMisPrestamos().remove(pos);
		}
	}
	@Override
	public boolean equals(Object obj) 
	{
		Libro miLibro;
		boolean centinela = false;
		if(obj instanceof Libro)
		{
			miLibro = (Libro)obj;
			if(miLibro.getNombre().equals(this.getNombre())&&miLibro.getNombreAutor().equals(this.getNombreAutor()))
			{
				centinela = true;
			}
		}
		return centinela;
	}
	/**
	 * Metodo que remplaza un Libro por otro, lo que hace es setear los atributos por los atributos del siguiente libro
	 * @param anterior libro que se considera this
	 * @param nuevo libro nuevo por el que se setea
	 */
	public void replace(Libro anterior, Libro nuevo)
	{
		Libro miLibro = nuevo;
		if(anterior.equals(this))
		{
			this.setFechaPublicacion(miLibro.getFechaPublicacion());
			this.setMisPrestamos(miLibro.getMisPrestamos());
			this.setMisTemas(miLibro.getMisTemas());
			this.setNombre(miLibro.getNombre());
			this.setNombreAutor(miLibro.getNombreAutor());
		}
	}
	@Override
	public String toString() 
	{
		String info = "("+getNombre()+") autor: ("+nombreAutor+") estado: ("+getMiEstadoLibro()+")";
		return info;
	}
	public int obtenerCantidadDePrestamosSegunFecha(LocalDate fecha)
	{
		int cantidad = 0;
		LocalDate fechaAux = LocalDate.now();
		for (int i = 0; i < getMisPrestamos().size(); i++) 
		{
			fechaAux = getMisPrestamos().get(i).getFechaPrestamo();
			if(fechaAux.equals(fecha))
			{
				cantidad++;
			}
		}
		return cantidad;
	}
}