package algoritmo.environment;

import java.awt.Point;
import java.util.List;
import java.util.ArrayList;

import aima.core.agent.Action;
import aima.core.util.datastructure.XYLocation;
import aima.core.util.datastructure.XYLocation.Direction;
import algoritmo.SensoresPoupador;
import algoritmo.environment.element.*;
import algoritmo.planning.path.ManathanHeuristic;

public class Maze
{	

	public static Maze get_instance( )
	{
		if( actual_maze == null )
			actual_maze = new Maze( );
		
		return actual_maze;
	}
	public static void reset_instance()
	{
		actual_maze = null;
	}
	private static Maze actual_maze = null;
	
	/**
	 * Maze constructor when unknown
	 * You start your problem with this constructor
	 */
	public Maze(  )
	{
		cells = new MazeCell[WIDTH][HEIGHT];
		for (int i = 0; i < WIDTH; i++)
			for( int j = 0; j < HEIGHT; j++ )
				cells[i][j] = new MazeCell( );
	}

	
	/**
	 * Maze copy constructor
	 * @param maze source to copy from
	 * @throws Exception to complain about unknowns cells id and out of bounds
	 */
	public Maze( Maze maze ) throws Exception
	{
		if ( maze == null )
			throw new NullPointerException();
		
		this.cells = new MazeCell[WIDTH][HEIGHT];
		for ( int i = 0; i < WIDTH; i++ )
			for ( int j = 0; j < HEIGHT; j++ )
				this.cells[i][j] = new MazeCell( maze.cells[i][j] );

	}
	
	
	/**
	 * Find the first instance of an element or element_id
	 * @param element or element_id: what you are looking for
	 * @return xy where it is or null if not found 
	 */
	public XYLocation where_is( MazeElement element )
	{
		return where_is( element.get_type( ) );
	}
	public XYLocation where_is( int element_id )
	{
		for ( int i = 0; i < WIDTH; i++ )
			for ( int j = 0; j < HEIGHT; j++ )
				if ( cells[i][j].get_element( ).get_type( ) == element_id )
					return new XYLocation( i, j );
		
		return null;
	}
	
	/**
	 * Find all instance of an element on the map
	 * @param element id you are looking for
	 * @return a list of position where you can find the element
	 */
	public List<XYLocation> where_is_all( int element_id )
	{
		List<XYLocation> elements_list = new ArrayList<XYLocation >( );
		
		for ( int i = 0; i < WIDTH; i++ )
			for ( int j = 0; j < HEIGHT; j++ )
				if ( cells[i][j].get_element( ).get_type( ) == element_id )
					elements_list.add( new XYLocation(i,j) );
		
		return elements_list;
	}

	
    /**
     * gets the known position of all robbers, if any
     * @author Dirso
     * @return
     */
    public List<XYLocation> where_are_all_robbers()
    {
    	List<XYLocation>  elements_list = new ArrayList<XYLocation >( );
    	
    	XYLocation[] robbers = new XYLocation[4];
    	robbers[0] = where_is(MazeElement.R1);
    	robbers[1] = where_is(MazeElement.R2);
    	robbers[2] = where_is(MazeElement.R3);
    	robbers[3] = where_is(MazeElement.R4);
    	for(int i = 0; i < robbers.length; i++)
    		if(robbers[i] != null)
    			elements_list.add(robbers[i]);
    	
    	return elements_list;
    }

	
	/**
	 * Move the actor to the new location and
	 * perform all games actions resulting from
	 * that move
	 * @param actor who moves
	 * @param action where it move
	 */
	public void move( Person actor, Action action )
	{

		MazeAction maze_action = (MazeAction) action;
		XYLocation.Direction direction = maze_action.get_direction( );	
		XYLocation actor_loc = where_is( actor );
		XYLocation move_loc = actor_loc.locationAt( direction );
		MazeElement move_to_element = what_is( move_loc );
		
		if( move_to_element.is_a( MazeElement.EMPTY ) || move_to_element.is_a(MazeElement.NO_VISION) )
		{
			set_element( move_loc, actor );
			set_element( actor_loc, new MazeElement( MazeElement.EMPTY ) );
		}
		else 
		{
			if ( actor instanceof Saver )
			{
				Saver saver = (Saver) actor;
				
				if ( move_to_element.is_a( MazeElement.BANK ) )
				{	
					Bank bank = (Bank) move_to_element;
					bank.deposit( saver );

				}
				else if ( move_to_element.is_a( MazeElement.MONEY ))
				{
					saver.pick_money( );
					set_element( move_loc, actor );
					set_element( actor_loc, new MazeElement( MazeElement.EMPTY ) );
				}
				else if ( move_to_element.is_a( MazeElement.POWER_UP ))
				{
					saver.pick_powerup( );
					set_element( move_loc, actor );
					set_element( actor_loc, new MazeElement( MazeElement.EMPTY ) );
				}
			}
			else if (actor instanceof Robber)
			{
				if ( 	move_to_element.is_a( MazeElement.S1 )
					 || move_to_element.is_a( MazeElement.S2 ) )
				{
					Robber robber = (Robber) actor;
					Saver saver = (Saver) move_to_element;
					robber.steal( saver );
				}
			}
		}
	}
	
	public boolean is_all_mapped()
	{
		for(int x = 0; x < WIDTH; x++)
			for(int y = 0; y <HEIGHT; y++)
				if(what_is(new XYLocation(x, y)).is_a(MazeElement.NO_VISION))
					return false;
		return true;
	}
	
	public boolean is_known_position(int type)
	{
		return where_is_all(type).size()>0;
	}
	
	/**
	 * Test if the actor can move in the direction
	 * @param actor who want to move
	 * @param direction where he want to move
	 * @return can it move
	 */
	public boolean can_move( Person actor, XYLocation.Direction direction )
	{
		XYLocation actor_loc = where_is( actor );
		XYLocation move_loc  = actor_loc.locationAt( direction );
		boolean can_go = actor.can_go_through( what_is( move_loc ) );
		
		if(can_go)
		{
			List<XYLocation> robbers = get_instance().where_are_all_robbers();
			if(robbers.size() > 0)
			{
				int newDist = ManathanHeuristic.getShortest(move_loc, robbers);
				int curDist = ManathanHeuristic.getShortest(actor_loc, robbers);
				can_go = (newDist >= 3) || (newDist >= curDist);
			}
		}
		
		return can_go;
	}
	
	/**
	 * Update maze with sensor ( vision and smell ) 
	 * @param sensor, only work for savers sensor
	 */
	public void update_poupador( SensoresPoupador sensor, int id )
	{
		// Identification
		Point point = sensor.getPosicao( );
		XYLocation actor_loc = new XYLocation( point.x, point.y ); 
		MazeElement actor = what_is( actor_loc  );
		
		// Create saver if it's the first time
		//if ( actor.get_type( ) == MazeElement.NO_VISION )
			set_element( actor_loc, new Saver( id ) );
		
		// Update potential smell
		if ( actor.get_type( ) == MazeElement.S1 )
		{
			for ( int i = 0; i < WIDTH; i++ )
				for ( int j = 0; j < HEIGHT; j++ )
					cells[i][j].update_smell( );
		}
		
		// Update vision
		int vision[] = sensor.getVisaoIdentificacao( );
		for ( int i = 0; i < vision.length; i++ )
		{
			XYLocation cell_loc = CellMapHelper.vision_map( i, actor_loc );
			
			MazeElement element = what_is(cell_loc);
			
			if( element.is_updatable( vision[i] ) )
				cells[cell_loc.x( )][cell_loc.y( )] = new MazeCell( vision[i], 0 );
			
		}
		
		int smell_robber[] = sensor.getAmbienteOlfatoLadrao( );
		for ( int i = 0; i < smell_robber.length; i++ )
		{
			XYLocation cell_loc = CellMapHelper.smell_map( i, actor_loc );
			if ( inside_maze( cell_loc ) )
				cells[ cell_loc.x( ) ][ cell_loc.y( ) ].set_smell( smell_robber[i] );	
			
		}
		
	}
	
	/**
	 * Get an element inside the maze
	 * @param loc where is the cell
	 * @return what element is at this position,
	 * 		   if it's out of bounds it return a wall
	 */
	public MazeElement what_is( XYLocation loc )
	{
		// Bound checks
		if(	! inside_maze( loc ) )
		{
			return new MazeElement( MazeElement.WALL );
		}
				
		return cells[loc.x( )][loc.y( )].get_element( );
	}
	
	private boolean inside_maze( XYLocation loc )
	{
		int x = loc.x( );
		int y = loc.y( );
		return ( ( 0 <= x && x < WIDTH ) &&	( 0 <= y && y < HEIGHT) );
	}
	
	/**
	 * Set element of a cell
	 * @param cell_loc where is the cell you want to change
	 * @param element for what element
	 */
	private void set_element( XYLocation cell_loc, MazeElement element )
	{
		cells[ cell_loc.x( ) ][ cell_loc.y( ) ].set_element( element );
	}
	
	/**
	 * Print the element id for all cells
	 */
	public void print()
	{
		for ( int i = 0; i < WIDTH; i++ )
		{
			System.out.println(  );
			for ( int j = 0; j < HEIGHT; j++ )
				cells[j][i].print( );
		}
		System.out.println( );
	}
	
	@Override
	public int hashCode( )
	{
		int hash = 17;
		for ( int i = 0; i < WIDTH; i++ )
			for ( int j = 0; j < HEIGHT; j++ )
				hash += i * cells[i][j].hashCode( ) * 7 + j;

		return hash;
	}
	
	@Override
	public boolean equals( Object o )
	{
		if (this == o)
			return true;
		if ((o == null) || (this.getClass() != o.getClass()))
			return false;
		
		Maze maze = (Maze) o;
		
		for ( int i = 0; i < WIDTH; i++ )
			for ( int j = 0; j < HEIGHT; j++ )
				if( !maze.cells[i][j].equals( this.cells[i][j] ) )
					return false;
		
		return true;
	}
	
	public static final int HEIGHT = 30;
	public static final int WIDTH = 30;
	
	private MazeCell[][] cells;
}

