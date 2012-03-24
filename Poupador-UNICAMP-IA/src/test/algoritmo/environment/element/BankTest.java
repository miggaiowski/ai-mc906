/**
 * 
 */
package test.algoritmo.environment.element;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import algoritmo.environment.element.MazeElement;
import algoritmo.environment.element.Saver;
import algoritmo.environment.element.Bank;


/**
 * @author Gui
 *
 */
public class BankTest
{

	private Bank bank;
	
	@Before
	public void setUp( )
	{
		bank = new Bank( );
		Bank.set_flags( true );
	}

	
	@After
	public void tearDown( ) throws Exception
	{
		bank = null;
	}

	@Test
	public void test_deposit_consult()
	{
		Saver saver = new Saver( MazeElement.S1 );
		
		int bank_money = bank.consult( saver );
		
		// Deposit nothing
		bank.deposit( saver );
		
		// Bank is still empty
		assertEquals( bank_money, bank.consult( saver ) );
		
		saver.pick_money( );
		
		// Deposit something
		bank.deposit( saver );
		
		// Saver has no more money
		assertEquals( 0, saver.consult( ) );
		
		// Bank store the money
		assertEquals( bank_money + 1, bank.consult( saver ) );
		
	}
}
