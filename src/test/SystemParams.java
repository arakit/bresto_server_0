package test;

//import java.awt.Dimension;
//import java.awt.Graphics2D;
//import java.awt.geom.Point2D;
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
//
//import org.omg.CORBA.OBJ_ADAPTER;
//import org.omg.CORBA.PUBLIC_MEMBER;
//import java.applet.*;
//import java.awt.*;
//import java.io.Serializable;
//import java.lang.reflect.Array;

public class SystemParams extends Object{
	public double repulsion;
	public double stiffness;
	public double friction;
	public double deltaTime;
	public boolean gravity;
	public double precision;
	public double timeout;
	public double H, W;
	
	public SystemParams(){
		this.repulsion 	= 1000;
		this.stiffness 	= 600;
		this.friction	= 0.5;
		this.deltaTime	= 0.02;
		this.gravity	= true;
		this.precision	= 0.6;
		this.timeout	= 1000 / 50;
		this.H = 512; this.W = 512;
	}
}
