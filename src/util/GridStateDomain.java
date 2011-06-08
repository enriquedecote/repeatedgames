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
public class GridStateDomain extends StateDomain {
	
	
	public static Vector<CoordinateState> domain = new Vector <CoordinateState>();
	
	
	// constructor for the class
	public GridStateDomain (int row, int column, int player){
		domain = new Vector <CoordinateState>();
		init (row, column, player);
	}
	
	// Override the constructor. It take in the xml file name and gets the values for the row, column and players itself.
	public GridStateDomain (String filename) throws SAXException, IOException{
		util.readxmlv2.getData(filename);
		int players = util.readxmlv2.getplayers();
		int row = util.readxmlv2.getRows();
		int colm =util.readxmlv2.getColumn();
		init (row, colm, players);
	
	}
	
	// method that creates the domain
	public static void init (int rows, int columns, int players){
		//State stat = new CoordinateState();
		Map<Agent,Vector<Integer>> state;
		
		Agent[] agentArray = new Agent[players];
		// all the states in the grid. eg [0,0], [0, 1], [0, 2].....etc
		Vector<Vector<Integer>> allCoordinates = new Vector<Vector<Integer>>();
		int noofCoor = rows* columns;
		
		
		// fills the vector allState with all the states in the grid.
		for (int i=0; i<rows; i++){
			for (int j=0; j<columns; j++){
				Vector<Integer> coor = new Vector<Integer>(2);
				coor.add(i);
				coor.add(j);
				allCoordinates.add(coor);
				
			}
		}
		
		// creates an array of agents using no of players in the game
		for(int t=0; t<players; t++){ 
			Agent agent = new QLearningAgent();
			agentArray[t]=agent;

		}
		
		// nPk permutation, n- noofCoor k - players 
		Permutations p = new Permutations(noofCoor,players);
		
		// finds all the possible combination of coordinates without repetition and adds then on the domain set.
		while (p.hasNext()) {
			
			state  = new HashMap<Agent, Vector<Integer>>();
	      int[] a = p.next();
	      for (int s=0; s<a.length; s++){
	    	  
	    	state.put(agentArray[s], allCoordinates.get(a[s]));
	      }
	      
	     CoordinateState feature = new CoordinateState(state);
	     
	      domain.add(feature);	     
		
	    }
	
	    
	}
			
// adding coordinates to the domain
	public void add (CoordinateState state){
		domain.add(state);		
	}
// get state domain		
	public Vector<CoordinateState> getStateSet(){
		
		return domain;
		
	}	
// returns size of the domain	
	public int size(){
		return domain.size();
	}
	
}
  

