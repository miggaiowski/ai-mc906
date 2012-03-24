package algoritmo.environment.element;

public class MazeElement 
{	
	// Static
	public static final int NO_VISION = -2;
	public static final int OUT_OF_BOUNDS = -1;
	public static final int EMPTY = 0;
	public static final int WALL = 1;
	public static final int BANK = 3;
	
	// Semi-Dynamic
	public static final int MONEY = 4;
	public static final int POWER_UP = 5;
	
	// Dynamic
	public static final int S1	= 100;
	public static final int S2 = 110;
	public static final int R1 = 200;
	public static final int R2 = 210;
	public static final int R3 = 220;
	public static final int R4 = 230;
	

	public MazeElement()
	{
		this.type = MazeElement.NO_VISION;
	}
	
	public MazeElement( int type )
	{
		this.type = type;
		
	}

	public MazeElement( MazeElement maze_element )
	{
		this.type = maze_element.type;
	}
	
	public boolean is_a( int type )
	{
		return this.type == type;
	}
	
	public boolean is_updatable( int vision_type )
	{
		if( type == NO_VISION )
			return true;
		
		if( type == EMPTY && ( R1 <= vision_type && vision_type <= R4 ) )
			return true;
		
		if( ( R1 <= type && type <= R4 ) && vision_type == EMPTY )
			return true;

		return false;
	}
	
	public int get_type( )
	{
		return type;
	}
	
	public void print()
	{
		System.out.print( type + "\t" );
	}
	
	@Override
	public int hashCode()
	{	
		return type;
	}
	
	@Override
	public boolean equals( Object otherObject )
	{
		if ( this == otherObject )
			return true;
		
		if( otherObject instanceof MazeElement )
		{
			MazeElement other = (MazeElement) otherObject;
			return this.type == other.type;
		}
		
		return false;
	}
	
	private int type;

}
