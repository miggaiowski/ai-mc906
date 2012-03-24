package algoritmo.planning.strategy;

import algoritmo.Poupador;
import algoritmo.SensoresPoupador;
import algoritmo.environment.Maze;
import algoritmo.environment.MazeAction;
import algoritmo.planning.path.Plan;

public abstract class Strategy {
	
	public Strategy(int id)
	{
		_id = id;
	}

	private int _id;
	private Plan _currentPlan = null;
	private int _stopped = 0;
	
	public abstract Plan select_plan(SensoresPoupador sensor);
	
	public abstract boolean is_done(SensoresPoupador sensor);
	
	public int GetId()
	{
		return _id;
	}
	
	public int move(SensoresPoupador sensor)
	{
		Plan newPlan = select_plan(sensor);
		if(newPlan != null)
			_currentPlan = newPlan;		
		int ret = has_plan()?_currentPlan.execute(sensor):MazeAction.NO_MOVE;
		if(ret == MazeAction.NO_MOVE)_stopped++;
		else _stopped = 0;
		return ret;
	}
	
	public boolean has_plan()
	{
		return !is_plan_null() && _currentPlan.is_executable();
	}
	
	public boolean is_plan_null()
	{
		return _currentPlan == null;
	}
	
	public boolean is_stopped()
	{
		return _stopped > 1;
	}
}
