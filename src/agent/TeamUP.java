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
package agent;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.io.*;
import java.util.Date;
import java.text.SimpleDateFormat;

import org.w3c.dom.Element;

import util.Action;
import util.ActionDomain_LeaderFollower;
import util.ObservableEnvInfo;
import util.TeamUPOpponentModel;
import util.Strategy;
import util.StrategyState;


/**
 * This is TeamUP as explained in [Enrique Munoz de Cote, A. Chapman, A. M. Sykulski and N. R. Jennings. 
 * Automated Planning in Repeated Adversarial Games].
 * Up to now, it only accepts int as actions
 * @author Enrique Munoz de Cote
 * 
 */

public class TeamUP extends Agent {

	private Vector<Integer> sequence = new  Vector<Integer>();
	private ActionDomain_LeaderFollower stratDomain;
	//states transforms from joint actions to its encoding state
	private Map<Vector<Strategy>,StrategyState> states = new HashMap<Vector<Strategy>, StrategyState>();
	//this is the algorithm's current high level strategy
	private Map<StrategyState,Strategy> strategy = new HashMap<StrategyState, Strategy>();
	//given a state, returns a set of pairs <action,value>
	Map<StrategyState,Map<Strategy,Double>> Q = new HashMap<StrategyState,Map<Strategy,Double>>();
	//Map<StrategyState,Double> V = new HashMap<StrategyState,Double>(stratDomain.size());
	private Strategy currentStrategy;
	StrategyState lastState;
	StrategyState currentState;
	private TeamUPOpponentModel opponents[];
	private int timeStep;
	private File file;
	private File tempFile;
	private boolean log = true;
	StringBuffer content;
	private int K = 15;
	//for computing followIndex
	private static double GAMMA = 0.05;
	private static double RHO = 0.5;
	//shaping
	private boolean shapingOn = true;
	int[] totals = new int[3];
	int[][] c= new int [2][4];
	int[] stateCount = new int [65];
	private int numPlayers = 0;
	private int[] oppNumActions;
	
	//private static final int T = 2; //how many time steps to stick to current strategy
	/**
	 * @param g
	 * @param seed
	 */
	public TeamUP() {
	}
	
	public void init(Element e, int id){
		//initialize variables
		super.init(e, id);
		GAMMA = Float.valueOf(e.getAttribute("gamma"));
		System.out.println("\t gamma: " + GAMMA);
		RHO = Float.valueOf(e.getAttribute("rho"));
		System.out.println("\t rho: " + RHO);
		K = Integer.valueOf(e.getAttribute("k"));
		System.out.println("\t K: " + K);
		shapingOn = Boolean.valueOf(e.getAttribute("shaping"));
		System.out.println("\t Q using shaping? " + shapingOn);
		numPlayers = Integer.valueOf(e.getAttribute("players"));
		oppNumActions = reward.getNumActions();
		
		
		//initialize structures
		initLog();
		timeStep = 0;
		opponents = new TeamUPOpponentModel[numPlayers-1];
		
		//create the opponent models with correct action number and their id tag
		int a=0;
		for (int j = 0; j < numPlayers; j++) {
			if(j==agentId)
				a--;
			else{
				opponents[a] = new TeamUPOpponentModel(oppNumActions[j],j);
			}
			a++;
		}
		
		
		for (int i = 0; i < numPlayers; i++) {
				
		}
		stratDomain = new ActionDomain_LeaderFollower(opponents);
		StrategyState s0 = new StrategyState();
		stratDomain.add(s0);
		currentStrategy = new Strategy("S");
		computeAction(currentStrategy);
		for (StrategyState state : stratDomain.getStateSet()) {
			if(shapingOn){
				if(!shape(state, numPlayers,s0))
					state.Init(numPlayers, stratDomain.getPlayerStrategySet("1"), s0, 6, K);
			}else state.Init(numPlayers, stratDomain.getPlayerStrategySet("1"), s0, 12, K);
			states.put(state.getFeatures(),state);
		}
		lastState = s0;
		ValueIteration();
		
	}
	
	@Override
	public void update(ObservableEnvInfo curr) {
	  //public void observeOutcome(int[] actions){
		Map<Integer,Action> currJointAct =  stateMapper.getActions(curr);
		Vector<Object> currO = new Vector<Object>();
		for (int act : currJointAct.keySet()) 
			currO.add(currJointAct.get(act).getCurrentState());
		
		double currReward = reward.getReward(curr, currO, agentId);
		
	    for (int i = 0; i < opponents.length; i++) {
	    	assert(currJointAct.get(i).getOwnerId() == i);
			opponents[i].lastAction((Integer)currJointAct.get(i).getCurrentState(), (Integer)currentAction.getCurrentState());
		}
	    //System.out.println(actions[0]+","+actions[1]+","+actions[2]);
	    Vector<Strategy> jointStrat = computeState();
	    //content.append(jointStrat+ "\r");

	    if (update1(jointStrat,currReward)){    
	    	ValueIteration();
	    }
		currentStrategy = strategy.get(currentState);
		computeAction(currentStrategy);
		//System.out.println("ACTION("+currentAction+"):"+Q.get(currentState).get(currentStrategy));
	    sequence.add((Integer)currentAction.getCurrentState());
	    
		timeStep++;
	  }
	  
	  /**
	   * does the requied updates
	 * @param jointStrat current joint strategy
	 * @param r instantaneous reward
	 * @return true if replanning is required
	 */
	public boolean update1(Vector<Strategy> jointStrat, double r){
		  boolean update = false;
		  currentState = states.get(jointStrat);
		  //currentState.update(currentStrategy(currentState), r);
		  //if(lastState != currentState)
			//  System.out.println(currentState.getFeatures());
		  update = lastState.update(strategy.get(lastState), currentState, r);
		  lastState = currentState;
		  return update;
	  }
	  
	  public void computeAction(Strategy strategy){
		if(strategy.getName() == "F2")
			currentAction.changeToState(follow(0));
		else if (strategy.getName() == "F3")
			currentAction.changeToState(follow(1));
	  }
	  
		/**
		 * follow agent i means BR to strategy i AND
		 * make agent j indifferent
		 * @param i
		 * @return
		 */
		public Object follow(int i){
			Vector<Object> jointAct = new Vector<Object>();
			
			//create a joint action to play with
			int a=0;
			for (int j = 0; j < numPlayers; j++) {
				if(j==agentId){
					jointAct.add(-1);
					a--;
				}
				else{
					jointAct.add(opponents[a].currentAction());
				}
				a++;
			}
			
			//get BR
			double max = Double.NEGATIVE_INFINITY;
			Vector<Object> maxActions= new Vector<Object>();
			Vector<Vector<Object>> maxJointActions = new Vector<Vector<Object>>();
			for (Object act : currentAction.getDomainSet()) {
				jointAct.remove(agentId);
				jointAct.add(agentId, act);
				double[]r=reward.getRewards(jointAct);
				if(r[agentId] > max){
					maxActions.clear(); maxActions.add(act);
					maxJointActions.clear(); maxJointActions.add(jointAct);
					max = r[agentId];
				}else if(r[agentId] == max){
					maxActions.add(act);
					maxJointActions.add(jointAct);
				}
			}
			
			//second criteria to choose a BR, there are two possibilities:
			//1) 'please' agent i <-- THIS IS THE ONE USED FOR NOW
			//2) make the 3rd guy (possibly the sucker) indifferent
			double maxOpp = Double.NEGATIVE_INFINITY;
			int k = 0;
			Object maxAction= null;
			for (Vector<Object> joint : maxJointActions) {
				double[]r=reward.getRewards(joint);
				if(r[opponents[i].getId()] > maxOpp){
					maxAction = joint.get(agentId);
					maxOpp = r[opponents[i].getId()];
				}
				k++;
			}
			assert(maxActions.contains(maxAction));
			return maxAction;
		}
		
	  public void ValueIteration(){
		  final double EPSILON = 0.01; 
		  final double GAMMA = 0.5;
		  double error = 12;
		  Set<Strategy> actions = stratDomain.getPlayerStrategySet("1");
		  
		  Map<StrategyState,Double> V = new HashMap<StrategyState,Double>(stratDomain.size());
		  //Map<StrategyState,Map<Strategy,Double>> Q = new HashMap<StrategyState,Map<Strategy,Double>>();
		  
		  for (StrategyState state : stratDomain.getStateSet()) {
			  V.put(state, 0.0);
			  Map<Strategy,Double>Qaux = new HashMap<Strategy,Double>();
			  
			  for (Strategy action : actions) 
				  Qaux.put(action, 0.0);
			  Q.put(state, Qaux);
		  }		
		  
		  int counter = 0;
		  while (error > EPSILON) {
		  //while (counter< 50) {
		  // (int  counter=0; error>EPSILON;counter++) {
			  error = -9999;
			  //int c=0;
			  for (StrategyState state : stratDomain.getStateSet()) {
				  double Vstate = -9999;
				  //int c1=0;
				  for (Strategy action : actions) {
					  Map<StrategyState,Double> probs = state.probs(action);
					  //System.out.println(probs.size());
					  //expected over future states
					  double sum = 0;
					  //System.out.println(probs.size());
					  for (StrategyState state1 : probs.keySet()) {
						sum += probs.get(state1) * V.get(state1);
					  }
					  double total = state.getReward(action) + GAMMA * sum;
					  Q.get(state).put(action, total);
					  if(total > Vstate){
						  Vstate = total;
						  strategy.put(state, action);
					  }
					  ///c1++;
				  }
				  error = Math.max(error, Math.abs(Vstate - V.get(state)));
				  V.put(state, Vstate);
				  //c++;
				  //System.out.println("+++"+c);
			  }	
			  counter++;
			  //System.out.println(counter);
		  }
		  
		  //System.out.println(counter);
		  //System.out.println(strattoString());
		  
	  }
	  
	  public Strategy currentStrategy(StrategyState state){
		  double curMax = -9999;
		  double tmpMax = -9999;
		  Strategy curStrat = null;
		  for (Strategy strat : stratDomain.getPlayerStrategySet("1")) {
			tmpMax = Math.max(curMax, Q.get(state).get(strat));
			if(tmpMax>curMax){
				curStrat = strat;
				curMax = tmpMax;
			}
		  }
		  return curStrat;
	  }
	 
	
	/**
	 * THIS FUNCTION IS THE ONE TO BE PLAYED WITH 
	 * up to now the strategy sticks if somebody is in front of it
	 * or follows agent 1
	 * @return the return type is dummy
	 */
	private Vector<Strategy> computeState(){
		Vector<Strategy> jointStrat = new Vector<Strategy>();
		jointStrat.add(currentStrategy);
		
		/*double f1 = followIndex(opponents[0].getSequence());
		//System.out.println("followIndex:"+ fOp+" threshold:"+opponents[i].threashold() );
		double f2 = followIndex(opponents[1].getSequence());
		//System.out.println("followIndex:"+ fOp+" threshold:"+opponents[i].threashold() );
		if(f1 < TRESHOLD)
			jointStrat.add(new Strategy("F1"));
		else if(f2 < TRESHOLD)
		jointStrat.add(new Strategy("F2"));*/
		
		for (int i = 0; i < opponents.length; i++) {
			//System.out.println("+++ "+ (i+1) +" +++");
			int j;
			if(i == 0) j =1;
			else j = 0;
			Strategy strat;
			double fOp = opponents[i].followIndex(opponents[j].getSequence());
			//System.out.println("followIndex:"+ fOp+" threshold:"+opponents[i].threashold() );
			double fMe = opponents[i].followIndex(sequence);
			//System.out.println("followIndex:"+ fOp+" threshold:"+opponents[i].threashold() );
			if(fMe < opponents[i].threashold()){
				strat = new Strategy("F1");
				c[i][0]++;
			}
			else if(fOp < opponents[i].threashold()){
				strat = new Strategy("F"+(j+2));
				c[i][1]++;
			}
			else if(opponents[i].isSticking()){
				strat = new Strategy("S");
				c[i][2]++;
			}
			else{
				strat = new Strategy("O");
				c[i][3]++;
			}
			//System.out.println("strat"+strat.getName() );
			jointStrat.add(strat);
		}
		return jointStrat;
	}
	
	private boolean shape(StrategyState state, int n, StrategyState s0){
		Strategy f1 = new Strategy("F1");
		Strategy f2 = new Strategy("F2");
		Strategy f3 = new Strategy("F3");
		Strategy s = new Strategy("S");
		Vector<Strategy> st = new Vector<Strategy>(3);
		//optimal states
		st.add(f2); st.add(f1); st.add(f1);
		if(state.getFeatures().equals(st)){
			state.Init(n, stratDomain.getPlayerStrategySet("1"), s0, 12, K);
			return true;
		}
		st.clear(); st.add(f3); st.add(f1); st.add(f1);
		if(state.getFeatures().equals(st)){
			state.Init(n, stratDomain.getPlayerStrategySet("1"), s0, 12, K);
			return true;
		}
		st.clear(); st.add(f2); st.add(f1); st.add(s);
		if(state.getFeatures().equals(st)){
			state.Init(n, stratDomain.getPlayerStrategySet("1"), s0, 12, K);
			return true;
		}
		st.clear(); st.add(f3); st.add(s); st.add(f1);
		if(state.getFeatures().equals(st)){
			state.Init(n, stratDomain.getPlayerStrategySet("1"), s0, 12, K);
			return true;
		}
		st.clear(); st.add(s); st.add(f1); st.add(f1);
		if(state.getFeatures().equals(st)){
			state.Init(n, stratDomain.getPlayerStrategySet("1"), s0, 12, K);
			return true;
		}
		st.clear(); st.add(s); st.add(f1); st.add(s);
		if(state.getFeatures().equals(st)){
			state.Init(n, stratDomain.getPlayerStrategySet("1"), s0, 12, K);
			return true;
		}
		st.clear(); st.add(s); st.add(s); st.add(f1);
		if(state.getFeatures().equals(st)){
			state.Init(n, stratDomain.getPlayerStrategySet("1"), s0, 12, K);
			return true;
		}
		//worst states
		st.clear(); st.add(f2); st.add(f1); st.add(f2);
		if(state.getFeatures().equals(st)){
			state.Init(n, stratDomain.getPlayerStrategySet("1"), s0, 0, K);
			return true;
		}
		st.clear(); st.add(f2); st.add(f3); st.add(f2);
		if(state.getFeatures().equals(st)){
			state.Init(n, stratDomain.getPlayerStrategySet("1"), s0, 0, K);
			return true;
		}
		st.clear(); st.add(f3); st.add(f3); st.add(f2);
		if(state.getFeatures().equals(st)){
			state.Init(n, stratDomain.getPlayerStrategySet("1"), s0, 0, K);
			return true;
		}
		st.clear(); st.add(f3); st.add(f3); st.add(f1);
		if(state.getFeatures().equals(st)){
			state.Init(n, stratDomain.getPlayerStrategySet("1"), s0, 0, K);
			return true;
		}
		return false;
		
	}
	
	private void initLog(){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMddHHmm");
        Date date = new Date();
        File tmpPath = new File(System.getProperty("user.home")+"/experiments/lemonade/tmp/");
        String tempPath = System.getProperty("user.home")+"/experiments/lemonade/tmp/tmp.txt";
        String logPath = System.getProperty("user.home")+"/experiments/lemonade/tmp/log.txt";
        //create elog file or reuse if an elog file is already present
/*        File temp = new File(System.getProperty("user.home")+"/experiments/lemonade/tmp/");
        FilenameFilter filter = new FilenameFilter() { public boolean accept(File dir, String name) { return name.endsWith("elog"); } }; 
        String s[] = temp.list(filter); 
        
        if(s.length == 0)
        	file = new File(System.getProperty("user.home")+"/experiments/lemonade/tmp/"+dateFormat.format(date)+".elog");
        else if (s.length == 1) {
			file = new File(System.getProperty("user.home")+"/experiments/lemonade/tmp/" + s[0]);
		}
        else
        	assert(s.length <2);*/
        if(!tmpPath.exists()){
        	tmpPath.mkdirs();
        }
        file = new File(logPath);
		tempFile = new File(tempPath);
		getFileStateExperience(tempFile);
		content = new StringBuffer();
	}
	
	private void getFileStateExperience(File aFile){
	    if(aFile.exists()){
	    try {
	      //use buffering, reading one line at a time
	      //FileReader always assumes default encoding is OK!
	      BufferedReader input =  new BufferedReader(new FileReader(aFile));
	      try {
	        String line = null; //not declared within while loop
	        /*
	        * readLine is a bit quirky :
	        * it returns the content of a line MINUS the newline.
	        * it returns null only for the END of the stream.
	        * it returns an empty String if two newlines appear in a row.
	        */
	        int i=0;
	        int k=0;
	        while (( line = input.readLine()) != null){
	        	stateCount[i] += Integer.parseInt(line);
	        	k += stateCount[i];
	        	i++;
	        }
	        assert(k == 100);
	      }
	      finally {
	        input.close();
	      }
	    }
	    catch (IOException ex){
	      ex.printStackTrace();
	    }
	    }

	}
	public void getStatesExperience(int[] states){
		int i = 0;
		for (StrategyState state : stratDomain.getStateSet()) {
			states[i] += state.getExperience();
			i++;
		}
	}
	
	public void flushtoFile() throws FileNotFoundException, IOException {
		String ret =	System.getProperty("line.separator");
		int i = 0;
		content.setLength(0);
		for (StrategyState state : stratDomain.getStateSet()) {
			if(state.getFeatures().size()==0)
				content.append("S_0,");
			else{
			for (Strategy strat : state.getFeatures()) 
				content.append(strat.getName() + ",");
			}
			content.append("\t" + stateCount[i] + ret);
			i++;
		}
		
		//for (Integer c : totals) 
		//	content.append(c.toString()+" ");
		Writer output = new BufferedWriter(new FileWriter(file));
		    try {
		      //FileWriter always assumes default encoding is OK!
		      output.write( content.toString() );
		    }
		    finally {
		      output.close();
		    }
	}
	
	private void flushtoTempFile() throws FileNotFoundException, IOException {
		String ret =	System.getProperty("line.separator");
		for (Integer c : stateCount) 
			content.append(c.toString()+ret);
		  Writer output = new BufferedWriter(new FileWriter(tempFile));
		    try {
		      //FileWriter always assumes default encoding is OK!
		      output.write( content.toString() );
		    }
		    finally {
		      output.close();
		    }
	}
	
	private void updateStateExperience(){
		int i = 0;
		for (StrategyState state : stratDomain.getStateSet()) {
			stateCount[i] += state.getExperience();
			i++;
		}
	}
	
	private void strattoLog(){
		String ret =	System.getProperty("line.separator");
    	for (StrategyState state : strategy.keySet()) {
			content.append(""+state.getFeatures()+": ");
			content.append(""+strategy.get(state)+ret);	
		}
	}
	
	private StringBuffer strattoString(){
		StringBuffer cont = new StringBuffer();
		String ret =	System.getProperty("line.separator");
    	for (StrategyState state : strategy.keySet()) {
			cont.append(""+state.getFeatures()+": ");
			cont.append(""+strategy.get(state)+ret);	
		}
    	return cont;
	}

	
	public void observeUtility(int[] u) {
		//strattoLog();
		updateStateExperience();
	    for (int i=0;i<u.length;i++) {
			totals[i] += u[i];
		}
	    if(log){
		    try {
				flushtoTempFile();
				flushtoFile();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	}
	
	private void setParams(String options){
		int index1 = 0;
		int index2 = 0;
		String sub;
		index1=options.indexOf(':');
		while (index1 != -1)
		{
			char key = options.charAt(index1-1);//gets the char after the :
			switch (key) {
			case '-':
				log = false;
				index2 = options.indexOf(':', index1+1);
				if (index2>0)
					sub = options.substring(index1+1, index2-1);
				else 
					sub = options.substring(index1+1);
				index1 = options.indexOf(':', index2+1);
				break;
			case 'K':
				index2 = options.indexOf(':', index1+1);
				if (index2>0)
					sub = options.substring(index1+1, index2-1);
				else 
					sub = options.substring(index1+1);
				this.K = Integer.parseInt(sub);
				index1 = options.indexOf(':', index2+1);
				break;
				
			case 'L':
				index2 = options.indexOf(':', index1+1);
				if (index2>0) 
					sub = options.substring(index1+1, index2-1);
				else
					sub = options.substring(index1+1);
				//tempFileName = sub;
				index1 = options.indexOf(':', index2+1);
				break;
			default:
				break;
			}
		}
	}

}