package util;

import java.util.LinkedHashSet;
import java.util.Iterator;
import java.util.Set;


public class LeaderFollowerDomain implements StateSet<StrategyState> {
	protected Set<StrategyState> states;
	
	public LeaderFollowerDomain(Set<OpponentModel> players){
		Init(players);
	}
	
	public LeaderFollowerDomain(OpponentModel[] opponents){
		Init(transformOppToSet(opponents));
	}
	
	private void Init(Set<OpponentModel> players){
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

	public Set<OpponentModel> transformOppToSet(OpponentModel[] opponents){
		Set<OpponentModel> players = new LinkedHashSet<OpponentModel>();
		for (int i =0; i < opponents.length; i++) 
			players.add(opponents[i]);
		return players;
	}

	@Override
	public final int size() {
		return states.size();
	}

}
