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
package reward;

import java.util.Vector;

import util.Action;
import util.ObservableEnvInfo;
import util.State;

// Generic type reward 
public abstract class Reward {

 
 public void Init(String game){
	 
 }
 public int getReward(ObservableEnvInfo s, Vector<Object> actions, int agent){
	 return Integer.MIN_VALUE;
 }
 
 public int getReward(Vector<Object> actions, int agent){
	 return Integer.MIN_VALUE;
 }
 
 public int[] getRewards(ObservableEnvInfo s, Vector<Action> actions){
	 return null;
 }

}
