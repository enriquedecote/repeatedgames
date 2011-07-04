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
package agent;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import reward.Reward;
//import strategy.ExternalRegretMatchingStrategy;
//import strategy.GIGA;
//import strategy.InternalRegretMatchingStrategy;

import util.*;

import org.w3c.dom.Element;

import experiment.ExperimentLogger;
import experiment.Logger;


/**
 * An abstract class for strategies which depend only upon
 * the utility of the action taken and the utility the player 
 * would have gotten had it taken another strategy.
 * 
 * @see ExternalRegretMatchingStrategy
 * @see InternalRegretMatchingStrategy
 * @see GIGA
 * 
 * This class also provides a random number generator. It is unclear
 * if this is the best place for it.
 * @author Enrique Munoz de Cote
 *
 */
public abstract class Agent {
	protected ObservableEnvInfo currentEnvInfo;
	protected State currentState;
	protected StateDomain stateDomain;
	protected Action currentAction;
	protected Reward reward;
	protected int round = 0;
	protected IPolicy policy; // policy= strategy + exploration strategy
	protected ActionDomain aDomain; 
	protected Map<State,Object> strategy; //this is the strategy of the agent
	protected int agentId;
	protected StateMapper stateMapper;
	protected ExperimentLogger log;
	
	public enum Policy
	{
	    EGREEDY, BOLTZMAN, RANDOM, _; 
	}
	
	public enum ActionType
	{
	    TWOACTIONS, NINTACTIONS; 
	}
	
	public enum StateType
	{
	    JOINTACTIONS, _; 
	}
	
// returns the current action of the agent
	public Action getAction (){
		return currentAction;
	}
	
	// updates the fields of the agent
	public void update (ObservableEnvInfo prevState, ObservableEnvInfo currState){
		currentEnvInfo = currState;
	}
	
	// updates the fields of the agent
	public void update (ObservableEnvInfo currState){
		currentEnvInfo = currState;
	}
	
	public void init(Reward r){
		reward = r;
	}
	
	/**
	 * @param params
	 * 0- (String) the policy: EGREEDY, BOLTZMAN, RANDOM
	 * 1- (string) epsilon: value on the range [0,1]
	 */
	public void init(Element e, int id){
		agentId = id;
		
		// visibility (state)
		switch (StateType.valueOf(e.getAttribute("stateType"))) {
		case JOINTACTIONS:
			stateMapper = new JointActionStateMapper();
			//log.recordConfig("\t jointaction states");
			System.out.println("\t jointaction states");
			break;
		
		case _:
			stateMapper = new SingleStateMapper();
			//log.recordConfig("\t Agent with no state info");
			System.out.println("\t Agent with no state info");
			break;
		}
		
		//action
		switch (ActionType.valueOf(e.getAttribute("actionType"))) {
		case TWOACTIONS:
			currentAction = new TwoActions(agentId);
			break;
			
		case NINTACTIONS:	
			int numberActions = Integer.valueOf(e.getAttribute("numActions"));
			currentAction = new NintegerAction(numberActions, agentId);
			//log.recordConfig("\t actionType: " + numberActions + "-intActions");
			System.out.println("\t actionType: " + numberActions + "-intActions");
			break;
			
		default:
			System.err.print("\t This actionType is not yet implemented");
			break;
		}// end switch
		
		//policy
		switch (Policy.valueOf(e.getAttribute("policy"))) {
		case EGREEDY:
			policy = new EpsilonGreedyPolicy(e, currentAction.getDomainSet());
			//log.recordConfig("\t e-greedy policy");
			System.out.println("\t e-greedy policy");
			break;
			
		case BOLTZMAN:
			System.err.print("\t Boltzman exploration is not yet implemented");
			break;
		
		case _:
			policy = null;
			break;
		}
		
	}
	
	public int getId(){
		return agentId;
	}
	public void constructStructures(ObservableEnvInfo state){
	}
	
	public void recordToLogger(ExperimentLogger log){
	}
	
	
	
	

}
