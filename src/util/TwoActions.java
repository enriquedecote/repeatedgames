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

public class TwoActions extends Action{
	public static TwoActions One = new TwoActions (1);
	public static TwoActions Two = new TwoActions (2);
	
	// constructors
	public TwoActions (int Name){
		super ();
		actionSet.add(One);
		actionSet.add(Two);
	}


	// constructors
	public TwoActions (){
		super ();
		actionSet.add(One);
		actionSet.add(Two);
	}
}
