package projet.dut.fight;

import java.io.Serializable;

import projet.dut.data.Moves;
import projet.dut.data.Pokedex;
import projet.dut.pokemon.Player;
import projet.dut.pokemon.PokemonTeam;

public class League implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nameLeague;
	private Player[] eliteCouncil;
	private Player champion;
	private int nbElite;
	private int elitedefeated = 0;

	public League() {
		nameLeague = null;
		nbElite = 4;
		eliteCouncil = new Player[nbElite];
		champion = null;
	}
	

	public int getNbElite() {
		return nbElite;
	}

	public void setTab(int k) {
		this.eliteCouncil = new Player[k];
	}

	public void createLeague(Pokedex pkd, Moves mv, int numLg) {
		int numPkmElite = 5;
		int numPkmMaster = 6;
		if (numLg == 1) {
			this.nameLeague = "DefaultLeague";

		} else if (numLg == 2) {
			this.nameLeague = "League Made in DUT 2";
			setTab(8);
			this.nbElite = 8;
			numPkmElite = 6;
		}
		for (int i = 0; i < nbElite; i++) {
			PokemonTeam team = new PokemonTeam();
			Player elite = new Player("Elite " + (i + 1), team);
			team.randomTeam(pkd, mv, numPkmElite);
			this.eliteCouncil[i] = elite;
		}
		PokemonTeam team = new PokemonTeam();
		team.randomTeam(pkd, mv, numPkmMaster);
		this.champion = new Player("Champion", team);
	}

	public Player getOpponent(int nb) {
		if (nb == nbElite + 1) {
			return champion;
		}
		elitedefeated += 1;
		return eliteCouncil[nb - 1];
	}

	public Player getChampion() {
		return champion;
	}

	@Override
	public String toString() {
		String s = nameLeague + " : \n ";
		s += "EliteFour :\n ";
		for (Player p : eliteCouncil) {
			s += "- " + p.toString();
		}
		s += champion.toString();
		return s;
	}
	
	public String getLeagueDetails() {
		StringBuilder s = new StringBuilder();
		s.append(nameLeague+"\n");
		for (Player p:eliteCouncil) {
			s.append(p.getPlayerDetails());
		}
		s.append(champion.getPlayerDetails());
		return s.toString();
	}


	public int getElitedefeated() {
		return elitedefeated;
	}
	
	
}
