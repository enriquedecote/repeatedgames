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
public class JointActionStateDomain extends StateDomain<JointActionState> {
	
	private Map<Vector<Object>,JointActionState> mapping;
	
	/**
	 * Only works for 2 agents
	 * @param actions
	 */
	public JointActionStateDomain (Vector<Action> actions){
		domain = new LinkedHashSet<JointActionState>();
		mapping = new HashMap<Vector<Object>, JointActionState>();
		Vector<Object> vectO;
		
		for(Object o0 : actions.get(0).getDomainSet()){
			for(Object o1: actions.get(1).getDomainSet()){
				//vectA = new Vector<Action>();
				//vectO = new Vector<Object>();
				//vectA.add(o0); vectA.add(o1);
				//vectO.add(o0); vectO.add(o1);
				JointActionState state = new JointActionState();
				state.addFeature(o0); state.addFeature(o1);
				//mapping.put(vectO, state);
				domain.add(state);
			}
		}
	}
	

	
// get state domain		
	public Set<JointActionState> getStateSet(){
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
	public JointActionState getState(ObservableEnvInfo e){
		String s = e.getClass().toString();
		if(s.equals("class util.NFGInfo")){
			NFGInfo nfg = (NFGInfo) e;
			Vector<Action> vectA = nfg.currentJointAction();
			Vector<Object> vectO = new Vector<Object>();
			for (int i=0; i< vectA.size(); i++) {
				vectO.add(vectA.get(i));
			}
			
			return mapping.get(vectO);
		}
		else{
			System.err.println("the state info is not of type NFGInfo, JointActionStateDomain cannot handdle this info type");
			return null;
		}
	}
	
}
  

