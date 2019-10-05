package excepciones;

public class PrestamoNullException extends NullPointerException
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public PrestamoNullException(String mensajeDeError) 
	{
		super(mensajeDeError);
	}
}