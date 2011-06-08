package util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import agent.Agent;


/*
 * E elements: Agent,Pods, etc.
 * F feature: Actions, Vector(coordinates)
 * Generic Type State
 * holds all the fields common to its subclass
 */
public abstract class State<E,F>{
	
	protected Vector<F> features;
	protected StateDomain domain;
	//protected Vector<Map<E, F>> features;
	// here E will be JointAction, and T is the Transition function
	protected Map <E,Map<State<E,F>,Integer>> T = new HashMap <E, Map<State<E,F>, Integer>>();
	protected boolean known;
	protected State currentState;
	
	public State(){
		features = new Vector<F>();
	}
	
	// adds features
	public void addFeature (F map){
		features.add(map);
	}
	
	public F getFeature(int i){
		return features.get(i);
	}	
	
	public Vector<F> getFeatures(){
		return features;
	}
	
	public StateDomain getStateDomain(){
		return domain;
	}
	
	public void updateState(ObservableEnvInfo info){
	}
}
