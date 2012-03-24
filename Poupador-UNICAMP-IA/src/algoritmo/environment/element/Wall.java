package algoritmo.environment.element;

public class Wall extends FixedElement
{

	public Wall( )
	{
		super(  MazeElement.WALL );
	}
	
	@Override
	public boolean equals( Object otherObject )
	{
		if ( otherObject instanceof Wall )
			return super.equals( otherObject );	
		
		return false;
	}
}
