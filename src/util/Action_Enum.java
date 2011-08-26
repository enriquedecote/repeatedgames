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

/*
 *  the types of enum actions required in the lemonade game 
 *  this class extends from generic type action
 */

public class Action_Enum extends Action{
	public static Action_Enum One = new Action_Enum (1);
	public static Action_Enum Two = new Action_Enum (2);
	public static Action_Enum Three = new Action_Enum (3);
	public static Action_Enum Four = new Action_Enum (4);
	public static Action_Enum Five = new Action_Enum (5);
	public static Action_Enum Six = new Action_Enum (6);
	public static Action_Enum Seven = new Action_Enum (7);
	public static Action_Enum Eight = new Action_Enum (8);
	public static Action_Enum Nine = new Action_Enum (9);
	public static Action_Enum Ten = new Action_Enum (10);
	public static Action_Enum Eleven = new Action_Enum (11);
	public static Action_Enum Twelve = new Action_Enum (12);
	
	// constructors
	public Action_Enum (int Name){
		super ();
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


	// constructors
	public Action_Enum (){
		super ();
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
