package projet.dut.bag;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Bag implements Serializable{
	private final Map<HealthCare, Integer> bag;
	private final Potion potion;
	private final Revive revive;
	private static final long serialVersionUID = 1L;


	public Bag() {
		this.bag = new HashMap<>();
		this.potion = new Potion();
		this.revive = new Revive();
	}

	public void addRandomStuff() {
		bag.put(potion, 5);
		bag.put(revive, 2);
	}

	public int getRevivePv(HealthCare hc) {
		return hc.getRestorePv();
	}
	public int getPotion() {
		return bag.get(potion);
	}
	public int getRevive() {
		return bag.get(revive);
	}
	public void remove(String hc) {

		if (hc.toLowerCase() == "potion") {
			int nb = bag.get(potion) - 1;
			setValue(potion, nb);
		}

		if (hc.toLowerCase() == "revive") {
			int nb = bag.get(revive) - 1;
			setValue(revive, nb);
		}

	}

	public void setValue(HealthCare hc, int i) {
		for (Map.Entry<HealthCare, Integer> entry : bag.entrySet()) {
			if (entry.getKey() == hc) {
				entry.setValue(i);
			}
		}

	}

	@Override
	public String toString() {
		String s = "Liste des objets :\n";
		for (Map.Entry<HealthCare, Integer> entry : bag.entrySet()) {
			s += entry.getKey().toString() + " x" + entry.getValue() + "\n";
		}
		return s;
	}
}
