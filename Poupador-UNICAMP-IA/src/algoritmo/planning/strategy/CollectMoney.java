package algoritmo.planning.strategy;

import java.awt.image.BandCombineOp;

import algoritmo.SensoresPoupador;
import algoritmo.environment.Maze;
import algoritmo.environment.element.Bank;
import algoritmo.environment.element.MazeElement;
import algoritmo.environment.element.Robber;
import algoritmo.environment.element.Saver;
import algoritmo.planning.path.ManathanHeuristic;
import algoritmo.planning.path.Plan;
import algoritmo.planning.path.PlanGetMoney;
import algoritmo.planning.path.PlanGoToExplore;

public class CollectMoney extends Strategy {

	public CollectMoney(int id)
	{
		super(id);
	}
	@Override
	public Plan select_plan(SensoresPoupador sensor) {
		// TODO Auto-generated method stub
		if(!has_plan())
		{
			return new PlanGoToExplore(new Saver(GetId()), sensor, 
					ManathanHeuristic.getClosest(Maze.get_instance().where_is(GetId()),
							Maze.get_instance().where_is_all(MazeElement.MONEY)));
			//return new PlanGetMoney(new Saver(GetId()), get_safe_money());
		}
		return null;
	}


	@Override
	public boolean is_done(SensoresPoupador sensor)
	{
		return (!has_plan()) || (sensor.getNumeroDeMoedas() > get_safe_money(sensor))
		|| (Maze.get_instance().where_is_all(MazeElement.MONEY).size() == 0);
	}
	
	public static int get_safe_money(SensoresPoupador sensor)
	{
		int good_money = Bank.last_deposit + 1;
		if(Saver.robbed > 0)
			good_money = (Saver.robbed + good_money) / 2;
		return good_money;
	}
}
