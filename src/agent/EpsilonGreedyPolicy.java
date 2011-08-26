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
/**
 * 
 */
package agent;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Vector;

import util.Action;
import util.StateDomain_JointAction;
import util.State;
import org.w3c.dom.Element;


/**
 * @author Enrique Munoz de Cote
 *
 */
public class EpsilonGreedyPolicy implements IPolicy {
	protected float EPSILON;
	protected String decay;
	protected Random random = new Random();
	protected Vector<Object> actionSet = new Vector<Object>();
	
	public enum Decay
	{
	    POLY, EXP; 
	}

	public EpsilonGreedyPolicy(Element e, Vector<Object> actions) {
		EPSILON = Float.valueOf(e.getAttribute("epsilon"));
		decay = e.getAttribute("epsilonDecay");
		actionSet = actions;
	}

	@Override
	public Object getNextAction(Object maxA) {
		if(random.nextFloat() < EPSILON){//random choose
			int randomA = random.nextInt(actionSet.size());
			//System.out.println("random action: " + actionSet.get(randomA));
			return actionSet.get(randomA);
		} else //return the max
			return maxA;
	}
	
	@Override
	public void init(Vector<State> stateSet, Vector<Action> actionSet) {
	
	}



}
