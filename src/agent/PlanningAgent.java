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
import java.util.Set;
import java.util.Vector;

import reward.Reward;

import environment.Environment;

import util.Strategy;
import util.StrategyState;


import util.Action;
import util.ActionDomain;
import util.GridActionDomain;
import util.JointActionState;
import util.ObservableEnvInfo;
import util.State;
import util.StateDomain;
//This is a subclass of agent which can be instantiated. It Creates a planning agent which has its own strategies
public class PlanningAgent extends Agent {
	State state;
	Map<State,Action> strategy;
	Reward reward;
	Random r;
	int sum;
	Action currentAction;
	StateDomain sDomain;
	Map<StrategyState,Map<Strategy,Double>> Q = new HashMap<StrategyState,Map<Strategy,Double>>();
	
	// constructor
	PlanningAgent (Reward rwd, long seed, Environment env){
		this.reward = rwd;
		this.r = new Random (seed);
		strategy = new HashMap<State, Action>();
	}
	// constructor
	public PlanningAgent(Reward rwd) {
		reward = rwd;
	}
	
	@Override
	// gets current action of agent
	public Action getAction() {
		return currentAction;
	}

	// updates all the relevant fields of the agent 
	public void update(ObservableEnvInfo prev, ObservableEnvInfo curr) {
		state = nextState;
		this.currentAction = strategy.get(state);
		//this.reward = rwd;
	}
	
	public void ValueIteration(){
		  final double EPSILON = 0.01; 
		  final double GAMMA = 0.5;
		  double error = 12;
		  Vector<Action> actions = currentAction.actionsSet();
		  
		  Map<State,Double> V = new HashMap<State,Double>(sDomain.size());
		  //Map<StrategyState,Map<Strategy,Double>> Q = new HashMap<StrategyState,Map<Strategy,Double>>();
		  
		  for (Object state : sDomain.getStateSet()) {
			  V.put((State)state, 0.0);
			  Map<Action,Double>Qaux = new HashMap<Action,Double>();
			  
			  for (Action action : actions) 
				  Qaux.put(action, 0.0);
			  Q.put(state, Qaux);
		  }		
		  
		  int counter = 0;
		  while (error > EPSILON) {
		  //while (counter< 50) {
		  // (int  counter=0; error>EPSILON;counter++) {
			  error = -9999;
			  //int c=0;
			  for (Object state : sDomain.getStateSet()) {
				  double Vstate = -9999;
				  //int c1=0;
				  for (Action action : actions) {
					  Map<State,Double> probs = state.probs(action);
					  //System.out.println(probs.size());
					  //expected over future states
					  double sum = 0;
					  //System.out.println(probs.size());
					  for (StrategyState state1 : probs.keySet()) {
						sum += probs.get(state1) * V.get(state1);
					  }
					  double total = state.getReward(action) + GAMMA * sum;
					  Q.get(state).put(action, total);
					  if(total > Vstate){
						  Vstate = total;
						  strategy.put(state, action);
					  }
					  ///c1++;
				  }
				  error = Math.max(error, Math.abs(Vstate - V.get(state)));
				  V.put(state, Vstate);
				  //c++;
				  //System.out.println("+++"+c);
			  }	
			  counter++;
			  //System.out.println(counter);
		  }
		  
		  //System.out.println(counter);
		  //System.out.println(strattoString());
		  
	  }
	

}
