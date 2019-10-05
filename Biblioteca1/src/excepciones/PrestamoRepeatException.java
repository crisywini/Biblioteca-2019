package excepciones;

public class PrestamoRepeatException extends Exception
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public PrestamoRepeatException(String mensajeDeError) 
	{
		super(mensajeDeError);
	}
}