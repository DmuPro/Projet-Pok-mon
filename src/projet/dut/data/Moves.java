package projet.dut.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

import projet.dut.pokemon.Move;
import projet.dut.pokemon.Pokemon;
import projet.dut.pokemon.Type;

public class Moves {
	private final HashMap<Integer, Move> movemap;

	private Moves(HashMap<Integer, Move> movemap) {
		this.movemap = movemap;
	}

	public static Moves createMoveList() {
		HashMap<Integer, Move> movemap = new HashMap<Integer, Move>();
		String line = "";
		Move mv;
		Path path = Paths.get("moves.csv");
		int id = 0;
		try (BufferedReader br = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
			br.readLine();
			while ((line = br.readLine()) != null) {
				String[] lines = line.split(",", Integer.MAX_VALUE);
				mv = new Move(lines[1], Type.readType(lines[2]), convertStringtoNumber(lines[3]),
						convertStringtoNumber(lines[4]), convertStringtoNumber(lines[5]), lines[6]);
				movemap.put(id, mv);
				id += 1;
			}
		}

		catch (IOException e) {
			e.printStackTrace();
		}
		return new Moves(movemap);
	}

	public static int convertStringtoNumber(String str) {
		int number = 0;

		try {
			if (str != "") {
				number = Integer.parseInt(str);
			}
		} catch (NumberFormatException e) {
			return number;
		}

		return number;

	}
	
	public static Move findMove(HashMap<Integer,Move> movemap,String move) {
		for (Move m:movemap.values()) {
			if (m.getMove().equals(move)) {
				return m;
			}
		}
		return null;
	}
	

	
	public Move get(int index) {
		if (index < movemap.size()) {
		return movemap.get(index);
		}
		throw new IndexOutOfBoundsException("La capacite n'est pas present dans la liste des capacites ! ");
		
	}

	public int size() {
		return movemap.size();
	}
	
	public Move searchMove(String move) {
		return findMove(movemap,move);
	}
	
	public Move putRandomMove(Pokemon pkm) {
        Type type1 = pkm.getType()[0];
        Type type2 = pkm.getType()[1];
        HashMap<Integer, Move> movesmap = new HashMap<Integer, Move>();
        for (Move m:movemap.values()) {
            if (type1.toString().equals(m.getType()) || (m.getType().toString() == "normal")) {
                movesmap.put(movesmap.size(), m);
            }
            else if (type2 != null && type2.toString().equals(m.getType().toString()) || (m.getType().toString() == "normal")){
                movesmap.put(movesmap.size(), m);
            }
        }
        int randomValue = (int) (Math.random() * movesmap.size());
        return movesmap.get(randomValue);
    }
	
	

}
