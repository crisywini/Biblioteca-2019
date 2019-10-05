package modelo;

import java.io.Serializable;

import java.time.LocalDate;
import java.util.ArrayList;

import excepciones.LibroNullException;
import excepciones.LibroRepeatException;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Prestamo implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LocalDate fechaPrestamo;
	private LocalDate fechaDeEntrega;
	private String codigo;
	private Amigo miAmigoAsociado;
	private ArrayList<Libro> misLibrosAsociados;
	private Biblioteca miBibliotecaAsociada;
	
	
	public Prestamo(LocalDate fechaPrestamo, LocalDate fechaDeEntrega, String codigo, Amigo miAmigoAsociado) {
		this.fechaPrestamo = fechaPrestamo;
		this.fechaDeEntrega = fechaDeEntrega;
		this.codigo = codigo;
		this.miAmigoAsociado = miAmigoAsociado;
		this.misLibrosAsociados = new ArrayList<>();
	}
	public Prestamo() 
	{
		this(LocalDate.now(), LocalDate.now(), "", null);
	}
	

	public LocalDate getFechaPrestamo() {
		return fechaPrestamo;
	}
	public void setFechaPrestamo(LocalDate fechaPrestamo) {
		this.fechaPrestamo = fechaPrestamo;
	}
	public StringProperty fechaPrestamoProperty()
	{
		StringProperty fechaPrestamoProperty = new SimpleStringProperty(this.fechaPrestamo+"");
		return fechaPrestamoProperty;
	}
	public StringProperty fechaEntregaProperty()
	{
		StringProperty fechaEntregaProperty = new SimpleStringProperty(this.fechaDeEntrega+"");
		return fechaEntregaProperty;
	}
	public StringProperty codigoProperty()
	{
		StringProperty codigoProperty = new SimpleStringProperty(this.codigo);
		return codigoProperty;
	}
	public StringProperty amigoProperty()
	{
		StringProperty amigoProperty = new SimpleStringProperty(this.miAmigoAsociado.toString());
		return amigoProperty;
	}
	public LocalDate getFechaDeEntrega() {
		return fechaDeEntrega;
	}
	public void setFechaDeEntrega(LocalDate fechaDeEntrega) {
		this.fechaDeEntrega = fechaDeEntrega;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public Amigo getMiAmigoAsociado() {
		return miAmigoAsociado;
	}
	public void setMiAmigoAsociado(Amigo miAmigoAsociado) {
		this.miAmigoAsociado = miAmigoAsociado;
	}
	public ArrayList<Libro> getMisLibrosAsociados() {
		return misLibrosAsociados;
	}
	public void setMisLibrosAsociados(ArrayList<Libro> misLibrosAsociados) {
		this.misLibrosAsociados = misLibrosAsociados;
	}
	public Biblioteca getMiBibliotecaAsociada() {
		return miBibliotecaAsociada;
	}

	public void setMiBibliotecaAsociada(Biblioteca miBibliotecaAsociada) {
		this.miBibliotecaAsociada = miBibliotecaAsociada;
	}

	public int obtenerPosLibro(String nombre, String nombreAutor)
	{
		String nombreAux = "";
		int pos = -1;
		boolean centinela = false;
		Libro miLibro = null;
		for (int i = 0; i < getMisLibrosAsociados().size()&&!centinela; i++) 
		{
			miLibro = getMisLibrosAsociados().get(i);
			nombreAux = miLibro.getNombre();
			if(nombre.equals(nombreAux)&& miLibro.getNombreAutor().equals(nombreAutor))
			{
				pos = i;
				centinela = true;
			}
		}
		return pos;
	}
	public Libro obtenerLibro(String nombre, String nombreAutor)throws LibroNullException
	{
		Libro aux = null;
		int pos = obtenerPosLibro(nombre, nombreAutor);
		if(pos==-1)
		{
			throw new LibroNullException("El libro: "+nombre+" no se encuentra en el prestamo: "+getCodigo());
		}
		else
		{
			aux = getMisLibrosAsociados().get(pos);
		}
		return aux;
	}
	public void eliminarLibro(String nombre, String nombreAutor) throws LibroNullException
	{
		int pos = obtenerPosLibro(nombre, nombreAutor);
		if(pos == -1)
		{
			throw new LibroNullException("El libro: "+nombre+" no se encuentra en el prestamo: "+ getCodigo());
		}
		else
		{
			Libro miLibro = getMisLibrosAsociados().get(pos);
			miLibro.setMiEstadoLibro(EstadoLibro.DISPONIBLE);
			getMisLibrosAsociados().remove(pos);
		}
	}
	/**
	 * Metodo que agrega un libro a un prestamo, a su vez setea el prestamo presente al libro
	 * @param nombre
	 * @throws LibroRepeatException
	 * @throws LibroNullException
	 */
	public void agregarLibroPrestamo(String nombre, String nombreAutor) throws LibroRepeatException, LibroNullException
	{
		int posLibroBiblioteca = getMiBibliotecaAsociada().obtenerPosLibro(nombre, nombreAutor);
		if(posLibroBiblioteca != -1)
		{
			int pos = obtenerPosLibro(nombre, nombreAutor);				
		    Libro miLibro = getMiBibliotecaAsociada().obtenerLibro(nombre, nombreAutor);

		    if(miLibro.getMiEstadoLibro()==EstadoLibro.DISPONIBLE)
		    {
		    	if(pos == -1)
				{
		    		miLibro.setMiEstadoLibro(EstadoLibro.PRESTADO);
					miLibro.getMisPrestamos().add(this);
					getMisLibrosAsociados().add(miLibro);
				}
				else
				{
					throw new LibroRepeatException("El libro: "+ nombre+ " ya se encuentra en el prestamo");
				}
		    }
		    else
		    {
		    	throw new LibroRepeatException("El libro se encuentra prestado");
		    }
		}
		else
		{
			throw new LibroNullException("El libro: "+nombre+" no se encuentra en la Biblioteca: Crisi");
		}
	}
	public void liberarLibros()
	{
		Libro miLibro = null;
		for (int i = 0; i < getMisLibrosAsociados().size(); i++) 
		{
			miLibro = getMisLibrosAsociados().get(i);
			liberarLibro(miLibro);
		}
	}
	public void liberarLibro(Libro miLibro)
	{
		miLibro.setMiEstadoLibro(EstadoLibro.DISPONIBLE);
	}
	@Override
	public String toString() 
	{
		String info = "Codigo: "+getCodigo()+"\nFecha Inicio: "+getFechaPrestamo()+"\nFecha Entrega: "+getFechaDeEntrega()+
				      "\nAmigo: "+getMiAmigoAsociado().toString()+"\nLibros: "+getMisLibrosAsociados().toString();
		return info;
	}
}