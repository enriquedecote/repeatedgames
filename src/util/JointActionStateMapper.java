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
public class JointActionStateMapper extends StateMapper<JointActionState> {
	
	public JointActionStateMapper(){
	}

	@Override
	public void init(ObservableEnvInfo info){
		NFGInfo state = (NFGInfo) info;
		Vector<Action> vectA = state.currentJointAction();

		Action a0 = vectA.get(0).newInstance();
		Action a1 = vectA.get(1).newInstance();
		vectA.clear();
		vectA.add(a0); vectA.add(a1);
		stateDomain = new JointActionStateDomain(vectA);

		for (JointActionState st : stateDomain.getStateSet()) {
			mapping.put(st.getFeatures(),st);
		}
	}
	
	public JointActionState getState(NFGInfo info){
		Vector<Action> vectA = info.currentJointAction();
		Vector<Object> vectO = new Vector<Object>();
		for (Action action : vectA) {
			vectO.add(action.getCurrentState());
		}
		
		return mapping.get(vectO);
	}
	
	@Override
	public JointActionState getState(ObservableEnvInfo info){
		NFGInfo state = (NFGInfo) info;
		Vector<Action> vectA = state.currentJointAction();
		Vector<Object> vectO = new Vector<Object>();
		for (Action action : vectA) {
			vectO.add(action.getCurrentState());
		}
		
		return mapping.get(vectO);
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
