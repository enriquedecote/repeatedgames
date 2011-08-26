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
import java.util.Vector;

/**
 * @author Enrique Munoz de Cote
 *
 */
public class StateMapper_Single extends StateMapper<State_JointAction> {
	private State_JointAction state; //dummy state
	
	public StateMapper_Single(){
	}
	/**
	 * Constructs state space and strategy
	 * @param e
	 */
	public void init(ObservableEnvInfo info){
		stateDomain = new StateDomain_Single();
		state = stateDomain.getState(info);
	}
	
	public State_JointAction getState(NFGInfo info){
		return state;
	}
	
	@Override
	public State_JointAction getState(ObservableEnvInfo info){
		return state;
	}
	
	@Override
	public Vector<Action> getActions(ObservableEnvInfo info){
		NFGInfo state = (NFGInfo) info;
		return state.currentJointAction();
	}
	
	@Override
	public Vector<Object> getFeatures(ObservableEnvInfo info){
		return getState(info).getFeatures();
	}
}
