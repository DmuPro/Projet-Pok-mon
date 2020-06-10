package projet.dut.pokemon;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Objects;

public class Move implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String name;
	private final Type type;
	private final int power;
	private int pp;
	private final int accuracy;
	private enum damage_type {
		statut,physical,special
	}
	private final damage_type dt;

	public Move(String name, Type type, int power, int pp, int accuracy, String damagetype) {
		this.name = name;
		this.type = type;
		this.power = power;
		this.pp = pp;
		this.accuracy = accuracy;
		this.dt = translateStringtoEnum(damagetype);
		
	};
	
	private Move(Move move) {
		name = move.name;
		type = move.type;
		power = move.power;
		pp = move.pp;
		accuracy = move.accuracy;
		dt = move.dt;

	}

	public static Move createCopyMove(Move move) {
		return new Move(move);
	}

	private damage_type translateStringtoEnum(String damagetype) {
		damage_type[] allTypes = damage_type.values();
		for (damage_type t : allTypes) {
			if (t.toString().equals(damagetype)) {
				return t;
			}
		}
		throw new IllegalArgumentException();
	}
	
	public int useMove() {
		if ((pp-1) >= 0) {
		return pp-=1;
		}
		throw new ArithmeticException();
	}
	
	public String getMove() {
		return name;    
	}
	
	public int getDamage() {
		return power;
	}
	
	public Type getType() {
		return type;
	}
	@Override
	public String toString() {
		return "Move [name=" + name + "; type=" + type + "; power=" + power + "; pp=" + pp + "; accuracy=" + accuracy
				+ "; dt=" + dt + "]";
	}
	public int getPP() {
		return pp;
	}
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Move)) {
			return false;
		}
		Move mv = (Move)o;
		return mv.name.equals(name);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(name);
	}
	
	public String getName() {
		return name;
	}

}
