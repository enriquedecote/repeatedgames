package agent;

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
import util.JointActionState;
import util.JointActionStateDomain;
import util.JointActionStateMapper;
import util.NFGInfo;
import util.ObservableEnvInfo;
import util.State;
import util.StateDomain;
import util.VectorQueue;

// This is a subclass of agent which can be instantiated. It Creates a learning agent which has its own strategies and learnd from its mistakes
public class QLearningAgent extends Agent {
	
	//this is the algorithm's current high level strategy
	//private Map<State,Action> strategy;
	//private static StateDomain sDomain;
	private State state;
	private Random r;
	
	//learning parameters
	private float epsilon;
	private double alpha;

	private static double polyAlphaDecay = 0.5000001;
	private String alphaDecay;
	private Map<State,Map<Action,Integer>> alpha_t;
	private float gamma;
	
	//given a state, returns a set of pairs <action,value>
	Map<State,Map<Object,Double>> Q = new HashMap<State,Map<Object,Double>>();
	Double Qinit;
	VectorQueue<State> memory = new VectorQueue<State>(1);
	
	
	public void init(Element e, int id){
		super.init(e, id);
		alpha = Double.valueOf(e.getAttribute("alpha"));
		System.out.println("\t alpha: " + alpha);
		alphaDecay = e.getAttribute("alphaDecay");
		System.out.println("\t alpha decay: " + alphaDecay);
		gamma = Float.valueOf(e.getAttribute("gamma"));
		System.out.println("\t gamma: " + gamma);
		Qinit = Double.valueOf(e.getAttribute("Qinit"));
		System.out.println("\t Q table init: " + Qinit);
	}
	// constructor
	public QLearningAgent(Reward r) {
		reward = r;
		
	}
	// constructor
	public QLearningAgent(){		
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
	 * Constructs state space and strategy
	 * @param e
	 */
	public void constructStructures(ObservableEnvInfo state){
		String s = state.getClass().toString();
		if(s.equals("class util.NFGInfo")){
			NFGInfo nfg = (NFGInfo) state;

			stateMapper.init(nfg);

			/*			Vector<Action> vectA = nfg.currentJointAction();
			Action a0 = vectA.get(0).newInstance();
			Action a1 = vectA.get(1).newInstance();
			vectA.clear();
			vectA.add(a0); vectA.add(a1);
			 */
			stateDomain = stateMapper.getStateDomain();
		}//end if

		
		//construct Q table and strategy
		strategy = new HashMap<State, Object>();
		State st = null;
		for (Object ob : stateDomain.getStateSet()) {
			st = (State) ob;
			strategy.put(st, currentAction.getCurrentState());
			//init Q table
			Map<Object,Double> m = new HashMap<Object, Double>();
			for(Object o : currentAction.getDomainSet()){
				m.put(o, Qinit);
			}
			Q.put(st, m);
		}
		memory.offerFirst(st);
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
