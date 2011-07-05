/*******************************************************************************
 * Copyright (c) 2011 Enrique Munoz de Cote.
 * repeatedgames is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * repeatedgames is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with repeatedgames.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * Please send an email to: jemc@inaoep.mx for comments or to become part of this project.
 * Contributors:
 *     Enrique Munoz de Cote - initial API and implementation
 ******************************************************************************/
package reward;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Vector;

import util.Action;
import util.ObservableEnvInfo;
import util.State;



//
/**
 * @author Enrique Munoz de Cote
 * This class creates rewards for different classic games
 */
public class GamutReward implements Reward{
	/**
	 * the first Vector is a vector of outcomes (joint strategies), that maps to the agent id and its reward
	 */
	Map<Integer, Map<Integer,Double>> rewards;
	private int numAgents = 0;
	private int numStates = 0;
	private int[] agentsActions;
	private String path = System.getProperty("user.home")+"/experiments/repeatedgames/gamutGames/";

	
	// generates the reward function
	public void Init (String gameName) {
	   try{
		   String game = buildGame(gameName);
		// command line parameter
		  FileInputStream fstream = new FileInputStream(game);
		  // Get the object of DataInputStream
		  DataInputStream in = new DataInputStream(fstream);
		  BufferedReader br = new BufferedReader(new InputStreamReader(in));
		  
		  String strLine;
		  numAgents = Integer.valueOf(br.readLine());
		  agentsActions = new int[numAgents];
		  strLine = br.readLine();
		  String[] actions = strLine.split(" "); //second line is the num of actions for each agent
		  int numberOfOutcomes = 1;
		  for (int i = 0; i < actions.length; i++){
			  agentsActions[i] = Integer.valueOf(actions[i]);
			  numberOfOutcomes *= agentsActions[i];
		  }
		  numStates=numberOfOutcomes;
		  buildStructs();
		  strLine = br.readLine();//third line is empty
		  //Read File Line By Line
		  Integer agent = 0;
		  while ((strLine = br.readLine()) != null)   {
			  String[] r = strLine.split(" ");
			  for (int i = 0; i < numberOfOutcomes; i++) 
				  rewards.get(i).put(agent, Double.valueOf(r[i]));
			  agent++;  
		  }
		  
		  //Close the input stream
		  in.close();  
	   }catch (Exception e){//Catch exception if any
		   System.err.println("Error: " + e.getMessage());   
	   }
	
	}
	
	public double getReward(ObservableEnvInfo state, Vector<Object> jointAction, int agent){
		int s = toState(jointAction);
		return rewards.get(s).get(agent); 
	}
	
	public double getReward(Vector<Object> jointAction, int agent){
		int s = toState(jointAction);
		return rewards.get(s).get(agent); 
	}
	
	@Override
	public double[] getRewards(ObservableEnvInfo state, Vector<Action> jointAction){
		int s = toState(toFeatures(jointAction));
		double[] r = new double[numAgents];
		for (int i = 0; i < r.length; i++)
			r[i] = rewards.get(s).get(i);
		
		return r;
	}
	
	@Override
	public double[] getRewards(Vector<Object> jointAction){
		int s = toState(toFeatures(jointAction));
		double[] r = new double[numAgents];
		for (int i = 0; i < r.length; i++)
			r[i] = rewards.get(s).get(i);
		
		return r;
	}
	
	private Vector<Object> toFeatures(Vector<Action> jointAction){
		Vector<Object> feats = new Vector<Object>();
		for (int i=0; i< jointAction.size(); i++) {
			feats.add(jointAction.get(i).getCurrentState());
		}
		return feats;
	}
	
	@Override
	public boolean isSymmetric() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public Reward swapPlayers(int i, int j) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * @param n the state (joint action)
	 * @return the vector of actions
	 */
	private int[] toActions(int n){
		int[] strat = new int[numAgents];
	       int i=0;
	       while (n > 0) {
	          strat[i] = n % agentsActions[i];
	          n = n/agentsActions[i];
	          i++;
	       }
	       assert(strat.length == numAgents);
	       return strat;
	}
	
	private int toState(Vector a) throws NumberFormatException {
		Object[] actions = a.toArray();
		// Initialize result to 0
		int res = 0;
		// Do not continue on an empty string
		if (actions.length==0) {
			throw new NumberFormatException("Empty string is not an octal number");
		}
		// Consider each digit in the string
		for (int i = 0; i < actions.length; i++) {
			// Get the nth char from the right (first = 0)
			int n = (Integer)actions[i];
			// Check if it's a valid bit
			if (n < 0 || n > agentsActions[i]) {
				// And if not, die horribly
				throw new NumberFormatException("Not a valid number");
			} else {
			// Only add the value if it's a 1
				res += n*Math.round(Math.pow(2.0, i));
			}
		}
		if(res > numStates)
			System.err.println("error!!");
		return res;
	}
	
	private String buildGame(String game){
		try {
			String cmd ="java -jar "+path+"gamut.jar -g "+game+ " -random_seed 1 -normalize -min_payoff 0 -max_payoff 5 -f "+
			path+game+".GT -players 3 -function_X 3 -function_Y 1 -function_Z 2 -output GTOutput";
	    	Process p = Runtime.getRuntime().exec(cmd);
	    	return path+game+".GT";
		}
        catch (IOException e) {
            System.out.println("exception happened - here's what I know: ");
            e.printStackTrace();
            return null;
        }
	}
	
	private void buildStructs(){
		rewards = new HashMap<Integer, Map<Integer,Double>>();
		for (int i = 0; i < numStates; i++) {
			Map<Integer,Double> rew = new HashMap<Integer, Double>();
			for (int a = 0; a < numAgents; a++) {
				rew.put(a, 0.0);
			}
			rewards.put(i, rew);
		}
	}

}

