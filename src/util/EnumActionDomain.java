package util;


import java.util.Vector;
/**
 * @author aladdinagentschool
 *This is a subclass of ActionDomain, this class can be implemented. it creates a domain of all possible enum actions
 * 
 */
public class EnumActionDomain extends ActionDomain {
	//constructor
	public EnumActionDomain(){
		Action One = new EnumActions (1);
		Action Two = new EnumActions (2);
		Action Three = new EnumActions (3);
		Action Four = new EnumActions (4);
		Action Five = new EnumActions (5);
		Action Six = new EnumActions (6);
		Action Seven = new EnumActions (7);
		Action Eight = new EnumActions (8);
		Action Nine = new EnumActions (9);
		Action Ten = new EnumActions (10);
		Action Eleven = new EnumActions (11);
		Action Twelve = new EnumActions (12);
		actionSet = new Vector<Action>();
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
