package excepciones;

public class AmigoNullException extends NullPointerException
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public AmigoNullException(String mensajeDeError) 
	{
		super(mensajeDeError);
	}

}
