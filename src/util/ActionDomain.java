package util;


import java.util.Vector;
/**
 * @author aladdinagentschool
 * generic type actiondomain, can be implemented. It holds all the generic fields and methods common it its subclasses 
 */
public abstract class ActionDomain {
	public Vector<Object> actionSet;
	public Object getFirstAction(){
		return actionSet.firstElement();
	}
	
	public Vector<Object> getActionSet(){
		return actionSet;
	}
	
	public boolean contains(Object o){
		return actionSet.contains(o);
	}
}
