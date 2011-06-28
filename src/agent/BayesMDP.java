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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Vector;

import org.w3c.dom.Element;

import edu.inaoe.util.Strategy;
import edu.inaoe.util.StrategyState;
import experiment.ExperimentLogger;
import experiment.Logger;


import agent.Agent.Policy;

import reward.Reward;

import util.Action;
import util.JointActionState;
import util.JointActionStateDomain;
import util.JointActionStateMapper;
import util.MDPModel;
import util.NFGInfo;
import util.ObservableEnvInfo;
import util.State;
import util.StateDomain;
import util.VectorQueue;


/**
 * This is a subclass of agent which can be instantiated. 
 * It implemets the algorithm described in 
 * [Optimal Planning Against Bayesian Learning Opponents, Munoz de Cote and Jennings 2011]
 * 
 * @author Enrique Munoz de Cote
 *
 */
public class BayesMDP extends Agent {
	
	private State state;
	private Random r;
	
	//MDP parameters
	private float epsilon;
	private MDPModel mdp;
	
	//given a state, returns a set of pairs <action,value>
	Map<State,Map<Object,Double>> Q = new HashMap<State,Map<Object,Double>>();
	Double Qinit;
	VectorQueue<State> memory = new VectorQueue<State>(1);
	
	
	public void init(Element e, int id){
		super.init(e, id);
	}
	// constructor
	public BayesMDP(Reward r) {
		reward = r;
		
	}
	// constructor
	public BayesMDP(){		
	}

	@Override
	// gets current action of agent
	public Action getAction() {
	
		return currentAction;
	}
	
	@Override
	public void update(ObservableEnvInfo curr) {
		NFGInfo info = (NFGInfo)curr;
			currentState = (State) stateMapper.getState(curr);
			//System.out.println(currentState.getFeatures().toString()+", agent:"+this);
			State prevState = (State) memory.getLast();
			//reward.getReward(prev, currentFeat, agentId);
			
			Double val=Double.NEGATIVE_INFINITY;
			Double maxQ = null;
			Object action = null;
			//get action=arg max_{a} and maxQ=max_{a}
			for(Object o : Q.get(currentState).keySet()){
				if(Q.get(currentState).get(o) >= val){
					action = o;
					maxQ = Q.get(currentState).get(o);
					val = maxQ;
				}
			}
			Vector<Action> currJointAct =  stateMapper.getActions(curr);
			Vector<Object> currO = new Vector<Object>();
			for (Action act : currJointAct) 
				currO.add(act.getCurrentState());
	
			int currReward = reward.getReward(curr, currO, agentId);
			Map<Object, Double> mapQ = Q.get(prevState);
			Action actQ = currJointAct.get(agentId);
			Double Qval = Q.get(prevState).get(currJointAct.get(agentId).getCurrentState());
			//System.out.println("R("+currO+")="+reward.getReward(curr, currO, agentId));
			Double newQ =
			(1-alpha)*Qval +
			alpha*(reward.getReward(curr, currO, agentId) + gamma*maxQ);

			//update Q value
			Q.get(prevState).put(currJointAct.get(agentId).getCurrentState(), newQ);
			
			//choose a new action
			currentAction.changeToState(policy.getNextAction(action)); 
			
			if(alphaDecay.equalsIgnoreCase("POLY"))
				alpha = 1/(Math.pow((double)round, polyAlphaDecay));
			round++;
			memory.offerFirst(currentState);
		
		//log.flush();
	}
	
	/**
	 * takes a bimatrix game and outputs all mixed strategies for the planner that make its opponent indifferent
	 * @param gameName
	 * @return A vector a of strategies (each expressed as rational numbers) 
	 * containing all strategies with indifference points
	 */
	public String getWpoints(String gameName){
		String s = null;

        try {

            Process p = Runtime.getRuntime().exec("gambit-enummixed -q < " + gameName);
            
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));

            // read the output from the command
            System.out.println("Here is the standard output of the command:\n");
            while ((s = stdInput.readLine()) != null) {
                System.out.println(s);
            }
            
            // read any errors from the attempted command
            System.out.println("Here is the standard error of the command (if any):\n");
            while ((s = stdError.readLine()) != null) {
                System.out.println(s);
            }
            
            System.exit(0);
        }
        catch (IOException e) {
            System.out.println("exception happened - here's what I know: ");
            e.printStackTrace();
            System.exit(-1);
        }
        return s;
    }
	
	/**
	 * The argument is a mixed strategy r/k
	 * @param a = r
	 * @param b = k-r
	 */
	public void constructBayesMDP(int a, int b){
		int numStates= (a+1)*(b+1);
		int numActions = currentAction.getDomainSet().size();
		mdp = new MDPModel();
		mdp.setStates(numStates);
		mdp.setActions(numActions);// action 0 or 1
		mdp.initialize();
		for (int s = 0; s < numStates; s++) {//for all states s
			for (int i = 0; i < numActions; i++) {//for all actions
				mdp.setTransition(s, i, nextState(s,i,numStates), 1);
				mdp.setRewardEntry(s, i, nextState(s,i,numStates), expUtility(s, i));
			}
		}
	}
	
	/**
	 * Uses the mixed strategy r/k to check next state and validity using eq. 12 and 13
	 * @param s state
	 * @param action chosen action
	 * @param a = r
	 * @param b = k-r
	 * @return
	 */
	public int nextState(int s, int a, int states){
		s = s + (int) Math.pow(currentAction.getDomainSet().size(), a);
		//if(action==0) s = s + a;
		//if(action==1) s = s + 1;
		int numStates= states;
		if(s >= numStates)
			s=0;
		return s;
	}
	
	public double expUtility(int s, int a){
		float[] strat = opponentModel(s);
		double util = 0;
		Vector actions = new Vector();
		actions.add(a);
		for (int i = 0; i < strat.length; i++) {
			actions.add(i);
			util = util + strat[i]*reward.getReward(actions, 0);
		}
		return util;
	}
	
	public float[] opponentModel(int s){
		//TODO: implement
		return null;
	}
	
	  public void ValueIteration(){
		  final double EPSILON = 0.01; 
		  double error = 12;
		  Vector<Object> actions = currentAction.getDomainSet();
		  
		  Map<StrategyState,Double> V = new HashMap<StrategyState,Double>(stratDomain.size());
		  //Map<StrategyState,Map<Strategy,Double>> Q = new HashMap<StrategyState,Map<Strategy,Double>>();
		  
		  for (StrategyState state : stratDomain.getStateSet()) {
			  V.put(state, 0.0);
			  Map<Strategy,Double>Qaux = new HashMap<Strategy,Double>();
			  
			  for (Strategy action : actions) 
				  Qaux.put(action, 0.0);
			  Q.put(state, Qaux);
		  }	
		  
	public void recordToLogger(ExperimentLogger log){
		String slog = new String();
		String ret =	System.getProperty("line.separator");
		slog = slog.concat("\n+++ AGENT: " + this.getClass()+ret);
		slog = slog.concat("Action type: " + currentAction.getClass()+ret);
		slog = slog.concat("Policy: " + policy.getClass()+ret);
		slog = slog.concat("\t alpha: " + alpha+ret);
		slog = slog.concat("\t alpha decay: " + alphaDecay+ret);
		slog = slog.concat("\t gamma: " + gamma+ret);
		slog = slog.concat("\t Q table init: " + Qinit+ret);
		//slog.concat("Q-table:\n" + Q.toString());
		slog = slog.concat("Q-table:" + ret);
		for (State state : Q.keySet()) {
			for (Object action : Q.get(state).keySet()) {
				slog = slog.concat("["+state.getFeatures().toString()+","+action.toString()+"]:"+Q.get(state).get(action)+ret);
			}
		}
		log.recordConfig(slog);
	}
	
	
	
}
