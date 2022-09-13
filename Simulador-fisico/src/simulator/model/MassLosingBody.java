package simulator.model;

import simulator.misc.Vector;

public class MassLosingBody extends Body{
	private double lossFactor;
	private double lossFrequency;
	private double cont = 0.0;
	public MassLosingBody(String s, Vector u, Vector v, Vector w, double m, double d, double f){
		super(s, u, v, w, m);
		lossFactor = d;
		lossFrequency = f;
	}
	void move(double t){
		super.move(t);
		cont+=t;
		if(cont>=lossFrequency) {
			mass = (getMass()*(1-lossFactor));
			cont=0.0;
		}
	}
}
