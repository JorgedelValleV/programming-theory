package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PhysicsSimulator {
	private double timePerMove;
	private double actTime;
	private GravityLaws gravLaws;
	private List<Body> bodies;
	private List<SimulatorObserver> lista;
	
	public PhysicsSimulator(GravityLaws gl,double d) throws  IllegalArgumentException{
		if(d<=0)
			throw new IllegalArgumentException("Tiempo de paso de simulacion invalido");
		timePerMove = d;
		if(gl==null)
			throw new IllegalArgumentException("Ley de gravedad no valida");
		gravLaws = gl;
		actTime= 0.0;
		bodies= new ArrayList<Body>();
		lista= new ArrayList<SimulatorObserver>();
	}
	public void addBody(Body b) throws IllegalArgumentException{
		for(Body bo: bodies) {
			if(bo.equals(b))//bo.getId().equals(b.getId())
				throw new IllegalArgumentException("Error al intentar añadir dos veces el mismo objeto. Coincidencia de id");
		}
		bodies.add(b);
		for(SimulatorObserver o: lista) {
			List<Body> bodiesRo=Collections.unmodifiableList(bodies);
			o.onBodyAdded(bodiesRo,b);
		}
	}
	public void advance() {
		gravLaws.apply(bodies);
		for(Body b: bodies) {
			b.move(timePerMove);
		}
		actTime += timePerMove;
		for(SimulatorObserver o: lista) {
			List<Body> bodiesRo=Collections.unmodifiableList(bodies);
			o.onAdvance(bodiesRo,actTime);
		}
	}
	public String toString() {
		StringBuilder sb=new StringBuilder();
		sb.append("{ \"time\": ").append(actTime).append(", \"bodies\": [ ");
		int cont=1;
		for(Body b: bodies) {
			sb.append(b.toString());
			if(cont!=bodies.size())sb.append(", ");
			++cont;
		}
		sb.append(" ]");
		return sb.toString();
	}
	
	public void reset() {
		actTime=0;
		bodies.clear();
		// vacía la lista de cuerpos y pone el tiempo a 0,0.
		for(SimulatorObserver o: lista) {
			List<Body> bodiesRo=Collections.unmodifiableList(bodies);
			o.onReset(bodiesRo,actTime,timePerMove, gravLaws.toString());
		}
	}
	public void setDeltaTime(double dt) throws  IllegalArgumentException{
		if(dt<=0)
			throw new IllegalArgumentException("Tiempo de paso de simulacion invalido");
		timePerMove=dt;
//		cambia el tiempo real por paso (delta-time de aquí
//		en adelante) a dt. Si dt tiene un valor no válido lanza una excepción de tipo IllegalArgumentException.
		for(SimulatorObserver o: lista) {
			o.onDeltaTimeChanged(timePerMove);
		}
	}
	public void setGravityLaws(GravityLaws gravityLaws) {
		if(gravityLaws==null)
			throw new IllegalArgumentException("Ley de gravedad no valida");
		gravLaws = gravityLaws;
//		cambia las leyes de gravedad del simulador a gravityLaws. 
//		Lanza una IllegalArgumentException si el valor no es válido, es decir, si es null.
		for(SimulatorObserver o: lista) {
			o.onGravityLawChanged(gravLaws.toString());
		}
	}
	public void addObserver(SimulatorObserver o) {
		//añade o a la lista de observadores, si es que no está ya en ella
		if(!lista.contains(o)) {
			// tira del equals al comparar.
			lista.add(o);
			List<Body> bodiesRo=Collections.unmodifiableList(bodies);// cada vez que pida la vista un array, hacer invariante-solo lectura.
			o.onRegister(bodiesRo,actTime,timePerMove, gravLaws.toString());
		}
		
	}
}
