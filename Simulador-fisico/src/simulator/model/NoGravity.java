package simulator.model;

import java.util.List;

public class NoGravity implements GravityLaws{
	public NoGravity() {
		
	}

	@Override
	public void apply(List<Body> bodies) {
		// TODO Auto-generated method stub
		// Este metodo no hace nada al no haber gravedad
	}
	public String toString(){
		return "No Gravity";
	}
}
