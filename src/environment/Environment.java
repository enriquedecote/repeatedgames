package environment;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import agent.Agent;

import util.Action;
import util.JointActionState;
import util.ObservableEnvInfo;
import util.State;
import util.StateSet;



 /**
 * @author Enrique Munoz de Cote
 *
 * The environment has several attributes that can be perceived by an agent,
 * the specific attributes will be declared in the classes that implement this interface
 * @param <E>
 * @param <F>
 */
public interface Environment<A> {
	 /**
	 *  Gives information about the environment given the actions that transform the env
	 * @param actions
	 * @return a map with the new env info
	 */
	public ObservableEnvInfo nextEnvInfo(Vector<A> actions);
	/**
	 * Gives information about the environment
	 * @return
	 */
	public ObservableEnvInfo currentEnvInfo();
	
	/**
	 * Initializes the object with action information
	 * @param actions
	 */
	public void Init(Vector<A> actions);
}
