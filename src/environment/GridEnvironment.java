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
package environment;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Vector;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import agent.Agent.ActionType;

import util.Action;
import util.Action_Grid;
import util.Info_Grid;
import util.ObservableEnvInfo;
import util.ReadXml;
/**
 * @author Enrique Munoz de Cote
 * Here's where the way the world behaves lies. It includes several attributes specific of grid domains
 */
//TODO: fix the starting position to be read from xml
public class GridEnvironment implements Environment<Action> {
	private Info_Grid envInfo;
	private int cols = 0;
	private int rows = 0;
	private Vector<int[]> jointCoord = new Vector<int[]>();
	protected Random random = new Random();
	public enum ActionType
	{
	    up, down, left, right, put; 
	}
	
	//walls
	Map<int[],Float> xWalls = new HashMap<int[],Float>();
	//walls
	Map<int[],Float> yWalls = new HashMap<int[],Float>();
	
	
	public GridEnvironment(){
		envInfo = new Info_Grid();
	}
	
	public GridEnvironment(Vector<Action> jointAction) {
		envInfo = new Info_Grid(jointAction);
	}
	
	@Override
	public ObservableEnvInfo nextEnvInfo(Vector<Action> actions) {
		envInfo.updateJointAction(actions);
		
		Vector<int[]> coords = new Vector<int[]>();
		for (int i = 0; i < actions.size(); i++) {
			Action_Grid action = (Action_Grid)actions.get(i);
			int[] coord = jointCoord.get(i);
			int[] tmp;
			
			switch (ActionType.valueOf((String)action.getCurrentState())) {
			case up:
				if((coord[0] + 1) < rows || !xWalls.containsKey(coord))
					coord[0] = coord[0] + 1;
				break;
				
			case down:
				tmp = coord;
				tmp[0] = tmp[0] -1;
				if((coord[0] -1) > -1 || !xWalls.containsKey(tmp))
					coord[0] = coord[0] - 1;
				break;
				
			case right:
				if((coord[1] + 1) < cols || !yWalls.containsKey(coord))
					coord[1] = coord[1] + 1;
				break;
				
			case left:
				tmp = coord;
				tmp[1] = tmp[1] -1;
				if((coord[1] -1) > -1 || !yWalls.containsKey(tmp))
					coord[1] = coord[1] - 1;
				break;
			}
			coords.add(coord);
		}
		
		//now check for collisions
		Vector<Integer> collision = new Vector<Integer>();
		for (int i = 0; i < actions.size(); i++) {
			int[] coord = coords.get(i);
			collision.add(i);
			for (int k = 0; k < actions.size(); k++) {
				if(coords.get(k) == coord && i!=k){
					collision.add(k);
				}	
			}
			if(collision.size() > 1){
				int randomA = random.nextInt(collision.size());//choose one agent randomly
				for (int k = 0; k < collision.size(); k++) {
					if(k != randomA){//the agent chosen randomly will stay in its new coordinates, the rest should return
						coords.remove(collision.get(k));
						coords.add(collision.get(k), jointCoord.get(collision.get(k)));
					}
				}
			}
			collision.clear();
		}
		jointCoord = coords;
		envInfo.updateJointCoord(coords);
		return envInfo;
	}

	@Override
	public ObservableEnvInfo currentEnvInfo() {
		return envInfo;
	}

	@Override
	public void Init(Vector<Action> actions) {
		envInfo.Init(actions);
	}

	@Override
	public void Init(ReadXml xml) {
		cols = Integer.parseInt(xml.getTagAttribute("Type", "columns"));
		rows = Integer.parseInt(xml.getTagAttribute("Type", "rows"));
		System.out.println("Grid: "+ cols + " x " + rows );
		
		//put the agents in the environment
		int[] coord = new int[2];
		coord[0]=0; coord[1]=0;
		jointCoord.add(coord);
		coord[0]=0; coord[1]=2;
		jointCoord.add(coord);
		envInfo.updateJointCoord(jointCoord);
		
		//build walls
		NodeList coinList;
		String splitCoordinate[]  = new String[2];
		//build Xwalls
		coinList = xml.getElementsByTagName("XWalls");
		for (int i = 0; i < coinList.getLength(); i++) {
			NodeList list = coinList.item(i).getChildNodes();
			Element wall = (Element) list.item(1);
			splitCoordinate = xml.getTextValue (wall, "Coordinate").split("\\,");
			int[] wcoord= new int[2];
			wcoord[0] = Integer.parseInt(splitCoordinate[0]);
			wcoord[1] = Integer.parseInt(splitCoordinate[1]);
			for (int j = 0; j < xml.getIntValue(wall, "Size"); j++) {
				xWalls.put(wcoord, xml.getFloatValue(wall, "Value"));
				wcoord[0] = wcoord[1] + 1;
			}
		}
		//build Ywalls
		coinList = xml.getElementsByTagName("YWalls");
		for (int i = 0; i < coinList.getLength(); i++) {
			NodeList list = coinList.item(i).getChildNodes();
			Element wall = (Element) list.item(1);
			splitCoordinate = xml.getTextValue (wall, "Coordinate").split("\\,");
			int[] wcoord= new int[2];
			wcoord[0] = Integer.parseInt(splitCoordinate[0]);
			wcoord[1] = Integer.parseInt(splitCoordinate[1]);
			for (int j = 0; j < xml.getIntValue(wall, "Size"); j++) {
				yWalls.put(wcoord, xml.getFloatValue(wall, "Value"));
				wcoord[1] = wcoord[1] + 1;
			}
		}
		
	}
	

}
