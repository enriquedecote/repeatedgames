/**
 * 
 */
package util;

import agent.Agent;

/**
 * @author Enrique Munoz de Cote
 *
 */
public class NintegerAction extends Action {
	//private NintegerActionDomain domain;
	private int action;
	private static int defAction = 0;
	private int size;
	
	
	public NintegerAction(String stringName, int domainRange){
		actionName = stringName;
		domain = new NintegerActionDomain(domainRange);
		size = domainRange;
	}
	public NintegerAction(int domainRange){
		domain = new NintegerActionDomain(domainRange);
		action = defAction;
		state = defAction;
		size = domainRange;
	}
	
	public NintegerAction(int domainRange, int id){
		domain = new NintegerActionDomain(domainRange);
		action = defAction;
		state = defAction;
		size = domainRange;
		agentId = id;
	}
	
	public NintegerAction newInstance(){
		return new NintegerAction(size, agentId);
	}
}
