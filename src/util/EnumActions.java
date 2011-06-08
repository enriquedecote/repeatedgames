package util;

/*
 *  the types of enum actions required in the lemonade game 
 *  this class extends from generic type action
 */

public class EnumActions extends Action{
	public static EnumActions One = new EnumActions (1);
	public static EnumActions Two = new EnumActions (2);
	public static EnumActions Three = new EnumActions (3);
	public static EnumActions Four = new EnumActions (4);
	public static EnumActions Five = new EnumActions (5);
	public static EnumActions Six = new EnumActions (6);
	public static EnumActions Seven = new EnumActions (7);
	public static EnumActions Eight = new EnumActions (8);
	public static EnumActions Nine = new EnumActions (9);
	public static EnumActions Ten = new EnumActions (10);
	public static EnumActions Eleven = new EnumActions (11);
	public static EnumActions Twelve = new EnumActions (12);
	
	// constructors
	public EnumActions (int Name){
		super ();
		actionSet.add(One);
		actionSet.add(Two);
		actionSet.add(Three);
		actionSet.add(Four);
		actionSet.add(Five);
		actionSet.add(Six);
		actionSet.add(Seven);
		actionSet.add(Eight);
		actionSet.add(Nine);
		actionSet.add(Ten);
		actionSet.add(Eleven);
		actionSet.add(Twelve);
	}


	// constructors
	public EnumActions (){
		super ();
		actionSet.add(One);
		actionSet.add(Two);
		actionSet.add(Three);
		actionSet.add(Four);
		actionSet.add(Five);
		actionSet.add(Six);
		actionSet.add(Seven);
		actionSet.add(Eight);
		actionSet.add(Nine);
		actionSet.add(Ten);
		actionSet.add(Eleven);
		actionSet.add(Twelve);
	}
}