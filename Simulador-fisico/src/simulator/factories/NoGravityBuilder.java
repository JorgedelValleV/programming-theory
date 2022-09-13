package simulator.factories;

import org.json.JSONObject;

import simulator.model.GravityLaws;
import simulator.model.NoGravity;

public class NoGravityBuilder extends Builder<GravityLaws>{
	public NoGravityBuilder() {
		super("ng", "No gravity (ng)");//type +desc
	}
	@Override
	protected GravityLaws createTheInstance(JSONObject j) {
		// TODO Auto-generated method stub
		return new NoGravity();
	}
}
