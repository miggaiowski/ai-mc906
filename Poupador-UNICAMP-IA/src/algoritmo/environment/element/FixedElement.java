package algoritmo.environment.element;

public abstract class FixedElement extends MazeElement
{

	public FixedElement( int type )
	{
		super( type );
	}
	
	public FixedElement( FixedElement otherFixedElement )
	{
		super( otherFixedElement );
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
