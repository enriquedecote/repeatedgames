package util;

/**
* @author aladdinagentschool
*This is a subclass of ActionDomain, this class can be implemented. // Class with the types of grid actions.
* 
*/
public class GridActions extends Action  {
	
	public static GridActions right = new GridActions ("right");
	public static GridActions left = new GridActions ("left");
	public static GridActions up = new GridActions ("up");
	public static GridActions down = new GridActions ("down");
	

	// constructors
	public GridActions (String Name){
		
		actionName= Name;
		actionSet.add(right);
		actionSet.add(left);
		actionSet.add(up);
		actionSet.add(down);
	
	}

	// constructors
	public GridActions (){
		//super();
		actionSet.add(right);
		actionSet.add(left);
		actionSet.add(up);
		actionSet.add(down);
	
	}
	
}


