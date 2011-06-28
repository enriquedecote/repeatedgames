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
import java.util.Vector;

public class OpponentModel {
		int numActions;
		private double stick = 6; //values range from 0 to 6
		//private Map<Boolean, Double> followIndex = new HashMap<Boolean, Double>();
		private int strat[];
		private Vector<Integer> sequence = new  Vector<Integer>();
		private Deque<Integer> pastActions;
		private static final int CAPACITY = 10;
		private static final double GAMMA = 0.05;
		private static final double RHO = 0.5;
		private static final double TRESHOLD = (Math.pow(6, RHO)/(1-GAMMA))*0.3; //this is 0.3 from max followIndex, anything bellow this is considered follower
		private int myLastActions[];
		
		public OpponentModel(int actions){
			this.numActions = actions;
			strat = new int[numActions];
			myLastActions = new int[2];
			java.util.Arrays.fill(strat,0);
			pastActions = new ArrayDeque<Integer>(CAPACITY);
			java.util.Arrays.fill(myLastActions,-1);
		}
		
		public void lastAction(int hisAction, int myAction){
			strat[hisAction]++;
			sequence.add(hisAction);
			if(pastActions.size() >= CAPACITY)
				pastActions.removeLast();
			pastActions.addFirst(hisAction);
			myLastActions[1] = myLastActions[0];
			myLastActions[0] = myAction;
		}
		
		protected void actionCorr(int myAction, int otherOpponent, int hisOwnAction){
		}
		
		public boolean isSticking(){
			return(stickIndex() < TRESHOLD);
		}
		
		
		/**
		 * computes the followIndex of a given sequence
		 * @param opponentSeq the sequence to compare to
		 * @return the normalized followIndex of the its sequence and the argument's sequence
		 * @TODO: work out the co-domain (range) of the function 
		 */
		public double followIndex(Vector<Integer> mySeq, Vector<Integer> otherSeq){
			double fIndex = 0;
			double sumG = 0;
			double dif;
			double dif1;
			double dif2;
			double norm;
			int t = 0;
			assert(this.sequence.size() == mySeq.size() && this.sequence.size() == otherSeq.size());
			for (int i = 0; i < sequence.size(); i++) 
				sumG += Math.pow(GAMMA, i);
			
			for(int i = sequence.size()-1; i >0 ; i--) {
				dif1 = Math.abs((sequence.get(i) -  mySeq.get(i-1) + 6) % 12);
				if(dif1 > 6)
					dif1 = 12 - dif1;
				dif2 = Math.abs((sequence.get(i) - otherSeq.get(i-1) + 6) % 12);
				if(dif2 > 6)
					dif2 = 12 - dif2;
				dif = Math.min(dif1, dif2);
				//if(t==0) System.out.print(dif+": ");
				dif = Math.pow(dif, RHO);
				norm = Math.pow(GAMMA, t) / sumG;
				fIndex += norm * dif;
				t++;
			}
			//System.out.println(fIndex);
			return fIndex;
		}
		
		/**
		 * computes the followIndex of a given sequence for a given opponent. 
		 * The co-domain (range) of the function is [pow(6,RHO),0]
		 * @param opponentSeq the sequence to compare to
		 * @return the normalized followIndex of the its sequence and the argument's sequence
		 */
		public double followIndex(Vector<Integer> otherSeq){
			double fIndex = 0;
			double sumG = 0;
			double dif;
			double norm;
			int t = 0;
			assert(this.sequence.size() ==  otherSeq.size());
			for (int i = 0; i < sequence.size(); i++) 
				sumG += Math.pow(GAMMA, i);
			
			for(int i = sequence.size()-1; i >0 ; i--) {
				dif = Math.abs((sequence.get(i) - otherSeq.get(i-1) + 6) % 12);
				if(dif > 6)
					dif = 12 - dif;
				//if(t==0) System.out.print(dif+": ");
				dif = Math.pow(dif, RHO);
				norm = Math.pow(GAMMA, t) / sumG;
				fIndex += norm * dif;
				t++;
			}
			//System.out.println(fIndex);
			return fIndex;
		}
		
		/**
		 * computes its stickIndex
		 * @return the normalized stickness value
		 */
		public double stickIndex(){
			double sIndex = 0;
			double sumG = 0;
			double dif;
			double norm;
			int t = 0;
			for (int i = 0; i < sequence.size(); i++) 
				sumG += Math.pow(GAMMA, i);
			
			for(int i = sequence.size()-1; i >0 ; i--) {
				dif = Math.min(Math.abs(sequence.get(i) - sequence.get(i-1)), 
						Math.abs(sequence.get(i-1) - sequence.get(i)));
				if(dif > 6)
					dif = 12 - dif;
				//if(t==0) System.out.print(dif+": ");
				dif = Math.pow(dif,RHO);
				norm = Math.pow(GAMMA, t) / sumG;
				sIndex += norm * dif;
				t++;
			}
			//System.out.println(sIndex);
			return sIndex;
		}
		
		public Vector<Integer> getSequence(){
			return sequence;
		}
		
		public double threashold(){
			return TRESHOLD;
		}
		
		protected boolean isLeader(){
			// TODO Auto-generated method stub
			return false;
		}
		public int currentAction(){
			return pastActions.getFirst();
		}
}
