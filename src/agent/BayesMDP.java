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

import experiment.ExperimentLogger;
import experiment.Logger;


import agent.Agent.Policy;

import reward.Reward;

import util.Action;
import util.State_JointAction;
import util.StateDomain_JointAction;
import util.StateMapper_JointAction;
import util.MDPModel;
import util.Info_NFG;
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
	
	private static final double GAMMA = 1;

	protected Map<Integer,Object> strategy = new HashMap<Integer, Object>();	
	private Random r;
	
	//MDP parameters
	private float epsilon;
	private MDPModel mdp;
	private int numActions = 1;
	private int oppActions = 1;
	private int numStates = 1;
	private Vector<int[]> wPointsVector = new Vector<int[]>();
	private String gameName;
	private int currState = 0;
	private boolean searchingIndiffPoint = false;
	private int[] maxWstrat;//for best equilibrium r/k; [0]=r,[1]=k-r
	private int totalActions;//for best equilibrium = k
	
	//given a state, returns a set of pairs <action,value>
	Map<Integer,Map<Integer,Double>> Q;
	Double Qinit;
	VectorQueue<State> memory = new VectorQueue<State>(1);
	
	
	public void init(Element e, int id){
		super.init(e, id);
		numActions = currentAction.getDomainSet().size();
		gameName = e.getAttribute("game");
		oppActions = Integer.valueOf(e.getAttribute("oppActions"));
		getWpoints(System.getProperty("user.home") + "/programing/gamutGames/" + gameName);
		
		double g = 0; //gain optimal max value
		int maxW=0;
		for (int i=0; i< wPointsVector.size(); i++){
			constructBayesMDP(wPointsVector.get(i));
			if(mdp.getGainOptimalReward() > g){
				maxW = i;
				g = mdp.getGainOptimalReward();
			}
		}
		constructBayesMDP(wPointsVector.get(maxW));
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
		Info_NFG info = (Info_NFG)curr;
		Vector<Action> currJointAct =  stateMapper.getActions(curr);
		currentState = (State) stateMapper.getState(curr);
		Vector<Object> currO = new Vector<Object>();
		
		for (Action act : currJointAct) 
			currO.add(act.getCurrentState());

		int currReward = (int)reward.getReward(curr, currO, agentId);
		
		if(!isPredictedReward(currReward) || searchingIndiffPoint)
			find_w();
		
		//update state
		System.out.println("["+currState+":"+currentAction.getCurrentState()+"]"+currReward);
		currState = nextState(currState, (int)(Integer)currentAction.getCurrentState());

			//choose a new action
			currentAction.changeToState(strategy.get(currState)); 
		
		
		round++;
		//memory.offerFirst(currentState);
		
		//log.flush();
	}
	
	private boolean isPredictedReward(int currRew){
		if(currState == 0 || currState == numStates-1)
			return true;
		else{
			double expRew = mdp.getRewardEntry(currState, (Integer)currentAction.getCurrentState(), nextState(currState,(Integer)currentAction.getCurrentState()));
			if(expRew==currRew)
				return true;
			else
				return false;
		}
	}
	
	private void find_w(){
		
	}
	
	/**
	 * takes a bimatrix game and outputs all mixed strategies for the planner that make its opponent indifferent
	 * @param gameName
	 * @return A vector a of strategies (each expressed as rational numbers) 
	 * containing all strategies with indifference points
	 */
	public void getWpoints(String gameName){
		String s = null;

        try {
        	//String[] cmd = new String[]{"/bin/sh", "-c", "gambit-enummixed","<" + gameName};
        	//String cmd = "/bin/sh -c 'gambit-enummixed < "+ gameName+"'";
        	String cmd = "/home/enrique/programing/gamutGames/launch.sh " +gameName;
        	Process p = Runtime.getRuntime().exec(cmd);
            //Process p = Runtime.getRuntime().exec("/usr/bin/gambit-enummixed -q < " + gameName+ "> sol.txt");
        	//Process p = Runtime.getRuntime().exec("cat " + gameName+ "> sol.txt");
            //Process p = Runtime.getRuntime().exec("ls -l");
        	
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            
            // read the output from the command
            //System.out.println("Here is the standard output of the command:\n");
            //System.out.println(stdInput.readLine());
            while ((s = stdInput.readLine()) != null) {
                System.out.println(s);
                int action = 0;
                int[] actions = new int[numActions];
                s = s.substring(3); //get the "NE," substring out of the way
                String[] supports = s.split(",");
                while(action < numActions){
                	String actionFracc[] = supports[action].split("/");
                	actions[action] = Integer.valueOf(actionFracc[0]); //numerator
                	if(actionFracc.length > 1)
                		assert(numActions == Integer.valueOf(actionFracc[1]));
                	action++;
                }
                wPointsVector.add(actions);
            }
            
            // read any errors from the attempted command
            //System.out.println("Here is the standard error of the command (if any):\n");
            while ((s = stdError.readLine()) != null) {
                System.out.println(s);
            }
        }
        catch (IOException e) {
            System.out.println("exception happened - here's what I know: ");
            e.printStackTrace();
            System.exit(-1);
        }
    }
	
	/**
	 * The argument is a mixed strategy r/k
	 * @param a = r
	 * @param b = k-r
	 */
	public void constructBayesMDP(int[] mixedStrat){
		resetVars();
		maxWstrat = mixedStrat;
		for (int i : mixedStrat) 
			totalActions += i;

		for (int i = 0; i < mixedStrat.length; i++) 
			numStates *= (mixedStrat[i]+1);

		numActions = currentAction.getDomainSet().size();
		mdp = new MDPModel();
		mdp.setStates(numStates);
		mdp.setActions(numActions);// action 0 or 1
		mdp.initialize();
		for (int s = 0; s < numStates; s++) {//for all states s
			for (int i = 0; i < numActions; i++) {//for all actions
				for (int s1 = 0; s1 < numStates; s1++) {//for all states s1
					if(s1 == nextState(s,i)){
						mdp.setTransition(s, i, s1, 1);
						mdp.setRewardEntry(s, i, s1, expUtility(s, i));
					}
					else{
						mdp.setTransition(s, i, s1, 0);
						mdp.setRewardEntry(s, i, s1, Double.NEGATIVE_INFINITY);
					}
				}

			}
		}
		averageRewardVI();
	}
	
	private void resetVars() {
		numStates = 1;
		totalActions = 0;
	}
	/**
	 * Uses the mixed strategy r/k to check next state and validity using eq. 12 and 13
	 * @param s state
	 * @param a chosen action
	 * @param inc increment (the number of times action a=0 is played (e.g. 4/7, inc=4)
	 * @return the next state
	 */
	private int nextState(int s, int a){
		s = s + (int)Math.pow(maxWstrat[0]+1, a);
		if(s >= numStates-1)
			s=0;
		return s;
	}
	
	public double expUtility(int s, int a){
		Vector<Integer> br = BR(s);//this is the opponent's BR in state s
		double[] strat = new double[oppActions];
		for (int i = 0; i < strat.length; i++) {
			if(br.contains(i))
				strat[i] = (double)1/(double)br.size();
			else
				strat[i] = 0.0;
		}
		double util = 0;
		Vector actions = new Vector();
		actions.add(a);
		for (int i = 0; i < strat.length; i++) {
			actions.add(i);
			util = util + strat[i]*reward.getReward(actions, 0);
			actions.removeElementAt(1);
		}
		return util;
	}
	
	
	/**
	 * Calculates the opponent's BR to state s=(model of bayesMDP)
	 * @param s state
	 * @return a vector of actions that maximize its obj. function
	 */
	public Vector<Integer> BR(int s){
		//get planner strategy
		double[] strat = stateToStrat(s);
		
		double maxUtil = Double.NEGATIVE_INFINITY;
		Vector<Integer> maxAct = new Vector<Integer>();
		for (int i = 0; i < oppActions; i++) {
			double util = 0;
			Vector actions = new Vector();
			actions.add(0);//planner's action (it will be removed later in the for cycle
			actions.add(i);
			for (int j = 0; j < strat.length; j++) {
				actions.remove(0); actions.add(0, j);
				double r = reward.getReward(actions, 1);
				util = util + strat[j]*reward.getReward(actions, 1);
			}
			
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
	
	//state s=0 is the equilibrium strategy
	private int[] stateToActions(int n){
		int[] strat = new int[numActions];
	       int i=0;
	       while (n > 0) {
	          strat[i] = (n % (maxWstrat[i]+1))+ strat[i];
	          n = n/(maxWstrat[i]+1);
	          i++;
	       }
	       
	       for (int j = 0; j < strat.length; j++) 
			strat[j] += maxWstrat[j];
		
	       assert(strat.length == numActions);
	       return strat;
	}
	
	private double[] stateToStrat(int n){
		int[] aux = stateToActions(n);
		double[] strat = new double[numActions];
		
/*		if(n == 0){
			for (int i = 0; i < strat.length; i++) {
				strat[i] = (double)1/(double)numActions;
			}
			return strat;
		}*/
			
			
		int sum=0;
		for (int i = 0; i < aux.length; i++)
			sum += aux[i];
		
		for (int i = 0; i < aux.length; i++)
			strat[i] = (double)aux[i]/(double)sum;
		
	    assert(strat.length == numActions);
	    
	    return strat;
	}
	
	  /**
	 * Implements av. reward VI as described in Jalali and Ferguson (Jalali & Ferguson, 1990) describe 
	 * an asynchronous value iteration algorithm that provably converges to produce a gain-optimal policy 
	 * if there is a state s E S that is reachable from every other state under all stationary policies.
	 * The recurring state in BayesMDP case is always the last one (i.e. s=numStates)
	 */
	public void averageRewardVIJalali(){
		  final double EPSILON = 0.01; 
		  double error = 12;
		  double p=0;//for iterative version of arithmetic mean
		  Vector<Object> actions = currentAction.getDomainSet();
		  //states to double
		  Map<Integer,Double> V = new HashMap<Integer,Double>(numActions);
		  Q = new HashMap<Integer,Map<Integer,Double>>(numStates);
		  
		  for (int i=0; i< numStates; i++) {
			  V.put(i, 0.0);
			  //actions to double
			  Map<Integer,Double>Qaux = new HashMap<Integer,Double>();
			  
			  for (int j=0; j< numActions; j++) 
				  Qaux.put(j, 0.0);
			  Q.put(i, Qaux);
		  }	
		  
		  int counter = 0;
		  while (error > EPSILON) {
		  //while (counter< 50) {
		  // (int  counter=0; error>EPSILON;counter++) {
			  error = -9999;
			  //int c=0;
			  for (int s=0; s< numStates; s++) {
				  double Vstate = -9999;
				  //int c1=0;
				  for (int a=0; a< numActions; a++) {
					  //System.out.println(probs.size());
					  //expected over future states
					  double sum = 0;
					  //System.out.println(probs.size());
					  for (int s1 = 0; s1 < numStates; s1++) {
						sum += mdp.getTransition(s, a, s1) * V.get(s1);
					  }
					  double total = mdp.getRewardEntry(s, a, nextState(s,a)) + GAMMA * sum;
					  total -= p;//this is the average discount for containing the sum (see Mahadevan Av. reward)
					  p = p + (1/(counter+1))*(mdp.getRewardEntry(s, a, nextState(s,a))-p);//iterative mean
					  if(s!=numStates-1)//the last state (which is recurring under any policy)
						  Q.get(s).put(a, total);
					  else
						  Q.get(s).put(a, 0.0);
					  if(total > Vstate){
						  Vstate = total;
						  strategy.put(s, a);
					  }
					  ///c1++;
				  }
				  error = Math.max(error, Math.abs(Vstate - V.get(s)));
				  V.put(s, Vstate);
				  //c++;
				  System.out.println(error);
			  }	
			  counter++;
			  System.out.println("counter:"+counter);
		  }
		  
		  //System.out.println(counter);
		  //System.out.println(strattoString());
		  mdp.setGainOptimalReward(V.get(0));
		  
	  }
		
	 /**
		 * Implements av. reward VI as described in Puterman'94, pg. 364
		 * where the stop criterion (eq. 8.5.4) is max_s-min_s' 
		 */
		public void averageRewardVI(){
			  final double EPSILON = 0.1; 
			  double sp = 12;
			  double spMin = -9999;
			  double spMax = -9999;
			  Vector<Object> actions = currentAction.getDomainSet();
			  //states to double
			  Map<Integer,Double> V = new HashMap<Integer,Double>(numActions);
			  
			  //step 1
			  for (int i=0; i< numStates; i++)
				  V.put(i, 0.0);
			  
			  int counter = 0;
			  
			  //step 3 (stop criterion)
			  while (sp > EPSILON) {
				  spMax = -9999;
				  spMin = 9999;
				  //step 2
				  for (int s=0; s< numStates; s++) {
					  double Vstate = -9999;
					  
					  for (int a=0; a< numActions; a++) {
						  //expected over future states
						  double sum = 0;
						  //System.out.println(probs.size());
						  for (int s1 = 0; s1 < numStates; s1++) {
							sum += mdp.getTransition(s, a, s1) * V.get(s1);
						  }
						  double total = mdp.getRewardEntry(s, a, nextState(s,a)) + GAMMA * sum;
						  
						  //the max_a part
						  if(total > Vstate){
							  Vstate = total;
							  strategy.put(s, a);
						  }
					  }
					  double spTmp = Math.max(sp, Math.abs(Vstate - V.get(s)));
					  //the max_s part for sp
					  if(spTmp > spMax) spMax = spTmp;
					  if(spTmp < spMin) spMin = spTmp;
					
					  V.put(s, Vstate);
				  }	
				  sp = spMax-spMin;
				  //System.out.println(sp);
				  counter++;
				  //System.out.println("counter:"+counter);
			  }
			  
			  //System.out.println(counter);
			  //System.out.println(strattoString());
			  mdp.setGainOptimalReward(V.get(0));
			  
		  }
	public void recordToLogger(ExperimentLogger log){
		String slog = new String();
		String ret =	System.getProperty("line.separator");
		slog = slog.concat("\n+++ AGENT: " + this.getClass()+ret);
		slog = slog.concat("Action type: " + currentAction.getClass()+ret);

		log.recordConfig(slog);
	}
	
	
	
}
