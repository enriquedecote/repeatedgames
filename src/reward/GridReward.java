package reward;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.xml.sax.SAXException;

import util.CoordinateState;
import util.State;
import util.readxmlv2;

//
/**
 * @author aladdinagentschool
 *extension of generic type reward. this clss can be implemented. Uses xml file to set the reward function and had method to get the rewards for coins, pods
 */
public class GridReward extends Reward{
	
	public Map<CoordinateState , Integer> coins ;
	public Map <CoordinateState, Integer> puds;
	public int collisionVal;
	int coinvalue;
	int podvalue;
	
	// generates the reward function
	public GridReward (Reward rew) {
		
		String cCoordinate = readxmlv2.getcCoordinate();
		
		int coinValue = readxmlv2.getcoins();
		coinvalue = coinValue;
		String splitCoordinate[]  = new String[2];
		splitCoordinate = cCoordinate.split("\\,");
		
		
		Vector<Integer>ccoordinate;
		ccoordinate = new Vector<Integer>(2);
		ccoordinate.add(Integer.parseInt(splitCoordinate[0]));
		ccoordinate.add(Integer.parseInt(splitCoordinate[1]));
		Map<Reward, Vector<Integer>> cfeature =new HashMap<Reward, Vector<Integer>>();
		cfeature.put(rew, ccoordinate);
		CoordinateState cstate = new CoordinateState (cfeature);
		coins = new HashMap <CoordinateState , Integer>();
		coins.put(cstate, coinValue);
		
		String pCoordinate = readxmlv2.getpCoordinate();
		podvalue = coinValue;
		String pudCoordinate[]  = new String[2];
		pudCoordinate = pCoordinate.split("\\,");
		Vector<Integer>pcoordinate= new Vector<Integer>(2);
		pcoordinate.add(Integer.parseInt(splitCoordinate[0]));
		pcoordinate.add(Integer.parseInt(splitCoordinate[1]));
		Map<Reward, Vector<Integer>> pfeature =new HashMap<Reward, Vector<Integer>>();
		pfeature.put(rew, ccoordinate);
		CoordinateState pstate = new CoordinateState (pfeature);
		puds = new HashMap <CoordinateState , Integer>();
		puds.put(pstate, coinValue);
	
	}
	
	
	
	/**
	 * @param state is the next state of the game
	 * @return coin reward of the agent for the state
	 */
	public int getcoinReward (State state){
		int rwd;
		rwd = coins.get(state);
		
		return rwd;
		
	}
	/**
	 * @param state is the next state of the game
	 * @return pod reward of the agent for the state
	 */
	public int getpudReward (State state){
		int rwd;
		rwd = puds.get(state);
		
		return rwd;
	}
	
	// returns reward as a double
	
	public double getReward(State s, boolean collision){
		double sum = 0;
			if (collision)
				sum = getpudReward(s) + getcoinReward(s) + collisionVal;
			else
				sum = getpudReward(s) + getcoinReward(s);
		return sum;
	}

}
