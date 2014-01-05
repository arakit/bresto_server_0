package test;

//import java.io.Serializable;

public class Spring extends Edge{
	public double stiffness;// ばね
	
	public Spring() {
		setStiffness(300.0);
	}
	
	public Particle point1(){
		return (Particle) this.source;// 親ノード
	}
	
	public Particle point2() {
		return (Particle) this.target;// 子ノード
	}
	
	public double distanceToParticle(Particle particle) {
		return this.distanceToNode(particle);
	}

	public double getStiffness_() {
		return stiffness;
	}

	public void setStiffness(double stiffness_) {
		this.stiffness = stiffness_;
	}
}
