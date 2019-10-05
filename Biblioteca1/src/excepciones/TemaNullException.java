package excepciones;

public class TemaNullException extends NullPointerException
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public TemaNullException(String mensajeDeError) 
	{
		super(mensajeDeError);
	}
}