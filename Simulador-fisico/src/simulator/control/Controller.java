package simulator.control;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import simulator.factories.Factory;
import simulator.model.Body;
import simulator.model.GravityLaws;
import simulator.model.PhysicsSimulator;
import simulator.model.SimulatorObserver;

public class Controller {
	private PhysicsSimulator ps;
	private Factory<Body> fb;
	private Factory<GravityLaws> fgl;
	public Controller(PhysicsSimulator ps, Factory<Body> fb,Factory<GravityLaws> fgl){
		this.ps=ps;
		this.fb=fb;
		this.fgl=fgl;
	}

	public void run(int n, OutputStream out) {
		PrintStream p=(out==null)? null : new PrintStream (out);
		StringBuilder sb=new StringBuilder();
		sb.append("{\n\"states\": [\n");
		for(int i = 0;i<n;++i) {
			sb.append(ps).append(" },\n");
			ps.advance();
		}
		sb.append(ps).append(" }");
		sb.append("\n]\n}");
		p.print(sb.toString());
		p.close();
	}
	public void loadBodies(InputStream in) throws IllegalArgumentException{
		JSONObject jsonInupt = new JSONObject(new JSONTokener(in)); 
		JSONArray ja = jsonInupt.getJSONArray("bodies");
		for (int i = 0; i < ja.length(); i++) {
			ps.addBody(fb.createInstance(ja.getJSONObject(i)));
		}
	}
	public void reset() {
//		invoca al método reset del simulador.
		ps.reset();
	}
	public void setDeltaTime(double dt) {
//		invoca al método setDeltaTime del simulador.
		ps.setDeltaTime(dt);
	}
	public void addObserver(SimulatorObserver o) {
//		invoca al método addObserver del simulador.
		ps.addObserver(o);
	}
	public void run(int n) {
//		ejecuta n pasos del simulador sin escribir nada en consola.
		for(int i = 0;i<n;++i) {
			ps.advance();
		}
	}
	public Factory<GravityLaws> getGravityLawsFactory() {
//		devuelve la factoría de leyes de gravedad.
		return fgl;
	}	
	public void setGravityLaws(JSONObject info) {
//		usa la factoría de leyes de gravedad actual para crear un nuevo objeto de 
//		tipo GravityLaws a partir de info y cambia las leyes de la gravedad del simulador por él.
		ps.setGravityLaws(fgl.createInstance(info));
	}
}
