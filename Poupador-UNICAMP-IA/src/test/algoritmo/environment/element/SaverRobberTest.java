/**
 * 
 */
package test.algoritmo.environment.element;

import static org.junit.Assert.*;

import java.math.BigInteger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import algoritmo.environment.element.Bank;
import algoritmo.environment.element.Money;
import algoritmo.environment.element.PowerUp;
import algoritmo.environment.element.Robber;
import algoritmo.environment.element.Saver;
import algoritmo.environment.element.MazeElement;
import algoritmo.environment.element.Wall;

/**
 * @author Gui
 *
 */
public class SaverRobberTest
{

	private Robber robber;
	private Saver saver;
	
	private static final int SOME_MONEY = 4;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp( ) throws Exception
	{
		
		robber = new Robber( MazeElement.R1 );
		saver = new Saver( MazeElement.S1 );
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown( ) throws Exception
	{
		robber = null;
	}

	/**
	 * Check if robber and saver are constructed ok
	 */
	@Test
	public void testRobberSaver( )
	{
		// Check no money
		assertEquals( 0, saver.consult() );
		
		// Check proper tag
		assertTrue( robber.is_a( MazeElement.R1 ) );
		assertTrue( saver.is_a( MazeElement.S1 ) );

	}

	/**
	 * Senario: saver2 pick money and robber2 steal him
	 *  saver and saver2 are instance of S1
	 *  robber and robber2 are instance of R1
	 *  I check consistency with equals and hashCode methods
	 *  and test the amount of money rober2 and saver has
	 *  before and after the steal event
	 */
	@Test
	public void test_Hash_Code_Steal()
	{

		for ( int j = 0; j < 4; j++ )
		{

			
			// Create another instance of the rober R1
			Robber robber2 = new Robber(  MazeElement.R1 );
			

			// Equivalent instance should have same hash
			assertTrue( robber.equals( robber2 ) );
			assertEquals( robber.hashCode( ), robber2.hashCode( ) );
		
			
			// Create another instance of S1
			Saver saver2 = new Saver( MazeElement.S1 );
			
		
			// Equivalent instance should have same hash			
			assertTrue( saver.equals( saver2 ) );
			assertEquals( saver.hashCode( ), saver2.hashCode( ) );				
			
			
			// Saver 2 start picking money
			for ( int i = 0; i < SOME_MONEY; i++ )
				saver2.pick_money( );
			
			// Moneywise Saver 2 is now different from saver
			assertFalse( saver.equals( saver2 ) );
			assertFalse( saver.hashCode( ) == saver2.hashCode( ) );	

			
			// Stealing
			robber2.steal( saver2 );
				
			// Robber2 has now the saver money
			assertEquals( 0, saver.consult( ) );
			
			assertTrue( saver.equals( saver2 ) );
			assertTrue( saver.hashCode( ) == saver2.hashCode( ) );
			
			// Prepare next
			robber = new Robber( MazeElement.R1 );
			saver = new Saver( MazeElement.S1 );
		}
	}

	
	/**
	 *  Test power-up picking from saver and immune from stealing
	 *  then after the immune period he is vulnerable again
	 */
	@Test
	public void test_PowerUp()
	{
		
		// Saver Cannot pick power-up with no money
		saver.pick_powerup( );
		assertTrue( saver.is_vulnerable( ) );
		
		// Saver get money
		for ( int i = 0; i < PowerUp.COST; i++ )
		{
			saver.pick_money( );
		}
		
		// Now he can take it
		saver.pick_powerup( );
		assertFalse( saver.is_vulnerable( ) );
		
		// Pick a money to test a steal
		saver.pick_money( );
		
		int saver_money = saver.consult( );
		
		robber.steal( saver );
		
		// Since saver is immune money count shouldn't change
		assertEquals( saver_money, saver.consult( ) );

		
		// Update until saver is vulnerable
		for ( int i = 0; i < PowerUp.IMMUNE_TICKS; i++ )
		{
			saver.update( );
		}
		
		saver_money = saver.consult( );

		
		robber.steal( saver );
		
		assertEquals( 0, saver.consult( ) );

		
	}
	
	/**
	 * Test saver go through
	 */
	@Test
	public void test_saver_gothrough( )
	{
		
// Possibles
		

		assertTrue( saver.can_go_through( new MazeElement( ) ) );

		assertTrue( saver.can_go_through( 
				new MazeElement( MazeElement.EMPTY) ) );
		
		assertTrue( saver.can_go_through( 
				new MazeElement( MazeElement.NO_VISION) ) );
		
		assertTrue( saver.can_go_through( new Bank( ) ) );
		assertTrue( saver.can_go_through( new Money( ) ) ); 
		
		// Can't power-up if no money
		assertFalse( saver.can_go_through( new PowerUp( ) ) );
		
		// Get enough money for power-up
		for ( int i = 0; i < PowerUp.COST; i++ )
			saver.pick_money( );
		
		// Possible -> Take
		assertTrue( saver.can_go_through( new PowerUp( ) ) );
		saver.pick_powerup( );
		
		// No more money
		assertFalse( saver.can_go_through( new PowerUp( ) ) );

// Not possible
		
		assertFalse( saver.can_go_through( 
				new MazeElement( MazeElement.OUT_OF_BOUNDS) ) );
		
		assertFalse( saver.can_go_through( new Wall( ) ) );
		assertFalse( saver.can_go_through( new Robber( MazeElement.R1 ) ) );
		assertFalse( saver.can_go_through( new Robber( MazeElement.R2 ) ) );
		assertFalse( saver.can_go_through( new Robber( MazeElement.R3 ) ) );
		assertFalse( saver.can_go_through( new Robber( MazeElement.R4 ) ) );
		
		assertFalse( saver.can_go_through( new Robber( MazeElement.S2 ) ) );
		
		// This is not planned because he can't move on his position
		// but it should be false anyway
		assertFalse( saver.can_go_through( new Robber( MazeElement.S1 ) ) );
	}
	
	/**
	 * Test robber go through
	 */
	@Test
	public void test_robber_gothrough( )
	{
		
// Possible
		
		assertTrue( saver.can_go_through( new MazeElement( ) ) );

		assertTrue( saver.can_go_through( 
				new MazeElement( MazeElement.EMPTY) ) );
		
		assertTrue( saver.can_go_through( 
				new MazeElement( MazeElement.NO_VISION) ) );
		
		assertTrue( robber.can_go_through( new Robber( MazeElement.S1 ) ) );
		
		assertTrue( robber.can_go_through( new Robber( MazeElement.S2 ) ) );
		
// Not Possible
		
		assertFalse( robber.can_go_through( new Wall( ) ) );
		
		assertFalse( robber.can_go_through( new Robber( MazeElement.R2 ) ) );
		assertFalse( robber.can_go_through( new Robber( MazeElement.R3 ) ) );
		assertFalse( robber.can_go_through( new Robber( MazeElement.R4 ) ) );
		
		
		// This is not planned because he can't move on his position
		// but it should be false anyway
		assertFalse( robber.can_go_through( new Robber( MazeElement.R1 ) ) );
	}
}
