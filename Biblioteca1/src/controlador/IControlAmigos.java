package controlador;

import java.util.ArrayList;

import excepciones.AmigoNullException;
import excepciones.AmigoRepeatException;
import modelo.Amigo;

public interface IControlAmigos 
{
	public ArrayList<Amigo> getMisAmigos();
	public void setMisAmigos(ArrayList<Amigo> misAmigos);
	public int obtenerPosAmigo(String nombre);
	public Amigo obtenerAmigo(String nombre) throws AmigoNullException;
	public void eliminarAmigo(String nombre) throws AmigoNullException;
	public void agregarAmigo(String nombre) throws AmigoRepeatException;
}
