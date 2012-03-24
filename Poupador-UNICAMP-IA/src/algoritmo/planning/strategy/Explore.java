package algoritmo.planning.strategy;

import aima.core.util.datastructure.XYLocation;
import algoritmo.SensoresPoupador;
import algoritmo.environment.*;
import algoritmo.environment.element.*;
import algoritmo.planning.path.*;

import java.io.Console;
import java.util.*;

public class Explore extends Strategy {
		
	private static List<XYLocation> locations;
	
	private static int cornerS1, cornerS2;
	private static boolean goals = false;
	
	private static List<XYLocation> targets;
	
	private boolean firstLoad = true;
	
	public static void start()
	{
		locations = new ArrayList<XYLocation>();
		locations.add(new XYLocation(2,2));
		locations.add(new XYLocation(2,27));
		locations.add(new XYLocation(27,2));
		locations.add(new XYLocation(27,27));
		
		targets = new ArrayList<XYLocation>();
		for(int x = 2; x <= 27; x += 5)
		{
			for(int y = 2; y <= 27; y += 5)
			{
				if(!((x==2&&y==2)||(x==2&&y==27)||(x==27&&y==2)||(x==27&&y==27)))
				{
					targets.add(new XYLocation(x,y));
				}
			}
		}
	}
	
	public static void setGoals()
	{
		int lastDist = -1;
		XYLocation s1 = Maze.get_instance().where_is(MazeElement.S1),
			s2 = Maze.get_instance().where_is(MazeElement.S2);
		for(int i = 0; i < locations.size(); i++)
		{
			for(int j = 0; j < locations.size(); j++)
			{
				if(i!=j)
				{
					int newSum = ManathanHeuristic.getDistance(s1, locations.get(i), false) +
						ManathanHeuristic.getDistance(s2, locations.get(j), false);
					if(lastDist == -1 || newSum < lastDist)
					{
						lastDist=newSum;
						cornerS1 = i;
						cornerS2 = j;
					}
				}
			}
		}
		goals = true;
	}

	public Explore(int id)
	{
		super(id);
		start();
	}
	
	@Override
	public Plan select_plan(SensoresPoupador sensor) {		
		if(!has_plan())
		{
			if(targets != null && targets.size() > 0)
			{
				// navega!
				Saver saver = new Saver(GetId());
				XYLocation loc = Maze.get_instance().where_is(GetId());
				while(targets.size() > 0)
				{
					int index = ManathanHeuristic.getClosestIndex(loc, targets);
					if(saver.can_go_through(Maze.get_instance().what_is(targets.get(index))))
					{
						Plan ret = new PlanGoToExplore(new Saver(GetId()), sensor, targets.get(index));
						targets.remove(index);
						return ret;
					}					
					targets.remove(index);
				}
			}
			else
			{
				if(!is_done(sensor))
				{
					return new PlanGoToExplore(new Saver(GetId()), sensor, 
							ManathanHeuristic.getClosest(Maze.get_instance().where_is(GetId()), 
									Maze.get_instance().where_is_all(MazeElement.NO_VISION)));
				}
			}
		}
		return null;
	}
	
	@Override
	public boolean is_done(SensoresPoupador sensor)
	{
		return Maze.get_instance().is_all_mapped();// ||		(Maze.get_instance().is_known_position(MazeElement.BANK) && (Plan.TICKS > 600));
	}
	
}
