package simulator.model;

import java.util.List;

public class FallingToCenterGravity implements GravityLaws{
	private final static double ACCELL = -9.81;
	public FallingToCenterGravity() {
		
	}

	@Override
	public void apply(List<Body> bodies) {
		// TODO Auto-generated method stub
		for(Body b : bodies) {
			b.setAcceleration(b.getPosition().direction().scale(ACCELL));//aceleracion = -9.8 * direccion
		}
	}
	public String toString(){
		return "Falling To Center";
	}
}
