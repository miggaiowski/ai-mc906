package algoritmo.environment;

import java.util.LinkedHashSet;
import java.util.Set;

import aima.core.agent.Action;
import aima.core.search.framework.ActionsFunction;
import aima.core.search.framework.ResultFunction;
import aima.core.util.datastructure.XYLocation;

import algoritmo.environment.element.Person;

public class MazeFactory
{

	
	public MazeFactory( Person actor )
	{
		this.actor = actor;
		action_function = new MazeActionFunction( actor );
		result_function = new MazeResultFunction( actor );
	}
	
	public ActionsFunction get_actions_function( )
	{
		return action_function;
	}
	
	public ResultFunction get_result_function( )
	{	
		return result_function;
	}

	
	/**
	 * This is a singleton that define possible actions base on a state
	 * for our maze context.
	 * @author Gui
	 *
	 */
	private class MazeActionFunction implements ActionsFunction
	{
		public MazeActionFunction( Person actor )
		{
			this.actor = actor; 
		}
		
		public Set<Action> actions( Object state )
		{
			Maze maze = (Maze) state;
			Person state_actor = (Person) maze.what_is( maze.where_is( actor ) ); 
			
			Set<Action> actions = new LinkedHashSet<Action>();
			
			for( XYLocation.Direction direction: XYLocation.Direction.values( ) )
			{
				if( maze.can_move( state_actor, direction ) )
				{
					actions.add( new MazeAction( direction ) );
				}
			}
			
			return actions;
		}
		
		private Person actor;
	}
	
	/**
	 * This is a singleton that define what is the result when actions
	 * are done in a certain state.
	 * @author Gui
	 *
	 */
	private class MazeResultFunction implements ResultFunction
	{
		public MazeResultFunction( Person actor )
		{
			this.actor = actor;
		}
		public Object result( Object state, Action action )
		{
			Maze maze = (Maze) state;
			
			Maze new_maze = null;
			
			try
			{
				new_maze = new Maze( maze );
				
			} catch( Exception e )
			{
				e.printStackTrace( );
			}
			
			// Perform actions
			new_maze.move( actor, action );
			
			return new_maze;
		}
		
		private Person actor;
	}
	
	
	private Person actor;
	private ActionsFunction action_function = null;
	private ResultFunction result_function = null;
	
}
