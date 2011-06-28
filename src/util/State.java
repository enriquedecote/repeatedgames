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

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import agent.Agent;


/*
 * E elements: Agent,Pods, etc.
 * F feature: Actions, Vector(coordinates)
 * Generic Type State
 * holds all the fields common to its subclass
 */
public abstract class State<E,F>{
	
	protected Vector<F> features;
	protected StateDomain domain;
	//protected Vector<Map<E, F>> features;
	// here E will be JointAction, and T is the Transition function
	protected Map <E,Map<State<E,F>,Integer>> T = new HashMap <E, Map<State<E,F>, Integer>>();
	protected boolean known;
	protected State currentState;
	
	public State(){
		features = new Vector<F>();
	}
	
	// adds features
	public void addFeature (F map){
		features.add(map);
	}
	
	public F getFeature(int i){
		return features.get(i);
	}	
	
	public Vector<F> getFeatures(){
		return features;
	}
	
	public StateDomain getStateDomain(){
		return domain;
	}
	
	public void updateState(ObservableEnvInfo info){
	}
}
