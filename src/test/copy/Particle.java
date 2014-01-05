package test.copy;

//import java.awt.Dimension;
//import java.awt.Graphics2D;
import java.awt.geom.Point2D;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Random;
//import javax.swing.GrayFilter;
//import javax.swing.JComponent;
//import javax.swing.JFrame;
//import javax.swing.JPanel;
//import javax.swing.SwingUtilities;
//import javax.swing.plaf.LabelUI;
//import org.omg.CORBA.PUBLIC_MEMBER;
//import java.applet.*;
//import java.awt.*;
//import java.io.Serializable;
//import java.lang.reflect.Array;

public class Particle extends Node{

	public Point2D.Double velocity;// ���x
	public Point2D.Double force;// ��
	public double tempMass;
	public Integer connections;// �q�����Ă����

	public Particle(int index) {
		super(index);
		this.velocity = new Point2D.Double(0, 0);
		this.force = new Point2D.Double(0, 0);
		this.tempMass = 0.0;
		this.connections = 0;
	}

	public void initWithVelocity(Point2D.Double velocity_, Point2D.Double force_, double tempMass_) {
		this.velocity = velocity_;
		this.force = force_;
		this.tempMass = tempMass_;
	}

	// �͂���������
	public void applyForce(Point2D.Double force) {
		Support su = new Support();
		this.force = su.PointAdd(this.force, su.PointDivide(force, this.mass));
	}
}
