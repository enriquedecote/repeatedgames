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
public class StateDomain_Single extends StateDomain<State_JointAction> {
	State_JointAction dummyState;
	private Map<Vector<Object>,State_JointAction> mapping;
	
	/**
	 * Only works for 2 agents
	 * @param actions
	 */
	public StateDomain_Single (){
		domain = new LinkedHashSet<State_JointAction>();
		mapping = new HashMap<Vector<Object>, State_JointAction>();
		Vector<Object> vectO;

				dummyState = new State_JointAction();
				domain.add(dummyState);

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
			return dummyState;


	}
	
}
  

