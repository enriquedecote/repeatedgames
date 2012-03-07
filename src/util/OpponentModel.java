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

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

public  class OpponentModel {
		protected int numActions;
		protected HashMap<Action, Integer> strat;
		protected int agentId;
		protected Vector<Object> sequence = new  Vector<Object>();
		protected Deque<Object> pastActions; // this player's actions done in the past, with a window of size CAPACITY
		protected static final int CAPACITY = 10;
		
		public OpponentModel(int actions, int id){
			this.numActions = actions;
			this.agentId = id;
			strat = new HashMap<Action, Integer>();
			pastActions = new ArrayDeque<Object>(CAPACITY);
		}
		
		/**
		 * records the last action pair
		 * @param hisAction
		 */
		public void lastAction(Action hisAction){
			Object hisOaction = hisAction.getCurrentState();
			strat.put(hisAction, strat.get(hisAction)+1);
			sequence.add(hisOaction);
			if(pastActions.size() >= CAPACITY)
				pastActions.removeLast();
			pastActions.addFirst(hisOaction);
		}
		
		public double[] currentStrategy(){
			double[] s = new double[numActions];
			
			int sum=0;
			for (Action a : strat.keySet())
				sum += strat.get(a);
			
			int i = 0;
			for (Action a : strat.keySet())
				s[i] = (double)strat.get(a)/(double)sum;
					
			assert(s.length == numActions);	    
			return s;
		}

		public Object currentAction(){
			return pastActions.getFirst();
		}
		public int getId(){
			return agentId;
		}
}
