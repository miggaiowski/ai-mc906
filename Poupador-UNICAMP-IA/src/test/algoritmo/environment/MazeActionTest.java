/**
 * 
 */
package test.algoritmo.environment;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import algoritmo.environment.MazeAction;

/**
 * @author Gui
 *
 */
public class MazeActionTest
{
	private MazeAction action_up;
	private MazeAction action_north;
	
	private MazeAction action_down;
	private MazeAction action_south;
	
	private MazeAction action_left;
	private MazeAction action_east;
	
	private MazeAction action_right;
	private MazeAction action_west;
	
	private MazeAction action_no;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp( ) throws Exception
	{
		action_up = new MazeAction( MazeAction.UP );
		action_down = new MazeAction( MazeAction.DOWN );
		action_left = new MazeAction( MazeAction.LEFT );
		action_right = new MazeAction( MazeAction.RIGHT );
		action_no = new MazeAction( MazeAction.NO_MOVE );
		
		
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown( ) throws Exception
	{
	}

	/**
	 * Test method for {@link algoritmo.environment.MazeAction#get_action()}.
	 */
	@Test
	public void testGet_action( )
	{
		fail( "Not yet implemented" );
	}

	/**
	 * Test method for {@link algoritmo.environment.MazeAction#set_action(int)}.
	 */
	@Test
	public void testSet_action( )
	{
		fail( "Not yet implemented" );
	}

	/**
	 * Test method for {@link algoritmo.environment.MazeAction#isNoOp()}.
	 */
	@Test
	public void testIsNoOp( )
	{
		fail( "Not yet implemented" );
	}

	/**
	 * Test method for {@link algoritmo.environment.MazeAction#MazeAction(int)}.
	 */
	@Test
	public void testMazeActionInt( )
	{
		fail( "Not yet implemented" );
	}

	/**
	 * Test method for {@link algoritmo.environment.MazeAction#MazeAction(aima.core.util.datastructure.XYLocation.Direction)}.
	 */
	@Test
	public void testMazeActionDirection( )
	{
		fail( "Not yet implemented" );
	}

	/**
	 * Test method for {@link algoritmo.environment.MazeAction#get_direction()}.
	 */
	@Test
	public void testGet_direction( )
	{
		fail( "Not yet implemented" );
	}

}
