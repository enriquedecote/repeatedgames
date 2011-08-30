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

import java.util.Vector;

/**
 * @author Enrique Munoz de Cote
 *
 */
public class State_JointActionGrid extends State {
	// actionssSet is the list of actions of the agents in the game, 
	private static Vector<Object> jointAction;
	
	public State_JointActionGrid(){
		super();
	}
	
	//Constructor
	public State_JointActionGrid (Vector<Action> actions, Vector<Coordinate> coords){
		jointAction = new Vector<Object>();
		init(actions);
	}
	
	public void init(Vector<Action> actions){
		for (Action action : actions) {
			jointAction.add(action.getCurrentState());
		}
		domain = new StateDomain_JointAction(actions);
	}
	
	public Vector<Object> getJointAction(){
		return jointAction;
	}
	
	public String name (Action a){
		return a.getName();
	}
}
