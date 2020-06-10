package projet.dut.main;

import java.io.IOException;
import java.util.Scanner;

import projet.dut.data.GameData;
import projet.dut.data.Moves;
import projet.dut.data.Pokedex;
import projet.dut.fight.Fight;
import projet.dut.fight.League;
import projet.dut.pokemon.Player;
import projet.dut.pokemon.Pokemon;
import projet.dut.pokemon.PokemonTeam;

public class Main {
	public static void main(String[] args) throws IOException, ClassNotFoundException {
////////////////////////////////////////////////////////////////////////
		League league = new League();
		PokemonTeam team = new PokemonTeam();
		Player player = new Player(null, team);
		boolean gamestart = false;
////////////////////////////////////////////////////////////////////////

		GameData gmdata = new GameData();

		System.out.println("Veuillez choisir un mode :\n 1 : Pokédex \n 2 : Ligue \n 3 : Multijoueur \n 4 : Charger");
		Scanner sc = new Scanner(System.in);
		int choiceMode = sc.nextInt();
		switch (choiceMode) {
		case 1:
			System.out.println(gmdata.getPkd());
		case 2:
			gamestart = true;
			league = new League();
			System.out.println("Veuillez choisir une ligue :\n 1 : Ligue par défaut \n 2 : ligue Made in DUT2\n");
			int choiceLeague = sc.nextInt();
			switch (choiceLeague) {
			case 1:
				league.createLeague(gmdata.getPkd(), gmdata.getMv(), choiceLeague);
			case 2:
				league.createLeague(gmdata.getPkd(), gmdata.getMv(), choiceLeague);

			}
			team = gmdata.initializePokeTeam();
			Player elite = new Player("", team);
			player = new Player(player.getName(), team);
			elite = league.getOpponent(1);
			Fight fight = new Fight(player, elite);
			fight.fightLeague(gamestart, gmdata, team, league, elite);
			break;
		case 4:
			gamestart = true;
			gmdata.loadData("save.txt");
			league = gmdata.getLeague();
			player = gmdata.getPlayer1();
			System.out.println(player);
			break;
		case 3:
			System.out.println("--------------------Joueur 1 --------------------\n");
			PokemonTeam team1 = new PokemonTeam();
			System.out.println("Créez votre team ---");
			team1 = gmdata.initializePokeTeam();
			Player player1 = new Player(null, team1);

			System.out.println("--------------------Joueur 2 --------------------\n");
			GameData gmdata2 = new GameData();
			PokemonTeam team2 = new PokemonTeam();
			System.out.println("Créez votre team ---");
			team2 = gmdata2.initializePokeTeam();
			Player player2 = new Player(null, team2);

			Fight f = new Fight(player1, player2);
			f.startFightPlayers(gmdata, gmdata2, league, 0, 1);

			break;

		}
	}
}
