package excepciones;

public class LibroNullException extends NullPointerException
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public LibroNullException(String mensajeDeError) 
	{
		super(mensajeDeError);
	}
}