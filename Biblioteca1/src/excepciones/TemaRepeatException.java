package excepciones;

public class TemaRepeatException extends Exception
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public TemaRepeatException(String mensajeDeError) 
	{
		super(mensajeDeError);
	}
}