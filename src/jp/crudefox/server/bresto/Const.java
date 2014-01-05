package jp.crudefox.server.bresto;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import jp.crudefox.server.bresto.db.DBKeywordsRelationTable.KeywordsRelationRow;
import jp.crudefox.server.bresto.db.DBkeywordsTable.KeywordsRow;
import jp.swkoubou.bresto.graph.Edge;
import jp.swkoubou.bresto.graph.Node;

public class Const {

    public static final String DB_URL = "jdbc:mysql://localhost:3306/bresto";
    public static final String DB_USER = "bresto";
    public static final String DB_PASSWORD = "Krx3wwpeZuaYJtW5";



    public static final Connection getDefaultDBConnection() throws SQLException{
    	return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }



	public static final boolean USE_PROXY = true;

	public static final String DEFAULT_UPFILES_NAME = "upfiles";





	public static final Node toNode(KeywordsRow kr, int like){

		if(kr==null) return null;

		Node node = new Node();
		node.id = kr.kid;
		node.keyword = kr.keyword;
		node.x = kr.x;
		node.y = kr.y;
		node.scale_x = kr.w;
		node.scale_y = kr.h;
		node.like = like;

		return node;
	}

	public static final Edge toEdge(KeywordsRelationRow krr){

		if(krr==null) return null;

		Edge edge = new Edge();
		edge.pid = krr.kid1;
		edge.cid = krr.kid2;

		return edge;
	}



	public static final String SES_PROJECT_ID  = "project_id";
	public static final String SES_USER_ID  = "user_id";

	public static final String REQ_SELECT_PAGE  = "select_page";
	public static final String REQ_PROJECT_LIST  = "project_list";

}
