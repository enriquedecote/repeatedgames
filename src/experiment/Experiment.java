package experiment;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;
import java.util.Vector;

import org.springframework.beans.factory.BeanFactory;

import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.xml.sax.SAXException;
import org.apache.xerces.parsers.*;

import reward.GridReward;
import reward.Reward;

import util.ReadXml;


import agent.Agent;
import environment.Environment;

import util.Action;
import util.CoordinateState;
import util.GridStateDomain;
import util.JointActionState;
import util.ObservableEnvInfo;
import util.State;
import util.readxmlv2;

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
			agents = xml.constructAgents();
			Vector<Action> jointAction = new Vector<Action>(agents.size());
			for (Agent agent : agents){
				agent.init(rewards);
				jointAction.add(agent.getAction());
			}
			//get state info to construct agent's structures
			env = xml.constructEnvironment();
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
	
	public static int[] manyIterations(Vector<Agent> agents, Environment env, int iterations) {
		
		int[] totUtil = new int[2]; 
		int[] runUtil = new int[2];
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
	  
	public static int[] oneIteration(Vector<Agent> agents, Environment env){
		Vector<Action> jointAction = new Vector<Action>(agents.size());
		Vector<Object> jointActionString = new Vector<Object>(agents.size());

		// 1) get actions
		for(Agent agent: agents){
			jointAction.add(agent.getAction());
			jointActionString.add(agent.getAction().getCurrentState());
		}

		// 2) get environmental change (foe state perceptions for agents)
		// prevState = currentState;
		currentState = env.nextEnvInfo(jointAction);
		int[] instReward = rewards.getRewards(currentState,jointAction);
		log.recordActions(jointActionString);
		//System.out.println(jointActionString);
		// 3) update agents
		for(Agent agent: agents)
			agent.update(currentState);
		
		return instReward;
	}


}

