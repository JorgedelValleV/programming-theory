package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;

public abstract class Builder<T> {
	protected String typeTag;
	protected String desc;
	public Builder(String t, String d){
		typeTag=t;
		desc = d;
	}
	protected double[] jsonArrayTodoubleArray(JSONArray j){
		double [] d = new double[j.length()];
		for (int i = 0; i < j.length(); i++) {
			d[i] = j.getDouble(i); 
		}
		return d;
	}
	
	public T createInstance(JSONObject info) throws IllegalArgumentException{
		if(info.get("type").equals(typeTag)) {
			return createTheInstance(info.getJSONObject("data"));
		}
		else return null;
	}
	public JSONObject getBuilderInfo(){

		JSONObject j = createData();
		j.put("desc", desc);
		return j;
	}
	protected JSONObject createData(){
		JSONObject jo = new JSONObject();
		JSONObject jovacio = new JSONObject();
		jo.put("type", typeTag);
		jo.put("data", jovacio);
		return jo;
	}
	
	protected abstract T createTheInstance(JSONObject j);
}
