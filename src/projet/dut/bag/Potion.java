package projet.dut.bag;

import java.io.Serializable;

public class Potion extends HealthCare implements Serializable{
	private final static String name ="Potion";
	private static final long serialVersionUID = 1L;
	
	public Potion() {
		super(name,20);
	}
}
