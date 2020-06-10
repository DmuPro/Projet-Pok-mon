package projet.dut.bag;

import java.io.Serializable;

public class Revive extends HealthCare implements Serializable{
	private final static String name ="Revive";
	private static final long serialVersionUID = 1L;
	public Revive() {
		super(name,0);
	}
}
