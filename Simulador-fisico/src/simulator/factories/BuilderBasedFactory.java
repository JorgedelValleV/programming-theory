package simulator.factories;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONObject;

public class BuilderBasedFactory<T> implements Factory<T>{
	private List<Builder<T>> builders;
	private List<JSONObject> arrayJSON;
	
	public BuilderBasedFactory(List<Builder<T>> builders) {
		this.builders = builders;
		arrayJSON = new ArrayList<JSONObject>();
		for(int i = 0;i<builders.size();++i) {
			arrayJSON.add(i, builders.get(i).getBuilderInfo());
		}
	}
	@Override
	public T createInstance(JSONObject info) throws IllegalArgumentException{
		//Si info es incorrecto lanzar excepcion.
		// TODO Auto-generated method stub
		T dev = null;
		for(int i = 0;i<builders.size() && dev==null;++i) {
			dev =builders.get(i).createInstance(info);
		}
		if(dev==null)
			throw new IllegalArgumentException("No se corresponde con ningún tipo");
		return dev;
	}

	@Override
	public List<JSONObject> getInfo() {
		// TODO Auto-generated method stub
		List<JSONObject> soloLectura = Collections.unmodifiableList(arrayJSON);
		return soloLectura;
	}

}
