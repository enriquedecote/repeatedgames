package util;

import java.util.Vector;

/**
 * This class gets the domain of the Action variable, it should NOT construct the Actions itself
 * @author Enrique Munoz de Cote
 *
 */
public class NintegerActionDomain extends ActionDomain {
	
	/**
	 * Generates a domain of size numberActions [0,numberActions-1]
	 * @param numberActions the size of the domain
	 */
	public NintegerActionDomain(int numberActions){
		
		actionSet = new Vector(numberActions);
		for (int i = 0; i < numberActions; i++) {
			actionSet.add(i);
		}
	}

}
