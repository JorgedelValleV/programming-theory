package simulator.factories;

import java.util.List;

import org.json.JSONObject;

public interface Factory<T> {
	//Recorre los builders 
	//A createtheInstance (en clase Builder) solo le pasamos el data. A esa se le llama desde createInstance de la clase Builder(bb.createInstance(())
	public T createInstance(JSONObject info);
	
	//Rellenar getInfo que se llama desde parseGravityLaws
	//Devuelve el arraylist(copia de solo lectura) de todas las posibles leyes
	public List<JSONObject> getInfo();
	
}
