package algoritmo.environment;


import java.util.HashMap;
import java.util.Map;

import aima.core.util.datastructure.XYLocation;

/**
 * This is a factory to convert cell number
 * of vision or smell sensor into a difference
 * vector from actor to that cell
 * @author Gui
 *
 */

public class CellMapHelper
{

	public static XYLocation vision_map( int n, XYLocation actor_loc )
	{
		if( map_vison == null )
			map_vison = new MapVision( );
		
		XYLocation diff = map_vison.get( n ); 
		
		return new XYLocation(	actor_loc.x( ) + diff.x( ),
								actor_loc.y( ) + diff.y( )  );
	}
	
	public static XYLocation smell_map( int n, XYLocation actor_loc )
	{
		if( map_smell == null )
			map_smell = new MapSmell( );
		
		XYLocation diff = map_smell.get( n ); 
		
		return new XYLocation(	actor_loc.x( ) + diff.x( ),
								actor_loc.y( ) + diff.y( )  );
		
	}

	private static class MapVision
	{
		public MapVision(  )
		{
			diff = new HashMap<Integer,XYLocation>();
			diff.put( new Integer( 0 ),  new XYLocation( -2, -2 ) );
			diff.put( new Integer( 1 ),  new XYLocation( -1, -2 ) );
			diff.put( new Integer( 2 ),  new XYLocation(  0, -2 ) );
			diff.put( new Integer( 3 ),  new XYLocation(  1, -2 ) );
			diff.put( new Integer( 4 ),  new XYLocation(  2, -2 ) );
			diff.put( new Integer( 5 ),  new XYLocation( -2, -1 ) );
			diff.put( new Integer( 6 ),  new XYLocation( -1, -1 ) );
			diff.put( new Integer( 7 ),  new XYLocation(  0, -1 ) );
			diff.put( new Integer( 8 ),  new XYLocation(  1, -1 ) );
			diff.put( new Integer( 9 ),  new XYLocation(  2, -1 ) );
			diff.put( new Integer( 10 ), new XYLocation( -2,  0 ) );
			diff.put( new Integer( 11 ), new XYLocation( -1,  0 ) );
			diff.put( new Integer( 12 ), new XYLocation(  1,  0 ) );
			diff.put( new Integer( 13 ), new XYLocation(  2,  0 ) );
			diff.put( new Integer( 14 ), new XYLocation( -2,  1 ) );
			diff.put( new Integer( 15 ), new XYLocation( -1,  1 ) );
			diff.put( new Integer( 16 ), new XYLocation(  0,  1 ) );
			diff.put( new Integer( 17 ), new XYLocation(  1,  1 ) );
			diff.put( new Integer( 18 ), new XYLocation(  2,  1 ) );
			diff.put( new Integer( 19 ), new XYLocation( -2,  2 ) );
			diff.put( new Integer( 20 ), new XYLocation( -1,  2 ) );
			diff.put( new Integer( 21 ), new XYLocation(  0,  2 ) );
			diff.put( new Integer( 22 ), new XYLocation(  1,  2 ) );
			diff.put( new Integer( 23 ), new XYLocation(  2,  2 ) );
		}
		
		public XYLocation get( int n )
		{
			return diff.get( n );
		}
		
		private Map< Integer, XYLocation > diff;
	}
	
	private static class MapSmell
	{
		public MapSmell(  )
		{
			diff = new HashMap<Integer,XYLocation>();
			diff.put( new Integer( 0 ), new XYLocation(-1,-1 ) );
			diff.put( new Integer( 1 ), new XYLocation(0,-1 ) );
			diff.put( new Integer( 2 ), new XYLocation(1,-1 ) );
			diff.put( new Integer( 3 ), new XYLocation(-1,0 ) );
			diff.put( new Integer( 4 ), new XYLocation(1,0 ) );
			diff.put( new Integer( 5 ), new XYLocation(-1,1 ) );
			diff.put( new Integer( 6 ), new XYLocation(0,1 ) );
			diff.put( new Integer( 7 ), new XYLocation(1,1 ) );
		}
		
		public XYLocation get( int n )
		{
			return diff.get( n );
		}

		private Map<Integer,XYLocation> diff;
	}
	
	private static MapSmell map_smell = null;
	private static MapVision map_vison = null; 

}
