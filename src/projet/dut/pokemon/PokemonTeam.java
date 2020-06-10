package projet.dut.pokemon;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import projet.dut.data.Moves;
import projet.dut.data.Pokedex;

public class PokemonTeam implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final ArrayList<Pokemon> team;
	private final ArrayList<Pokemon> mapDeadPkm;

	public PokemonTeam() {
		this.team = new ArrayList<Pokemon>();
		this.mapDeadPkm = new ArrayList<Pokemon>();
	}

	public void addPokemon(Pokemon pokemon) {
		team.add(pokemon);
	}

	public boolean removePkm(Pokemon pkm) {
		for (Pokemon p : team) {
			if (p.equals(pkm)) {
				mapDeadPkm.add(p);
				team.remove(p);
				return true;
			}
		}
		return false;
	}

	public boolean isEmpty() {
		return team.isEmpty();
	}

	public void randomTeam(Pokedex pkd, Moves mv, int nbPkm) {
		for (int i = 0; i < nbPkm; i++) {
			Pokemon randomPkm = pkd.randomPokemon();
			team.add(randomPkm);
			for (int j = 0; j < 4; j++) {
				randomPkm.addMove(mv.putRandomMove(randomPkm));
			}
		}
	}

	public List<Pokemon> getTeam() {
		return Collections.unmodifiableList(team);
	}

	public Pokemon findPkm(String pkm) {
		for (int i = 0; i < team.size(); i++) {
			if (team.get(i).getName().equals(pkm)) {
				return team.get(i);
			}
		}
		return null;
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		String etoile = "*";
		s.append("Votre équipe :\n");
		for (Pokemon p : team) {
			s.append(etoile + p.getName() + "| hp :" + p.getHp() + "\n");
			s.append("capacités : [");
			s.append(p.getMoves());
			s.append("]\n");
			s.append("\n");
			etoile += "*";
		}
		return s.toString();
	}
	public void swap(Pokemon pkm) {
		int index = team.indexOf(pkm);
		Collections.swap(team, 0, index);
	}
	
	public List<Pokemon> getDeadTeam() {
		return Collections.unmodifiableList(mapDeadPkm);
	}
	
	public String teamDetails() {
		StringBuilder s  = new StringBuilder();
		s.append("Team\n");
		for(Pokemon p :team) {
			s.append(p.getPokemonDetails()+"\n");
		}
		s.append("DeadTeam\n");
		for(Pokemon p :mapDeadPkm) {
			s.append(p.getPokemonDetails()+"\n");
		}
		return s.toString();
		
	}

	public ArrayList<Pokemon> pkmDamaged() {
		ArrayList<Pokemon> listDamaged = new ArrayList<>();
		for (int i = 0; i < team.size(); i++) {
			if (team.get(i).isDamaged()) {
				listDamaged.add(team.get(i));
			}
		}
		return listDamaged;
	}

	public Pokemon selectPkm() {
		while (true) {
			Scanner sc = new Scanner(System.in);
			System.out.println("Quel pokemon souhaitez-vous soigner ? \n" + pkmDamaged().toString());
			String choice = sc.nextLine();

			for (int i = 0; i < team.size(); i++) {
				if (team.get(i).getName().equals(choice) && team.get(i).isDamaged()) {
					return team.get(i);
				}
			}
			System.out.println("Ce pokémon n'est pas présent dans la liste");

		}

	}

	public Pokemon chooseDead() {
		while (true) {
			Scanner sc = new Scanner(System.in);
			System.out.println("Quel pokemon souhaitez-vous faire revivre ? \n" + mapDeadPkm.toString());
			String choice = sc.nextLine();
			for (int i = 0; i < mapDeadPkm.size(); i++) {
				if (mapDeadPkm.get(i).getName().equals(choice)) {
					return mapDeadPkm.get(i);
				}
			}
			System.out.println("Ce pokémon n'est pas présent dans la liste");
		}

	}
	public boolean isEmptyDead() {
		return mapDeadPkm.isEmpty();
	}
	 public Pokemon get(int i) {
	        if (team.isEmpty()) {
	            return null;
	        }
	        return team.get(i);        
	    }

	public boolean removePkmDead(Pokemon pkm) {
		for (Pokemon p : mapDeadPkm) {
			if (p.equals(pkm)) {
				team.add(p);
				mapDeadPkm.remove(p);
				return true;
			}
		}
		return false;
	}


}
