package simulator.model;

import java.util.List;

import simulator.misc.Vector;

public class NewtonUniversalGravitation implements GravityLaws{
	private final static double G = -6.67e-11;
	public NewtonUniversalGravitation(){
		
	}
	@Override
	public void apply(List<Body> bodies) {
		
		for(int i = 0; i<bodies.size();++i) {
			Vector a = new Vector(2);
			if( bodies.get(i).getMass()== 0.0) {
				Vector aux = new Vector(2);
				bodies.get(i).setAcceleration(aux);
				bodies.get(i).setVelocity(aux);
			}
			else {
				for(int j = 0;j<bodies.size();++j) {
					if(j!=i) {
						double r = bodies.get(i).getPosition().distanceTo(bodies.get(j).getPosition());
						if(r!=0) {
							Vector v = bodies.get(i).getPosition().minus(bodies.get(j).getPosition()).direction();
							double f = (G *  bodies.get(i).getMass() * bodies.get(j).getMass())/(r*r);
							Vector fuerza = v.scale(f);
							a = a.plus(fuerza.scale(1/(bodies.get(i).getMass())));
						}
					}
				}
				
				
				bodies.get(i).setAcceleration(a);
			}
		}
	}
	public String toString(){
		return "Newton's Universal Gravitation";
	}
}
