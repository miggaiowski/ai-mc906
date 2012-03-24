package algoritmo.planning.path;

public class ImpossibleGoalException extends IllegalArgumentException
{	 
	private static final long serialVersionUID = 1L;

	public ImpossibleGoalException( String message )
	{
		super( message );
	}
}
