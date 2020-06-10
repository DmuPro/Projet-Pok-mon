package projet.dut.pokemon;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Objects;

import projet.dut.data.Moves;

public class Pokemon implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String name;
	private final Type type1;
	private final Type type2;
	private final String linkImage;
	private final int height;
	private final int weight;
	private int atk;
	private int defense;
	private int hp;
	private int lvl = 100;
	private int atkspe;
	private int defspe;
	private int speed;
	private String nickname;
	private int xp;
	private final HashMap<Integer, Move> movset;
	private int indexmove;
	private int hpBase;

	public Pokemon(String name, String linkImage, int height, int weight, Type type1, Type type2) {
		this.name = name;
		this.height = height;
		this.weight = weight;
		this.type1 = type1;
		this.type2 = type2;
		this.linkImage = linkImage;
		xp = 0;
		movset = new HashMap<Integer, Move>();
	}

	public Pokemon(String name, String linkImage, int height, int weight, Type type1) {
		this(name, linkImage, height, weight, type1, null);
	}

	private Pokemon(Pokemon copyPokemon) {
		name = copyPokemon.name;
		height = copyPokemon.height;
		weight = copyPokemon.weight;
		type1 = copyPokemon.type1;
		type2 = copyPokemon.type2;
		linkImage = copyPokemon.linkImage;
		xp = 0;
		movset = new HashMap<Integer, Move>();
		hpBase = copyPokemon.hpBase;
		setStats(copyPokemon.hp, copyPokemon.atk, copyPokemon.defense, copyPokemon.atkspe, copyPokemon.defspe,
				copyPokemon.speed);
	}

	public static Pokemon createCopyPokemon(Pokemon pok) {
		return new Pokemon(pok);
	}

	public int getSpeed() {
		return speed;
	}

	public String getName() {
		return name;
	}

	public int getLvl() {
		return lvl;
	}

	public int getDef() {
		return defense;
	}

	public int getHp() {
		return hp;
	}

	public boolean isDead() {
		return hp <= 0;
	}

	public String getMoves() {
		int compteur = movset.size();
		StringBuilder s = new StringBuilder();
		for (Move m : movset.values()) {
			if (compteur == 1) {
				s.append(m.getMove() + " [PP :" + m.getPP()+"]");
				break;
			}
			s.append(m.getMove() + " [PP :" + m.getPP()+"]" + ", ");
			compteur--;

		}
		return s.toString();
	}

	@Override
	public String toString() {
		if (type2 != null) {
			return name + " " + "[" + type1.toString() + ", " + type2.toString() + "] " + height + "m " + weight + "kg";
		}
		return name + " " + "[" + type1.toString() + "] " + height + "m " + weight + "kg";
	}

	public void setNickName(String newname) {
		nickname = newname;
	}

	private int takeDamage(Move m, Pokemon pkm) {
		int damage = (int) (((((lvl * 0.4 + 2) * atk * m.getDamage()) / (pkm.getDef() * 50)) + 2)*(m.getType().collision(type1)*m.getType().collision(type2)));
		return hp -= damage;
	}

	public int useMove(String move, Pokemon pkm) {
		Move m = Moves.findMove(movset, move);
		if (m == null) {
			throw new IllegalArgumentException();
		}
		m.useMove();
		System.out.println(name + " lance " + move);
		
		return pkm.takeDamage(m, pkm);
	}

	public int useRandomMove(Pokemon pkm) {
		int random = (int) (Math.random() * movset.size());
		return useMove(movset.get(random).getMove(), pkm);

	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Pokemon)) {
			return false;
		}
		Pokemon pkm = (Pokemon) o;
		return pkm.name.equals(name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

	public boolean addMove(Move move) {
		if (move == null) {
			return false;
		}
		Move m = Move.createCopyMove(move);
		if ((movset.size() < 4) && (!(movset.containsValue(m)))
				&& ((type1.toString() == m.getType().toString()) || (m.getType().toString() == "normal"))) {
			movset.put(indexmove, m);
			indexmove++;
		} else if ((movset.size() < 4) && (!(movset.containsValue(m))) && (type2 != null)
				&& ((type1.toString() == m.getType().toString()) || (type2.toString() == m.getType().toString())
						|| (m.getType().toString() == "normal"))) {
			movset.put(indexmove, m);
			indexmove++;
		} else {
			System.out
					.println("La capacité choisie n'est pas du bon type ou alors vous avez déjà ajouté cette capacité");
			return false;
		}
		return true;
	}

	public String getPokemonDetails() {
		return name + "," + linkImage + "," + height + "," + weight + "," + atk + "," + defense + "," + hp + "," + lvl
				+ "," + atkspe + "," + defspe + "," + speed + "," + nickname + "," + xp + "," + movset + "," + indexmove
				+ "," + type1 + "," + type2;

	}

	public Type[] getType() {
		Type[] types;
		types = new Type[2];
		types[0] = type1;
		types[1] = type2;
		return types;

	}

	public int countMove() {
		int compteur = 0;
		for (Move m : movset.values()) {
			compteur++;
		}
		return compteur;
	}

	public void setStats(int hp, int atk, int def, int atkspe, int defspe, int speed) {
		this.hp = hp;
		this.atk = atk;
		this.defense = def;
		this.defspe = defspe;
		this.atkspe = atkspe;
		this.speed = speed;
		hpBase = hp;

	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public void setLvl(int lvl) {
		this.lvl = lvl;
	}

	public void setDefspe(int defspe) {
		this.defspe = defspe;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public void setAtk(int atk) {
		this.atk = atk;
	}

	public void setDefense(int defense) {
		this.defense = defense;
	}

	public void setAtkspe(int atkspe) {
		this.atkspe = atkspe;
	}

	public int pvAfterHeal(int i) {
		if ((i+hp) >= hpBase){
			return hpBase;
		}
		return i+hp;
	}
	public boolean isDamaged() {
		return hpBase > hp;
	}

	public int getHpBase() {
		return hpBase;
	}


}
