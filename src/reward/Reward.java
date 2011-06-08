package reward;

import java.util.Vector;

import util.Action;
import util.ObservableEnvInfo;
import util.State;

// Generic type reward 
public abstract class Reward {

 
 public void Init(String game){
	 
 }
 public int getReward(ObservableEnvInfo s, Vector<Object> actions, int agent){
	 return Integer.MIN_VALUE;
 }
 
 public int[] getRewards(ObservableEnvInfo s, Vector<Action> actions){
	 return null;
 }

}
