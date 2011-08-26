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
package util;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Vector;
import java.util.LinkedHashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Map;

import agent.Agent;
import agent.QLearningAgent;

/**
 * @author aladdinagentschool
 * subclass of generic type statedomain, can be implemented. It implements all the possible lemonade states
 */

public class StateDomain_Lemonade extends StateDomain<State_JointAction> {  
	protected Set<State_JointAction> states;
	
	// creates the domain
	public void Init (){
		int player =5;
		Vector<Agent> agentSet = new Vector <Agent>();
		states = new LinkedHashSet <State_JointAction>();
		Map <Agent , Action> newState;
		newState = new HashMap <Agent, Action>();
		Action One = new Action_Enum (1);
		Action Two = new Action_Enum (2); Action Three = new Action_Enum (3);Action Four = new Action_Enum (4); Action Five = new Action_Enum (5);
		Action Six = new Action_Enum (6); Action Seven = new Action_Enum (7); Action Eight = new Action_Enum (8); Action Nine = new Action_Enum (9); Action Ten = new Action_Enum (10);
		Action Eleven = new Action_Enum (11); Action Twelve = new Action_Enum (12);
		for (int i=0; i<player; i++){
			Agent agent = new QLearningAgent();
			agentSet.add(agent);
		}
		
		for (Action One1 : One.actionsSet()){
			for(Action Two2:Two.actionsSet()){
				for (Action Three3 : Three.actionsSet()){
					for (Action Four4 : Four.actionsSet()){
						for (Action Five5 : Five.actionsSet()){
							for (Action Six6 : Six.actionsSet()){
								for (Action Seven7 : Seven.actionsSet()){
									for (Action Eight8 : Eight.actionsSet()){
										for (Action Nine9 : Nine.actionsSet()){
											for (Action Ten10 : Ten.actionsSet()){
												for (Action Eleven11 : Eleven.actionsSet()){
													for (Action Twelve12 : Twelve.actionsSet()){
												
														State_JointAction NewState = new State_JointAction ();
															if (One1.getName()!= "One" && Two2.getName()!= "Two" && Three3.getName() != "Three" && Four4.getName()!= "Four" && Five5.getName() != "Five" && Six6.getName()!= "Six" && Seven7.getName() != "Seven" && Eight8.getName()!= "Eight" && Three3.getName() != "Three" && Two2.getName()!= "Two" && Nine.getName() != "Nine" && Ten10.getName()!= "Ten" && Eleven11.getName() != "Eleven" && Twelve12.getName()!= "Twelve"){
																newState.put(agentSet.get(player), One1);newState.put(agentSet.get(player), Two2);newState.put(agentSet.get(player), Three3);newState.put(agentSet.get(player), Four4);newState.put(agentSet.get(player), Five5);newState.put(agentSet.get(player), Six6);newState.put(agentSet.get(player), Seven7);newState.put(agentSet.get(player), Eight8);newState.put(agentSet.get(player), Nine9);newState.put(agentSet.get(player), Ten10);newState.put(agentSet.get(player), Eleven11);newState.put(agentSet.get(player), Twelve12);
																
																
																NewState.addFeature(newState); //NewState.addFeature(Two2); NewState.addFeature(Three3);NewState.addFeature(Four4);NewState.addFeature(Five5);NewState.addFeature(Six6);NewState.addFeature(Seven7); NewState.addFeature(Eight8); NewState.addFeature(Nine9); NewState.addFeature(Ten10); NewState.addFeature(Eleven11); NewState.addFeature(Twelve12);
																states.add(NewState);
															}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
	
	}
	
	// adds state to the domain
	public void add (State_JointAction state){
		states.add(state);
	}
	
	// to get lemonade state domain
	public Set<State_JointAction >  getStateDomain(){
		return states;
		}
	
	// returns size of domain
	public int size(){
		return states.size();
		
	}
}


	
		
	
