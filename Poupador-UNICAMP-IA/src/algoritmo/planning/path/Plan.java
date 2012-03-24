package algoritmo.planning.path;

import aima.core.util.datastructure.XYLocation;
import algoritmo.SensoresPoupador;
import algoritmo.environment.Maze;
import algoritmo.environment.MazeAction;
import algoritmo.environment.element.MazeElement;
import algoritmo.environment.element.Saver;

public abstract class Plan
{
	public static int TICKS = 0;

	public Plan( Saver saver )
	{
		this.saver = saver;
	}
	
	public void update( SensoresPoupador sensor )
	{
		TICKS++;
		Maze.get_instance().update_poupador( sensor, saver.get_type());
	}
	
	protected MazeAction get_bump()
	{
		Maze maze = Maze.get_instance( );
		
		XYLocation bank_loc = maze.where_is( MazeElement.BANK );
		XYLocation actor_loc = maze.where_is( saver );
		
		MazeAction bump = new MazeAction( MazeAction.NO_MOVE );
		
		if ( actor_loc.left( ).equals( bank_loc ) )
		{
			bump = new MazeAction( MazeAction.LEFT );
		}
		else if ( actor_loc.right( ).equals( bank_loc ) )
		{
			bump = new MazeAction( MazeAction.RIGHT );
		}
		else if ( actor_loc.down( ).equals( bank_loc ) )
		{
			bump = new MazeAction( MazeAction.DOWN );
		}
		else if ( actor_loc.up( ).equals( bank_loc ) )
		{
			bump = new MazeAction( MazeAction.UP );
		}
		
		return bump;
	}
	
	public abstract int execute( SensoresPoupador sensor );

	public abstract boolean is_executable();
	
	private Saver saver;
	
}
