package util;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

/**
 * @author aladdinagentschool
 * subclass of generic type State, and can be implemented. It holds all the generic fields and methods common it its subclasses 
 * it hold all the joint action of the agents. It also creates a domain of all possible joint action. , it has both get and set methods
 * to access the private fields
 */


public class  JointActionState extends State{
	// actionssSet is the list of actions of the agents in the game, 
	private static Vector<Object> jointAction;
	
	public JointActionState(){
		super();
	}
	
	//Constructor
	public JointActionState (Vector<Action> actions){
		jointAction = new Vector<Object>();
		for (Action action : actions) {
			jointAction.add(action.getCurrentState());
		}
		domain = new JointActionStateDomain(actions);
	}
	
	public void init(Vector<Action> actions){
		for (Action action : actions) {
			jointAction.add(action.getCurrentState());
		}
		domain = new JointActionStateDomain(actions);
	}
	
	public Vector<Object> getJointAction(){
		return jointAction;
	}
	
	public String name (Action a){
		return a.getName();
	}
	
}


