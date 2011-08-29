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
* @author aladdinagentschool
*This is a subclass of ActionDomain, this class can be implemented. it creates a domain of all possible gridactions
* 
*/
public class ActionDomain_Grid extends ActionDomain {
	
	public ActionDomain_Grid(){
		actionSet = new Vector();
		actionSet.add("right");
		actionSet.add("left");
		actionSet.add("up");
		actionSet.add("down");
		actionSet.add("put");	
	}
}
