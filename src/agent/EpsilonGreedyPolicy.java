/**
 * 
 */
package agent;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Vector;

import util.Action;
import util.JointActionStateDomain;
import util.State;
import org.w3c.dom.Element;


/**
 * @author Enrique Munoz de Cote
 *
 */
public class EpsilonGreedyPolicy implements IPolicy {
	protected float EPSILON;
	protected String decay;
	protected Random random = new Random();
	protected Vector<Object> actionSet = new Vector<Object>();
	
	public enum Decay
	{
	    POLY, EXP; 
	}

	public EpsilonGreedyPolicy(Element e, Vector<Object> actions) {
		EPSILON = Float.valueOf(e.getAttribute("epsilon"));
		decay = e.getAttribute("epsilonDecay");
		actionSet = actions;
	}

	@Override
	public Object getNextAction(Object maxA) {
		if(random.nextFloat() < EPSILON){//random choose
			int randomA = random.nextInt(actionSet.size());
			//System.out.println("random action: " + actionSet.get(randomA));
			return actionSet.get(randomA);
		} else //return the max
			return maxA;
	}
	
	@Override
	public void init(Vector<State> stateSet, Vector<Action> actionSet) {
	
	}



}
