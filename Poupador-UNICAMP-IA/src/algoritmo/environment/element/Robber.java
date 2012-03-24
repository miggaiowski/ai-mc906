package algoritmo.environment.element;

public class Robber extends Person
{
	public Robber( int id )
	{
		super( id );
	}

	public boolean can_go_through( MazeElement element )
	{
		return super.can_go_through( element ) || element.is_a( S1 ) || element.is_a( S2 );
	}
	
	public void steal( Saver saver )
	{
		if( saver.is_vulnerable( ) )
			saver.take_all_money( );
	}
	
	@Override
	public boolean equals( Object otherObject )
	{
		return super.equals( otherObject );
	}
	
	@Override
	public int hashCode( )
	{
		return super.hashCode( );
	}

}
