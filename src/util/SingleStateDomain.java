package util;

import java.io.IOException;
import java.util.*;

import org.xml.sax.SAXException;

import agent.Agent;
import agent.QLearningAgent;


/*
 * extension of generic type StateDomain
 */
/**
 * @author aladdinagentschool
 *subclass of  generic type statedomain, can be implemented. It holds all the generic fields and methods common it its subclasses 
 *it creates the grid domain 
 */
public class SingleStateDomain extends StateDomain<JointActionState> {
	JointActionState dummyState;
	private Map<Vector<Object>,JointActionState> mapping;
	
	/**
	 * Only works for 2 agents
	 * @param actions
	 */
	public SingleStateDomain (){
		domain = new LinkedHashSet<JointActionState>();
		mapping = new HashMap<Vector<Object>, JointActionState>();
		Vector<Object> vectO;

				dummyState = new JointActionState();
				domain.add(dummyState);

	}
	

	
// get state domain		
	public Set<JointActionState> getStateSet(){
		return domain;	
	}	
// returns size of the domain	
	public int size(){
		return domain.size();
	}
	
	/**
	 * Transforms info coming from the environment to the perceived state info
	 * @param info the info coming from the environment
	 * @return
	 */
	public JointActionState getState(ObservableEnvInfo e){	
			return dummyState;


	}
	
}
  

