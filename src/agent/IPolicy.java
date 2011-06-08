/**
 * 
 */
package agent;

import java.io.Serializable;
import java.util.Vector;

import util.Action;
import util.State;

/**
 * @author Enrique Munoz de Cote
 *
 */
public interface IPolicy extends Serializable {

	/**
	 * Initializes the object strategy, for that it needs:
	 * @param stateSet
	 */
	public void init(Vector<State> stateSet, Vector<Action> actionSet);
	public Object getNextAction(Object maxA);
}
