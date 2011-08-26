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
			Action_Grid action = new Action_Grid();
		}else 
		if (actName== "EnumActions"){
			Action_Enum action = new Action_Enum();
		}else
		if (actName== "LeaderFollowerActions"){
			Action_LeaderFollower action = new Action_LeaderFollower();
		}else
		if (actName== "TwoActions"){
			TwoActions action = new TwoActions();
		}
		state = domain.getFirstAction();
	}
	// returns int id of the owner of the action 
	public int getOwnerId (){
		return agentId;
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

	public String getName() {
		return actionName;
	}

}
