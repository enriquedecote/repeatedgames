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
package environment;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import agent.Agent;

import util.Action;
import util.JointActionState;
import util.ObservableEnvInfo;
import util.State;
import util.StateSet;



 /**
 * @author Enrique Munoz de Cote
 *
 * The environment has several attributes that can be perceived by an agent,
 * the specific attributes will be declared in the classes that implement this interface
 * @param <E>
 * @param <F>
 */
public interface Environment<A> {
	 /**
	 *  Gives information about the environment given the actions that transform the env
	 * @param actions
	 * @return a map with the new env info
	 */
	public ObservableEnvInfo nextEnvInfo(Vector<A> actions);
	/**
	 * Gives information about the environment
	 * @return
	 */
	public ObservableEnvInfo currentEnvInfo();
	
	/**
	 * Initializes the object with action information
	 * @param actions
	 */
	public void Init(Vector<A> actions);
}
