package util;

import java.util.AbstractList;
import java.lang.Cloneable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import agent.Agent;


/**
 * @author aladdinagentschool
 *This is a subclass of state, this class can be implemented. it creates a coordinate when the constructor is called and it 
 *has methods to get next state, compare coordinates
 * @param <E>
 */
public class CoordinateState<E> extends State<E, Map<E,Vector<Integer>>>{
	/* 
	 * Agent is the key of the map
	 * Coordinate is a vector , where the first element is the x coordinate and the second element is the y coordinate. this is the value.
	 */
	private boolean collision = false;


	// constructor
	public CoordinateState(Map<E,Vector<Integer>> feat){
		
		features = new Vector<Map<E,Vector<Integer>>>();
		features.add(feat);
		T = new HashMap <E, Map<State, Integer>>();
	} 

	// constructors
	public CoordinateState(){
		
	}
	public CoordinateState(int [] coor){
		
		Vector<Integer> c;
		for (int i=0; i<coor.length; i=i+2){
			 c = new Vector<Integer>();
			c.add(coor[i]);
			c.add(coor[i+1]);
		}
		
	}



	// constructors
/*	public CoordinateState( CoordinateState state){
		features = new Vector<Map<E, Vector<Integer>>>();
		features = state.features;
		
	}

	// constructors
	public CoordinateState( Vector<Map<E, Vector<Integer>>> feature){
		this.features = feature;
		
	}*/



	public Vector<Map<E, Vector<Integer>>> getCoordinateState (){
		return features;
	}
	
	// method to retrieve the coordinate of an Agent
	public Vector<Integer> getCoordiante (Agent agent, CoordinateState state){
		Map<E,Vector<Integer>> feature;
		Vector<Integer> coordinate;
		
		feature = new HashMap <E, Vector<Integer>>();
		coordinate = new Vector <Integer>();
		//System.out.println("its" +state);
		feature = features.get(0);
		
		coordinate = feature.get(agent);
		return coordinate;
				
	}
	
	
	  
	// method to get next state
	/**
	 * @param actionState this the joint action of the agents 
	 * @param s is the state of the game
	 * @return next state as a Coordinate state
	 */
	public CoordinateState getNextState (JointActionState actionState, State s ){
		//public CoordinateState getNextState (Action actionState, State s ){
		CoordinateState state = (CoordinateState) s;
	 
 		Vector<Action> actions = new Vector<Action>();
		actions = actionState.getJointAction();
		
		
		int i =0;
		Map<Agent, Vector <Integer>> coorstate = (Map<Agent, Vector<Integer>>) state.features.get(0);
		
		Set<Agent> agentList = coorstate.keySet();
		
		
		
		for (Iterator itr1= agentList.iterator(); itr1.hasNext();){	
			Agent agent = (Agent) itr1.next();
			
			Vector<Integer> coor = state.getCoordiante(agent, state);
			
			Action action = actions.get(i);
			i++;
			String name = action.getName();
		
			if (action.getName() == "right"){
				int value = coor.get(0);
				//coor.add(index, element)
				coor.set(0, value+1);
				//System.out.print("right");
				
			}
			else if (action.getName() == "left"){
				
				int value = coor.get(0);
				coor.set(0, value-1);	
				//System.out.print("left");
			}
			else if (action.getName() == "up"){
				
				int value = coor.get(1);
				coor.set(1, value+1);	
				
				//System.out.print("up");
			}
			else if (action.getName() == "down"){
				
				int value = coor.get(1);
				coor.set(1, value-1);	
				//System.out.print("down");
			}
			
			
			}
	
		
		collision = compareCoordinate(state);

		
		
		return state;
	}
	
	// compare the Coordiante of a State to check if the agents collide
	public Boolean compareCoordinate (CoordinateState s){
		Map<Agent, Vector <Integer>> coorstate1 = (Map<Agent, Vector<Integer>>) s.features.get(0);
		Map<Agent, Vector <Integer>> coorstate2 = (Map<Agent, Vector<Integer>>) s.features.get(0);
		boolean compare = false;
		Collection list = coorstate1.values();
		Collection list1 = coorstate2.values();
		
		if (list.containsAll(list1)){
			compare = true;
			
		}
		else {
			compare = false;
			
		}
		
		return compare;
	}
	
	// method to check that two agents not have the same state at the same time
	public Boolean compareState (CoordinateState state1, CoordinateState state2){
		Map<Agent, Vector <Integer>> coorstate1 = (Map<Agent, Vector<Integer>>) state1.features.get(0);
		Map<Agent, Vector <Integer>> coorstate2 = (Map<Agent, Vector<Integer>>) state2.features.get(0);
		
		boolean compare = false;
		Collection list = coorstate1.values();
		Collection list1 = coorstate2.values();
		
		if (list.containsAll(list1)){
			compare = true;
			
		}
		else {
			compare = false;
			
		}
		
		return compare;
	}
	public boolean didCollide(){
		return collision;
	}
	
	

}


