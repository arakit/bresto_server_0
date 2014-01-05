package test;

//import java.awt.Dimension;
//import java.awt.Graphics2D;
//import java.awt.geom.Point2D;
//import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
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

public class SystemState extends Object{
	public HashMap<Integer, Node>	nodes;
	public HashMap<Integer, Edge>	edges;
	public HashMap<String, Node>	names;
	public HashMap<Integer, Object>	adjacency;
	
	public SystemState() {
		this.nodes = new HashMap<Integer, Node>(32);
		this.edges = new HashMap<Integer, Edge>(32);
		this.names = new HashMap<String, Node>(32);
		this.adjacency = new HashMap<Integer, Object>(32);
	}
	
	public Collection<Node> nodes_() {
		return nodes.values();
	}
	
	public Collection<Edge> edges_(){
		return edges.values();
	}
	
	public Collection<Object> adjacency_(){
		return adjacency.values();
	}
	
	public Collection<Node> names_(){
		return names.values();
	}
	
	public void setNodeObject(Node NodesObject, Integer key) {
		if(NodesObject == null || key == null) return;
		
		nodes.put(key, NodesObject);
	}
	
	public Node getNodesObjectForKey(Integer key) {
		if(key == null) return null;
		
		return nodes.get(key);
	}
	
	public void setEdgesObject(Edge EdgesObject, Integer key) {
		if(EdgesObject == null || key == null) return;
		
		edges.put(key, EdgesObject);
	}
	
	public Edge getEdgeObjectForKey(Integer key) {
		if(key == null) return null;
		
		return edges.get(key);
	}
	
	public void setNamesObject(Node NamesObject, String key) {
		if(NamesObject == null || key == null) return;
		
		names.put(key, NamesObject);
	}
	
	public Node getNameObjectForKey(String key) {
		if(key == null) return null;
		
		return names.get(key);
	}
	
	public void setAdjacencyObject(HashMap<Integer, Edge> AdjacencyObject, Integer key) {
		if(AdjacencyObject == null || key == null) return;

		adjacency.put(key, AdjacencyObject);
	}
	

	@SuppressWarnings("unchecked")
	public HashMap<Integer, Edge> getAdjacencyObjectForKey(Integer key) {
		if(key == null) return null;
		
		return (HashMap<Integer, Edge>) adjacency.get(key);
	}
}
