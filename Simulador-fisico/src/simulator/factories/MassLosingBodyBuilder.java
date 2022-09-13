package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Vector;
import simulator.model.Body;
import simulator.model.MassLosingBody;

public class MassLosingBodyBuilder extends Builder<Body>{
	public MassLosingBodyBuilder(){
		super("mlb","body that loses mass");
	}
	@Override
	protected Body createTheInstance(JSONObject data) throws IllegalArgumentException{//recibe data del json creo que no recibe todo, sino faltaria JSONObject j = data.getJSONObject("data");
		// TODO Auto-generated method stub
		try {
			double m = data.getDouble("mass");
			if(m<0)
				throw new IllegalArgumentException("Masa negativa");
			String s = data.getString("id");
			double fac = data.getDouble("factor");
			if(fac<0 || fac>1)
				throw new IllegalArgumentException("Factor de perdida de masa invalida");
			double fre = data.getDouble("freq");
			if(fre<=0)
				throw new IllegalArgumentException("Frecuencia invalida");
			
			JSONArray jp = data.getJSONArray("pos");
			JSONArray jv = data.getJSONArray("vel");
			double[] p={jp.getDouble(0),jp.getDouble(1)};
			Vector pos = new Vector (p);
			double[] v={jv.getDouble(0),jv.getDouble(1)};
			Vector vel = new Vector (v);
			Vector acc = new Vector(2);
			return new MassLosingBody(s, pos, vel, acc, m, fac, fre);
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
		jodata.put("freq", 0.0);
		jodata.put("factor", 0.0);
		jo.put("type", typeTag);
		jo.put("data", jodata);
		return jo;
	}
}
