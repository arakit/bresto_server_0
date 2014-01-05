package test.copy;

//import java.io.Serializable;

public class Spring extends Edge{
	public double stiffness;// �΂�
	
	public Spring() {
		setStiffness(300.0);
	}
	
	public Particle point1(){
		return (Particle) this.source;// �e�m�[�h
	}
	
	public Particle point2() {
		return (Particle) this.target;// �q�m�[�h
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
