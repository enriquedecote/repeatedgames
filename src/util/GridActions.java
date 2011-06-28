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

/**
* @author aladdinagentschool
*This is a subclass of ActionDomain, this class can be implemented. // Class with the types of grid actions.
* 
*/
public class GridActions extends Action  {
	
	public static GridActions right = new GridActions ("right");
	public static GridActions left = new GridActions ("left");
	public static GridActions up = new GridActions ("up");
	public static GridActions down = new GridActions ("down");
	

	// constructors
	public GridActions (String Name){
		
		actionName= Name;
		actionSet.add(right);
		actionSet.add(left);
		actionSet.add(up);
		actionSet.add(down);
	
	}

	// constructors
	public GridActions (){
		//super();
		actionSet.add(right);
		actionSet.add(left);
		actionSet.add(up);
		actionSet.add(down);
	
	}
	
}


