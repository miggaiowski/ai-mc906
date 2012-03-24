/**
 * 
 */
package test.algoritmo.environment;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import algoritmo.environment.Maze;

/**
 * @author Gui
 *
 */
public class MazeTest
{

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp( ) throws Exception
	{
		maze = Maze.get_instance( );
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown( ) throws Exception
	{
		Maze.reset_instance( );
	}
	
	@Test
	public void testHash()
	{
		
	}


	private Maze maze;
}
