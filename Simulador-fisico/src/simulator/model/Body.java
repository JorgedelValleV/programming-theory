package simulator.model;

import simulator.misc.Vector;

public class Body {
	//PREGUNTARLA POR VECTORES DE DOS DIMENSIONES
	private String id;
	private Vector velocity;
//	private static final double[] cero ={0.0, 0.0};
//	private Vector acceleration = new Vector(cero);
	private Vector acceleration = new Vector(2);
	private Vector position;
	protected double mass;
	
	public Body(String s, Vector u, Vector v, Vector w, double m){
		id = s;
		position = u;
		velocity = v;
		acceleration = w;
		mass = m;
	}
	public String getId() {
		return id;
	}
	public Vector getVelocity() {
		return velocity;
	}
	public Vector getAcceleration() {
		return acceleration;
	}
	public Vector getPosition() {
		return position;
	}
	public double getMass() {
		return mass;
	}
	
	void setVelocity(Vector velocity) {
		this.velocity=new Vector(velocity);
	}
	void setAcceleration(Vector acceleration) {
		this.acceleration=new Vector(acceleration);
	}
	void setPosition(Vector position) {
		this.position=new Vector(position);
	}
	
	void move(double t){
		Vector nuevaPos = position.plus(velocity.scale(t)).plus(acceleration.scale(0.5*t*t));
		Vector nuevaVel = velocity.plus(acceleration.scale(t));
		setPosition(nuevaPos);
		setVelocity(nuevaVel);
	}
	public String toString(){
		StringBuilder sb=new StringBuilder();
		//HACER FORMATO DE JSON.
		sb.append("{  \"id\": \"").append(id).append("\", \"mass\": ").append(mass).append(", \"pos\": ").append(position);
		sb.append(", \"vel\": ").append(velocity).append(", \"acc\": ").append(acceleration).append(" }");
		return sb.toString();
	}
	public boolean equals (Object obj)
	{
	if (this == obj) return true;
	if (obj == null) return false;
	if (this.getClass() != obj.getClass()) return false;
	//en este punto sabemos que obj es de la clase MiClase y no es null
	Body miclase = (Body) obj;
	if(!miclase.id.equals(this.id))return false;
	return true;
	}
}
