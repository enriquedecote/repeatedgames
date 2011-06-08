package util;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * @author Enrique Munoz de Cote
 *
 */
public class SingleStateMapper extends StateMapper<JointActionState> {
	private JointActionState state; //dummy state
	
	public SingleStateMapper(){
	}
	/**
	 * Constructs state space and strategy
	 * @param e
	 */
	public void init(ObservableEnvInfo info){
		stateDomain = new SingleStateDomain();
		state = stateDomain.getState(info);
	}
	
	public JointActionState getState(NFGInfo info){
		return state;
	}
	
	@Override
	public JointActionState getState(ObservableEnvInfo info){
		return state;
	}
	
	@Override
	public Vector<Action> getActions(ObservableEnvInfo info){
		NFGInfo state = (NFGInfo) info;
		return state.currentJointAction();
	}
	
	@Override
	public Vector<Object> getFeatures(ObservableEnvInfo info){
		return getState(info).getFeatures();
	}
}
