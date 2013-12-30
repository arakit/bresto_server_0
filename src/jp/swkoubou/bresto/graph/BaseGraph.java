package jp.swkoubou.bresto.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class BaseGraph implements IGraph{

//	public interface UpdateListener{
//
//	}
//
//
//	private UpdateListner mUpdateListner;
//
//
//	private void setOnUpdateListener(UpdateListner lis){
//		mUpdateListner = lis;
//	}


	private HashMap<Integer, Node> mNodes = new HashMap<Integer, Node>();
	private ArrayList<Edge> mEdges = new ArrayList<Edge>();

	@Override
	public void addNode(Node node){
		mNodes.put(node.id, node);
	}
	@Override
	public void addEdge(Edge edge) {
		mEdges.add(edge);
	}

	public void start(){

	}

	public void stop(){

	}

	public void step(){

	}



	@Override
	public List<Node> getUpdateNode() {
		return null;
	}
	@Override
	public List<Edge> getUpdateEdge() {
		return null;
	}


}
