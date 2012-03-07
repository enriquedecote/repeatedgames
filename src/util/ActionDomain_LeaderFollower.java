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

import java.util.LinkedHashSet;
import java.util.Iterator;
import java.util.Set;


public class ActionDomain_LeaderFollower implements StateSet<StrategyState> {
	protected Set<StrategyState> states;
	
	public ActionDomain_LeaderFollower(Set<TeamUPOpponentModel> players){
		Init(players);
	}
	
	public ActionDomain_LeaderFollower(TeamUPOpponentModel[] opponents){
		Init(transformOppToSet(opponents));
	}
	
	private void Init(Set<TeamUPOpponentModel> players){
		states = new LinkedHashSet<StrategyState>();
		//construct each each strategyState
		Strategy st1 = new Strategy("O");
		Strategy st2 = new Strategy("O");
		Strategy st3 = new Strategy("O");
		for (Strategy s1: st1.domain()) {
			for (Strategy s2: st2.domain()) {
				for (Strategy s3: st3.domain()) {
					StrategyState state = new StrategyState();
					if(s1.getName() != "F1"&&s2.getName() != "F2"&&s3.getName() != "F3") {
						state.addFeature(s1);state.addFeature(s2); state.addFeature(s3);
						states.add(state);
					}
				}
			}
		}
	}
	
	@Override
	public final void add(StrategyState state) {
		states.add(state);
	}

	@Override
	public final Set<StrategyState> getStateSet() {
		return states;
	}
	
	public Set<Strategy> getPlayerStrategySet(CharSequence i){
		Set<Strategy> strats = new LinkedHashSet<Strategy>();
		Strategy temp = new Strategy("O");
		strats = temp.domain();
		for (Strategy strategy : strats) {
			if(strategy.getName().contains(i)){
				strats.remove(strategy);
				return strats;
			}
		}
		return null;
	}

	public Set<TeamUPOpponentModel> transformOppToSet(TeamUPOpponentModel[] opponents){
		Set<TeamUPOpponentModel> players = new LinkedHashSet<TeamUPOpponentModel>();
		for (int i =0; i < opponents.length; i++) 
			players.add(opponents[i]);
		return players;
	}

	@Override
	public final int size() {
		return states.size();
	}

}
