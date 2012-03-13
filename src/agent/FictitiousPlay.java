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
package agent;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Vector;

import org.w3c.dom.Element;

import experiment.ExperimentLogger;

import reward.Reward;

import util.Action;
import util.Info_NFG;
import util.ObservableEnvInfo;
import util.OpponentModel;
import util.TeamUPOpponentModel;
import util.State;
import util.VectorQueue;
/**
 * @author Enrique Munoz de Cote
 * This is a subclass of agent which can be instantiated. It Creates an agent that best responsds to
 * the opponent's empirical (observed) frequencies
 */
public class FictitiousPlay extends Agent {
	
	VectorQueue<State> memory = new VectorQueue<State>(1);
	private OpponentModel opponents[];
	private int numPlayers = 0;
	private int oppNumActions;
	
	public void init(Element e, int id){
		super.init(e, id);
		
		//initialize variables
		numPlayers = Integer.valueOf(e.getAttribute("players"));
		oppNumActions = Integer.valueOf(e.getAttribute("oppActions"));
		
		//initialize structures
				opponents = new OpponentModel[numPlayers-1];
				
				//create the opponent models with correct action number and their id tag
				int a=0;
				for (int j = 0; j < numPlayers; j++) {
					if(j==agentId)
						a--;
					else{
						opponents[a] = new OpponentModel(oppNumActions,j);
					}
					a++;
				}
	}
	
	// constructor
	public FictitiousPlay(Reward r) {
		reward = r;
		
	}
	// constructor
	public FictitiousPlay(){		
	}

	@Override
	// gets current action of agent
	public Action getAction() {
	
		return currentAction;
	}
	
	@Override
	public void update(ObservableEnvInfo curr) {
		Map<Integer,Action> currJointAct =  stateMapper.getActions(curr);
		
	    for (int i = 0; i < opponents.length; i++) {
	    	assert(currJointAct.get(i).getOwnerId() == i);
			opponents[i].lastAction(currJointAct.get(i));
		}
	    //System.out.println(actions[0]+","+actions[1]+","+actions[2]);

		computeAction();
		//System.out.println("ACTION("+currentAction+"):"+Q.get(currentState).get(currentStrategy));
	    
	}
	
	
	/**
	 * Works only for 2 players
	 */
	private void computeAction() {
		assert(opponents.length==1); //Works only for 2 players
		Vector<Integer> br = BR(opponents[0].currentStrategy());
		double[] strat = new double[currentAction.getDomainSet().size()];
		for (int i = 0; i < strat.length; i++) {
			if(br.contains(i))
				strat[i] = (double)1/(double)br.size();
			else
				strat[i] = 0.0;
		}
		
		Random random = new Random();
		currentAction.changeToState(br.get(random.nextInt(br.size())));
	}

	/**
	 * Constructs state space and strategy
	 * @param e
	 */
	public void constructStructures(ObservableEnvInfo state){
		String s = state.getClass().toString();
		if(s.equals("class util.Info_NFG")||s.equals("class util.Info_Grid")){
			Info_NFG nfg = (Info_NFG) state;

			stateMapper.init(nfg);
			stateDomain = stateMapper.getStateDomain();
		}//end if

		
		//compute initial strategy
		
	}
	
	/**
	 * Calculates the BR to strategy strat. If more than one opponent, strat is considered to be
	 * the joint strat probability distribution
	 * @param s state
	 * @return a vector of actions that maximize its obj. function
	 */
	private Vector<Integer> BR(double[] strat){
		double maxUtil = Double.NEGATIVE_INFINITY;
		Vector<Integer> maxAct = new Vector<Integer>();
		for (int i = 0; i < strat.length; i++) {
			double util = expUtility(strat, i);
			
			if(util==maxUtil)
				maxAct.add(i);
			if(util > maxUtil){
				maxAct.removeAllElements();
				maxAct.add(i);
				maxUtil = util;
			}
		}
		
		return maxAct;
	}
	
	/**
	 * this will only work for symmetric games
	 * (for symmetric games it does not matter what player plays row or column)
	 * @param strat
	 * @param a
	 * @return
	 */
	private double expUtility(double[] strat, int a){
		double util = 0;
		Vector actions = new Vector();
		actions.add(a);
		for (int i = 0; i < strat.length; i++) {
			actions.add(i);
			util = util + strat[i]*reward.getReward(actions, agentId);
			actions.removeElementAt(1);
		}
		return util;
	}
	
	public void recordToLogger(ExperimentLogger log){
		String slog = new String();
		String ret =	System.getProperty("line.separator");
		slog = slog.concat("\n+++ AGENT: " + this.getClass()+ret);
		slog = slog.concat("Action type: " + currentAction.getClass()+ret);
	
		log.recordConfig(slog);
	}
	
	
}
