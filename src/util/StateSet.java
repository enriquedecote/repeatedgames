package util;

import java.util.Set;

//TODO: rename to StateDomain
public interface StateSet<T> {
	public void add(T action);

	public Set<T> getStateSet();

	public int size();
	
}
