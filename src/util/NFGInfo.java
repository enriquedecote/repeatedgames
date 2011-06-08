package util;

import java.util.Vector;

/**
 * @author Enrique Munoz de Cote
 *
 */
public class NFGInfo implements ObservableEnvInfo {
	protected Vector<Action> jointAction;
	
	public NFGInfo(){
	}
	
	public NFGInfo(Vector<Action> j){
		jointAction = j;
	}
	
	public Vector<Action> currentJointAction(){
		return jointAction;
	}
	
	public void updateJointAction(Vector<Action> j){
		jointAction = j;
	}
}
