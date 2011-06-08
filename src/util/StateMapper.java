/**
 * 
 */
package util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

/**
 * @author Enrique Munoz de Cote
 *
 */
public abstract class StateMapper<State> {
	protected StateDomain<State> stateDomain;
	
	//mapping transforms from joint actions to its encoding state
	protected Map<Vector<Object>,State> mapping = new HashMap<Vector<Object>, State>();
	
	public void init(ObservableEnvInfo state){
	}
	
	public Set<State> getStateSet(){
		return stateDomain.getStateSet();
	}
	public StateDomain<State> getStateDomain(){
		return stateDomain;
	}
	
	
	public State getState(Vector<Object> state){
		return mapping.get(state);
	}
	
	public State getState(ObservableEnvInfo state){
		return null;
	}
	
	public Vector<Action> getActions(ObservableEnvInfo info){
		return null;
	}
	
	public Vector<Object> getFeatures(ObservableEnvInfo info){
		return null;
	}
}
