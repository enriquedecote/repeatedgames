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
/**
 * 
 */
package util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

/**
 * @author Enrique Munoz de Cote
 *
 */
public abstract class StateMapper<State> {
	protected StateDomain<State> stateDomain;
	
	//mapping transforms from joint actions to its encoding state
	protected Map<Vector<Object>,State> mapping = new HashMap<Vector<Object>, State>();
	
	public void init(ObservableEnvInfo state){
	}
	
	public Set<State> getStateSet(){
		return stateDomain.getStateSet();
	}
	public StateDomain<State> getStateDomain(){
		return stateDomain;
	}
	
	
	public State getState(Vector<Object> state){
		return mapping.get(state);
	}
	
	public State getState(ObservableEnvInfo state){
		return null;
	}
	
	public Vector<Action> getActions(ObservableEnvInfo info){
		return null;
	}
	
	public Vector<Object> getFeatures(ObservableEnvInfo info){
		return null;
	}
}
