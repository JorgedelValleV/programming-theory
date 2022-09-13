package simulator.factories;

import org.json.JSONObject;

import simulator.model.GravityLaws;
import simulator.model.NewtonUniversalGravitation;

public class NewtonUniversalGravitationBuilder extends Builder<GravityLaws>{
	public NewtonUniversalGravitationBuilder() {
		super("nlug", "Newton's law of universal gravitation (nlug)");
	}
	@Override
	protected GravityLaws createTheInstance(JSONObject j) {
		// TODO Auto-generated method stub
		return new NewtonUniversalGravitation();
	}
}
