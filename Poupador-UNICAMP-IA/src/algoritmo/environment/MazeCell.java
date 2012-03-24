package algoritmo.environment;



import algoritmo.environment.element.*;
/**
 * One cell of our environment
 * contain pheromone and physical object information
 * @author Gui
 *
 */
public class MazeCell
{
	
	public MazeCell( )
	{
		this.element = new MazeElement( );
		smell_robber = 0;
	}
	
	public MazeCell(  MazeCell cell )
	{
		this( cell.get_element( ).get_type( ), cell.smell_robber );
	}
	
	public MazeCell( int cell_id, int smell_robber )
	{
		switch( cell_id )
		{
			case MazeElement.NO_VISION:
				element = new MazeElement( MazeElement.NO_VISION );	
			break;
			
			case MazeElement.EMPTY:
				element = new MazeElement( MazeElement.EMPTY );
			break;
			
			case MazeElement.WALL:
				element = new Wall( );
			break;
				
			case MazeElement.MONEY:
				element = new Money( );
			break;
			
			case MazeElement.POWER_UP:
				element = new PowerUp( );
			break;
			
			case MazeElement.R1:
			case MazeElement.R2:
			case MazeElement.R3:
			case MazeElement.R4:
				element = new Robber( cell_id );
			break;
			
			case MazeElement.S1:
			case MazeElement.S2:
				element = new Saver( cell_id );
			break;
			
			case MazeElement.BANK:
				element = new Bank( );
			break;

		}
		
				
		this.smell_robber = smell_robber;

	}
	
	@Override
	public int hashCode()
	{	
		return element.hashCode( );
	}
	
	@Override
	public boolean equals( Object o )
	{
		if (this == o)
			return true;
		if ((o == null) || (this.getClass() != o.getClass()))
			return false;
		
		MazeCell maze_cell = (MazeCell) o;
		
		if( maze_cell.smell_robber != this.smell_robber )
			return false;
	
		return maze_cell.element.equals( this.element );
	}
	/**
	 * Evaporate pheromone so you can have an estimation
	 * of pheromone for unknown zones
	 * This should be done only once per tick.
	 */
	public void update_smell( )
	{
		if ( smell_robber > 0 )
			smell_robber--;	
		
	}
	
	/**
	 * Agent take a sniff to update pheromone count
	 * @param smell_robber pheromone ladrao
	 */
	public void set_smell( int smell_robber )
	{
		this.smell_robber = smell_robber;
	}
	
	public int get_smell_robber()
	{
		return smell_robber;
	}
	
	public void set_element( MazeElement element )
	{
		this.element = element;
	}
	
	public MazeElement get_element( )
	{
		return this.element;
	}
	
	public void print(  )
	{
		element.print();
	}
		
	// Olfactive information
	private int smell_robber;
	
	// Element information
	private MazeElement element;
	
}