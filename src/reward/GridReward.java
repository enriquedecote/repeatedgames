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

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import util.Action;
import util.Info_Grid;
import util.ObservableEnvInfo;
import util.ReadXml;
import util.State;

/**
 * @author enrique
 *
 */
public class GridReward implements Reward {

	/**
	 * This class implements the most generic reward function for grid, specializations should inherit from here
	 */
	
	//coins
	HashMap<Vector<Integer>,Float> coins = new HashMap<Vector<Integer>,Float>();
	
	//puds
	HashMap<Vector<Integer>,Float> puds = new HashMap<Vector<Integer>,Float>();
	
	private int collisionVal = 0;
	
	public GridReward() {

	}

	/* (non-Javadoc)
	 * @see reward.Reward#Init(java.lang.String)
	 */
	@Override
	public void Init(String game) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see reward.Reward#getReward(util.ObservableEnvInfo, java.util.Vector, int)
	 */
	@Override
	public double getReward(ObservableEnvInfo s, Vector<Object> actions,
			int agent) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see reward.Reward#getReward(java.util.Vector, int)
	 */
	@Override
	public double getReward(Vector<Object> actions, int agent) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see reward.Reward#getRewards(util.ObservableEnvInfo, java.util.Vector)
	 */
	@Override
	public double[] getRewards(ObservableEnvInfo st, Map<Integer,Action> actions) {
		boolean collision= false;
		Info_Grid s = (Info_Grid)st;
		double sum[] = new double[s.currentJointAction().size()];
		for (int i = 0; i < sum.length; i++) {
			if (collision)
				sum[i] = getpudRewards(s)[i] + getcoinRewards(s)[i] + collisionVal;
			else
				sum[i] = getpudRewards(s)[i] + getcoinRewards(s)[i];
		}
	return sum;
	}

	/* (non-Javadoc)
	 * @see reward.Reward#getRewards(java.util.Vector)
	 */
	@Override
	public double[] getRewards(Vector<Object> actions) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see reward.Reward#isSymmetric()
	 */
	@Override
	public boolean isSymmetric() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see reward.Reward#swapPlayers(int, int)
	 */
	@Override
	public Reward swapPlayers(int i, int j) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see reward.Reward#getNumActions()
	 */
	@Override
	public int[] getNumActions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void Init(ReadXml xml) {
		String splitCoordinate[]  = new String[2];
		
		collisionVal = Integer.parseInt(xml.getTagAttribute("Type", "collisionVal"));
		System.out.println("collision value:" + collisionVal);
		
		NodeList coinList = xml.getElementsByTagName("Coins");
		
		for (int i = 0; i < coinList.getLength(); i++) {
			NodeList list = coinList.item(i).getChildNodes();
			Element coord = (Element) list.item(1);
			splitCoordinate = xml.getTextValue (coord, "Coordinate").split("\\,");
			Vector<Integer>ccoordinate= new Vector<Integer>(2);
			ccoordinate.add(Integer.parseInt(splitCoordinate[0]));
			ccoordinate.add(Integer.parseInt(splitCoordinate[1]));
			coins.put(ccoordinate, xml.getFloatValue (coord, "Value"));
		}
		
	}
	
	/**
	 * @param state is the next state of the game
	 * @return coin reward of the agent for the state
	 */
	private float[] getcoinRewards(Info_Grid s){
		float[] r = new float[s.currentJointCoord().size()];
		for (int i = 0; i <s.currentJointCoord().size(); i++) {
			if(coins.containsKey(s.currentJointCoord().get(i)))
				r[i] = coins.get(s.currentJointCoord().get(i));
			else
				r[i] =  0;
		}
		return r;	
	}
	
	/**
	 * @param state is the next state of the game
	 * @return coin reward of the agent for the state
	 */
	private float[] getpudRewards(Info_Grid s){
		float[] r = new float[s.currentJointCoord().size()];
		for (int i = 0; i <s.currentJointCoord().size(); i++) {
			if(puds.containsKey(s.currentJointCoord().get(i)))
				r[i] = puds.get(s.currentJointCoord().get(i));
			else
				r[i] =  0;
		}
		return r;	
	}

}
