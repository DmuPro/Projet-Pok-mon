package projet.dut.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import projet.dut.pokemon.Pokemon;
import projet.dut.pokemon.Type;

public class Pokedex {
	private final HashMap<Integer, Pokemon> pokedex;

	private Pokedex(HashMap<Integer, Pokemon> pokedex) {
		this.pokedex = pokedex;
	}

	public static Pokedex createPokedex() throws IOException {
		HashMap<Integer, Pokemon> pokedex = new HashMap<Integer, Pokemon>();
		String line = "";
		Pokemon pok;
		Path path = Paths.get("pokedex(1).csv");
		int id = 0;
		try (BufferedReader br = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
			br.readLine();

			while ((line = br.readLine()) != null) {
				String[] lines = line.split(",");
				if (lines.length == 6) {
					pok = new Pokemon(lines[1], lines[2], Integer.parseInt(lines[3]), Integer.parseInt(lines[4]),
							Type.readType(lines[5]));
				} else {
					pok = new Pokemon(lines[1], lines[2], Integer.parseInt(lines[3]), Integer.parseInt(lines[4]),
							Type.readType(lines[5]), Type.readType(lines[6]));
				}
				pokedex.put(id, pok);
				id += 1;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return new Pokedex(pokedex);
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		for (Pokemon p : pokedex.values()) {
			s.append(p.toString() + "\n");
		}
		return s.toString();
	}

	public Map<Integer, Pokemon> getPokedex() {
		return Collections.unmodifiableMap(pokedex);
	}

	public Pokemon get(int index) {
		if (index < pokedex.size()) {
			return pokedex.get(index);
		}
		throw new IndexOutOfBoundsException("Le pokémon n'est pas présent dans le pokédex ! ");
	}

	public Pokemon findPokemon(String pokemon) {
		for (Pokemon p : pokedex.values()) {
			if (p.getName().equals(pokemon)) {
				return p;
			}
		}
		System.out.println(pokemon);
		return null;
	}

	public Pokemon randomPokemon() {
		int random = (int) (Math.random() * pokedex.size());
		return pokedex.get(random);
	}

	public void setPokStats() {
		String lines = "";
		int index = 0;
		Path p = Paths.get("newstats.txt");
		try (BufferedReader br = Files.newBufferedReader(p, StandardCharsets.UTF_8)) {
			while ((lines = br.readLine()) != null) {
				String line[] = lines.split(",", Integer.MAX_VALUE);
				pokedex.get(index).setStats(Moves.convertStringtoNumber(line[5]), Moves.convertStringtoNumber(line[6]),
						Moves.convertStringtoNumber(line[7]), Moves.convertStringtoNumber(line[8]),
						Moves.convertStringtoNumber(line[9]), Moves.convertStringtoNumber(line[10]));
				;
				index++;

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
