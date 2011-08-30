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
 */
public class Info_Grid extends Info_NFG{
	protected Object[] jointC;
	private Vector<Coordinate> jointCoord = new Vector();
	
	public Info_Grid(){
		super();
	}
	
	public Info_Grid(Vector<Action> j){	
		super(j);
	}
	
	public void updateJointAction(Vector<Action> j){
		for (int i = 0; i < j.size(); i++) {
			jointO[i] = j.get(i).getCurrentState();
		}
	}
	
	
	public void updateJointCoord(Vector<Coordinate> j){
		jointCoord = j;
	}
	
	public Vector<Coordinate> currentJointCoord(){
		return jointCoord;
	}
}
