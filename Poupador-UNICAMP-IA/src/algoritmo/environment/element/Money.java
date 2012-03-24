package algoritmo.environment.element;

public class Money extends PickupElement
{
	public Money()
	{
		super( MazeElement.MONEY );
	}
	
	@Override
	public boolean equals( Object otherObject)
	{
		if ( otherObject instanceof Money )
			return super.equals( otherObject );
		
		return false;
	}
	
	@Override
	public int hashCode( )
	{
		return super.hashCode( );
	}
}
