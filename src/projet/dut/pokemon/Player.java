package projet.dut.pokemon;

import java.io.Serializable;
import java.util.Objects;
import java.util.Scanner;

import projet.dut.bag.Bag;
import projet.dut.bag.Revive;

public class Player implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String name;
	private final PokemonTeam team;
	private final Bag bag;

	public Player(String name, PokemonTeam team) {
		if (name == null) {
			Scanner sc = new Scanner(System.in);
			System.out.println("Entrez votre pseudo :");
			String choiceName = sc.nextLine();
			this.name = choiceName;

		} else {
			this.name = name;
		}
		this.team = team;
		Bag baggy = new Bag();
		baggy.addRandomStuff();
		this.bag = baggy;
	}

	public String getName() {
		return name;
	}

	public PokemonTeam getTeam() {
		return team;
	}

	@Override
	public String toString() {
		return name + "  : " + team;
	}
	
	public String getPlayerDetails() {
		return new String(name+"\n"+team.teamDetails());
	}

	public void showBag() {

	}

	public void usePopo() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Quel element de votre sac souhaitez-vous uitliser : " + bag.toString());
		String choice = sc.nextLine();
		if (bag.getPotion() == 0 && choice.toLowerCase().equals("potion")) {
			System.out.println("Cette action n'est pas possible : aucun potion disponible");
			return;
		}
		if (bag.getRevive() == 0 && choice.toLowerCase().equals("revive")) {
			System.out.println("Cette action n'est pas possible : aucun rappel disponible");
			return;
		}
		if (team.pkmDamaged().isEmpty() && choice.toLowerCase().equals("potion")) {
			System.out.println("Cette action n'est pas possible : aucun de vos pokémon n'est blessé");
			return;
		}
		if (choice.toLowerCase().equals("potion") && !(team.pkmDamaged().isEmpty())) {
			Pokemon pkm = team.selectPkm();
			int i = pkm.pvAfterHeal(20);
			pkm.setHp(i);
			bag.remove("potion");
			System.out.println("Une potion a ete utilisé sur " + pkm.getName());
			System.out.println("hp : " + pkm.getHp());
			return;
		}
		if (team.isEmptyDead()) {
			System.out.println("Cette action n'est pas possible : aucun de vos pokémon n'est mort");
			return;
		}

		if (choice.toLowerCase().equals("revive") && !(team.isEmptyDead())) {
			Pokemon pkmRevive = team.chooseDead();
			pkmRevive.setHp(bag.getRevivePv(new Revive()));
			pkmRevive.setHp(pkmRevive.getHpBase() / 2);
			team.removePkmDead(pkmRevive);
			bag.remove("revive");
			System.out.println(pkmRevive.getName() + " a ete ramener a la vie");
			System.out.println("hp : " + pkmRevive.getHp());
			return;
		}

	}

}
