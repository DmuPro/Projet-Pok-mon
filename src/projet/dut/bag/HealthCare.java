package projet.dut.bag;

import java.io.Serializable;

public class HealthCare implements Serializable{
	private final String name;
	protected int restorePv;
	private static final long serialVersionUID = 1L;
	
	public HealthCare(String name,int restorePv) {
		this.name = name;
		this.restorePv = restorePv;
		
	}
	public int getRestorePv() {
		return restorePv;
	}
	@Override
	public String toString() {
		return name;
	}
}
