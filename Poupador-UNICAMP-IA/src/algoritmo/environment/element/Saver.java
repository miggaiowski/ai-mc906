package algoritmo.environment.element;

public class Saver extends Person
{	
	public Saver( int id )
	{
		super( id );
		immune_tick = 0;
		money = 0;
	}
	
	public static int robbed = 0;
	
	public boolean can_go_through( MazeElement element )
	{
		if ( super.can_go_through( element ) )
			return true;
		
		if ( element.is_a( BANK ) ) 
			return true;
		
		if( element.is_a( MONEY ) )
			return true;
		
		if( element.is_a( POWER_UP ) )
			if( can_buy_powerup( ) )
				return true;
		
		return false;
	}
	
	public int consult()
	{
		return money;
	}
	
	public void pick_money()
	{
		money++;
	}
	
	public int take_all_money( )
	{
		int temp_money = money;
		money = 0;
		robbed = Math.max(robbed, temp_money);
		return temp_money;
	}
	
	public void pick_powerup()
	{
		if ( can_buy_powerup( ) )
		{
			money -= PowerUp.COST;
			immune_tick = PowerUp.IMMUNE_TICKS;	
		}
	}
	
	/**
	 * Update the immune ticks
	 */
	public void update()
	{
		if( immune_tick > 0 )
			immune_tick--;	
	}
	
	/**
	 * Check for vulnerability
	 * @return vulnerable or not
	 */
	public boolean is_vulnerable( )
	{
		return immune_tick == 0;
	}
	
	/**
	 * Check if you can buy a powerup
	 * @return can buy or not
	 */
	private boolean can_buy_powerup()
	{
		return money >= PowerUp.COST;
	}
	
	@Override
	public boolean equals( Object otherObject )
	{
		if( super.equals( otherObject ) )
		{
			if ( otherObject instanceof Saver )
			{
				Saver saver = (Saver) otherObject;
				
				if ( saver.immune_tick != this.immune_tick )
					return false;
 
				if( saver.money != this.money )
					return false;
				
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public int hashCode()
	{
		final int HASH_MULTIPLIER = 13;
		
		int hash = super.hashCode( );
		hash = hash * HASH_MULTIPLIER + immune_tick;
		hash = hash * HASH_MULTIPLIER + money;
		
		return hash;
	}
		
	private int money;
	private int immune_tick;

}
