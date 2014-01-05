package jp.crudefox.server.bresto.servlet.api;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedHashMap;

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
		description = "ノード追加",
		urlPatterns = { "/api/add_node" },
		initParams = {
				//@WebInitParam(name = "sid", value = ""),
				@WebInitParam(name = "project_id", value = ""),
				@WebInitParam(name = "parent_kid", value = ""),
				@WebInitParam(name = "keyword", value = "")
		})
public class AddNode extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddNode() {
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

		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, DELETE, OPTIONS");
		response.setHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"));
		response.setHeader("Access-Control-Max-Age", "-1");


		Connection cn = null;
		CFUtil.initMySQLDriver();

		response.setContentType("application/json; charset=utf-8");
        PrintWriter pw = response.getWriter();

		try{

			CFServletParams params = new CFServletParams(this, request, response, new File(Const.DEFAULT_UPFILES_NAME));
			//String project_id = params.getStringParam("project_id");
			String _parent_kid = params.getStringParam("parent_kid");
			String keyword = params.getStringParam("keyword");

//			if(TextUtil.isEmpty(sid)) throw new Exception("non sid");
//			if(TextUtil.isEmpty(project_id)) throw new Exception("non project id.");
//			if(TextUtil.isEmpty(parent_kid)) throw new Exception("non project name");
			if(TextUtil.isEmpty(keyword)) throw new Exception("non keyword.");

			int parent_kid;
			if( TextUtil.isEmpty( _parent_kid ) ) parent_kid = -1;
			else parent_kid = Integer.parseInt(_parent_kid);
			if( parent_kid < -1 ) throw new Exception("can not parent_kid < -1");

			String project_id, user_id;

			HttpSession ses = request.getSession();
			project_id = (String) ses.getAttribute(Const.SES_PROJECT_ID);
			user_id = (String) ses.getAttribute(Const.SES_USER_ID);

//			project_id = "MyxLvXLIoYYLDurWxDqYPECZSZOwRXaN";
//			user_id = "chikara";

			if(TextUtil.isEmpty(project_id))  throw new Exception("not select project.");
			if(TextUtil.isEmpty(user_id))  throw new Exception("not login.");

	         //データベースへの接続
	         cn = Const.getDefaultDBConnection();
	         cn.setAutoCommit(false);

//	         DBProjectTable db_pro = new DBProjectTable(cn);
//	         ProjectRow pr = db_pro.getById(project_id);
//	         if(pr==null) throw new Exception("not exist project.");

	         DBkeywordsTable db_k = new DBkeywordsTable(cn);

	         if(parent_kid != -1){
		         KeywordsRow k1 = db_k.getById(project_id, parent_kid);
		         if(k1==null) throw new Exception("not exist k1.");
	         }

	         KeywordsRow kr = new KeywordsRow();
	         kr.project_id = project_id;
	         kr.keyword = keyword;
	         kr.x = 0; kr.y = 0;
	         kr.w = 20; kr.h = 20;
	         kr = db_k.insertByAutoIncrement(kr);

	         if(kr==null) throw new Exception("failed node insert.");

	         DBKeywordsRelationTable db_kr = new DBKeywordsRelationTable(cn);
	         KeywordsRelationRow krr = null;

	         if(parent_kid!=-1){
		         krr = new KeywordsRelationRow();
		         krr.kid1 = parent_kid;
		         krr.kid2 = kr.kid;
		         krr.modified_time = null;
		         krr.project_id = project_id;

		         if(!db_kr.insert(krr)) throw new Exception("insert edge failed.");
	         }


//	         DBSessionTable db_s = new DBSessionTable(cn);
//	         SessionRow sr = db_s.getBySessionID(sid);
//
//	         if(sr==null) throw new Exception("non login");
//
//	         DBUserTable at = new DBUserTable(cn);
//	         UserRow ur = at.getById(sr.user_id);
//
//	         DBProjectTable db_pro = new DBProjectTable(cn);
//	         ProjectRow pr = db_pro.insertNew(project_name, ur.id);
//
//	         if(pr==null) throw new Exception("can not created project.");


	         SocketNodeEdge nd = SocketNodeEdge.getInstance();
	         if(nd!=null){
//	        	 DBGoodTable g_tb = new DBGoodTable(cn);
//	        	 g_tb.getByKId(project_id, kr.kid);

//		         KeywordsRow kwr =new KeywordsRow();
//		         kwr.kid = (int)( Math.random() * Integer.MAX_VALUE );
//		         kwr.keyword = keyword;
//		         kwr.project_id = "p1";
//
//		         KeywordsRelationRow kwr_r =new KeywordsRelationRow();
//		         kwr_r.kid1 = parent_kid!=null ? parent_kid : -1;
//		         kwr_r.kid2 = kwr.kid;

		         System.out.println("addNodeします.");
		         nd.addNodeAndEdge(project_id, Const.toNode(kr, 0), Const.toEdge(krr), false);
		         System.out.println("addNodeしました。.");
	         }

	         //接続のクローズ
	         cn.commit(); cn.close(); cn = null;

	         //結果
	         ObjectMapper om = new ObjectMapper();
	         om.configure(SerializationFeature.INDENT_OUTPUT  , true);

	         LinkedHashMap<String, Object> b = new LinkedHashMap<String, Object>();
	         LinkedHashMap<String, Object> b_data = new LinkedHashMap<String, Object>();

	         b_data.put("kid", kr.kid);
	         b_data.put("keyword", kr.keyword);
	         b_data.put("parent_kid", krr!=null ? krr.kid1 : null);


	         b.put("result", "OK");
	         b.put("data", b_data);

	         String json = om.writeValueAsString(b);
	         //json = "callback(" + json + ");";

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

