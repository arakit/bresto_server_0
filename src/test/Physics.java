package test;

import java.awt.geom.Point2D;
import java.util.ArrayList;


public class Physics extends Object{
	public ArrayList<Particle> activeParticles; // TODO: private
	public ArrayList<Spring> activeSprings; // TODO: private
	ArrayList<Particle> freeParticles;

	ArrayList<Particle> particles_;
	ArrayList<Spring> spring_;

	Energy energy;

	public double speedLimit;

	public double stiffness;	//����
	public double repulsion;	//����
	public double friction;		//���C
	public double deltaTime_;
	public boolean gravity;		//�d�S�_
	public double	theta;		//�p�x
	public double 	w, h;		// �L�����o�X�̑傫��

	public Physics() {
		this.activeParticles = new ArrayList<Particle>();
		this.activeSprings	= new ArrayList<Spring>();
		this.freeParticles 	= new ArrayList<Particle>();
		this.particles_		= new ArrayList<Particle>();
		this.spring_ 		= new ArrayList<Spring>();
		this.energy 		= new Energy();

		this.speedLimit 	= 1000;
		this.deltaTime_ 	= 0.02;
		this.stiffness		= 1000;
		this.repulsion		= 600;
		this.friction		= 0.3;
		this.gravity		= true;
		this.theta			= 0.4;

		this.w 				= 512;
		this.h				= 512;
	}

	public void initWithDeltaTime(double deltaTime, double stiffness_, double repulsion_, double friction_, double w_, double h_) {
		this.deltaTime_ = deltaTime;
		this.stiffness = stiffness_;
		this.repulsion = repulsion_;
		this.friction = friction_;
		this.w = w_;
		this.h = h_;
	}

	// ���q�ǉ�
	public void addParticle(Particle particle) {
		if(particle == null) {
			return;
		}

		particle.connections = 0;
		activeParticles.add(particle);
		particles_.add(particle);
	}

	// �Ȃ����ǉ�
	public void addSpring(Spring spring) {
		if(spring == null) {
			return;
		}

		activeSprings.add(spring);
		spring_.add(spring);

		// �q�����Ă����
		spring.point1().connections++;
		spring.point2().connections++;
	}

	// �v�Z�@motion�̒l�ɂ���Čv�Z���~�܂�
	public boolean update() {
		tendParticles();
		eulerIntegrator(deltaTime_);

		// double motion = (energy.mean + energy.max) / 2;
		double motion = (this.energy.max - this.energy.mean) / 2;

		if(motion < 0.05) {
			// System.out.println("stop now.");
			return false;
		} else {
			return true;
		}
	}

	public void tendParticles() {
		Point2D.Double bottomright = new Point2D.Double(0, 0);
		Point2D.Double topleft = new Point2D.Double(0, 0);
		boolean firstparticle = true;

		for(Particle particle : activeParticles) {
			if(particle.tempMass != 0.0) {
				if(Math.abs(particle.mass - particle.tempMass) < 1.0) {
					particle.mass = particle.tempMass;
					particle.tempMass = 0.0;
				} else {
					particle.mass *= 0.98;
				}
			}

			// 1�ڐ��肩�玟�̑��x��0��
			particle.velocity = new Point2D.Double(0, 0);

			// ���E���X�V
			Point2D.Double pt = particle.position;

			if(firstparticle) {
				bottomright   = (Point2D.Double)pt.clone();
				topleft 	  = (Point2D.Double)pt.clone();
				firstparticle = false;
			}

			if(pt.x > bottomright.x) bottomright.x = pt.x;
			if(pt.y > bottomright.y) bottomright.y = pt.y;
			if(pt.x < topleft.x)	 topleft.x = pt.x;
			if(pt.y < topleft.y)	 topleft.y = pt.y;
		}
	}

	// ���낢��Ȍv�Z
	public void eulerIntegrator(double deltaTime) {
		if(deltaTime <= 0.0) {
			return;
		}

		if(repulsion > 0.0) {
			applyBruteForceRepulsion();
		}

		if(stiffness > 0.0) {
			applySprings();
		}
		applyCenterDrift();
		// �d�͂�����Ƃ��Ɍv�Z
		if(gravity) {
			applyCenterGravity();
		}
		updateVelocity(deltaTime);
		updatePosition(deltaTime);
	}

	// �˗͌v�Z
	public void applyBruteForceRepulsion() {
		Support support = new Support();
		for(Particle subject : activeParticles) {
			for(Particle object : activeParticles) {
				if(subject != object){
					Point2D.Double d = support.PointSubtract(subject.position, object.position);
					double distance = Math.max( 1.0, support.PointMagnitude(d));// ��_�Ԃ̋���
					Point2D.Double direction = (support.PointMagnitude(d) > 0.0) ? d : support.PointNormalize( support.PointRandom(1.0) );

					/*�@�e�|�C���g�ɗ͂�������
					 * �~�̑傫���ɂ���ė͂������
					 * ��̓_�͂��ꂼ��t���֗͂���������
					 */
					Point2D.Double force = support.PointDivide(
								support.PointScale( direction, (repulsion * object.mass * (object.circleradius / 50) * 5) ) ,
									(distance * distance * 0.5 ) );

					subject.applyForce(force);

					force = support.PointDivide(
								support.PointScale( direction, (repulsion * subject.mass * (subject.circleradius/ 50) * 5) ) ,
									(distance * distance * -0.5 ) );

					object.applyForce(force);
				}
			}
		}
	}

	/* ��͌v�Z�i�G�b�W�j
	 * �m�[�h�����q�����Ă�����̂͂����Ōv�Z����A�݂��Ɉ���
	 */
	public void applySprings() {
		Support support = new Support();
		for(Spring spring : activeSprings) {

			Point2D.Double d = support.PointSubtract(spring.target.position, spring.source.position);
			double displacement = spring.length - support.PointMagnitude(d);// ��_�Ԃ̋���
			Point2D.Double direction = support.PointNormalize( support.PointMagnitude(d) > 0.0 ? d : support.PointRandom(1.0));

			spring.point1().applyForce(support.PointScale(direction, spring.stiffness * displacement * -0.5));
			spring.point2().applyForce(support.PointScale(direction, spring.stiffness * displacement * 0.5));
		}
	}

	public void applyCenterDrift() {
		// �V�X�e�����̑S�Ă̗��q�̏d�S�������āA�V�t�g����
		int numParticles = 0;
		Point2D.Double centroid = new Point2D.Double(0, 0);
		Support su = new Support();

		for(Particle particle : activeParticles) {
			centroid = su.PointAdd(centroid, particle.position);
			numParticles++;
		}

		if(numParticles == 0) {
			return;
		}

		Point2D.Double correction = su.PointDivide(centroid, -numParticles);

		for(Particle particle : activeParticles) {
			particle.applyForce(correction);
		}
	}

	// �d��
	public void applyCenterGravity() {
		Support su = new Support();
		for(Particle particle : activeParticles) {
			Point2D.Double direction = su.PointScale(particle.position, -1.0);

			// �L�����o�X�̊O���ɋ߂Â��قǁA�d�͂̋������傫���Ȃ�
			if(particle.position.x > w + 10 || particle.position.x < -10 - w || particle.position.y > h + 10 || particle.position.y < -10 - h){
				particle.applyForce(su.PointScale(direction, (repulsion / 50.0)));
			} else {
				particle.applyForce(su.PointScale(direction, (repulsion / 120.0)));
			}

		}
	}

	// ���x�X�V
	public void updateVelocity(double timestep) {
		Support su = new Support();
		if(timestep <= 0.0) {
			return;
		}

		for(Particle particle : activeParticles) {
			if(particle.fixed) {
				particle.velocity = new Point2D.Double(0, 0);
				particle.force = new Point2D.Double(0, 0);
				continue;
			}

			particle.velocity = su.PointScale( su.PointAdd(particle.velocity,
								   su.PointScale(particle.force, timestep) ),
										(1.0 - friction) );

			// �͂�0�ɂ���
			particle.force = new Point2D.Double(0, 0);

			// �����Ȃ肷���Ȃ��悤�ɒ������s��
			double speed = su.PointMagnitude(particle.velocity);
			if(speed > this.speedLimit) {
				particle.velocity = su.PointDivide(particle.velocity, speed * speed);
			}
		}
	}

	// �S�Ẵm�[�h�̍��W���X�V����
	public void updatePosition(double timestep) {
		Support su = new Support();

		if(timestep <= 0.0) {
			return;
		}

		double sum = 0.0, max = 0.0, n = 0.0;
		Point2D.Double bottomright = new Point2D.Double(0, 0);
		Point2D.Double topleft = new Point2D.Double(0, 0);
		boolean firstParticle = true;

		for(Particle particle : activeParticles) {

			// �V�����ʒu�Ƀm�[�h���ړ�
			particle.position = su.PointAdd(particle.position,
									su.PointScale(particle.velocity, timestep));

			double speed = su.PointMagnitude(particle.velocity);
			double e = speed * speed;
			sum += e;
			max = Math.max(e,  max);
			n++;

			Point2D.Double pt = particle.position;

			if(firstParticle) {
				bottomright = (Point2D.Double)pt.clone();
				topleft = (Point2D.Double)pt.clone();
				firstParticle = false;
			}

			if(pt.x > bottomright.x) bottomright.x = pt.x;
			if(pt.y > bottomright.y) bottomright.y = pt.y;
			if(pt.x < topleft.x)	 topleft.x = pt.x;
			if(pt.y < topleft.y)	 topleft.y = pt.y;
		}

		energy.sum 		= sum;
		energy.max 		= max;
		energy.mean 	= sum / n;
		energy.count 	= (int) n;
	}
}
