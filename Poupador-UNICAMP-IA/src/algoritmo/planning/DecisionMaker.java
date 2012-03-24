package algoritmo.planning;

import aima.core.util.datastructure.XYLocation;
import algoritmo.Poupador;
import algoritmo.SensoresPoupador;
import algoritmo.environment.Maze;
import algoritmo.environment.MazeAction;
import algoritmo.planning.path.*;
import algoritmo.planning.strategy.*;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction;

import algoritmo.environment.element.*;

public class DecisionMaker {

	public final int EXPLORING = 0;
	public final int FLEEING = 1;
	public final int SAVING = 2;
	
	public final int DISTANCE_TO_REPLAN = 4;
	
	public static boolean ready = false;
	
	// Holds the current state
	private int _state;
	// "Saver" ID
	private int _id;
	// the current strategy
	private Strategy _currentStrategy;
	
	private int _stopped = 0;
	private XYLocation _lastLoc = null;
	
	public DecisionMaker(int id, SensoresPoupador sensor)
	{
		_id = id;
		SetState(EXPLORING);
		Maze.get_instance().update_poupador( sensor, id);
		if(id == MazeElement.S2)ready =true;
		else ready = false;
	}
	
	public int GetState()
	{
		return _state;
	}
	
	private void SetState(int newState)
	{
		_state = newState;
		switch (newState) {
		case EXPLORING:
			_currentStrategy = new Explore(GetId());
			break;
		case FLEEING:
			_currentStrategy = new Flee(GetId());
			break;
		case SAVING:
			_currentStrategy = new CollectMoney(GetId());
			break;
		}
	}
	
	public int GetId()
	{
		return _id;
	}
	
	public int move(SensoresPoupador sensores)
	{
		if(ready)
		{
			XYLocation newLoc = new XYLocation(sensores.getPosicao().x, sensores.getPosicao().y);
			if(_lastLoc != null)
			{
				if(_lastLoc.equals(newLoc))_stopped++;
				else 
				{
					_lastLoc = newLoc;
					_stopped = 0;
				}
			}
			else _lastLoc = newLoc;
			// verify what is the current state
			select_state(sensores, _stopped > 1);
			
			return _currentStrategy.move(sensores);
		}
		else return MazeAction.NO_MOVE;
	}
	
	private void select_state(SensoresPoupador sensores, boolean stopped)
	{

		if(_currentStrategy == null)
			_currentStrategy = new Explore(GetId());	// first state
		else
		{
			// otherwise verify which state should be selected
			switch(_state)
			{
			case EXPLORING:
				if(_currentStrategy.is_done(sensores))
				{
					if(sensores.getNumeroDeMoedas() > 0)
						SetState(FLEEING);
					else SetState(SAVING);
				}
				break;
			case FLEEING:
				if(_currentStrategy.is_done(sensores))
					SetState(SAVING);
				break;
			case SAVING:
				if(_currentStrategy.is_done(sensores))
					SetState(FLEEING);
				break;
			}
		}
	}
}
