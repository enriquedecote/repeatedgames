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
package experiment;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;


import org.xml.sax.SAXException;

import reward.Reward;

import util.ReadXml;


import agent.Agent;
import environment.Environment;

import util.Action;
import util.ObservableEnvInfo;

/*
 * the main class of the project.
 			 * For (every agent in agents )
			 * 1) next Action
			 * 2) getNextState
			 * 3) Get Rewards
			 * 4) Update
 */
 
public class Experiment {
	
	static int runs;
	static int iterations;
	static Vector<Agent> agents;
	static Environment env;
	static Reward rewards;
	static ObservableEnvInfo currentState;
	static ObservableEnvInfo prevState;
	static ExperimentLogger log;

	public static void main(String[] args) throws SAXException, IOException{


		//Scanner input = new Scanner (System.in);
		//System.out.println("Please Enter XML Filename");
		//String filename = input.nextLine();
		String xmlFile = args[0];
		ReadXml xml = new ReadXml(xmlFile+".xml", "bean.xml");
		
		log = new ExperimentLogger();
		runs = Integer.parseInt(xml.getExperimentRuns());
		iterations = Integer.parseInt(xml.getExperimentIterations());	
		rewards = xml.constructReward(log);
		log.recordConfig("Runs: " + runs);
		log.recordConfig("Iterations per run: " + iterations);
		for (int i = 0; i < runs; i++) {
			//create log file
			log.setLogFile(xmlFile,i);
			
			//destroy previous objects
			if(i>0){
				agents.clear();
				env = null;
			}
			
			// create all elements from XML
			agents = xml.constructAgents(rewards);
			Map<Integer,Action> jointAction = new HashMap<Integer,Action>(agents.size());
			for (Agent agent : agents)
				jointAction.put(agent.getId(),agent.getAction());
			
			//get state info to construct agent's structures
			env = xml.constructEnvironment();
			env.Init(jointAction);
			currentState = env.nextEnvInfo(jointAction);
			for (Agent agent : agents)
				agent.constructStructures(currentState);
				
	
			manyIterations(agents, env, iterations);
			System.out.println("end of run " + i);
			log.flush();
		}
		
		for (Agent agent : agents) 
			agent.recordToLogger(log);

		log.flushtoConfigFile();
}
	
	public static double[] manyIterations(Vector<Agent> agents, Environment env, int iterations) {
		
		double[] totUtil = new double[agents.size()]; 
		double[] runUtil = new double[agents.size()];
		int totIterations = iterations;
		while (iterations>0){
			iterations = iterations - 1;
			runUtil = oneIteration(agents, env);
			for (int i = 0; i < runUtil.length; i++) {
				totUtil[i] += runUtil[i];
			}
			log.recordUtils(runUtil, iterations);
		}
		log.recordMean(totUtil, totIterations);
		return totUtil;
	}
	  
	public static double[] oneIteration(Vector<Agent> agents, Environment env){
		Map<Integer,Action> jointAction = new HashMap<Integer,Action>(agents.size());
		Vector<Object> jointActionString = new Vector<Object>(agents.size());

		// 1) get actions
		for(Agent agent: agents){
			jointAction.put(agent.getId(),agent.getAction());
			jointActionString.add(agent.getAction().getCurrentState());
		}

		// 2) get environmental change (foe state perceptions for agents)
		currentState = env.nextEnvInfo(jointAction);
/*		for (Object o : currentState.currentState()) {
			System.out.print("(");
			int[] c=(int[])o;
			for (int i : c) {
				System.out.print(i+",");
			}
			System.out.print("), ");
			
		}*/
		
		double[] instReward = rewards.getRewards(currentState,jointAction);
		//System.out.println("rewards: "+instReward);
		log.recordState(currentState);
		log.recordActions(jointActionString);
		//System.out.println("State:"+jointActionString);
		// 3) update agents
		for(Agent agent: agents)
			agent.update(currentState);
		
		return instReward;
	}


}

