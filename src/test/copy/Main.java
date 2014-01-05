package test.copy;
//package test;
//
//
//import java.awt.Color;
//import java.awt.Graphics;
//
//import javax.swing.JFrame;
//import javax.swing.JPanel;
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
//import org.omg.CORBA.PUBLIC_MEMBER;
//import java.applet.*;
//import java.awt.*;
//import java.io.Serializable;
//import java.lang.reflect.Array;
//
//public class Main extends Systems{
//	
//	public static void main(String[] args) {
//		Systems play = new Systems();
////		for(int i = 0; i < 10; i++) {
////			Node a = new Node();
////			Point2D.Double start = new Point2D.Double(0, 0);
////			a.initWithName("0" + i, 1.0, start, false);
////			play.addNode(a.name, a);
////		}
////		
//////		for(Node n : play.state.nodes.values()) {
//////			System.out.println(n.name);
//////		}
////		
////		for(int i = 0; i < 5; i++) {
////			Edge edges = new Edge();
////			play.addEdgeFromNode("0" + i, "0" + (i + 5), edges);
////		}
////		//System.out.println(play.physics.spring_.size());
////		
//////		for(Edge e : play.state.edges.values()) {
//////			System.out.println(e.source.name + "   " + e.target.name);
//////			System.out.println(e.source.position + "   " + e.target.position);
//////		}
//		
//		play.addNode("0", (new Node()).initWithName("0", 1.0, new Point2D.Double( 10,  10), false, 1));
//		play.addNode("1", (new Node()).initWithName("1", 1.0, new Point2D.Double(-15, -15), false, 1));
//		play.addNode("2", (new Node()).initWithName("2", 1.0, new Point2D.Double(-10,  10), false, 1));
//		play.addEdgeFromNode("0", "1", new Edge());
//		play.addEdgeFromNode("1", "2", new Edge());
//		play.addEdgeFromNode("2", "0", new Edge());
//		
//		for(int i = 0; i < 1000 && play.physics.update(); i++) {
//			;
//			
////			for(Particle p : play.physics.activeParticles) {
////				System.out.println(p.name + "  " + p.position);
////			}
//		}
//		
////		for(Particle p : play.physics.activeParticles) {
////			System.out.println(p.name + "  " + p.position);
////		}
//	}
//}
