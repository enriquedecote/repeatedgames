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
package util;

import java.io.IOException;
import java.util.*;

import org.xml.sax.SAXException;

import agent.Agent;
import agent.QLearningAgent;


/*
 * extension of generic type StateDomain
 */
/**
 * @author aladdinagentschool
 *subclass of  generic type statedomain, can be implemented. It holds all the generic fields and methods common it its subclasses 
 *it creates the grid domain 
 */
public class StateDomain_JointAction extends StateDomain<State_JointAction> {
	
	private Map<Vector<Object>,State_JointAction> mapping;
	private int numAgents = 0;
	private int numStates = 0;
	private int[] agentsActions;
	
	/**
	 * @param actions
	 */
	public StateDomain_JointAction (Vector<Action> actions){
		domain = new LinkedHashSet<State_JointAction>();
		mapping = new HashMap<Vector<Object>, State_JointAction>();
		numAgents = actions.size();
		agentsActions = new int[numAgents];
		int numberOfOutcomes = 1;
		  for (int i = 0; i < numAgents; i++){
			  agentsActions[i] = actions.get(i).getDomainSet().size();
			  numberOfOutcomes *= agentsActions[i];
		  }
		  numStates = numberOfOutcomes;
		
		for (int i = 0; i < numberOfOutcomes; i++) {
			State_JointAction state = new State_JointAction();
			int[] jointAct = toActions(i);
			for (int j = 0; j < jointAct.length; j++) 
				state.addFeature(jointAct[j]);
			domain.add(state);
		}
	}
	

	
// get state domain		
	public Set<State_JointAction> getStateSet(){
		return domain;	
	}	
// returns size of the domain	
	public int size(){
		return domain.size();
	}
	
	/**
	 * Transforms info coming from the environment to the perceived state info
	 * @param info the info coming from the environment
	 * @return
	 */
	public State_JointAction getState(ObservableEnvInfo e){
		String s = e.getClass().toString();
		if(s.equals("class util.NFGInfo")){
			NFGInfo nfg = (NFGInfo) e;
			Vector<Action> vectA = nfg.currentJointAction();
			Vector<Object> vectO = new Vector<Object>();
			for (int i=0; i< vectA.size(); i++) {
				vectO.add(vectA.get(i));
			}
			
			return mapping.get(vectO);
		}
		else{
			System.err.println("the state info is not of type NFGInfo, JointActionStateDomain cannot handdle this info type");
			return null;
		}
	}
	
	/**
	 * @param n the state (joint action)
	 * @return the vector of actions
	 */
	private int[] toActions(int n){
		int[] strat = new int[numAgents];
	       int i=0;
	       while (n > 0) {
	          strat[i] = n % agentsActions[i];
	          n = n/agentsActions[i];
	          i++;
	       }
	       assert(strat.length == numAgents);
	       return strat;
	}
	
}
  

