package util;

import java.util.Vector;

/**
 * @author Enrique Munoz de Cote
 *
 */
public class NFGInfo implements ObservableEnvInfo {
	protected Object[] jointO;
	protected Vector<Action> jointAction = new Vector();
	
	public NFGInfo(){
	}
	
	public NFGInfo(Vector<Action> j){
		Init(j);
	}
	
	public Vector<Action> currentJointAction(){

		for (int i = 0; i < jointO.length; i++) {
			jointAction.get(i).changeToState(jointO[i]);
		}

		return jointAction;
	}
	
	public void updateJointAction(Vector<Action> j){
		for (int i = 0; i < j.size(); i++) {
			jointO[i] = j.get(i).getCurrentState();
		}
	}
	
	public void Init(Vector<Action> j){
		jointO = new Object[j.size()];
		
		for (int i = 0; i < j.size(); i++) {
			jointO[i] = j.get(i).getCurrentState();
			jointAction.add(j.get(i));
		}
	}
}
