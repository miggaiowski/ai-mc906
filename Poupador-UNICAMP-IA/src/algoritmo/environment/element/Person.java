package algoritmo.environment.element;

public abstract class Person extends MazeElement
{
	public Person( int id )
	{
		super( id );
	}
	
	public boolean can_go_through( MazeElement element )
	{
		return element.is_a( EMPTY ) || element.is_a( NO_VISION );
	}
	
	@Override
	public boolean equals( Object o)
	{
		return super.equals( o );
	}
	
	@Override
	public int hashCode( )
	{
		return super.hashCode( );
	}
}
