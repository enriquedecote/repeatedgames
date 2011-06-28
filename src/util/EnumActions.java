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

public class EnumActions extends Action{
	public static EnumActions One = new EnumActions (1);
	public static EnumActions Two = new EnumActions (2);
	public static EnumActions Three = new EnumActions (3);
	public static EnumActions Four = new EnumActions (4);
	public static EnumActions Five = new EnumActions (5);
	public static EnumActions Six = new EnumActions (6);
	public static EnumActions Seven = new EnumActions (7);
	public static EnumActions Eight = new EnumActions (8);
	public static EnumActions Nine = new EnumActions (9);
	public static EnumActions Ten = new EnumActions (10);
	public static EnumActions Eleven = new EnumActions (11);
	public static EnumActions Twelve = new EnumActions (12);
	
	// constructors
	public EnumActions (int Name){
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
	public EnumActions (){
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
