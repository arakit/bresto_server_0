package test;

public class Kernel{
	public Physics	physics;
	public Energy	lastEnergy;
	public double	H, W;
	
	public Kernel() {
		this.physics = new Physics();
		this.lastEnergy = new Energy();
		this.H = 512; this.W = 512;
		this.physics.initWithDeltaTime(0.02, 1000.0, 600.0, 0.5, this.W, this.H);
	}
	
	public void initCanvas(double H_, double W_){
		this.H = H_; this.W = W_;
		this.physics.initWithDeltaTime(0.02, 1000.0, 600.0, 0.5, this.W, this.H);
	}
	
	// ?p?[?e?B?N?????
	public void addParticle(Particle particle) {
		if(particle == null) return;
		this.physics.addParticle(particle);
	}
	
	//?@?X?v?????O???
	public void addSpring(Spring spring) {
		if(spring == null) return;
		
		this.physics.addSpring(spring);
	}

}
