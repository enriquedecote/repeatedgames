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

import java.util.Vector;

import org.w3c.dom.Element;

import util.Action;
import util.Info_Grid;
import util.ObservableEnvInfo;
/**
 * @author Enrique Munoz de Cote
 * Here's where the way the world behaves lies. It includes several attributes specific of grid domains
 */
//TODO: fix the starting position to be read from xml
public class GridEnvironment implements Environment<Action> {
	private Info_Grid envInfo;
	private int cols = 0;
	private int rows = 0;
	private Vector<Vector<Integer>> jointCoord = new Vector();
	
	
	public GridEnvironment(){
		envInfo = new Info_Grid();
	}
	
	public GridEnvironment(Vector<Action> jointAction) {
		envInfo = new Info_Grid(jointAction);
	}
	
	@Override
	public ObservableEnvInfo nextEnvInfo(Vector<Action> actions) {
		envInfo.updateJointAction(actions);
		envInfo.updateJointCoord(j);
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
	public void Init(Element e) {
		cols = Integer.valueOf(e.getAttribute("columns"));
		rows = Integer.valueOf(e.getAttribute("rows"));
		System.out.println("Grid: "+ cols + " x " + rows );
		
		//put the agents in the environment
		for(int i=0; i< envInfo.currentJointAction().size(); i++){//for every agent
			Vector<Integer> c = new Vector(2);
			c.add(0);c.add(i);
			jointCoord.add(c);
		}
		
	}
	

}
