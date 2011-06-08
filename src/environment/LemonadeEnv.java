package environment;
import java.util.HashMap;

import util.readxmlv2;


/*
 * subclass of environment, it can be implemented.
 * created a lemonade environment for the game
 */
public class LemonadeEnv extends Environment {
	
	// sets the lemonade environment
	public void setRewardCircle (int position, int reward){
		rewardCircle = new HashMap <Integer, Integer>();
		rewardCircle.put(position, reward);
	}
	
	public void init (){
		readxmlv2.getsteps();

	}

}
