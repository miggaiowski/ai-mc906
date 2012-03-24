package algoritmo.planning.strategy;

import algoritmo.SensoresPoupador;
import algoritmo.environment.Maze;
import algoritmo.environment.element.Saver;
import algoritmo.planning.path.Plan;
import algoritmo.planning.path.PlanGoToBank;

public class Flee extends Strategy {

	public Flee(int id)
	{
		super(id);
	}

	@Override
	public Plan select_plan(SensoresPoupador sensor) {
		if(!has_plan())
			return new PlanGoToBank(new Saver(GetId()));
		return null;
	}

	@Override
	public boolean is_done(SensoresPoupador sensor)
	{
		return !has_plan() || sensor.getNumeroDeMoedas() == 0;
	}
}
