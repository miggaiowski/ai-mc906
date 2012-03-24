package algoritmo.environment;

import aima.core.agent.Action;
import aima.core.util.datastructure.XYLocation;

public class MazeAction implements Action
{
	public static final int NO_MOVE = 0;
	public static final int UP = 1;
	public static final int DOWN = 2;
	public static final int RIGHT = 3;
	public static final int LEFT = 4;
	
	public int get_action()
	{
		return type;
	}
	
	public void set_action( int type )
	{
		this.type = type;
	}
	
	public boolean isNoOp( )
	{
		return type == MazeAction.NO_MOVE;
	}
	
	public MazeAction( int type )
	{
		this.type = type;
	}
	
	public MazeAction( XYLocation.Direction direction )
	{
		if( direction.equals( XYLocation.Direction.North ))
		{
			this.type = UP;
		}
		else if (direction.equals( XYLocation.Direction.East ))
		{
			this.type = RIGHT;
		}
		else if (direction.equals( XYLocation.Direction.South ))
		{
			this.type = DOWN;
		}
		else if (direction.equals( XYLocation.Direction.West ))
		{
			this.type = LEFT;
		}
		else if (direction.equals( XYLocation.Direction.None ))
		{
			this.type = NO_MOVE;
		}
	}
	
	public XYLocation.Direction get_direction( )
	{
		switch( type )
		{
			case UP:
				return XYLocation.Direction.North;
				
			case DOWN:
				return XYLocation.Direction.South;
				
			case LEFT:
				return XYLocation.Direction.West;
				
			case RIGHT:
				return XYLocation.Direction.East;
				
			case NO_MOVE:
				return XYLocation.Direction.None;
				
			default:
				return XYLocation.Direction.None;
		}
	}
	
	private int type;
}
