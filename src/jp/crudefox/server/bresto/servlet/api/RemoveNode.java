package jp.crudefox.server.bresto.servlet.api;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.crudefox.server.bresto.Const;
import jp.crudefox.server.bresto.db.DBKeywordsRelationTable;
import jp.crudefox.server.bresto.db.DBKeywordsRelationTable.KeywordsRelationRow;
import jp.crudefox.server.bresto.db.DBkeywordsTable;
import jp.crudefox.server.bresto.db.DBkeywordsTable.KeywordsRow;
import jp.crudefox.server.bresto.util.CFServletParams;
import jp.crudefox.server.bresto.util.CFUtil;
import jp.crudefox.server.bresto.util.TextUtil;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * Servlet implementation class Login
 */
@WebServlet(
		description = "プロジェクト作成",
		urlPatterns = { "/api/remove_node" },
		initParams = {
				//@WebInitParam(name = "sid", value = "")
				@WebInitParam(name = "kid", value = "")
		})
public class RemoveNode extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public RemoveNode() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		doProc(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		doProc(request, response);

	}

	private void doProc(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {



		Connection cn = null;
		CFUtil.initMySQLDriver();

		response.setContentType("application/json; charset=utf-8");
        PrintWriter pw = response.getWriter();

		try{

			CFServletParams params = new CFServletParams(this, request, response, new File(Const.DEFAULT_UPFILES_NAME));
//			String sid = CFUtil.getParam(request,"sid");
//			String project_id = CFUtil.getParam(request,"project_id");
			String _kid = params.getStringParam("kid");

//			if(TextUtil.isEmpty(sid)) throw new Exception("non sid");
//			if(TextUtil.isEmpty(project_id)) throw new Exception("non project id.");
//			if(TextUtil.isEmpty(parent_kid)) throw new Exception("non project name");
			if(TextUtil.isEmpty(_kid)) throw new Exception("non kid.");

			int kid;
			kid = Integer.parseInt(_kid);
			if( kid < 0 ) throw new Exception("can not kid < 0");

			String project_id, user_id;

			HttpSession ses = request.getSession();
			project_id = (String) ses.getAttribute(Const.SES_PROJECT_ID);
			user_id = (String) ses.getAttribute(Const.SES_USER_ID);

			if(TextUtil.isEmpty(project_id))  throw new Exception("not select project.");
			if(TextUtil.isEmpty(user_id))  throw new Exception("not login.");

	         //データベースへの接続
	         cn = Const.getDefaultDBConnection();
	         cn.setAutoCommit(false);

//	         DBProjectTable db_pro = new DBProjectTable(cn);
//	         ProjectRow pr = db_pro.getById(project_id);
//	         if(pr==null) throw new Exception("not exist project.");

	         DBkeywordsTable db_k = new DBkeywordsTable(cn);

	         KeywordsRow rk = db_k.getById(project_id, kid);
	         if(rk==null) throw new Exception("not exist keyword.");

	         DBKeywordsRelationTable db_kr = new DBKeywordsRelationTable(cn);

	         List<KeywordsRelationRow> krr_list = db_kr.getByProjectId(project_id);
	         if(krr_list==null) throw new Exception("failed get edge list.");

	         for(KeywordsRelationRow e : krr_list){
	        	 if( e.kid1 == kid ) throw new Exception("has child.");
	         }

	         for(KeywordsRelationRow e : krr_list){
	        	 if( e.kid2 == kid ){
	        		if( !db_kr.delete(e) ) throw new Exception("failed remove edge.");
	        	 }
	         }

	         if( !db_k.delete(rk.kid, rk.project_id) ) throw new Exception("failed remove node.");


	         SocketNodeEdge nd = SocketNodeEdge.getInstance();
	         if(nd!=null){
		         System.out.println("removeNodeしたい.");

//		         System.out.println("addNodeします.");
//		         nd.addNodeAndEdge(project_id, kr, krr);
//		         System.out.println("addNodeしました。.");
	         }

	         //接続のクローズ
	         cn.commit(); cn.close(); cn = null;

	         //結果
	         ObjectMapper om = new ObjectMapper();
	         om.configure(SerializationFeature.INDENT_OUTPUT  , true);

	         LinkedHashMap<String, Object> b = new LinkedHashMap<String, Object>();
	         LinkedHashMap<String, Object> b_data = new LinkedHashMap<String, Object>();

	         b_data.put("kid", rk.kid);
	         b_data.put("keyword", rk.keyword);

	         b.put("result", "OK");
	         b.put("data", b_data);

	         String json = om.writeValueAsString(b);

	         response.setStatus(HttpServletResponse.SC_OK);
	         pw.write(json);


	     }
	     catch(Exception e){
	        e.printStackTrace();

	         ObjectMapper om = new ObjectMapper();
	         LinkedHashMap<String, Object> b = new LinkedHashMap<String, Object>();
	         b.put("result", "FAILED");
	         b.put("info", e.getMessage() );

	         String json = om.writeValueAsString(b);
	         
	         response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	         pw.write(json);
	     }


		//データベースと切断
		if(cn!=null){ try { if( !cn.isClosed() ) cn.close(); cn = null;} catch (SQLException e) { e.printStackTrace();}}

	}






}

