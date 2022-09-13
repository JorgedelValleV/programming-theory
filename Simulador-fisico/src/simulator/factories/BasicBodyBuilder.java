package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Vector;
import simulator.model.Body;

public class BasicBodyBuilder extends Builder<Body>{
	public BasicBodyBuilder() {
		super("basic","basic body");
	}
	@Override
	protected Body createTheInstance(JSONObject j) throws IllegalArgumentException{
		// TODO Auto-generated method stub
		try {
			double m = j.getDouble("mass");
			if(m<0)
				throw new IllegalArgumentException("Masa negativa");
			String s = j.getString("id");
			JSONArray jp = j.getJSONArray("pos");
			JSONArray jv = j.getJSONArray("vel");
			double[] p = this.jsonArrayTodoubleArray(jp);
			Vector pos = new Vector (p);
			double[] v = this.jsonArrayTodoubleArray(jv);
			Vector vel = new Vector (v);
			Vector acc = new Vector(2);
			return new Body(s, pos, vel, acc, m);
		}catch(NumberFormatException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}
	protected JSONObject createData(){
		JSONObject jo = new JSONObject();
		JSONObject jodata = new JSONObject();
		double[] d= {0.0, 0.0};
		jodata.put("id", "");
		jodata.put("pos", d);
		jodata.put("vel", d);
		jodata.put("mass", 0.0);
		jo.put("type", typeTag);
		jo.put("data", jodata);
		return jo;
	}
}
