package controlador;

import java.time.LocalDate;

import java.util.ArrayList;

import excepciones.PrestamoNullException;
import excepciones.PrestamoRepeatException;
import modelo.Amigo;
import modelo.Prestamo;

public interface IControlPrestamos 
{
	public ArrayList<Prestamo> getMisPrestamos();
	public void setMisPrestamos(ArrayList<Prestamo> misPrestamos);
	public int obtenerPosPrestamo(String codigo);
	public Prestamo obtenerPrestamo(String codigo) throws PrestamoNullException;
	public void eliminarPrestamo(String codigo) throws PrestamoNullException;
	public void agregarPrestamo(LocalDate fechaPrestamo, LocalDate fechaDeEntrega, String codigo, Amigo miAmigoAsociado) throws PrestamoRepeatException;
}
