package modelo;

import java.io.Serializable;

public class Tema implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nombre;
	private Libro miLibroAsociado;
	
	public Tema(String nombre, Libro miLibroAsociado) 
	{
		this.setNombre(nombre);
		this.setMiLibroAsociado(miLibroAsociado);
	}
	public Tema(String nombre)
	{
		this.setNombre(nombre);
	}
	public Tema()
	{
		this("", null);
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Libro getMiLibroAsociado() {
		return miLibroAsociado;
	}
	public void setMiLibroAsociado(Libro miLibroAsociado) {
		this.miLibroAsociado = miLibroAsociado;
	}
	@Override
	public String toString() 
	{
		String informacion = "Nombre: "+nombre+" Libro asociado: "+getMiLibroAsociado().getNombre();
		return informacion;
	}
}