package agent;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import reward.Reward;

import util.*;

import org.w3c.dom.Element;

import experiment.ExperimentLogger;
import experiment.Logger;


// This is the agent super class, It holds all the generic fields and methods common it its subclasses 
public abstract class Agent {
	protected ObservableEnvInfo currentEnvInfo;
	protected State currentState;
	protected StateDomain stateDomain;
	protected Action currentAction;
	protected Reward reward;
	protected int round = 0;
	protected IPolicy policy;
	protected ActionDomain aDomain; 
	protected Map<State,Object> strategy;
	protected int agentId;
	protected StateMapper stateMapper;
	protected ExperimentLogger log;
	
	public enum Policy
	{
	    EGREEDY, BOLTZMAN, RANDOM; 
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
			currentAction = new TwoActions();
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
		
		default:
			break;
		}
		
	}
	
	public void constructStructures(ObservableEnvInfo state){
	}
	
	public void recordToLogger(ExperimentLogger log){
	}
	
	
	
	

}
