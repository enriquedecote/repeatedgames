package util;

/*
 *  the types of enum actions required in the lemonade game 
 *  this class extends from generic type action
 */

public class TwoActions extends Action{
	public static TwoActions One = new TwoActions (1);
	public static TwoActions Two = new TwoActions (2);
	
	// constructors
	public TwoActions (int Name){
		super ();
		actionSet.add(One);
		actionSet.add(Two);
	}


	// constructors
	public TwoActions (){
		super ();
		actionSet.add(One);
		actionSet.add(Two);
	}
}