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
import java.util.Set;
import java.util.Vector;

/**
 * @author Enrique Munoz de Cote
 *
 * This is the abstract class StateDomain. 
 * @param T a subclass of state
 */
public abstract class StateDomain<State> {
	
	protected Set<State> domain;
	
	public StateDomain(){
		
	}
	
	// Method to get the domain
	public Set<State> getStateSet () {
		return domain;
	}
// return domain size
	public int size() {
		return domain.size();
	}
	 
	/**
	 * Transforms info coming from the environment to the perceived state info
	 * @param info the info coming from the environment
	 * @return
	 */
	public State getState(ObservableEnvInfo info){
		return null;
	}
	 
}


