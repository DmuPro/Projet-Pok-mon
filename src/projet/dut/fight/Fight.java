package projet.dut.fight;

import java.io.IOException;
import java.util.Scanner;

import projet.dut.data.GameData;
import projet.dut.pokemon.Move;
import projet.dut.pokemon.Player;
import projet.dut.pokemon.Pokemon;
import projet.dut.pokemon.PokemonTeam;

public class Fight {
	private final Player one;
	private Player two;
	private boolean turnFinished = false;
	private boolean playerPlayed = false;
	private boolean player2Played = false;
	private boolean playerAttacked = false;
	private boolean player2Attacked = false;
	private String movePlayer1;
	private String movePlayer2;
	private int nbtour = 2;
	private boolean battlestate = false;
	private boolean battlestate2 = false;
	private Pokemon firstPkmPlayer;
	private Pokemon firstPkmPlayer2;

	public Fight(Player one, Player two) {
		this.one = one;
		this.two = two;
	}

	public void setOpponent(Player newPlayer) {
		this.two = newPlayer;
	}
	public void fightLeague(boolean gamestart,GameData gmdata,PokemonTeam team,League league,Player elite) throws ClassNotFoundException, IOException {
		boolean championDefeated = false;
		boolean OpponentDefeated = false;
		
		
		int countElite = 1;
		if (gamestart) {
			while (!(championDefeated)) {
				setOpponent(elite);

				if (startFight(gmdata, league, countElite, 1)) {
					OpponentDefeated = true;
				} else {
					break;
				}
				if (OpponentDefeated && countElite <= league.getNbElite()) {
					countElite += 1;
					elite = league.getOpponent(countElite);
					OpponentDefeated = false;
				}
				if (countElite == (league.getNbElite() + 1) && OpponentDefeated) {
					System.out.println("Bravo vous avez vaincu le maître de la ligue ! Vous êtes le nouveau maître !");
					championDefeated = true;
				}
			}
		}
	}

	public boolean startFight(GameData gmdata, League league, int countElite, int round)
			throws IOException, ClassNotFoundException {
		System.out.println(one.getName() + "\n" + one.getTeam());
		System.out.println("-----------------------------------------------------------");
		System.out.println(two.getName() + "\n" + two.getTeam());
		System.out.println("-----------------------------------------------------------");
		PokemonTeam team = one.getTeam();
		PokemonTeam team2 = two.getTeam();

		int nbtour = 2;

		while (true) {

			Pokemon firstPkmPlayer = one.getTeam().getTeam().get(0);
			Pokemon firstPkmPlayer2 = two.getTeam().getTeam().get(0);
			System.out.println("-----------------------------------------------------------");
			System.out.println("Round " + round);
			playerPlayed = false;
			System.out.println(" --- " + firstPkmPlayer.getName() + " hp : " + firstPkmPlayer.getHp());
			System.out.println(" --- " + firstPkmPlayer2.getName() + " hp : " + firstPkmPlayer2.getHp());

			///////////////////////////////////////////////////////////////////////////////
			// Determine quel pokémon sera le premier a jouer
			////////////////////////////////////////////////////////////////////////////////

			nbtour = 2;
			firstPkmPlayer = one.getTeam().getTeam().get(0);
			firstPkmPlayer2 = two.getTeam().getTeam().get(0);
			while (nbtour > 0) {
				turnFinished = false;
				playerAttacked = false;
				while (!(turnFinished)) {
					boolean choice = choice(one, two, round, league, countElite, gmdata, "save.txt", 1);
					nbtour -= 1;
					if (!choice) {
						return choice;
					}
					// Le bot joue
					nbtour -= 1;
				}
				if (firstPkmPlayer.getSpeed() > firstPkmPlayer2.getSpeed()) {
					battlestate2 = battleStatePlayer(1, one, two, team, team2.get(0));
					battlestate = battleStateBot(two,one, team2, team.get(0));

				} else {
					battlestate = battleStateBot(two,one, team2, team.get(0));
					battlestate2 = battleStatePlayer(1, one, two, team, team2.get(0));
				}

				/////////////////////////////////////////////////////////////////////////////////
				if (battlestate) {
					return false;
				}
				if (battlestate2) {
					return true;
				}
				System.out.println(nbtour);
			}
			// Fin While(Tour)
			round++;
			System.out.println("Tour terminé\n");

			/////////////////////////////////////////////////////////////////////////////////////
		}
	}

	public boolean startFightPlayers(GameData gmdata, GameData gmdata2, League league, int countElite, int round)
			throws IOException, ClassNotFoundException {
		System.out.println(one.getName() + "\n" + one.getTeam());
		System.out.println("-----------------------------------------------------------");
		System.out.println(two.getName() + "\n" + two.getTeam());
		System.out.println("-----------------------------------------------------------");
		PokemonTeam team = one.getTeam();
		PokemonTeam team2 = two.getTeam();

		while (true) {
			Pokemon firstPkmPlayer = one.getTeam().getTeam().get(0);
			Pokemon firstPkmPlayer2 = two.getTeam().getTeam().get(0);
			System.out.println("-----------------------------------------------------------");
			System.out.println("Round " + round);
			System.out.println(" --- " + firstPkmPlayer.getName() + " hp : " + firstPkmPlayer.getHp());
			System.out.println(" --- " + firstPkmPlayer2.getName() + " hp : " + firstPkmPlayer2.getHp());

			///////////////////////////////////////////////////////////////////////////////
			// Determine quel pokémon sera le premier a jouer
			////////////////////////////////////////////////////////////////////////////////

			nbtour = 2;
			playerPlayed = false;
			player2Played = false;
			while (nbtour > 0) {
				boolean multi = false;
				turnFinished = false;
				while ((!(turnFinished)||(!multi))) {
					/////////// Le joueur 1 joue
					if (!multi && !playerPlayed) {
						System.out.println(playerPlayed);
						boolean choice = choice(one, two, round, league, countElite, gmdata, "save1.txt", 1);
						System.out.println(playerPlayed);
						if (!(choice)) {
							return choice;
						}
					}
					else if(!multi && !player2Played) {
						////// Le joueur 2 joue
						boolean choice = choice(two, one, round, league, countElite, gmdata, "save2.txt", 2);
						if (!(choice)) {
							return choice;
						}
					}
					if (playerPlayed && player2Played) {
						multi = true;
					}
				

				}
				if (firstPkmPlayer.getSpeed() >= firstPkmPlayer2.getSpeed()) {
					battlestate2 = battleStatePlayer(1, one, two, team, team2.get(0));
					battlestate = battleStatePlayer(2, two, one, team2, team.get(0));

				} else {
					battlestate = battleStatePlayer(2, two, one, team2, team.get(0));
					battlestate2 = battleStatePlayer(1, one, two, team, team2.get(0));
				}

				/////////////////////////////////////////////////////////////////////////////////
				if (battlestate || battlestate2) {
					return true;
				}
				
				/////////////////////////////////////////////////////////////////////////////////
				// Fin While(Tour)

				/////////////////////////////////////////////////////////////////////////////////////
			}
			round++;
			System.out.println("Tour terminé\n");
		}
	}

	public boolean choice(Player one, Player two, int round, League league, int countElite, GameData gmdata,
			String path, int oneP) throws IOException {
		System.out.println("\n" + one.getName() + "\n" + one.getTeam().getTeam().get(0));
		System.out.println("Choississez une action :\n1.Attaquer\n2.Fuir\n3.Changer\n4.Sac\n5.Sauvegarder\n");
		Scanner sc = new Scanner(System.in);
		Pokemon firstPkmPlayer = one.getTeam().getTeam().get(0);
		PokemonTeam team = one.getTeam();
		Pokemon firstPkmPlayer2 = two.getTeam().getTeam().get(0);
		int choice = sc.nextInt();
		// Le joueur joue
		////////////////////////////////////////////////////////////////////////////
		// Choix action
		switch (choice) {

		//////////////////////////////////////////////////////////////////////////////
		// Choisis l'attaque
		case 1:
			Scanner scAtt = new Scanner(System.in);
			System.out.println(
					"Capacité(s) du pokémon : " + firstPkmPlayer.getMoves() + "\n Quelle attaque choisissez-vous ? ");
			String choiceAtt = scAtt.nextLine();
			nbtour -= 1;
			if (oneP == 1) {
				movePlayer1 = choiceAtt;
				playerPlayed = true;
				playerAttacked = true;
			} else if (oneP == 2) {
				movePlayer2 = choiceAtt;
				player2Attacked = true;
				player2Played = true;
			}
			turnFinished = true;
			break;

		case 2:
			//////////////////////////////////////////////////////////////////////////////
			// Choisis la fuite
			System.out.println("Vous choisissez la fuite");
			System.out.println("Victoire de " + two.getName());
			System.out.println("---------------------------------------------------------------");
			System.out.println("Fin du combat");
			
			return false;

		case 3:
			///////////////////////////////////////////////////////////////////////////////
			// Choisis de changer de pokémon
			System.out.println(firstPkmPlayer.getName() + " est rappelé");
			Scanner scPkm = new Scanner(System.in);
			System.out.println("Choisissez un autre pokémon : \n" + team.toString());
			String newPkm = scPkm.nextLine();
			if (team.findPkm(newPkm) != null) {
				team.swap(team.findPkm(newPkm));
				firstPkmPlayer = team.findPkm(newPkm);
				System.out.println(firstPkmPlayer.getName() + " est appelé");
				nbtour -= 1;
			}
			if (oneP == 1) {
				playerPlayed = true;
			} else if (oneP == 2) {
				player2Played = true;
			}
			turnFinished = true;
			break;

		case 4:
			one.showBag();
			one.usePopo();
			break;

		case 5:
			///////////////////////////////////////////////////////////////////////////////
			// Choisis de sauvegarder
			gmdata.save(round, nbtour, one, two, league, countElite, path);
			break;
		}
		return true;
	}

	public boolean battleStateBot(Player two,Player one, PokemonTeam pkmTeam, Pokemon firstPkmPlayer) {
		Pokemon firstPkmPlayer2 = pkmTeam.get(0);
		if (firstPkmPlayer2.isDead()) {
			System.out.println(firstPkmPlayer2.getName() + " n'a plus de vie");
			System.out.println("Omg mort !!");
			pkmTeam.removePkm(firstPkmPlayer2);
		}
		if (lose(one,two)||lose(two,one)) {
			return true;
		}
		if (!(pkmTeam.isEmpty()) && firstPkmPlayer2.isDead()) {
			firstPkmPlayer2 = pkmTeam.getTeam().get(0);
			System.out.println(two.getName() + " appel " + firstPkmPlayer2.getName());
			turnFinished = true;
			return false;
		} else if (pkmTeam.isEmpty()) {
			System.out.println(two.getName() + " a  perdu !\nVous avez gagné");
			System.out.println("---------------------------------------------------------------");
			System.out.println("Fin du combat");
			return true;
		}
		firstPkmPlayer2.useRandomMove(firstPkmPlayer);
		return false;
	}

	public boolean battleStatePlayer(int op, Player player, Player player2, PokemonTeam team, Pokemon firstPkmPlayer2) {
		Pokemon firstPkmPlayer = team.getTeam().get(0);
		if (firstPkmPlayer.isDead()) {
			System.out.println(firstPkmPlayer.getName() + " n'a plus de vie");
			team.removePkm(firstPkmPlayer);
		}
		if (lose(player,player2)||lose(player2,player)) {
			return true;
		}
		if (!(team.isEmpty()) && firstPkmPlayer.isDead()) {
			System.out.println(firstPkmPlayer.getName()
					+ " n'a plus de vie \nSelectionnez un autre pokémon de votre team :\n" + team.toString());
			Scanner scPkm = new Scanner(System.in);
			String nwPkm = scPkm.nextLine();

			if (team.findPkm(nwPkm) != null) {
				firstPkmPlayer = team.findPkm(nwPkm);
				System.out.println("Vous appelez " + firstPkmPlayer.getName());
				turnFinished = true;
				return false;
			}
		}
		if ((op == 1)&&(playerAttacked)) {
			firstPkmPlayer.useMove(movePlayer1, firstPkmPlayer2);
		} else if((op == 2)&&(player2Attacked)){
			firstPkmPlayer.useMove(movePlayer2, firstPkmPlayer2);
		}
 
		return false;
	}

	public boolean lose(Player one, Player two) {
		if (one.getTeam().isEmpty()) {
			System.out.println("Vous avez perdu\nVictoire de " + two.getName());
			System.out.println("---------------------------------------------------------------");
			System.out.println("Fin du combat");
			return true;
		}
		return false;
	}
	
	public boolean checkDead(PokemonTeam team) {
		Pokemon firstPkmPlayer = team.getTeam().get(0);
		if (firstPkmPlayer.isDead()) {
			System.out.println(firstPkmPlayer.getName() + " n'a plus de vie");
			team.removePkm(firstPkmPlayer);
			return true;
		}
		return false;
	}

}
