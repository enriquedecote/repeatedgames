/**
 * 
 */
package environment;

import java.util.Map;
import java.util.Vector;

import util.Action;
import util.JointActionState;
import util.NFGInfo;
import util.ObservableEnvInfo;
import util.State;

/**
 * @author enrique
 */
public class NFGEnvironment implements Environment<Action> {
	NFGInfo envInfo;
	
	public NFGEnvironment(Vector<Action> jointAction){
		envInfo = new NFGInfo(jointAction);
	}
	
	public NFGEnvironment(){
		envInfo = new NFGInfo();
	}

	@Override
	public ObservableEnvInfo currentEnvInfo() {
		return envInfo;
	}

	@Override
	public ObservableEnvInfo nextEnvInfo(Vector<Action> actions) {
		envInfo.updateJointAction(actions);
		return envInfo;
	}

}
