package excepciones;

public class LibroRepeatException extends Exception
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LibroRepeatException(String mensajeDeError) 
	{
		super(mensajeDeError);
	}
}
