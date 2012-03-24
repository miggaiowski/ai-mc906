package algoritmo.planning.path;

import aima.core.search.framework.GoalTest;
import aima.core.search.framework.HeuristicFunction;
import aima.core.util.datastructure.XYLocation;

import algoritmo.environment.Maze;
import algoritmo.environment.element.MazeElement;
import algoritmo.environment.element.Person;

public class GoToFactory
{

        public GoToFactory( Person actor, XYLocation goal ) throws ImpossibleGoalException
        {
                Maze maze = Maze.get_instance( );
                MazeElement goal_element = maze.what_is( goal );
                
                if ( actor.can_go_through( goal_element ) )
                {
                        heuristic_function = new ManathanHeuristic( actor, goal );
                        goal_function = new GetToGoal( actor, goal );
                        
                }
                else
                {
                        throw new ImpossibleGoalException( 
                                        "Actor cannot go trought goal element id: "
                                        + goal_element.get_type( ) );
                }
                
        }
        
        public HeuristicFunction getHeuristicFunction( )
        {       
                return heuristic_function;
        }
        
        public GoalTest getGoalFunction( )
        {
                return goal_function;
        }
        
        
        private class GetToGoal implements GoalTest
        {
                public GetToGoal( MazeElement actor, XYLocation goal )
                {
                        this.actor = actor;
                        this.goal = goal;
                        expanded_nodes = 0;
                }
                
                public boolean isGoalState( Object state )
                {
                        expanded_nodes++;
                        if ( expanded_nodes > MAX_NODES )
                        		return true;
                        
                        Maze maze = (Maze) state;
                        XYLocation agent_pos = maze.where_is( actor );

                        return goal.equals( agent_pos );
                }
                
                private MazeElement actor;
                private XYLocation goal;
                
                private static final int MAX_NODES = 500;
                private int expanded_nodes;

        }

        private HeuristicFunction heuristic_function;
        private GoalTest goal_function;
        

        
}