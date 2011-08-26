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
 *This is a subclass of ActionDomain, this class can be implemented. it creates a domain of all possible enum actions
 * 
 */
public class ActionDomain_Enum extends ActionDomain {
	//constructor
	public ActionDomain_Enum(){
		Action One = new Action_Enum (1);
		Action Two = new Action_Enum (2);
		Action Three = new Action_Enum (3);
		Action Four = new Action_Enum (4);
		Action Five = new Action_Enum (5);
		Action Six = new Action_Enum (6);
		Action Seven = new Action_Enum (7);
		Action Eight = new Action_Enum (8);
		Action Nine = new Action_Enum (9);
		Action Ten = new Action_Enum (10);
		Action Eleven = new Action_Enum (11);
		Action Twelve = new Action_Enum (12);
		actionSet = new Vector<Action>();
		actionSet.add(One);
		actionSet.add(Two);
		actionSet.add(Three);
		actionSet.add(Four);
		actionSet.add(Five);
		actionSet.add(Six);
		actionSet.add(Seven);
		actionSet.add(Eight);
		actionSet.add(Nine);
		actionSet.add(Ten);
		actionSet.add(Eleven);
		actionSet.add(Twelve);
	}
}
