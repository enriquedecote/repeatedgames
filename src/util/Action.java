package util;

//

import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import agent.Agent;
/**
 * @author aladdinagentschool
 * generic type action, can be implemented. It holds all the generic fields and methods common it its subclasses 
 */
public abstract class Action {
	
	protected int agentId;
	//protected Vector<Action> actionSet= new Vector<Action>();
	protected String actionName;
	protected ActionDomain domain;
	protected Object state;
	
	
	public Action (){
		
	}

	public Action(String actName) {
		
		if (actName== "GridActions"){
			GridActions action = new GridActions();
		}else 
		if (actName== "EnumActions"){
			EnumActions action = new EnumActions();
		}else
		if (actName== "LeaderFollowerActions"){
			LeaderFollowerActions action = new LeaderFollowerActions();
		}else
		if (actName== "TwoActions"){
			TwoActions action = new TwoActions();
		}
		state = domain.getFirstAction();
	}
	// returns name of the action 
	public String getName (){
		return actionName;
	}

	/* returns a set of actions
	 * public Vector<Action> actionSet(){
		return actionSet;
	}
	*/
	
	public Vector<Object> getDomainSet(){
		return domain.getActionSet();
	}
	
	public Object getCurrentState(){
		return state;
	}
	
	public void changeToState(Object o){
		assert(domain.contains(o));
		state = o;
	}
	
	public Action newInstance(){
		return null;
	}

}
