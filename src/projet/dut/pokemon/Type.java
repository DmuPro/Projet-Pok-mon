package projet.dut.pokemon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import projet.dut.data.GameData;

public enum Type {
	Steel("steel"), Fighting("fighting"), Dragon("dragon"), Water("water"), Electric("electric"), Fairy("fairy"),
	Fire("fire"), Ice("ice"), Bug("bug"), Normal("normal"), Grass("grass"), Poison("poison"), Psychic("psychic"),
	Rock("rock"), Ground("ground"), Ghost("ghost"), Dark("dark"), Flying("flying");
	private final String name;
	private final ArrayList<Double> collision;
	public static HashMap<String,Type> typemap = initializeTypeMap();
	public static HashMap<Type,Integer> collisionmap = initializeCollisionMap();

	Type(String name) {
		this.name = name;
		collision = new ArrayList<Double>();
		collision.addAll(Arrays.asList(GameData.typecollision.get(name)));
	}
	

	@Override
	public String toString() {
		return name;
	}

//	public static Type readType(String type) {
//		Type[] allTypes = Type.values();
//		if ((type.equals("")) || (type.equals("null"))) {
//			return null;
//		}
//		for (Type t : allTypes) {
//			if (t.toString().equals(type)) {
//				return t;
//			}
//		}
//		System.out.println(type);
//		throw new IllegalArgumentException();
//	}


	public static HashMap<String, Type> initializeTypeMap() {
		HashMap<String,Type> typemap = new HashMap<String,Type>();
		for(Type t:Type.values()) {
			typemap.put(t.name, t);
		}
		return typemap;
	} 
	public static HashMap<Type, Integer> initializeCollisionMap() {
		HashMap<Type,Integer> collisionmap = new HashMap<Type,Integer>();
		int index = 0;   
		for(Type t:Type.values()) {
			collisionmap.put(t, index);
			index++;
		}
		return collisionmap;
	} 

	public static Type readType(String type) {
		return typemap.get(type);
	}
	public double collision(Type type) {
		if (type !=null) {
		return collision.get(collisionmap.get(type));
		}
		return 1;
	}

}
