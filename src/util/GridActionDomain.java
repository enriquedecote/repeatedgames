package util;


import java.util.Vector;


/**
* @author aladdinagentschool
*This is a subclass of ActionDomain, this class can be implemented. it creates a domain of all possible gridactions
* 
*/
public class GridActionDomain extends ActionDomain {
	public GridActionDomain(){
		Action right = new GridActions("right");
		Action left = new GridActions("left");
		Action up = new GridActions("up");
		Action down = new GridActions("down");
		Action put = new GridActions("put");
		actionSet = new Vector<Action>();
		actionSet.add(right);
		actionSet.add(left);
		actionSet.add(up);
		actionSet.add(down);
		actionSet.add(put);
	
		
		
	}
}
