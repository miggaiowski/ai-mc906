package algoritmo.environment.element;

public class Bank extends FixedElement
{

	public Bank( )
	{
		super(  MazeElement.BANK );
		money_s1 = 0;
		money_s2 = 0;
	}
	
	public static int last_deposit = 0; 
	
	public Bank( Bank otherBank )
	{
		super( otherBank );
		this.money_s1 = otherBank.money_s1;
		this.money_s2 = otherBank.money_s2;
	}
	
	/**
	 * Each save has their own account
	 * @param saver
	 */
	public void deposit( Saver saver )
	{
		int temp_money =saver.take_all_money( );
		last_deposit = Math.max(last_deposit, temp_money);
		
		if ( saver.get_type( ) == MazeElement.S1 )
		{
			money_s1 += temp_money;
		}
		else
		{
			money_s2 += temp_money;
		}
		
		
	}
	
	public int consult( Saver saver )
	{
		if ( saver.get_type( ) == MazeElement.S1 )
		{
			return money_s1;
		}
		else
		{
			return money_s2;
		}
		
	}
	
	@Override
	public int hashCode()
	{
		final int HASH_MULTIPLIER = 17;
		
		if ( flag_money )
		{
			return super.hashCode( ) * HASH_MULTIPLIER
					+ money_s1 * 5 + money_s2 * 7;
		}
		else
		{
			return super.hashCode( );
		}
	}
	
	@Override
	public boolean equals( Object otherObject )
	{
		if ( super.equals( otherObject ) )
		{
			if ( flag_money )
			{
				if ( otherObject instanceof Bank )
				{
					Bank bank = (Bank) otherObject;
					
					return bank.money_s1 == this.money_s1
						&& bank.money_s2 == this.money_s2;
				}
				else
				{
					return false;
				}
			}
			else
			{
				return true;
			}
		}
		else
		{
			return false;
		}
	}
	
	private int money_s1;
	private int money_s2;
	
	private static boolean flag_money = false;
	
	/**
	 * Set this flag on if your objective depends
	 * on how money there is on the bank
	 * @param flag_money
	 */
	public static void set_flags( boolean flag_money )
	{
		Bank.flag_money = flag_money;
	}
	
	
}
