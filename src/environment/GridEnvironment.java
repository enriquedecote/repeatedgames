/*******************************************************************************
 * Copyright (c) 2011 Enrique Munoz de Cote.
 * repeatedgames is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * repeatedgames is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with repeatedgames.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * Please send an email to: jemc@inaoep.mx for comments or to become part of this project.
 * Contributors:
 *     Enrique Munoz de Cote - initial API and implementation
 ******************************************************************************/
package environment;


import java.util.HashMap;

import java.util.Iterator;
import java.util.Map;


import java.util.Vector;

import agent.Agent;

import util.Action;
import util.CoordinateState;
import util.GridStateDomain;
import util.JointActionState;
import util.State;
import util.StateDomain;
 
// sets the grid Environment for the game and also fills the Transition function
// it is a subclass of the generic environment and it can be implemented.
public class GridEnvironment implements Environment {
	int []coordinate; 
	int rows;
	int columns;
	int []coinValue;

	// Initializes the environment
	public GridEnvironment (Vector<Agent> players, int r, int c){
		System.out.println("initializing gridEnvironment");
		
		this.rows = r;
		this.columns = c;
		
		JointActionState jointAct = new JointActionState(players.capacity());
		
		
		//calls the state domain class and init the state domain
		
		Vector <Vector<Action>>allJointActs;
		
		allJointActs = new Vector<Vector<Action>>();
		GridStateDomain domain = new GridStateDomain(rows, columns, players.capacity());
		
		// creates a vector of all the joint actions.
		allJointActs =	jointAct.allJointActions;
		
		
		Map<State, Integer> reward ;
		
		
		
		//iterate over all joint actions
		for(Iterator itr1 = allJointActs.iterator(); itr1.hasNext();){
			
			JointActionState jointAction = new JointActionState(players.capacity());
			jointAction.setJointAction((Vector<Action>) itr1.next());
			
			reward = new HashMap <State, Integer>();
					
			// Calculates the no of possible states, i.e the size of the state domain
			int n = rows* columns;
			int factn = factorial(n);
			int factr = factorial (n-players.capacity());
			int noofperm = factn/factr;
			

			//System.out.println("no of perm" + noofperm);
			
			
			// Iterate over all the possible states.
			for (int j=0; j<noofperm; j++){
				GridStateDomain domain1 = new GridStateDomain ( rows, columns, players.capacity());
			
				Vector<CoordinateState> set = new Vector<CoordinateState>();
				
				
				set.add(domain1.domain.get(j));
				
					CoordinateState state = new CoordinateState();
					state = (CoordinateState) set.get(0); //itrset.next();
				
			
				//System.out.println(state.features.get(0));
				
				
				
				
				
				CoordinateState nxtState = new CoordinateState();
				
				
				// for each joint action get next state
				nxtState = state.getNextState(jointAction, state);
				
				
				
				
				// check next state with state domain
				Vector<CoordinateState> set1 = domain1.getStateSet();
				
				boolean end = false;			
				
				for (Iterator itrset1 = set1.iterator(); itrset1.hasNext();){
					CoordinateState comp = new CoordinateState();
					comp = (CoordinateState) itrset1.next();
					
					
					boolean result = comp.compareState(comp, state);
					
					if (result){
						end = true;
						break;
						
					}			
				}
				
				if (end){
					
					reward.put(state, 1);
				
				}
				else {
					
					reward.put(state, 0);
				}
				
		 	}
			//jointAct.T.put(jointAction,reward);
						
		}
		
		
	}
		
			
	

	// finds the factorial of a given no.
	
	public static int factorial( int n )
    {
        if( n <= 1 )     // base case
            return 1;
        else
            return n * factorial( n - 1 );
    }




	@Override
	public State currentState() {
		// TODO Auto-generated method stub
		return null;
	}




	@Override
	public State nextState(State prevState, Vector<Action>) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	
}
