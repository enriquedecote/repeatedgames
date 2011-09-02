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
public class Action_Grid extends Action  {
/*	
	public static Action_Grid right = new Action_Grid ("right");
	public static Action_Grid left = new Action_Grid ("left");
	public static Action_Grid up = new Action_Grid ("up");
	public static Action_Grid down = new Action_Grid ("down");
	*/
	

	// constructors
	public Action_Grid (String name){
		actionName= name;
		domain = new ActionDomain_Grid();
		changeToState(name);
	}

	// constructors
	public Action_Grid (){
		domain = new ActionDomain_Grid();
		changeToState(domain.getFirstAction());
	}
	
	public Action_Grid(int id){
		agentId = id; 
		domain = new ActionDomain_Grid();
		changeToState(domain.getFirstAction());
	}
	
	public Action_Grid newInstance(){
		return new Action_Grid(agentId);
	}
	
}


