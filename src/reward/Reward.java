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

/**
 * An interface representing the reward function of a game.
 * Rewards are integers instead of doubles so there are
 * no issues with representation.
 * @author Enrique Munoz de Cote
 *
 */
public interface Reward {

 
 public void Init(String game);
 
 public double getReward(ObservableEnvInfo s, Vector<Object> actions, int agent);
 
 public double getReward(Vector<Object> actions, int agent);
 
 public double[] getRewards(ObservableEnvInfo s, Vector<Action> actions);
 
 public boolean isSymmetric();
 
 public Reward swapPlayers(int i, int j);

}
