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
public class StateMapper_JointActionGrid extends StateMapper<State_JointActionGrid> {
	
	public StateMapper_JointActionGrid(){
	}

	@Override
	public void init(ObservableEnvInfo info){
		Info_Grid state = (Info_Grid) info;
/*		Vector<Action> vectA = state.currentJointAction();
		Vector<Action> vectB = new Vector();
		Action a0 = vectA.get(0).newInstance();
		Action a1 = vectA.get(1).newInstance();
		vectB.add(a0); vectB.add(a1);*/
		stateDomain = new StateDomain_JointActionGrid(state);

		for (State_JointActionGrid st : stateDomain.getStateSet()) {
			mapping.put(st.getFeatures(),st);
		}
	}
	
	public State_JointAction getState(Info_NFG info){
		Vector<Action> vectA = info.currentJointAction();
		Vector<Object> vectO = new Vector<Object>();
		for (Action action : vectA) {
			vectO.add(action.getCurrentState());
		}
		
		return mapping.get(vectO);
	}
	
	@Override
	public State_JointActionGrid getState(ObservableEnvInfo info){
		Info_NFG state = (Info_NFG) info;
		Vector<Action> vectA = state.currentJointAction();
		Vector<Object> vectO = new Vector<Object>();
		for (Action action : vectA) {
			vectO.add(action.getCurrentState());
		}
		
		return mapping.get(vectO);
	}
	
	@Override
	public Vector<Action> getActions(ObservableEnvInfo info){
		Info_NFG state = (Info_NFG) info;
		return state.currentJointAction();
	}
	
	@Override
	public Vector<Object> getFeatures(ObservableEnvInfo info){
		return getState(info).getFeatures();
	}
}
