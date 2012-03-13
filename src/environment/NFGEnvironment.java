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
/**
 * 
 */
package environment;

import java.util.Map;
import java.util.Vector;

import org.w3c.dom.Element;

import util.Action;
import util.Info_NFG;
import util.ObservableEnvInfo;
import util.ReadXml;

/**
 * @author enrique
 */
public class NFGEnvironment implements Environment<Action> {
	Info_NFG envInfo;
	
	public NFGEnvironment(Map<Integer,Action> jointAction){
		envInfo = new Info_NFG(jointAction);
	}
	
	public NFGEnvironment(){
		envInfo = new Info_NFG();
	}

	@Override
	public ObservableEnvInfo currentEnvInfo() {
		return envInfo;
	}

	@Override
	public ObservableEnvInfo nextEnvInfo(Map<Integer,Action> actions) {
		envInfo.updateJointAction(actions);
		return envInfo;
	}

	@Override
	public void Init(Map<Integer,Action> actions) {
		envInfo.Init(actions);
	}

	@Override
	public void Init(ReadXml xml) {
		// TODO Auto-generated method stub
		
	}

}
