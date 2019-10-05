package excepciones;

public class AmigoRepeatException extends Exception
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public AmigoRepeatException(String mensajeDeError) 
	{
		super(mensajeDeError);
	}
}