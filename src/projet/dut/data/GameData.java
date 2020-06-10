package projet.dut.data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import projet.dut.fight.League;
import projet.dut.pokemon.Player;
import projet.dut.pokemon.Pokemon;
import projet.dut.pokemon.PokemonTeam;

public class GameData {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final PokemonTeam pkmteam;
	private final Pokedex pkd;
	private final Moves mv;
	public static HashMap<String, Double[]> typecollision = initializeCollision();
	private Player one;
	private Player two;
	private League league;
	private int round;

	public GameData() throws IOException {
		pkmteam = new PokemonTeam();
		new PokemonTeam();
		mv = Moves.createMoveList();
		pkd = Pokedex.createPokedex();
		pkd.setPokStats();

	}

	public Pokedex getPkd() {
		return pkd;
	}

	public Moves getMv() {
		return mv;
	}

	public void save(int round, int nbtour, Player one, Player two, League league, int countElite,String path)
			throws IOException {
		Path p = Paths.get(path);
		//Sauvegarde les Objets en utilisant les streams
		try (OutputStream back = Files.newOutputStream(p); ObjectOutputStream out = new ObjectOutputStream(back)) {
			out.writeObject(one);
			out.writeObject(two);
			out.writeObject(round);
			out.writeObject(league);
			out.writeObject(countElite);
			out.flush();
		}
//			bw.write("TeamPokemon1\n");
//			write(bw,pkmteam.getTeam());
//			bw.write("DeadTeamPokemon1\n");
//			write(bw,pkmteam.getDeadTeam());
//			bw.write("TeamPokemon2\n");
//			write(bw,secondpkmteam.getTeam());
//			bw.write("DeadTeamPokemon2\n");
//			write(bw,secondpkmteam.getDeadTeam());
//			bw.write("PokemonPlaying\n"+pokemonPlaying.getPokemonDetails()+"\n");
//			bw.write("Round\n"+round+"\n");
//			bw.write("PokemonJoueur\n"+firstPkmPlayer.getPokemonDetails() + "\n");
//			bw.write("PokemonJoueur2\n"+firstPkmPlayer2.getPokemonDetails() + "\n");
//			bw.write(league.getLeagueDetails());

	}



//	public GameData loadData() throws IOException {
//		String lines = "";
//		GameData gmdata = new GameData();
//		Path p = Paths.get("save.txt");
//		try (BufferedReader br = Files.newBufferedReader(p, StandardCharsets.UTF_8)) {
//			while ((lines = br.readLine()) != null) {
//				String[] line = lines.split(",", Integer.MAX_VALUE);
//				if (line.length == 1) {
//
//				}
//				if (line.length != 1) {
//					Pokemon pkm = new Pokemon(line[0], line[1], Moves.convertStringtoNumber(line[2]),
//							Moves.convertStringtoNumber(line[3]), Type.readType(line[15]), Type.readType(line[16]));
//					gmdata.add(pkm);
//				}
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return gmdata;
//	}
	public GameData loadData(String path) throws ClassNotFoundException, IOException {
		GameData gmdata = new GameData();
		Path p = Paths.get(path);
		try (InputStream back = Files.newInputStream(p); ObjectInputStream in = new ObjectInputStream(back)) {
			//Récupération des objets
			one = (Player) in.readObject();
			two = (Player) in.readObject();
			round = (int) in.readObject(); 
			league = (League) in.readObject();
		}
		return gmdata;
	}

	public Player getPlayer1() {
		return one;
	}
	
	//Initialisation de la map contenant le couple nomduType,tableau des collisions des types
	public static HashMap<String, Double[]> initializeCollision() {
		HashMap<String, Double[]> typecollision = new HashMap<String, Double[]>();
		Path p = Paths.get("grid_types(1).csv");
		String lines = "";
		Double[] db = new Double[18];
		int compteur = 0;
		try (BufferedReader br = Files.newBufferedReader(p, StandardCharsets.UTF_8)) {
			while (compteur < 18) {
				lines = br.readLine();
				String[] line = lines.split(",", Integer.MAX_VALUE);
				if (!(line[0].equals("Type1"))) {
					for (int i = 2; i < line.length; i++) {
						db[i - 2] = Double.parseDouble(line[i]);
					}
					typecollision.put(line[0], db.clone());
					compteur++;

				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return typecollision;
	}

//	public void formatFile() {
//		Path p = Paths.get("a.txt");
//		Path p2 = Paths.get("newstats.txt");
//		String lines = "";
//		int compteur = 0;
//		try (BufferedReader br = Files.newBufferedReader(p, StandardCharsets.UTF_8);
//				BufferedWriter bw = Files.newBufferedWriter(p2, StandardCharsets.UTF_8);) {
//			while ((lines = br.readLine()) != null) {
//				String line[] = lines.split(",", Integer.MAX_VALUE);
//				if (!(line[0].equals("#"))) {
//					int index = Moves.convertStringtoNumber(line[0]);
//					if ((index != compteur)) {
//						System.out.println(index);
//					}
//					compteur++;
//				}
//
//				System.out.println(lines);
//				if (!(lines.equals("") || ((lines.contains("Mega"))))) {
//					bw.write(lines + "\n");
//				}
//			}
//
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}

	public PokemonTeam initializePokeTeam() {
		int compteurPkm = 0;
		int compteurMv = 0;
		int key = 0;
		while (compteurPkm < 6) {
			Scanner scPkm = new Scanner(System.in);
			System.out.println("Veuillez choisir un pokemon :");
			String choicePkm = scPkm.nextLine();
			if (choicePkm.equals("stop")) {
				break;
			}
			Pokemon pkm = pkd.findPokemon(choicePkm);
			if (pkm != null) {
				pkmteam.addPokemon(pkm);
				compteurPkm++;

				while (compteurMv < 2) {
					Scanner scMv = new Scanner(System.in);
					System.out.println("Veuillez choisir une capacite :");
					String choiceMv = scMv.nextLine();
					if (pkm.addMove(mv.searchMove(choiceMv))) {
						System.out.println("La capacite a ete ajoutee\n");
						compteurMv++;
					}
				}
				System.out.println(choicePkm + " a ete ajoute a votre equipe\n");
				key++;
			}
			compteurMv = 0;
		}
		return pkmteam;

	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public PokemonTeam getPkmteam() {
		return pkmteam;
	}

	public static HashMap<String, Double[]> getTypecollision() {
		return typecollision;
	}

	public Player getOne() {
		return one;
	}

	public Player getTwo() {
		return two;
	}

	public League getLeague() {
		return league;
	}

	public int getRound() {
		return round;
	}

}
