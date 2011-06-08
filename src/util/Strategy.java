package util;

//TODO: this implementation is for TeamUP's abstraction. Strategy should be abstract or an interface
import java.util.Arrays;
import java.util.HashSet;

public class Strategy {
	public static final Strategy F1 = new Strategy("F1");
	public static final Strategy F2 = new Strategy("F2");
	public static final Strategy F3 = new Strategy("F3");
	public static final Strategy S = new Strategy("S");
	public static final Strategy O = new Strategy("O");
	//public static final HashSet<Strategy> domain2 = new HashSet(Arrays.asList({F1,F2,F3,S}));
	private HashSet<Strategy> domain = new HashSet<Strategy>();
	//the name of the agent that uses this strategy
	private String name;
	//the integer identifier of the agent implementing this strategy
	private int player;
	private Integer hashCode;
	
	public Strategy(String name){
		this.name = name;
		domain.add(F1);domain.add(F2);domain.add(F3);domain.add(S);domain.add(O);
	}
	
	public Strategy(String name, int player){
		this.name = name;
		this.player = player;
	}
	
	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Strategy) {
			Strategy other = (Strategy) obj;
			return other.getName().equals(name);
		}

		return false;
	}

	@Override
	public int hashCode() {
		if (hashCode == null) {
			hashCode = name.hashCode();
		}

		return hashCode;
	}
	
	public HashSet<Strategy> domain(){
		return domain;
	}
	
	
	public int player(){
		return player;
	}
}
