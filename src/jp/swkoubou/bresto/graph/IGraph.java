package jp.swkoubou.bresto.graph;

import java.util.List;


public interface IGraph {

//	public interface UpdateListener{
//		public void onUpdateNode(int id, Node node);
//		public void onUpdateEdge(Edge edge);
//	}
//
//	private void setOnUpdateListener(UpdateListner lis);



	public void addNode(Node node);

	public void addEdge(Edge edge);

	public List<Node> getUpdateNode();

	public List<Edge> getUpdateEdge();

}
