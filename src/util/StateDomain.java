package util;
import java.util.Set;
import java.util.Vector;

/**
 * @author Enrique Munoz de Cote
 *
 * This is the abstract class StateDomain. 
 * @param T a subclass of state
 */
public abstract class StateDomain<State> {
	
	protected Set<State> domain;
	
	public StateDomain(){
		
	}
	
	// Method to get the domain
	public Set<State> getStateSet () {
		return domain;
	}
// return domain size
	public int size() {
		return domain.size();
	}
	 
	/**
	 * Transforms info coming from the environment to the perceived state info
	 * @param info the info coming from the environment
	 * @return
	 */
	public State getState(ObservableEnvInfo info){
		return null;
	}
	 
}


