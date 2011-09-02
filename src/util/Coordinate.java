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
 * @author enrique
 * 
 * Implements a coordinate.
 */
public class Coordinate {
	private int[] limits = new int[2];
	private int[] state = new int[2];

	public Coordinate(int rowSize, int colSize){
		limits[0] = rowSize;
		limits[1] = colSize;
		state[0] = rowSize-1;
		state[1] = colSize-1;
	}
	
	public int[] getCurrentState(){
		return state;
	}
	
	public void changeToState(int[] s){
		for (int i = 0; i < s.length; i++) 
			state[i] = s[i];
	}
	
	public void changeToState(int index, int state){
		this.state[index] = state;
	}
	
	public int[] getLimits(){
		return limits;
	}
}
