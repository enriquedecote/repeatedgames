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
 * This class gets the domain of the Action variable, it should NOT construct the Actions itself
 * @author Enrique Munoz de Cote
 *
 */
public class NintegerActionDomain extends ActionDomain {
	
	/**
	 * Generates a domain of size numberActions [0,numberActions-1]
	 * @param numberActions the size of the domain
	 */
	public NintegerActionDomain(int numberActions){
		
		actionSet = new Vector(numberActions);
		for (int i = 0; i < numberActions; i++) {
			actionSet.add(i);
		}
	}

}
