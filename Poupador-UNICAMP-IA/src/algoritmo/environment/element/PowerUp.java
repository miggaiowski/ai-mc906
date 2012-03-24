package algoritmo.environment.element;

public class PowerUp extends PickupElement
{
	public static final int COST = 5;
	public static final int IMMUNE_TICKS = 15;
	
	public PowerUp()
	{
		super( MazeElement.POWER_UP );
	}
	
	@Override
	public int hashCode()
	{
		return super.hashCode( );
	}
	
	@Override
	public boolean equals( Object otherObject )
	{
		if ( otherObject instanceof PowerUp )
			return super.equals( otherObject );	
		
		return false;
		
	}
}
