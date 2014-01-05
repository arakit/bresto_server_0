package test;

import java.util.HashMap;
import java.util.HashSet;


public class Systems extends Kernel{
	public double viewTweenStep;
	
	public SystemState	state;
	public SystemParams	parameters;
	
	public Systems() {
		this.state 			= new SystemState();
		this.parameters 	= new SystemParams();
		this.viewTweenStep  = 0.04;
	}
	
	public void initWithState(SystemState state_, SystemParams parameters_) {
		this.state 		= state_;
		this.parameters = parameters_;
	}
	
	public Node addNode(String name, Node node_) {
		if(name == null) return null;
		
		Node priorNode = this.state.getNameObjectForKey(name);
		if(priorNode != null) {
			priorNode = node_;
			return priorNode;
		} else {
			Particle node = new Particle();
			node.initWithName(name, node_.mass, node_.position, node_.fixed, node.like);
			
			this.state.setNamesObject(node, name);
			this.state.setNodeObject(node, node.index);
			
			this.addParticle(node);
			
			return node;
		}
	}
	
	public Node getNode(String nodeName) {
		if(nodeName == null) return null;
		
		return this.state.getNameObjectForKey(nodeName);
	}
	
	public Edge addEdgeFromNode(String source, String target, Edge edge_) {
		
		Support su = new Support();
		if(source == null || target == null) return null;
		
		Node sourceNode = this.getNode(source);
		Node targetNode = this.getNode(target);
		
		if(sourceNode == null) {
			
			sourceNode = this.addNode(source, null);
			
			if(targetNode != null) {
				sourceNode.position = su.PointNearPoint(targetNode.position, 1.0);
			}
		}
		
		
		
		if(targetNode == null) {
			
			targetNode = this.addNode(target, null);
			
			if(sourceNode != null) {
				targetNode.position = su.PointNearPoint(sourceNode.position, 1.0);
			}
		}
		
		if(sourceNode == null || targetNode == null) return null;
		
		Integer src = sourceNode.index;
		Integer dst = targetNode.index;
		
		HashMap<Integer, Edge> from = this.state.getAdjacencyObjectForKey(src);
		
		if(from == null) {
			
			from = new HashMap<Integer, Edge>(32);
			this.state.setAdjacencyObject(from, src);
		}
		
		Edge to = from.get(dst);
		if(to != null) {
			to = edge_;
			return to;
		}
		Spring edge = new Spring();
		edge.initWithSource(sourceNode, targetNode, edge_.length);
		
		this.state.setEdgesObject(edge, edge.index);
		
		from.put(dst, edge);
		
		this.addSpring(edge);
		
		return edge;
	}

	
	public HashSet<Edge> getEdgesFromNode(String source, String target) {
		if(source == null || target == null) return new HashSet<Edge>();
		
		Node aNode1 = this.getNode(source);
		Node aNode2 = this.getNode(target);
		
		if(aNode1 == null || aNode2 == null) return new HashSet<Edge>();
		
		Integer src = aNode1.index;
		Integer dst = aNode2.index;
		
		HashMap<Integer, Edge> from = this.state.getAdjacencyObjectForKey(src);
		if(from == null) {
			return new HashSet<Edge>();
		}
		
		Edge to = from.get(dst);
		if(to == null) {
			return new HashSet<Edge>();
		}
		
		HashSet<Edge> set = new HashSet<Edge>();
		set.add(to);
		return set;
	}
	
	public HashSet<Edge> getEdgesFromNode(String node) {
		if(node == null) return new HashSet<Edge>();
		
		Node aNode = this.getNode(node);
		if(aNode == null) return new HashSet<Edge>();
		
		Integer src = aNode.index;
		
		HashMap<Integer, Edge> from = this.state.getAdjacencyObjectForKey(src);
		if(from == null) {
			@SuppressWarnings("null")
			HashSet<Edge> res = (HashSet<Edge>) from.values();
			return res;
		}
		
		return new HashSet<Edge>();
	}
	
	public HashSet<Edge> getEdgeToNode(String node) {
		
		if(node == null) return new HashSet<Edge>();
		
		Node aNode = this.getNode(node);
		if(aNode == null) return new HashSet<Edge>();;
		
		HashSet<Edge> nodeEdges = new HashSet<Edge>();;
		for(Edge edge : this.state.edges.values()) {
			if(edge.target == aNode) {
				nodeEdges.add(edge);
			}
		}
		
		return nodeEdges;
	}
}
